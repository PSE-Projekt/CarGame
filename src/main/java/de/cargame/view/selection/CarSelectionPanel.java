package de.cargame.view.selection;

import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

public class CarSelectionPanel extends SceneButton implements Clickable {

    private final CarType carType;
    private final SelectionInstanceView instanceView;

    public CarSelectionPanel(CarType carType, SelectionInstanceView instanceView) {

        super(null, null); //TODO: image
        this.carType = carType;
        this.instanceView = instanceView;
    }

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getPlayerApi().setCarSelection(playerID, this.carType);
        setLockedInSelection(true);
        instanceView.confirmChoice();
    }
}
