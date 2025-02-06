package de.cargame.model.service.entity.sound;

public class CarRaceSound extends SoundClip {


    public CarRaceSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "/sounds/car_race.wav";
    }
}
