package de.cargame.controller.input.gamepadmapping;

public class Ps3Mapping extends GamePadMapping {

    public Ps3Mapping() {
        super();
    }

    @Override
    protected void setControllerName() {
        this.controllerName = "PS3/PC WirelessGamePad";
    }

    @Override
    protected void setXAxisComponentName() {
        this.X_AxisComponentName = "X-Achse";
    }

    @Override
    protected void setYAxisComponentName() {
        this.Y_AxisComponentName = "Y-Achse";
    }

    @Override
    protected void setFastForwardComponentName() {
        this.fastForwardComponentName = "Taste 2";
    }


}
