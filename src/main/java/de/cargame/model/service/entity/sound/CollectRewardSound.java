package de.cargame.model.service.entity.sound;

public class CollectRewardSound extends SoundClip {


    public CollectRewardSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "src/main/resources/sounds/collect_reward.wav";
    }
}
