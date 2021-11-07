package game.level;

import game.entity.Tower;
import game.prototype.Tile;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Tile> tiles;
    private Tower friendlyTower;
    private Tower enemyTower;

    public Level() {
        this.tiles = new ArrayList<>();
    }

    public void setFriendlyTower(Tower tower) {
        this.friendlyTower = tower;
    }

    public Tower getFriendlyTower() {
        return this.friendlyTower;
    }

    public void setEnemyTower(Tower tower) {
        this.enemyTower = tower;
    }

    public Tower getEnemyTower() {
        return this.enemyTower;
    }

    public void append(Tile tile) {
        this.tiles.add(tile);
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
