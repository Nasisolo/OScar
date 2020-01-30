package it.unive.dais.legodroid.app;


import android.graphics.Path;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.plugs.GyroSensor;
import it.unive.dais.legodroid.lib.plugs.LightSensor;
import it.unive.dais.legodroid.lib.plugs.TachoMotor;
import it.unive.dais.legodroid.lib.plugs.TouchSensor;
import it.unive.dais.legodroid.lib.plugs.UltrasonicSensor;
import it.unive.dais.legodroid.lib.util.Prelude;
import it.unive.dais.legodroid.lib.util.ThrowingConsumer;

public class Robot {

    //private final static String BRICK_NAME = "OScar";

    private String brickName;
    private EV3.Api api;
    private boolean busy = false;

    // output ports
    private final static EV3.OutputPort RIGHT_MOTOR_PORT = EV3.OutputPort.A;    //out. port for right motor
    private final static EV3.OutputPort LEFT_MOTOR_PORT = EV3.OutputPort.B;     //out. port for left motor
    private final static EV3.OutputPort EXTRA_MOTOR_PORT =  EV3.OutputPort.C;   //out. port unused
    private final static EV3.OutputPort ARM_MOTOR_PORT = EV3.OutputPort.D;      //out. port for mech. arm

    // output ports
    private final static EV3.InputPort LIGHT_SENSOR_PORT = EV3.InputPort._3;
    private final static EV3.InputPort ULTRASONIC_SENSOR_PORT = EV3.InputPort._2;
    private final static EV3.InputPort TOUCH_SENSOR_PORT = EV3.InputPort._1;
    private final static EV3.InputPort GYRO_SENSOR_PORT = EV3.InputPort._4;


    /* speed parameters */
    private final static int SPEED_FORWARD = 40;
    private final static int STEP1_FORWARD = 100;
    private final static int STEP2_FORWARD = 485;
    private final static int STEP3_FORWARD = 100;

    /* rotation parameters */
    private final static int SPEED_ROTATION = 40;
    private final static int STEP1_ROTATION = 100;
    private final static int STEP2_ROTATION = 135;
    private final static int STEP3_ROTATION = 100;


    private final static int STEP_PICKUP_RELEASE = 2100;
    private final static int SPEED_PICKUP_RELEASE = 100;


    private LightSensor lightSensor;
    private UltrasonicSensor ultraSensor;
    private TouchSensor touchSensor;
    private GyroSensor gyroSensor;

    private WheelMotor right;
    private WheelMotor left;
    private ArmMotor arm;

    public enum Direction{
        NORTH, EAST, SOUTH, WEST;
    }


    public Robot(EV3.Api api, String brickName) {

        // get sensors
        this.lightSensor = api.getLightSensor(LIGHT_SENSOR_PORT);
        this.ultraSensor = api.getUltrasonicSensor(ULTRASONIC_SENSOR_PORT);
        this.touchSensor = api.getTouchSensor(TOUCH_SENSOR_PORT);
        this.gyroSensor = api.getGyroSensor(GYRO_SENSOR_PORT);

        // Motors initialization
        this.right = new WheelMotor(api, RIGHT_MOTOR_PORT);
        this.left = new WheelMotor(api, LEFT_MOTOR_PORT);
        this.arm = new ArmMotor(api, ARM_MOTOR_PORT);

        this.brickName = brickName;

    }





    // GETTER

    public TachoMotor getLeftMotor(){
        return left.getMotor();
    }
    public TachoMotor getRightMotor(){
        return right.getMotor();
    }
    public TachoMotor getArmMotor(){
        return arm.getMotor();
    }


    public void applyMotor(@NonNull ThrowingConsumer<TachoMotor, Throwable> f) {
        if (left != null && right != null && arm != null) {
            Prelude.trap(() -> f.call(getLeftMotor()));
            Prelude.trap(() -> f.call(getRightMotor()));
            Prelude.trap(() -> f.call(getArmMotor()));
        }
    }

    public void waitUntilReady() throws IOException, ExecutionException, InterruptedException {
        right.getMotor().waitUntilReady();
        left.getMotor().waitUntilReady();
        arm.getMotor().waitUntilReady();
    }


    /* --------------- STATUS SENSOR UPDATER --------------- */

    private Future<Float> getGyroAngle() throws IOException{
        return gyroSensor.getAngle();
    }
    public Float getGyroAngleValue() throws IOException, ExecutionException, InterruptedException {
        return this.getGyroAngle().get();
    }

