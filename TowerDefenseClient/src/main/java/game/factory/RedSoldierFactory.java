package game.factory;

import game.entity.Archer;
import game.entity.Barbarian;
import game.entity.Ghost;
import game.entity.Skeleton;
import game.entity.red.RedArcher;
import game.entity.red.RedBarbarian;
import game.entity.red.RedGhost;
import game.entity.red.RedSkeleton;

public class RedSoldierFactory extends AbstractSoldierFactory {
    @Override
    public Ghost createGhost() {
        return new RedGhost();
    }

    @Override
    public Archer createArcher() {
        return new RedArcher();
    }

    @Override
    public Skeleton createSkeleton() {
        return new RedSkeleton();
    }

    @Override
    public Barbarian createBarbarian() {
        return new RedBarbarian();
    }
}
