package view.navigation;

public class InputReceiver {

    private String observedPlayerID;
    private Navigator sceneNavigator;

    public InputReceiver(String playerID){
        this.observedPlayerID = playerID;
    }

    public void assignNavigator(Navigator navigator){
        this.sceneNavigator = navigator;
    }
    public void update(Object userInputBundle) {
        sceneNavigator.receiveInput(userInputBundle, observedPlayerID);
    }
}
