package game.entity.blue;

import game.entity.Tower;
import game.factory.AbstractSoldierFactory;
import game.factory.BlueSoldierFactory;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BlueTower extends Tower {
    public BlueTower() {
        this(UUID.randomUUID());
    }

    public BlueTower(UUID uuid) {
        super(uuid, new ImageView("blue/tower.png"));
    }

    @Override
    public AbstractSoldierFactory getAbstractSoldierFactory() {
        return new BlueSoldierFactory();
    }

    @Override
    public String getType() {
        return "blue-tower";
    }
}