    private Future<Short> getLightAmbient() throws IOException{
        return lightSensor.getAmbient();
    }
    public Short getLightAmbientValue() throws IOException, ExecutionException, InterruptedException{
        return this.getLightAmbient().get();
    }

    private Future<Short> getLightReflection() throws IOException{
        return lightSensor.getReflected();
    }
    public Short getLightReflectionValue() throws IOException, ExecutionException, InterruptedException{
        return this.getLightReflection().get();
    }

    private Future<LightSensor.Color> getLightColor() throws IOException{
        return lightSensor.getColor();
    }
    public LightSensor.Color getLightColorValue() throws IOException, ExecutionException, InterruptedException{
        return this.getLightColor().get();
    }

    private Future<Float> getUltraDistance() throws IOException{
        return ultraSensor.getDistance();
    }

    public Float getUltraDistanceValue() throws IOException, ExecutionException, InterruptedException{
        return this.getUltraDistance().get();
    }



    /* --------------- MOVING METHODS --------------- */

    public void moveForward() throws IOException {
        right.moveStepWheel(SPEED_FORWARD, STEP1_FORWARD-10, STEP2_FORWARD, STEP3_FORWARD, true);
        left.moveStepWheel(SPEED_FORWARD, STEP1_FORWARD, STEP2_FORWARD, STEP3_FORWARD-10, true);
        right.waitWheelCompletion();
        left.waitWheelCompletion();
    }

    public void moveBackward() throws IOException {
        right.moveStepWheel(-SPEED_FORWARD, STEP1_FORWARD, STEP2_FORWARD, STEP3_FORWARD, true);
        left.moveStepWheel(-SPEED_FORWARD, STEP1_FORWARD, STEP2_FORWARD, STEP3_FORWARD, true);
        right.waitWheelCompletion();
        left.waitWheelCompletion();
    }

    public void moveLeft() throws IOException{
        left.moveStepWheel(-SPEED_ROTATION, STEP1_ROTATION, STEP2_ROTATION, STEP3_ROTATION, true);
        right.moveStepWheel(SPEED_ROTATION, STEP1_ROTATION, STEP2_ROTATION, STEP3_ROTATION, true);
        left.waitWheelCompletion();
        right.waitWheelCompletion();
    }

    public void moveRight1() throws IOException{

        right.moveStepWheel(SPEED_FORWARD, 78-10, 40, 78,true);
        left.moveStepWheel(SPEED_FORWARD, 78, 40, 78-10, true);
        right.waitWheelCompletion();
        left.waitWheelCompletion();


        left.moveStepWheel(SPEED_ROTATION, STEP1_ROTATION, STEP2_ROTATION, STEP3_ROTATION, true);
        right.moveStepWheel(-SPEED_ROTATION, STEP1_ROTATION, STEP2_ROTATION, STEP3_ROTATION, true);
        left.waitWheelCompletion();
        right.waitWheelCompletion();

        right.moveStepWheel(-SPEED_FORWARD+10, 0, 210, 0,true);
        left.moveStepWheel(-SPEED_FORWARD+10, 0, 210, 0, true);
        left.waitWheelCompletion();
        right.waitWheelCompletion();
    }

    public void moveRight() throws IOException{

        right.moveStepWheel(-20, 0, 50, 0,true);
        left.moveStepWheel(-20, 0, 50, 0, true);
        right.waitWheelCompletion();
        left.waitWheelCompletion();


        //right.moveStepWheel(-SPEED_ROTATION, STEP1_ROTATION, STEP2_ROTATION, STEP3_ROTATION, true);
        left.moveStepWheel(SPEED_ROTATION, STEP1_ROTATION, 465, STEP3_ROTATION, true);
        //right.waitWheelCompletion();
        left.waitWheelCompletion();

        right.moveStepWheel(-SPEED_FORWARD+10, 0, 365, 0,true);
        left.moveStepWheel(-SPEED_FORWARD+10, 0, 365, 0, true);
        right.waitWheelCompletion();
        left.waitWheelCompletion();


    }

    public void pickup() throws IOException{
        arm.pickup(SPEED_PICKUP_RELEASE, STEP_PICKUP_RELEASE);
    }

    public void release() throws IOException{
        arm.release(SPEED_PICKUP_RELEASE, STEP_PICKUP_RELEASE);
    }

    /* ------------------ NEARBY METHODS ----------------------*/
}
