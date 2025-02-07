package de.cargame.view.selection;

import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

/**
 * Provides functionality for selecting a car by
 * implementing the onClick() method of Clickable.
 * In each selection instance, two of these will be displayed,
 * symbolizing the choice between two CarTypes.
 */
class CarSelectionPanel extends SceneButton implements Clickable {

    private final CarType carType;
    private final SelectionInstanceView instanceView;

    /**
     * Creates a new CarSelectionPanel button for the MenuScene
     *
     * @param carType      type of car the button will represent
     * @param instanceView the instanceView to report the choice back to
     */
    CarSelectionPanel(CarType carType, SelectionInstanceView instanceView) {
        super(getDefaultCarImg(carType), getSelectedCarImg(carType));
        this.carType = carType;
        this.instanceView = instanceView;
    }

    private static String getDefaultCarImg(CarType carType) {
        return switch (carType) {
            case AGILE_CAR -> "/frontend/agileCar_default.png";
            case FAST_CAR -> "/frontend/fastCar_default.png";
            default -> null;
        };
    }

    private static String getSelectedCarImg(CarType carType) {
        return switch (carType) {
            case AGILE_CAR -> "/frontend/agileCar_selected.png";
            case FAST_CAR -> "/frontend/fastCar_selected.png";
            default -> null;
        };
    }

    /**
     * Sets the Player’s selected car to the one matching the button. Assignment is done
     * using the playerID.
     */
    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getPlayerApi().setCarSelection(playerID, this.carType);
        setLockedInSelection(true);
        instanceView.confirmChoice();
    }
}
