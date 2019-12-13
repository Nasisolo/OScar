package it.unive.dais.legodroid.app;

import java.io.IOException;

import it.unive.dais.legodroid.lib.EV3;

public class ArmMotor extends Motor {

    private final static int STEP_PICKUP_RELEASE = 2100;
    private final static int SPEED_PICKUP_RELEASE = 100;

    public ArmMotor(EV3.Api api, EV3.OutputPort outputPort){
        super(api, outputPort);
    }

    public void pickup() throws IOException {
        super.getMotor().setStepSpeed(SPEED_PICKUP_RELEASE,0, STEP_PICKUP_RELEASE, 0, true);
        super.getMotor().waitCompletion();
    }

    public void release() throws IOException{
        super.getMotor().setStepSpeed(-SPEED_PICKUP_RELEASE,0, STEP_PICKUP_RELEASE, 0, true );
        super.getMotor().waitCompletion();
    }
}
