package it.unive.dais.legodroid.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.GenEV3;
import it.unive.dais.legodroid.lib.comm.BluetoothConnection;
import it.unive.dais.legodroid.lib.plugs.GyroSensor;
import it.unive.dais.legodroid.lib.plugs.LightSensor;
import it.unive.dais.legodroid.lib.plugs.Plug;
import it.unive.dais.legodroid.lib.plugs.TachoMotor;
import it.unive.dais.legodroid.lib.plugs.TouchSensor;
import it.unive.dais.legodroid.lib.plugs.UltrasonicSensor;
import it.unive.dais.legodroid.lib.util.Consumer;
import it.unive.dais.legodroid.lib.util.Prelude;
import it.unive.dais.legodroid.lib.util.ThrowingConsumer;


public class MainActivity extends AppCompatActivity {

    private final static String BRICK_NAME = "OScar";

    private final static int STEP_ROTATION = 215;
    private final static int STEP_FORWARD = 1000;
    private final static int SPEED_FORWARD = 100;
    private final static int SPEED_ROTATION = 100;
    private final static int STEP_PICKUP_RELEASE = 2100;


    private static final String TAG = Prelude.ReTAG("MainActivity");

    private TextView textView;
    private final Map<String, Object> statusMap = new HashMap<>();
    @Nullable
    private TachoMotor motorL;   // this is a class field because we need to access it from multiple methods
    private TachoMotor motorR;
    private TachoMotor motorHand;



    private void updateStatus(@NonNull Plug p, String key, Object value) {
        Log.d(TAG, String.format("%s: %s: %s", p, key, value));
        statusMap.put(key, value);
        runOnUiThread(() -> textView.setText(statusMap.toString()));
    }

