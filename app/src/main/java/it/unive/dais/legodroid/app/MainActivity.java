package it.unive.dais.legodroid.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.GenEV3;
import it.unive.dais.legodroid.lib.plugs.Plug;
import it.unive.dais.legodroid.lib.plugs.TachoMotor;
import it.unive.dais.legodroid.lib.util.Consumer;
import it.unive.dais.legodroid.lib.util.Prelude;


public class MainActivity extends AppCompatActivity {

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

    /*
    private void applyMotor(@NonNull ThrowingConsumer<TachoMotor, Throwable> f) {
        if (motorL != null && motorR != null && motorHand != null) {
            Prelude.trap(() -> f.call(motorL));
            Prelude.trap(() -> f.call(motorR));
            Prelude.trap(() -> f.call(motorHand));
        }
    }

     */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTitle("OScar");
        textView = findViewById(R.id.textView);


        try {

            //BluetoothConnection.BluetoothChannel conn = new BluetoothConnection(BRICK_NAME).connect(); // replace with your own brick name
            BrickConnection conn = new BrickConnection("OScar");

            // connect to EV3 via bluetooth
            GenEV3<MyCustomApi> ev3 = new GenEV3<>(conn.connectToEv3());
            // EV3 ev3 = new EV3(conn);  // alternatively an EV3 subclass



            Button stopButton = findViewById(R.id.stopButton);
            stopButton.setOnClickListener(v -> {
                ev3.cancel();   // fire cancellation signal to the EV3 task
            });

            Button startButton = findViewById(R.id.startButton);
            startButton.setOnClickListener(v -> Prelude.trap(() -> ev3.run(this::legoMainCustomApi, MyCustomApi::new)));
            // alternatively with plain EV3
            // startButton.setOnClickListener(v -> Prelude.trap(() -> ev3.run(this::legoMain)));
            /* NON ELIMINARE
            setupEditable(R.id.powerEdit, (x) -> robot.applyMotor((m) -> {
                m.setPower(x);
                m.start();      // setPower() and setSpeed() require call to start() afterwards
            }));
            setupEditable(R.id.speedEdit, (x) -> applyMotor((m) -> {
                m.setSpeed(x);
                m.start();
            }));

            */


            // functions for any test + intent + passing parameters x robot
        } catch (IOException e) {
            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }
    }




    // main program executed by EV3

    private void legoMain(EV3.Api api) {
        final String TAG = Prelude.ReTAG("legoMain");

        Robot robot = new Robot(api, "OScar", new FieldMap(0,0, 0, 0, Robot.Direction.NORTH));
        BrickConnection conn = new BrickConnection("OScar");






        try {
            robot.applyMotor(TachoMotor::resetPosition);

            while (!api.ev3.isCancelled()) {    // loop until cancellation signal is fired
                try {


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

/*                  SOSTITUITO DA Robot.waitUntilReady();

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
                    //robot.pickup();
                    /*
                    for(int i =0; i<1; i++)
                        robot.moveForward();
                    */
                    //robot.moveRight();
                    //robot.moveLeft();

                    /*
                    for(int i=0; i<1; i++)
                        robot.moveBackward();
                    */

                    //robot.release();

                    /*
                    robot.moveForward();
                    robot.moveBackward();
                    */

                    //robot.moveRight();

                    robot.waitUntilReady();
                } catch (IOException | InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            robot.applyMotor(TachoMotor::stop);
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


