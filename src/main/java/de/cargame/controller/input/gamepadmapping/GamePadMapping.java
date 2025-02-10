package de.cargame.controller.input.gamepadmapping;

import lombok.Getter;

@Getter
public abstract class GamePadMapping {

    protected String controllerName;
    protected String X_AxisComponentName;
    protected String Y_AxisComponentName;
    protected String fastForwardComponentName;


    public GamePadMapping() {
        setControllerName();
        setXAxisComponentName();
        setYAxisComponentName();
        setFastForwardComponentName();
    }


    protected abstract void setControllerName();
    protected abstract void setXAxisComponentName();
    protected abstract void setYAxisComponentName();
    protected abstract void setFastForwardComponentName();


    @Override
    public String toString() {
        return "Controller Name: " + controllerName + "\n" +
                "X Axis: " + X_AxisComponentName + "\n" +
                "Y Axis: " + Y_AxisComponentName + "\n" +
                "FF-Name: " + fastForwardComponentName + "\n";
    }
}
