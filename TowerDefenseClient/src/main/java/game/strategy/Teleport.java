package game.strategy;

import game.entity.Soldier;
import game.prototype.Tile;

public class Teleport extends Movement {
    @Override
    public boolean move(Soldier soldier) {
        boolean isRed = soldier.isRed();
        Tile tile = soldier.getTile();
        if (tile == null)
            return false;
        Tile path = isRed ? tile.getRedPath() : tile.getBluePath();
        if (path == null)
            return false;

        Tile next = isRed ? path.getRedPath() : path.getBluePath();
        if (next != null)
            path = next;

        soldier.setX(path.getX());
        soldier.setY(path.getY());
        soldier.setTile(path);

        soldier.send();
        return true;
    }
}
