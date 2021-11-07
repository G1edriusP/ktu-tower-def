package game.strategy;

import game.entity.Soldier;
import game.prototype.Tile;

public class Walk extends Movement {
    @Override
    public void move(Soldier soldier, Tile tile) {
        boolean isRed = soldier.isRed();
        Tile path = isRed ? tile.getRedPath() : tile.getBluePath();
        if (path == null)
            return;

        soldier.setX(path.getX());
        soldier.setY(path.getY());

        soldier.send();
    }
}
