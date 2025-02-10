package de.cargame.controller.input;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class representing a collection of user inputs which are currently pressed by a player. It is designed to handle, manage, and retrieve
 * user input events. It supports adding and removing user inputs, checking the state of the inputs,
 * and retrieving the most recent inputs, optionally filtering certain input types.
 * <p>
 * The class maintains an internal list of {@code UserInput} objects, processes duplicate entries,
 * and manages a fast-forward mode based on specific user input types.
 */
@Getter
@Setter
public class UserInputBundle {

    private final List<UserInput> userInputs = new CopyOnWriteArrayList<>();
    private final UserInput USER_INPUT_NONE = new UserInput(UserInputType.NONE);
    private boolean fastForward;


    public UserInputBundle() {
        reset();
    }

    public UserInputBundle(UserInputBundle userInputBundle) {
        this.userInputs.addAll(userInputBundle.getUserInputs());
        this.fastForward = userInputBundle.isFastForward();
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


    /**
     * Checks whether the collection of user inputs in this bundle is empty.
     *
     * @return true if the collection of user inputs is empty; false otherwise
     */
    public boolean isEmpty() {
        return userInputs.isEmpty();
    }


    /**
     * Retrieves the most recently pressed user input from the list of user inputs.
     * The determination of the latest input is based on the pressed timestamp,
     * with a higher timestamp indicating a more recent input.
     *
     * @return an {@code Optional<UserInput>} containing the latest user input if present,
     * or an empty {@code Optional} if the input list is empty
     */
    public Optional<UserInput> getLatestInput() {
        return userInputs.stream()
                .min((o1, o2) -> Long.compare(o2.getPressedTimeStamp(), o1.getPressedTimeStamp()));
    }

    /**
     * Retrieves the most recent user input from the list of inputs, excluding any input
     * of type {@code UserInputType.CONFIRM}. Among the remaining inputs, the one with
     * the most recent timestamp is selected.
     *
     * @return an {@code Optional} containing the latest non-confirm user input, or an
     * empty {@code Optional} if no such input exists.
     */
    public Optional<UserInput> getLatestInputWithoutConfirm() {
        return userInputs.stream()
                .filter(input -> input.getUserInputType() != UserInputType.CONFIRM)
                .min((o1, o2) -> Long.compare(o2.getPressedTimeStamp(), o1.getPressedTimeStamp()));
    }

    /**
     * Retrieves the total number of user inputs currently stored in the input bundle.
     *
     * @return the size of the user input list
     */
    public int size() {
        return userInputs.size();
    }


    public boolean contains(UserInputType userInputType) {
        return userInputs.stream().anyMatch(input -> input.getUserInputType().equals(userInputType));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInputBundle that = (UserInputBundle) o;
        return fastForward == that.fastForward &&
                userInputs.equals(that.userInputs);
    }

    @Override
    public int hashCode() {
        int result = userInputs.hashCode();
        result = 31 * result + Boolean.hashCode(fastForward);
        return result;
    }
}
