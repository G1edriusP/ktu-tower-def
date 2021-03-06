package game.strategy;

import game.chain.ArmyManager;
import game.chain.requests.MoveSoldierRequest;
import game.entity.Soldier;
import game.prototype.Tile;
import game.visitor.SingleVisitor;

public class Walk extends Movement {
    @Override
    public boolean move(Soldier soldier) {
        boolean isRed = soldier.isRed();
        Tile start = soldier.getTile();
        Tile path = new SingleVisitor().visit(start, isRed);
        if (path == null)
            return false;
        ArmyManager.getInstance().add(new MoveSoldierRequest(soldier, path));
        return true;
    }
}