    private void setupEditable(@IdRes int id, Consumer<Integer> f) {
        EditText e = findViewById(id);
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int x = 0;
                try {
                    x = Integer.parseInt(s.toString());
                } catch (NumberFormatException ignored) {
                }
                f.call(x);
            }
        });
    }

    private static class MyCustomApi extends EV3.Api {

        private MyCustomApi(@NonNull GenEV3<? extends EV3.Api> ev3) {
            super(ev3);
        }

        public void mySpecialCommand() { /* do something special */ }
    }

    // quick wrapper for accessing field 'motor' only when not-null; also ignores any exception thrown
    private void applyMotor(@NonNull ThrowingConsumer<TachoMotor, Throwable> f) {
        if (motorL != null && motorR != null && motorHand != null) {
            Prelude.trap(() -> f.call(motorL));
            Prelude.trap(() -> f.call(motorR));
            Prelude.trap(() -> f.call(motorHand));
        }
    }

    private void moveForward(TachoMotor l, TachoMotor r) throws IOException, InterruptedException, ExecutionException{
        l.setStepSpeed(SPEED_FORWARD, 0, STEP_FORWARD, 0, true);
        r.setStepSpeed(SPEED_FORWARD, 0, STEP_FORWARD, 0, true);
        l.waitCompletion();
        r.waitCompletion();
    }

    private void moveLeft(TachoMotor l, TachoMotor r) throws IOException, InterruptedException, ExecutionException{
        l.setStepSpeed(-SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
        r.setStepSpeed(SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
        l.waitCompletion();
        r.waitCompletion();
    }

    private void moveRight(TachoMotor l, TachoMotor r) throws IOException, InterruptedException, ExecutionException{
        l.setStepSpeed(SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
        r.setStepSpeed(-SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
        l.waitCompletion();
        r.waitCompletion();
    }



    private void move(TachoMotor l, TachoMotor r, String direction) throws IOException{
        switch (direction) {
            case "forward":
                l.setStepSpeed(SPEED_FORWARD, 0, STEP_FORWARD, 0, true);
                r.setStepSpeed(SPEED_FORWARD, 0, STEP_FORWARD, 0, true);
                l.waitCompletion();
                r.waitCompletion();
            break;

            case "left":
                l.setStepSpeed(-SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
                r.setStepSpeed(SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
                l.waitCompletion();
                r.waitCompletion();
                break;

            case "right":
                l.setStepSpeed(SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
                r.setStepSpeed(-SPEED_ROTATION, 0, STEP_ROTATION, 0, true);
                l.waitCompletion();
                r.waitCompletion();
                break;

            default:
                break;

        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);


        try {

            //BluetoothConnection.BluetoothChannel conn = new BluetoothConnection(BRICK_NAME).connect(); // replace with your own brick name
            BrickConnection conn = new BrickConnection("OScar");

            // connect to EV3 via bluetooth
            GenEV3<MyCustomApi> ev3 = new GenEV3<>(conn.connectToEv3());
//            EV3 ev3 = new EV3(conn);  // alternatively an EV3 subclass

            Button stopButton = findViewById(R.id.stopButton);
            stopButton.setOnClickListener(v -> {
                ev3.cancel();   // fire cancellation signal to the EV3 task
            });

            Button startButton = findViewById(R.id.startButton);
            startButton.setOnClickListener(v -> Prelude.trap(() -> ev3.run(this::legoMainCustomApi, MyCustomApi::new)));
            // alternatively with plain EV3
//            startButton.setOnClickListener(v -> Prelude.trap(() -> ev3.run(this::legoMain)));

            setupEditable(R.id.powerEdit, (x) -> applyMotor((m) -> {
                m.setPower(x);
                m.start();      // setPower() and setSpeed() require call to start() afterwards
            }));
            setupEditable(R.id.speedEdit, (x) -> applyMotor((m) -> {
                m.setSpeed(x);
                m.start();
            }));

        } catch (IOException e) {
            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }
    }




    // main program executed by EV3

    private void legoMain(EV3.Api api) {
        final String TAG = Prelude.ReTAG("legoMain");





        // get sensors
        /*
        final LightSensor lightSensor = api.getLightSensor(EV3.InputPort._3);
        final UltrasonicSensor ultraSensor = api.getUltrasonicSensor(EV3.InputPort._2);
        final TouchSensor touchSensor = api.getTouchSensor(EV3.InputPort._1);
        final GyroSensor gyroSensor = api.getGyroSensor(EV3.InputPort._4);
        */
        // get motors
        /*
        motorL = api.getTachoMotor(EV3.OutputPort.A);
        motorR = api.getTachoMotor(EV3.OutputPort.B);
        motorHand = api.getTachoMotor(EV3.OutputPort.D);
        */



        try {
            applyMotor(TachoMotor::resetPosition);

            while (!api.ev3.isCancelled()) {    // loop until cancellation signal is fired
                try {
                    Robot robot = new Robot(api, "OScar");

                    // values returned by getters are boxed within a special Future object

                    /*
                    Future<Float> gyro = gyroSensor.getAngle();
                    updateStatus(gyroSensor, "gyro angle", gyro.get()); // call get() for actually reading the value - this may block!

                    Future<Short> ambient = lightSensor.getAmbient();
                    updateStatus(lightSensor, "ambient", ambient.get());

                    Future<Short> reflected = lightSensor.getReflected();
                    updateStatus(lightSensor, "reflected", reflected.get());

                    Future<Float> distance = ultraSensor.getDistance();
                    updateStatus(ultraSensor, "distance", distance.get());

                    Future<LightSensor.Color> colf = lightSensor.getColor();
                    LightSensor.Color col = colf.get();
                    updateStatus(lightSensor, "color", col);
                    // when you need to deal with the UI, you must do it within a lambda passed to runOnUiThread()
                    runOnUiThread(() -> findViewById(R.id.colorView).setBackgroundColor(col.toARGB32()));

                    Future<Boolean> touched = touchSensor.getPressed();
                    updateStatus(touchSensor, "touch", touched.get() ? 1 : 0);
                    */

                    Future<Float> posL = robot.getLeftMotor().getPosition();
                    updateStatus(motorL, "motor position", posL.get());

                    Future<Float> speedL = robot.getLeftMotor().getSpeed();
                    updateStatus(motorL, "motor speed", speedL.get());

                    Future<Float> posR = robot.getRightMotor().getPosition();
                    updateStatus(motorR, "motor position", posR.get());

                    Future<Float> speedR = robot.getRightMotor().getSpeed();
                    updateStatus(motorR, "motor speed", speedR.get());

                    Future<Float> posH = robot.getArmMotor().getPosition();
                    updateStatus(motorHand, "motor position", posH.get());

                    Future<Float> speedH = robot.getArmMotor().getSpeed();
                    updateStatus(motorHand, "motor speed", speedH.get());





                    /*
                    motorL.setStepSpeed(100, 0, 5000, 0, true);
                    motorR.setStepSpeed(100, 0, 5000, 0, true);
                    motorL.waitCompletion();
                    motorR.waitCompletion();
                    */
                    //motorL.setStepSpeed(-20, 0, 5000, 0, true);

                    //moveForward(motorL, motorR);
                    //moveLeft(motorL, motorR);
                    //moveRight(motorL, motorR);

                    //motorHand.setStepSpeed(100,0,1700, 0, false);


                    /*
                    robot.moveLeft();

                    robot.pickup();
                    robot.release();

                    robot.moveRight();

                     */

/*

                    Log.d(TAG, "waiting for long motor operation completed...");
                    robot.getLeftMotor().waitUntilReady();
                    Log.d(TAG, "long motor operation completed");

                    //--------------------------

                    //motorR.setStepSpeed(-100, 0, 5000, 0, true);
                    //motorR.waitCompletion();
                    //motorR.setStepSpeed(-20, 0, 5000, 0, true);
                    Log.d(TAG, "waiting for long motor operation completed...");
                    robot.getRightMotor().waitUntilReady();
                    Log.d(TAG, "long motor operation completed");



                    Log.d(TAG, "waiting for long motor operation completed...");
                    robot.getArmMotor().waitUntilReady();
                    Log.d(TAG, "long motor operation completed");
*/
                } catch (IOException | InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            applyMotor(TachoMotor::stop);
        }
    }

    private void legoMainCustomApi(MyCustomApi api) {
        final String TAG = Prelude.ReTAG("legoMainCustomApi");
        // specialized methods can be safely called
        api.mySpecialCommand();
        // stub the other main
        legoMain(api);
    }


}


