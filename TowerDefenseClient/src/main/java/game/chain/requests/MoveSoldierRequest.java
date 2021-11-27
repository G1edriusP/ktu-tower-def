package game.chain.requests;

import game.entity.Soldier;
import game.prototype.Tile;

public class MoveSoldierRequest implements Request {
    private Soldier soldier;
    private Tile tile;

    public MoveSoldierRequest(Soldier soldier, Tile tile) {
        this.soldier = soldier;
        this.tile = tile;
    }

    public Soldier getSoldier() {
        return this.soldier;
    }

    public Tile getTile() {
        return this.tile;
    }
}
