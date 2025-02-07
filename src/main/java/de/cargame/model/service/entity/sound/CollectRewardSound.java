package de.cargame.model.service.entity.sound;

/**
 * Represents the sound effect played when a reward is collected in the game.
 * This class extends the SoundClip abstract class and provides the
 * specific file path for the reward collection audio clip.
 * <p>
 * The CollectRewardSound object initializes and configures the sound clip
 * located at the path "/sounds/collect_reward.wav". It allows the sound
 * to be played, looped, or reset based on the functionality provided by the
 * parent SoundClip class.
 * <p>
 * This sound effect is typically used to provide auditory feedback
 * to the player upon the successful collection of a reward in the game.
 */
public class CollectRewardSound extends SoundClip {


    public CollectRewardSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "/sounds/collect_reward.wav";
    }
}
