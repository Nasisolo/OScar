package it.unive.dais.legodroid.app;

import java.io.IOException;

import it.unive.dais.legodroid.lib.EV3;

public class ArmMotor extends Motor {



    public ArmMotor(EV3.Api api, EV3.OutputPort outputPort){
        super(api, outputPort);
    }

    public void pickup(int speed, int step) throws IOException {
        super.getMotor().setStepSpeed(speed,0, step, 0, true);
        super.getMotor().waitCompletion();
    }

    public void release(int speed, int step) throws IOException{
        super.getMotor().setStepSpeed(speed*(-1),0, step, 0, true );
        super.getMotor().waitCompletion();
    }
}
