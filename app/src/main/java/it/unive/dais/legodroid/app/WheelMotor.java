package it.unive.dais.legodroid.app;

import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.plugs.TachoMotor;

public class WheelMotor extends Motor {

    public WheelMotor(EV3.Api api, EV3.OutputPort outputPort){
        super(api, outputPort);
    }


}
