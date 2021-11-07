package game.strategy;

import game.entity.Soldier;
import game.prototype.Tile;

abstract public class Movement {
    public abstract void move(Soldier soldier, Tile tile);
}
