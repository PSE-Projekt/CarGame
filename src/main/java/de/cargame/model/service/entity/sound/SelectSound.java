package de.cargame.model.service.entity.sound;

public class SelectSound extends SoundClip {

    public SelectSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "src/main/resources/sounds/select.wav";
    }
}
