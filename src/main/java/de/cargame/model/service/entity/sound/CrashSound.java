package de.cargame.model.service.entity.sound;

/**
 * Represents the sound effect played during a crash event in the game.
 * This class extends the abstract SoundClip class and provides the specific
 * file path for the crash audio clip.
 * <p>
 * The CrashSound object initializes and configures the sound clip
 * located at the path "/sounds/crash.wav". It allows the sound to be
 * played, looped, or reset according to the functionality provided by
 * the parent SoundClip class.
 * <p>
 * This sound effect is typically used to provide auditory feedback
 * to the player when a crash event occurs in the game.
 */
public class CrashSound extends SoundClip {


    @Override
    protected void setPath() {
        this.path = "/sounds/crash.wav";
    }
}
