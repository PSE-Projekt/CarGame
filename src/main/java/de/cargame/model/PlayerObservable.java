package de.cargame.model;

import de.cargame.model.entity.player.PlayerObserver;
import de.cargame.model.entity.player.PlayerUpdate;

public interface PlayerObservable {

    void addObserver(PlayerObserver playerObserver);

    void removeObserver(PlayerObserver observer);

    void notifyObservers(PlayerUpdate playerUpdate);
}
