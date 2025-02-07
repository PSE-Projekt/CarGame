package de.cargame.model.service.entity.sound;

/**
 * Represents the sound effect associated with a car race scenario.
 * This class extends the abstract SoundClip class and provides
 * the specific file path for the car race audio clip.
 * <p>
 * The CarRaceSound object initializes and configures
 * the sound clip located at the specified path and allows it to
 * be played in various playback modes (e.g., single play, loop).
 * <p>
 * The sound clip is expected to be located at "/sounds/car_race.wav".
 */
public class CarRaceSound extends SoundClip {


    public CarRaceSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "/sounds/car_race.wav";
    }
}
