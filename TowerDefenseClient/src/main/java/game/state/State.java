package game.state;

import game.entity.Soldier;

public abstract class State {
    public abstract State doOperation (Soldier soldier);
}
