package de.cargame.model.service;

import de.cargame.model.service.entity.sound.*;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.Clip;

/**
 * The SoundService class provides a centralized manager for handling
 * various sound effects within an application. It operates as a Singleton,
 * ensuring that only one instance is created and shared throughout the
 * application.
 *
 * The class initializes and manages multiple sound clip objects, each of
 * which represents a specific sound effect. These sound effects include:
 * - Crash sound, triggered during collisions or crash events.
 * - Selection sound, played when an item or option is selected.
 * - Change selection sound, providing feedback for navigation actions.
 * - Reward collection sound, played when a reward is collected.
 * - Continuous car race background sound, looped during gameplay.
 *
 * The SoundService also provides utility methods for playing, looping,
 * and stopping sounds, as well as ensuring sound clips are properly
 * reset before usage to maintain consistent playback behavior across
 * different scenarios.
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
     * <p>
     * This method is used to trigger the playback of the crash sound,
     * typically in response to events such as collisions or crashes.
     * The crash sound effect is managed by the {@code crashSoundClip} instance
     * and is reset before playback to ensure consistent behavior.
     * <p>
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
     * <p>
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
     * <p>
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
