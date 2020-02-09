package it.unive.dais.legodroid.app;

import android.os.Bundle;

import com.google.android.gms.nearby.connection.Strategy;

public class MainActivity2 extends ConnectionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



    }

    @Override
    protected String getName() {
        return "OScar";
    }

    @Override
    protected String getServiceId() {
        return "OScar";
    }

    @Override
    protected Strategy getStrategy() {
        return Strategy.P2P_STAR;
    }


}
