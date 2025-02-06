package de.cargame.model.service.entity.sound;

public class ChangeSelectionSound extends SoundClip {


    public ChangeSelectionSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "/sounds/change_selection.wav";
    }
}
