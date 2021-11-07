package game.strategy;

import game.entity.Soldier;
import game.prototype.Tile;

public class Teleport extends Movement {
    @Override
    public void move(Soldier soldier) {
        boolean isRed = soldier.isRed();
        Tile tile = soldier.getTile();
        Tile path = isRed ? tile.getRedPath() : tile.getBluePath();
        if (path == null)
            return;

        path = isRed ? path.getRedPath() : path.getBluePath();
        if (path == null)
            return;

        soldier.setX(path.getX());
        soldier.setY(path.getY());
        soldier.setTile(path);

        soldier.send();
    }
}
