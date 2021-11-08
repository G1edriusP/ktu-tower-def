package game.command;

import game.entity.Soldier;
import game.factory.AbstractSoldierFactory;
import javafx.scene.Group;

public class SkeletonCommand extends SoldierCommand {

    public SkeletonCommand(AbstractSoldierFactory soldierFactory, Group group, int x, int y, int timeToSpawn) {
        super(soldierFactory, group, x, y, timeToSpawn);
    }

    @Override
    protected Soldier makeSoldier() {
        return this.soldierFactory.createSkeleton();
    }
}

