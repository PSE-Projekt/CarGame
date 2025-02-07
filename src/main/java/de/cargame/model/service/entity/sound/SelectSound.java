package de.cargame.model.service.entity.sound;

/**
 * Represents the sound effect associated with menu selection in the user interface.
 * This class extends the abstract SoundClip class and provides the specific
 * file path for the "select" audio clip.
 *
 * The SelectSound object initializes and configures the sound clip located
 * at the path "/sounds/select.wav". It allows the sound to be played, looped,
 * or reset based on the functionality provided by the parent SoundClip class.
 *
 * This sound is typically used as auditory feedback when a user confirms
 * or selects an option within the game interface.
 */
public class SelectSound extends SoundClip {

    public SelectSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "/sounds/select.wav";
    }
}
