package it.unive.dais.legodroid.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
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


public class MainActivity1 extends AppCompatActivity {

    private static final String TAG = Prelude.ReTAG("MainActivity1");
    private final Map<String, Object> statusMap = new HashMap<>();

    private static class MyCustomApi extends EV3.Api {

        private MyCustomApi(@NonNull GenEV3<? extends EV3.Api> ev3) {
            super(ev3);
        }

        public void mySpecialCommand() { /* do something special */ }
    }

    private void updateStatus(@NonNull Plug p, String key, Object value) {
        Log.d(TAG, String.format("%s: %s: %s", p, key, value));
        statusMap.put(key, value);
        //runOnUiThread(() -> textView.setText(statusMap.toString()));
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

    private int Msize, Nsize, Minit, Ninit;
    private FieldMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        Intent intent = getIntent();

        Msize = Integer.parseInt(intent.getStringExtra("Msize"));
        Nsize = Integer.parseInt(intent.getStringExtra("Nsize"));
        Minit = Integer.parseInt(intent.getStringExtra("Minit"));
        Ninit = Integer.parseInt(intent.getStringExtra("Ninit"));

        String sDir = intent.getStringExtra("Direction");
        Robot.Direction dir = Robot.Direction.NORTH;

        map = new FieldMap(Msize, Nsize, Minit, Ninit, dir);


        //Integer[] field = new Integer[Msize*Nsize];
        String[] field = new String[Msize*Nsize];
        Integer[] buffer = new Integer[Msize];
        int invIter = (Msize*Nsize)-1;

        for(int i = 0; i< Msize*Nsize; i++)
            field[i]="";

        gridView.setNumColumns(Msize);

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, R.layout.row, field);
        gridView.setAdapter(adapter);

        try {

            //BluetoothConnection.BluetoothChannel conn = new BluetoothConnection(BRICK_NAME).connect(); // replace with your own brick name
            BrickConnection conn = new BrickConnection("OScar");

            // connect to EV3 via bluetooth
            GenEV3<MainActivity1.MyCustomApi> ev3 = new GenEV3<>(conn.connectToEv3());
            // EV3 ev3 = new EV3(conn);  // alternatively an EV3 subclass



            Button stopButton = findViewById(R.id.buttonStop);
            stopButton.setOnClickListener(v -> {
                ev3.cancel();   // fire cancellation signal to the EV3 task
            });

            Button startButton = findViewById(R.id.buttonStart);
            startButton.setOnClickListener(v -> Prelude.trap(() -> ev3.run(this::legoMainCustomApi, MainActivity1.MyCustomApi::new)));
            // alternatively with plain EV3
            //startButton.setOnClickListener(v -> Prelude.trap(() -> ev3.run(this::legoMain));


            // functions for any test + intent + passing parameters x robot
        } catch (IOException e) {
            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }
    }




    // main program executed by EV3

    private void legoMain(EV3.Api api) {
        final String TAG = Prelude.ReTAG("legoMain");

        Robot robot = new Robot(api, "AGELM", map);
        //BrickConnection conn = new BrickConnection("OScar");






        try {
            robot.applyMotor(TachoMotor::resetPosition);

            while (!api.ev3.isCancelled()) {    // loop until cancellation signal is fired
                try {

                    //algorithm\

                    robot.moveTo(0,0);

                    robot.turnRight();

                    for(int i = map.getMact(); i < map.getM(); i++) {
                        for (int j = map.getNact(); j < map.getN(); j++)
                            robot.moveTo(i, j);
                        robot.moveTo(i, 0);

                        //if(map.getMact() < map.getM()-1)
                            //<move to next cell of index i>
                    }

                    robot.moveTo(map.getMsafe(), map.getNsafe());





                    robot.waitUntilReady();
                } catch (IOException | InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            robot.applyMotor(TachoMotor::stop);
        }
    }

    private void legoMainCustomApi(MainActivity1.MyCustomApi api) {
        final String TAG = Prelude.ReTAG("legoMainCustomApi");
        // specialized methods can be safely called
        api.mySpecialCommand();
        // stub the other main
        legoMain(api);
    }







    }

