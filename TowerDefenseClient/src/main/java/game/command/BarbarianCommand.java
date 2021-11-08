package game.command;

import game.entity.Barbarian;
import game.entity.Soldier;
import game.factory.AbstractSoldierFactory;
import javafx.scene.Group;

public class BarbarianCommand extends SoldierCommand {

    public BarbarianCommand(AbstractSoldierFactory soldierFactory,  Group group, int x, int y, int timeToSpawn) {
        super(soldierFactory, group, x, y, timeToSpawn);
    }

    @Override
    protected Soldier makeSoldier() {
        return this.soldierFactory.createBarbarian();
    }
}
