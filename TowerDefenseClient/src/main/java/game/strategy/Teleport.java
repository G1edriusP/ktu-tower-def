package game.strategy;

import game.chain.ArmyManager;
import game.chain.requests.MoveSoldierRequest;
import game.entity.Soldier;
import game.prototype.Tile;
import game.visitor.TeleportVisitor;

public class Teleport extends Movement {
    @Override
    public boolean move(Soldier soldier) {
        boolean isRed = soldier.isRed();
        Tile start = soldier.getTile();
        Tile path = new TeleportVisitor().visit(start, isRed);
        if (path == null)
            return false;
        ArmyManager.getInstance().add(new MoveSoldierRequest(soldier, path));
        return true;
    }
}
