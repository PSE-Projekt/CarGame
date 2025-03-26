package de.cargame.model.service.entity.sound;

import de.cargame.exception.SoundFileException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The SoundClip class is an abstract representation of a playable sound clip
 * used in an application. It provides common functionality for managing audio
 * playback, including playing, looping, and resetting sounds, as well as
 * managing file loading and volume adjustments.
 * <p>
 * Subclasses of this abstract class are responsible for defining the specific
 * file path for the audio resource by implementing the abstract `setPath` method.
 * <p>
 * Key functionalities include:
 * - Loading a sound file from the specified path.
 * - Playing the sound in a single or looping manner.
 * - Resetting the playback position.
 * - Adjusting the sound clip's volume.
 */
@Getter
@Slf4j
public abstract class SoundClip {

    protected String path;
    private Clip clip;

    protected SoundClip() {
        setPath();
        loadSoundFile(path);
        adjustLoudness(-10f);
    }

    protected abstract void setPath();

    /**
     * Plays the audio clip in a new thread to ensure non-blocking execution
     * for the rest of the program. The method utilizes the `clip.start()`
     * functionality of the Java Sound API to initiate playback.
     * <p>
     * This method is typically used to play short sound effects or audio
     * tracks within an application, such as feedback sounds in a game or
     * application interface.
     * <p>
     * Note:
     * - The playback is executed in a separate thread to prevent
     * potential UI or main-thread blocking.
     * - Proper initialization of the `clip` object is required before invoking this method.
     * - If the audio clip is already playing and needs to be restarted,
     * reset() should be called prior to play().
     * <p>
     * Preconditions:
     * - The `clip` object must be initialized and loaded with an audio file.
     * <p>
     * Postconditions:
     * - The audio clip will begin playing once the thread is executed.
     */
    public void play() {
        new Thread(clip::start).start();
    }

    /**
     * Sets the clip to play in a loop for the specified number of times.
     *
     * @param count the number of times the sound clip should loop. If set to
     *              {@code Clip.LOOP_CONTINUOUSLY}, the clip will loop indefinitely.
     */
    public void loop(int count) {
        clip.loop(count);
    }

    /**
     * Resets the audio clip to its initial state by stopping any playback
     * and setting the playback position to the beginning.
     * <p>
     * This method is commonly used to ensure that the audio clip
     * starts from the beginning before being played again or looped.
     * <p>
     * Key functionality:
     * - Stops any ongoing playback of the audio clip.
     * - Resets the playback position to the start of the clip.
     */
    public void reset() {
        clip.stop();
        clip.setMicrosecondPosition(0);
    }

    protected void loadSoundFile(String soundFilePath) {
        InputStream soundClipInputStream = getClass().getResourceAsStream(soundFilePath);
        if (soundClipInputStream == null) {
            throw new SoundFileException("Sound file not found: " + soundFilePath);
        }

        BufferedInputStream bufferedStream = null;
        try {
            bufferedStream = new BufferedInputStream(soundClipInputStream);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedStream);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioIn);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new SoundFileException("Failed to load sound file: " + soundFilePath);
        } finally {
            try {
                if (bufferedStream != null) {
                    bufferedStream.close();
                }
            } catch (IOException e) {
                log.error("Failed to close sound file stream: {}", soundFilePath, e);
            }
        }
    }

    /**
     * Adjusts the global gain by the given {@param delta} value.
     */
    private void adjustLoudness(Float delta) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(delta); // Reduce volume by 10 decibels.
    }
}
