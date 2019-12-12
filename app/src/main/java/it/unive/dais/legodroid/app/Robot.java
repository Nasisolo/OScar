package it.unive.dais.legodroid.app;

import java.io.IOException;

import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.comm.BluetoothConnection;
import it.unive.dais.legodroid.lib.plugs.GyroSensor;
import it.unive.dais.legodroid.lib.plugs.LightSensor;
import it.unive.dais.legodroid.lib.plugs.TachoMotor;
import it.unive.dais.legodroid.lib.plugs.TouchSensor;
import it.unive.dais.legodroid.lib.plugs.UltrasonicSensor;

public class Robot {

    //private final static String BRICK_NAME = "OScar";

    private String brickName;

    // output ports
    private final static EV3.OutputPort RIGHT_MOTOR_PORT = EV3.OutputPort.A;    //out. port for right motor
    private final static EV3.OutputPort LEFT_MOTOR_PORT = EV3.OutputPort.B;     //out. port for left motor
    private final static EV3.OutputPort ARM_MOTOR_PORT = EV3.OutputPort.D;      //out. port for mech. arm

    // output ports
    private final static EV3.InputPort LIGHT_SENSOR_PORT = EV3.InputPort._3;
    private final static EV3.InputPort ULTRASONIC_SENSOR_PORT = EV3.InputPort._2;
    private final static EV3.InputPort TOUCH_SENSOR_PORT = EV3.InputPort._1;
    private final static EV3.InputPort GYRO_SENSOR_PORT = EV3.InputPort._4;

    // const of movements
    private final static int STEP_ROTATION = 215;
    private final static int STEP_FORWARD = 1000;
    private final static int SPEED_FORWARD = 100;
    private final static int SPEED_ROTATION = 100;


    private LightSensor lightSensor;
    private UltrasonicSensor ultraSensor;
    private TouchSensor touchSensor;
    private GyroSensor gyroSensor;

    private WheelMotor right;
    private WheelMotor left;
    private ArmMotor arm;


    public Robot(EV3.Api api, String brickName) {

        // get sensors
        this.lightSensor = api.getLightSensor(LIGHT_SENSOR_PORT);
        this.ultraSensor = api.getUltrasonicSensor(ULTRASONIC_SENSOR_PORT);
        this.touchSensor = api.getTouchSensor(TOUCH_SENSOR_PORT);
        this.gyroSensor = api.getGyroSensor(GYRO_SENSOR_PORT);

        // get motors
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




/*
    public void moveLeft() throws IOException{
        left.setStepSpeed(-SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
        right.setStepSpeed(SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
        left.waitCompletion();
        right.waitCompletion();
    }

    public void moveRight() throws IOException{
        left.setStepSpeed(SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
        right.setStepSpeed(-SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
        left.waitCompletion();
        right.waitCompletion();
    }

 */
}
