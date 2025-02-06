package de.cargame.controller.input;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class UserInputBundle {

    private final List<UserInput> userInputs = new CopyOnWriteArrayList<>();
    private final UserInput USER_INPUT_NONE = new UserInput(UserInputType.NONE);
    private boolean fastForward;


    public UserInputBundle() {
        reset();
    }

    public void reset() {
        this.userInputs.clear();
        this.fastForward = false;
    }

    /**
     * Adds a user input to the input bundle. If the input type is {@code UserInputType.CONFIRM},
     * the fast-forward mode is enabled. If the input type is not currently in the list of user inputs,
     * it is added. The placeholder input type {@code USER_INPUT_NONE} is removed before processing.
     *
     * @param userInputType the type of user input to be added
     */
    public void addUserInput(UserInputType userInputType) {
        UserInput userInput = new UserInput(userInputType);
        userInputs.remove(USER_INPUT_NONE);

        if (userInput.getUserInputType().equals(UserInputType.CONFIRM)) {
            fastForward = true;
            this.userInputs.add(userInput);
        } else {
            if (!userInputs.contains(userInput)) {
                this.userInputs.add(userInput);
            }
        }
    }


    /**
     * Removes a user input from the input bundle. If the provided user input type is
     * {@code UserInputType.CONFIRM}, it disables the fast-forward mode. For other input types,
     * it searches the list of user inputs and removes it.
     *
     * @param userInputType the type of user input to be removed
     */
    public void removeUserInput(UserInputType userInputType) {

        if (userInputType.equals(UserInputType.CONFIRM)) {
            fastForward = false;
            this.userInputs.remove(USER_INPUT_NONE);
        } else {
            userInputs.stream()
                    .filter(input -> input.getUserInputType().equals(userInputType))
                    .findAny()
                    .ifPresent(this.userInputs::remove);
        }
    }


    public boolean isEmpty() {
        return userInputs.isEmpty();
    }



    public Optional<UserInput> getLatestInput() {
        return userInputs.stream()
                .min((o1, o2) -> Long.compare(o2.getPressedTimeStamp(), o1.getPressedTimeStamp()));
    }

    public Optional<UserInput> getLatestInputWithoutConfirm() {
        return userInputs.stream()
                .filter(input -> input.getUserInputType() != UserInputType.CONFIRM)
                .min((o1, o2) -> Long.compare(o2.getPressedTimeStamp(), o1.getPressedTimeStamp()));
    }

    public int size() {
        return userInputs.size();
    }
}
