package game.entity.blue;

import game.decorator.SoldierSpawnDecorator;
import game.entity.Tower;
import game.factory.AbstractSoldierFactory;
import game.factory.BlueSoldierFactory;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BlueTower extends Tower {
    public BlueTower() {
        this(UUID.randomUUID());
    }

    public BlueTower(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("blue/tower.png")));
    }

    @Override
    public AbstractSoldierFactory getAbstractSoldierFactory() {
        AbstractSoldierFactory factory = new BlueSoldierFactory();
        factory = new SoldierSpawnDecorator(factory, this.troopSpawnTile);
        return factory;
    }

    @Override
    public String getType() {
        return "blue-tower";
    }
}
