package de.cargame.model.service;

import de.cargame.model.service.entity.sound.*;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.Clip;

/**
 * SoundService is a singleton class responsible for managing and playing various sound effects in the application.
 * It provides methods to play specific sounds, loop sounds, and stop continuous playback.
 *
 * This service manages a set of predefined sound effects, each associated with a specific game or application event,
 * such as crash sounds, selection sounds, and gameplay-related sounds.
 *
 * The sound effects are represented by instances of the SoundClip class, which provides capabilities to play, loop,
 * and reset audio clips. Each sound effect corresponds to a unique subclass of SoundClip that defines the audio
 * file path specific to that sound.
 *
 * Key functionalities of the SoundService class include:
 * - Playing sound effects for crash events, menu selection, changing selections, and reward collection.
 * - Looping the car race sound continuously or until manually stopped.
 * - Ensuring that only one instance of the SoundService exists in the application (singleton pattern).
 */
@Slf4j
public class SoundService {

    private static SoundService INSTANCE = new SoundService();

    private final SoundClip crashSoundClip;
    private final SoundClip selectSoundClip;
    private final SoundClip changeSelectionSoundClip;
    private final SoundClip collectRewardSoundClip;
    private final SoundClip carRaceSoundClip;

    public SoundService() {
        log.debug("Initializing SoundService");
        this.crashSoundClip = new CrashSound();
        this.selectSoundClip = new SelectSound();
        this.changeSelectionSoundClip = new ChangeSelectionSound();
        this.collectRewardSoundClip = new CollectRewardSound();
        this.carRaceSoundClip = new CarRaceSound();
        log.debug("SoundService initialized");
    }

    /**
     * Returns the singleton instance of the SoundService class, ensuring that only one instance
     * is created and shared throughout the application. This method is thread-safe.
     *
     * @return the single instance of SoundService
     */
    public static synchronized SoundService getInstance() {
        return INSTANCE;
    }


    /**
     * Plays the crash sound effect.
     *
     * This method is used to trigger the playback of the crash sound,
     * typically in response to events such as collisions or crashes.
     * The crash sound effect is managed by the {@code crashSoundClip} instance
     * and is reset before playback to ensure consistent behavior.
     *
     * The sound clip is defined and initialized within the SoundService class
     * during its construction phase.
     */
    //generated from https://sfxr.me/
    public void playCrashSound() {
        playSound(crashSoundClip);
    }

    /**
     * Plays the sound effect associated with a selection action.
     * This method uses the selectSoundClip instance to produce the audio effect
     * for user interface or gameplay-related selection events.
     *
     * The sound clip is reset before being played to ensure it starts from the
     * beginning each time it is triggered.
     */
    public void playSelectSound() {
        playSound(selectSoundClip);
    }

    /**
     * Plays the sound effect associated with changing a selection in the application.
     * This method resets and triggers playback of the pre-defined "change selection" sound clip.
     * The sound clip provides a distinct auditory feedback for selection change events.
     */
    public void playChangeSelectionSound() {
        playSound(changeSelectionSoundClip);
    }

    /**
     * Plays the sound effect associated with collecting a reward.
     * This method uses the predefined `collectRewardSoundClip` audio clip, which is reset before playback.
     * The sound is played once to provide audio feedback for the reward collection event.
     */
    public void playRewardCollectedSound() {
        playSound(collectRewardSoundClip);
    }


    /**
     * Plays the car race sound in a continuous loop. This method ensures that the car race
     * sound effect, represented by the `carRaceSoundClip` instance, is reset and then played
     * in a loop until manually stopped. It is typically used to simulate ongoing car race
     * gameplay scenarios by providing continuous background auditory feedback.
     *
     * The sound will loop indefinitely until the `stopCarRaceSoundLoop` method is invoked to
     * explicitly stop the playback.
     */
    public void playCarRaceSoundLoop() {
        playSoundAndWaitForFinish(carRaceSoundClip);
    }

    /**
     * Stops the looping sound associated with the car race.
     * This method resets the sound clip used for the car race sound effects,
     * ensuring it stops playing immediately.
     */
    public void stopCarRaceSoundLoop() {
        carRaceSoundClip.reset();
    }


    private void playSound(SoundClip soundClip) {
        soundClip.reset();
        soundClip.play();
    }

    private void playSoundAndWaitForFinish(SoundClip soundClip) {
        soundClip.reset();
        soundClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
