package de.cargame.model.service.entity.sound;

/**
 * Represents the sound effect associated with changing a selection in the user interface.
 * This class extends the abstract SoundClip class and provides the specific
 * file path for the "change selection" audio clip.
 * <p>
 * The ChangeSelectionSound object initializes and configures the sound clip
 * that is located at the path "/sounds/change_selection.wav". It allows the sound
 * to be played, looped, or reset, as defined in the functionality provided
 * by the parent SoundClip class.
 * <p>
 * This sound is typically triggered when a user navigates or switches between
 * multiple options in a menu, indicating the change through an auditory cue.
 */
public class ChangeSelectionSound extends SoundClip {


    public ChangeSelectionSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "/sounds/change_selection.wav";
    }
}
