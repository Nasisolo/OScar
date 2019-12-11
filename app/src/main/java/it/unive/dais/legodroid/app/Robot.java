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

    private final static String BRICK_NAME = "OScar";

    private BluetoothConnection.BluetoothChannel connection;

    // output ports
    private final static EV3.OutputPort RIGHT_MOTOR_PORT = EV3.OutputPort.A;    //out. port for right motor
    private final static EV3.OutputPort LEFT_MOTOR_PORT = EV3.OutputPort.B;     //out. port for left motor
    private final static EV3.OutputPort ARM_MOTOR_PORT = EV3.OutputPort.D;      //out. port for mech. arm

    // output ports
    private final static EV3.InputPort LIGHT_SENSOR_PORT = EV3.InputPort._3;
    private final static EV3.InputPort ULTRASONIC_SENSOR_PORT = EV3.InputPort._2;
    private final static EV3.InputPort TOUCH_SENSOR_PORT = EV3.InputPort._1;
    private final static EV3.InputPort GYRO_SENSOR_PORT = EV3.InputPort._4;

    private LightSensor lightSensor;
    private UltrasonicSensor ultraSensor;
    private TouchSensor touchSensor;
    private GyroSensor gyroSensor;

    private TachoMotor right;
    private TachoMotor left;
    private TachoMotor arm;


    public Robot(EV3.Api api) {
        //try {

            // get sensors
            this.lightSensor = api.getLightSensor(LIGHT_SENSOR_PORT);
            this.ultraSensor = api.getUltrasonicSensor(ULTRASONIC_SENSOR_PORT);
            this.touchSensor = api.getTouchSensor(TOUCH_SENSOR_PORT);
            this.gyroSensor = api.getGyroSensor(GYRO_SENSOR_PORT);

            // get motors
            this.right = api.getTachoMotor(RIGHT_MOTOR_PORT);
            this.left = api.getTachoMotor(LEFT_MOTOR_PORT);
            this.arm = api.getTachoMotor(ARM_MOTOR_PORT);

/*
        }catch (IOException e) {
            System.out.print(e);
            */
        }


    public void connectToEV3(String brick_name) throws IOException{
        this.connection = new BluetoothConnection(brick_name).connect(); // replace with your own brick name
    }
}
