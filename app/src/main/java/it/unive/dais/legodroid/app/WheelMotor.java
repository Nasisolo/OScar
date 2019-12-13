package it.unive.dais.legodroid.app;

import java.io.IOException;

import it.unive.dais.legodroid.lib.EV3;

public class WheelMotor extends Motor {

    public WheelMotor(EV3.Api api, EV3.OutputPort outputPort){
        super(api, outputPort);
    }

    public void moveStepWheel(int speed, int step1, int step2, int step3, boolean brake) throws IOException {
        this.getMotor().setStepSpeed(speed, step1, step2, step3, brake);
    }

    public void waitWheelCompletion() throws IOException{
        this.getMotor().waitCompletion();
    }


}
