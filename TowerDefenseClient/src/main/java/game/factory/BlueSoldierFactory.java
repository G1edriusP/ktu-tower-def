package game.factory;

import game.entity.Archer;
import game.entity.Barbarian;
import game.entity.Ghost;
import game.entity.Skeleton;
import game.entity.blue.BlueArcher;
import game.entity.blue.BlueBarbarian;
import game.entity.blue.BlueGhost;
import game.entity.blue.BlueSkeleton;

public class BlueSoldierFactory extends AbstractSoldierFactory {
    @Override
    public Ghost createGhost() {return new BlueGhost();}

    @Override
    public Archer createArcher() {
        return new BlueArcher();
    }

    @Override
    public Skeleton createSkeleton() {
        return new BlueSkeleton();
    }

    @Override
    public Barbarian createBarbarian() {
        return new BlueBarbarian();
    }
}
