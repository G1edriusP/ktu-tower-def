package game.decorator;

import game.entity.Archer;
import game.entity.Barbarian;
import game.entity.Ghost;
import game.entity.Skeleton;
import game.factory.AbstractSoldierFactory;
import game.prototype.Tile;

public class SoldierSpawnDecorator extends AbstractSoldierFactory {
    private AbstractSoldierFactory soldierFactory;
    private Tile spawnTile;

    public SoldierSpawnDecorator(AbstractSoldierFactory soldierFactory, Tile spawnTile) {
        this.soldierFactory = soldierFactory;
        this.spawnTile = spawnTile;
    }

    @Override
    public Ghost createGhost() {
        Ghost ghost = this.soldierFactory.createGhost();
        ghost.setX(spawnTile.getX());
        ghost.setY(spawnTile.getY());
        ghost.setTile(spawnTile);
        return ghost;
    }

    @Override
    public Archer createArcher() {
        Archer archer = this.soldierFactory.createArcher();
        archer.setX(spawnTile.getX());
        archer.setY(spawnTile.getY());
        archer.setTile(spawnTile);
        return archer;
    }

    @Override
    public Skeleton createSkeleton() {
        Skeleton skeleton = this.soldierFactory.createSkeleton();
        skeleton.setX(spawnTile.getX());
        skeleton.setY(spawnTile.getY());
        skeleton.setTile(spawnTile);
        return skeleton;
    }

    @Override
    public Barbarian createBarbarian() {
        Barbarian barbarian = this.soldierFactory.createBarbarian();
        barbarian.setX(spawnTile.getX());
        barbarian.setY(spawnTile.getY());
        barbarian.setTile(spawnTile);
        return barbarian;
    }
}
