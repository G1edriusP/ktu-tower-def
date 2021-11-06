package game.entity.red;

import game.decorator.SoldierSpawnDecorator;
import game.entity.Tower;
import game.factory.AbstractSoldierFactory;
import game.factory.BlueSoldierFactory;
import game.factory.RedSoldierFactory;
import game.prototype.Tile;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class RedTower extends Tower {
    public RedTower() {
        this(UUID.randomUUID());
    }

    public RedTower(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("red/tower.png")));
    }

    @Override
    public AbstractSoldierFactory getAbstractSoldierFactory() {
        AbstractSoldierFactory factory = new RedSoldierFactory();
        factory = new SoldierSpawnDecorator(factory, this.troopSpawnTile);
        return factory;
    }

    @Override
    public String getType() {
        return "red-tower";
    }
}
