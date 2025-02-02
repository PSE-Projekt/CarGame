package de.cargame.model.service.entity.sound;

public class CarRaceSound extends SoundClip {


    public CarRaceSound() {
        setPath();
    }

    @Override
    protected void setPath() {
        this.path = "src/main/resources/sounds/car_race.wav";
    }
}
