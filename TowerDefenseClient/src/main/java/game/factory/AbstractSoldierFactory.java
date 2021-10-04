package game.factory;

import game.entity.Archer;
import game.entity.Barbarian;
import game.entity.Ghost;
import game.entity.Skeleton;

abstract public class AbstractSoldierFactory {
    abstract public Ghost createGhost();
    abstract public Archer createArcher();
    abstract public Skeleton createSkeleton();
    abstract public Barbarian createBarbarian();
}
