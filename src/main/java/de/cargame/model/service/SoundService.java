package de.cargame.model.service;

import de.cargame.model.service.entity.sound.*;

public class SoundService {

    private final SoundClip crashSoundClip;
    private final SoundClip selectSoundClip;
    private final SoundClip changeSelectionSoundClip;
    private final SoundClip collectRewardSoundClip;

    public SoundService() {
        this.crashSoundClip = new CrashSound();
        this.selectSoundClip = new SelectSound();
        this.changeSelectionSoundClip = new ChangeSelectionSound();
        this.collectRewardSoundClip = new CollectRewardSound();
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


    private void playSound(SoundClip soundClip) {
        soundClip.reset();
        soundClip.play();
    }
}
