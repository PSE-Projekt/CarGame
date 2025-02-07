package de.cargame.view.navigation;

import de.cargame.controller.input.UserInput;
import de.cargame.controller.input.UserInputBundle;
import de.cargame.controller.input.UserInputType;
import de.cargame.model.service.SoundService;
import de.cargame.view.ApiHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Provides structures for selectable objects.
 * Endpoint for receiving inputs from InputReceivers of the View module.
 * The inputs determine navigation over mentioned selectables and the triggering of
 * related calls to the Controller.
 */
public abstract class Navigator {
    protected final ApiHandler apiHandler;
    protected final SoundService soundService;
    @Getter
    private final Selectable initialSelectable;
    @Setter
    @Getter
    protected Selectable currentSelection;

    /**
     * Creates a new Navigator instance, and passes a ApiHandler to it.
     */
    protected Navigator(ApiHandler apiHandler) {
        soundService = SoundService.getInstance();
        this.currentSelection = new DummySelectable();
        this.initialSelectable = this.currentSelection;
        this.apiHandler = apiHandler;
    }

    /**
     * Receives input from receivers. Receiving an input is bound
     * to the Id of the Player causing the call.
     */
    public void receiveInput(UserInputBundle userInputBundle, String playerID) {
        if (userInputBundle.getLatestInput().isEmpty()) {
            return;
        }
        Optional<UserInput> latestInput = userInputBundle.getLatestInput();
        UserInputType latestInputType = UserInputType.NONE;
        if (latestInput.isPresent()) {
            latestInputType = latestInput.get().getUserInputType();
        }

        if (userInputBundle.isFastForward() && this.currentSelection instanceof Clickable selection) {
            soundService.playSelectSound();
            selection.onClick(this.apiHandler, playerID);
        }

        Selectable newSelection = null;

        if (latestInputType.equals(UserInputType.UP)) {
            newSelection = currentSelection.getNeighbour(Direction.UP);
        } else if (latestInputType.equals(UserInputType.LEFT)) {
            newSelection = currentSelection.getNeighbour(Direction.LEFT);
        } else if (latestInputType.equals(UserInputType.RIGHT)) {
            newSelection = currentSelection.getNeighbour(Direction.RIGHT);
        } else if (latestInputType.equals(UserInputType.DOWN)) {
            newSelection = currentSelection.getNeighbour(Direction.DOWN);
        }

        if (newSelection != null) {

            soundService.playChangeSelectionSound();
            this.currentSelection.deselect();
            this.currentSelection = newSelection;
            this.currentSelection.select();
        }
    }

    /**
     * Refreshes the navigator back to it's original state.
     */
    public void reset() {
        this.currentSelection.setLockedInSelection(false);
        this.currentSelection.deselect();
        this.currentSelection = this.initialSelectable;
        this.currentSelection.select();
    }
}
