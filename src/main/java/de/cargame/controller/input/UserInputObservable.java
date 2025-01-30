package de.cargame.controller.input;


import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;

public interface UserInputObservable {

    void registerObserver(UserInputObserver o);

    void removeObserver(UserInputObserver o);

    void notifyObservers(UserInputBundle userInputBundle);

}
