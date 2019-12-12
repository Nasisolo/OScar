package it.unive.dais.legodroid.app;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.plugs.TachoMotor;

public class Motor {

    private TachoMotor motor;

    public Motor(EV3.Api api, EV3.OutputPort outputPort){
        this.motor = api.getTachoMotor(outputPort);
    }

    public TachoMotor getMotor(){
        return motor;
    }

    public Future<Float> getMotorPosition() throws IOException {
        return this.getMotor().getPosition();
    }

    public Float getMotorPositionValue() throws IOException, InterruptedException, ExecutionException {
        return this.getMotor().getPosition().get();
    }

    public Future<Float> getMotorSpeed() throws IOException {
        return this.getMotor().getSpeed();
    }

    public Float getMotorSpeedValue() throws IOException, InterruptedException, ExecutionException {
        return this.getMotor().getSpeed().get();
    }


}
