package de.cargame.model.service.entity.sound;

import lombok.Getter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


@Getter
public abstract class SoundClip {

    protected String path;
    private Clip clip;


    public SoundClip() {
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
        File f = new File(soundFilePath);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            this.clip = AudioSystem.getClip();
            this.clip.open(audioIn);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Adjusts the global gain by the given {@param delta} value.
     */
    private void adjustLoudness(Float delta) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(delta);// Reduce volume by 10 decibels.
    }

}
