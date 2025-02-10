package de.cargame.controller.input.gamepadmapping;

public class XBoxOneGamePadMapping extends GamePadMapping {


    public XBoxOneGamePadMapping() {
        super();
    }

    @Override
    protected void setControllerName() {
        this.controllerName = "Xbox Wireless Controller";
    }

    @Override
    protected void setXAxisComponentName() {
        this.X_AxisComponentName = "x";
    }

    @Override
    protected void setYAxisComponentName() {
        this.Y_AxisComponentName = "y";
    }

    @Override
    protected void setFastForwardComponentName() {
        this.fastForwardComponentName = "0";
    }
}
