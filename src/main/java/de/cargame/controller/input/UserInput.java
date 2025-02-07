package de.cargame.controller.input;

import lombok.Getter;

import java.util.Objects;

@Getter
public class UserInput {

    private final UserInputType userInputType;
    private long pressedTimeStamp;

    public UserInput(final UserInputType userInputType) {
        setPressedTimeStamp();
        this.userInputType = userInputType;
    }


    /**
     * Determines whether the specified amount of time in milliseconds has elapsed
     * since the `time` field of the class instance was last set.
     *
     * @param timeMillis the time duration in milliseconds to compare the elapsed time against
     * @return true if the elapsed time since the `time` field was last set is greater than or equal to the specified timeMillis, false otherwise
     */
    public boolean timeElapsed(double timeMillis) {
        return System.currentTimeMillis() - pressedTimeStamp >= timeMillis;
    }


    /**
     * Updates the `pressedTimeStamp` field to the current system time in milliseconds.
     * This method is used to record the timestamp of the last user input interaction.
     */
    private void setPressedTimeStamp() {
        this.pressedTimeStamp = System.currentTimeMillis();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserInput userInput)) return false;
        return userInputType == userInput.userInputType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInputType, pressedTimeStamp);
    }
}
