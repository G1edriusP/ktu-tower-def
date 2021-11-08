package game.command;

import game.entity.Soldier;
import game.factory.AbstractSoldierFactory;
import javafx.scene.Group;

public class GhostCommand extends SoldierCommand {

    public GhostCommand(AbstractSoldierFactory soldierFactory,  Group group, int x, int y, int timeToSpawn) {
        super(soldierFactory, group, x, y, timeToSpawn);
    }

    @Override
    protected Soldier makeSoldier() {
        return this.soldierFactory.createGhost();
    }
}
