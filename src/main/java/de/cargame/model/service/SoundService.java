package de.cargame.model.service;

import de.cargame.model.service.entity.sound.*;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.Clip;

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

    public static synchronized SoundService getInstance() {
        return INSTANCE;
    }


    //generated from https://sfxr.me/
    public void playCrashSound() {
        playSound(crashSoundClip);
    }

    public void playSelectSound() {
        playSound(selectSoundClip);
    }

    public void playChangeSelectionSound() {
        playSound(changeSelectionSoundClip);
    }

    public void playRewardCollectedSound() {
        playSound(collectRewardSoundClip);
    }


    public void playCarRaceSoundLoop() {
        playSoundAndWaitForFinish(carRaceSoundClip);
    }


    private void playSound(SoundClip soundClip) {
        soundClip.reset();
        soundClip.play();
    }

    private void playSoundAndWaitForFinish(SoundClip soundClip) {
        soundClip.reset();
        soundClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopCarRaceSoundLoop() {
        carRaceSoundClip.reset();
    }
}
