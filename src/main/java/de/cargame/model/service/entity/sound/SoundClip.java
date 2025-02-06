
package de.cargame.model.service.entity.sound;

import lombok.Getter;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter
public abstract class SoundClip {

    protected String path;
    private Clip clip;

    protected SoundClip() {
        setPath();
        loadSoundFile(path);
        adjustLoudness(-10f);
    }

    protected abstract void setPath();

    public void play() {
        new Thread(clip::start).start();
    }

    public void loop(int count) {
        clip.loop(count);
    }

    public void reset() {
        clip.stop();
        clip.setMicrosecondPosition(0);
    }

    protected void loadSoundFile(String soundFilePath) {
        InputStream soundClipInputStream = getClass().getResourceAsStream(soundFilePath);
        if (soundClipInputStream == null) {
            throw new RuntimeException("Sound file not found: " + soundFilePath);
        }

        BufferedInputStream bufferedStream = null; // Declare this outside the try block to manage its lifecycle
        try {
            // Wrap in a BufferedInputStream to support mark/reset
            bufferedStream = new BufferedInputStream(soundClipInputStream);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedStream);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioIn);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException("Failed to load sound file: " + soundFilePath, e);
        } finally {
            // Ensure streams are closed properly without affecting the Clip or AudioInputStream
            try {
                if (bufferedStream != null) {
                    bufferedStream.close(); // Close the BufferedInputStream explicitly
                }
            } catch (IOException e) {
                e.printStackTrace(); // Print error if cleanup fails
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
