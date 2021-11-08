package game.command;

import game.factory.AbstractSoldierFactory;
import javafx.scene.Group;

public class SoldiersBarracks {

    private Group group;
    private AbstractSoldierFactory soldierFactory;

    public SoldiersBarracks(AbstractSoldierFactory soldierFactory, Group group) {
        this.group = group;
        this.soldierFactory = soldierFactory;
    }

    public void makeDeferredBarbarian(int x, int y) {
        new BarbarianCommand(soldierFactory, group, x, y, 4000).exec();
    }

    public void makeDeferredArcher(int x, int y) {
        new ArcherCommand(soldierFactory, group, x, y, 6000).exec();
    }

    public void makeDeferredGhost(int x, int y) {
        new GhostCommand(soldierFactory, group, x, y, 10000).exec();
    }

    public void makeDeferredSkeleton(int x, int y) {
        new SkeletonCommand(soldierFactory, group, x, y, 2000).exec();
    }
}
