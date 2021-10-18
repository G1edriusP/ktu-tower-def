package game.entity.red;

import game.entity.Tower;
import game.factory.AbstractSoldierFactory;
import game.factory.RedSoldierFactory;
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
        return new RedSoldierFactory();
    }

    @Override
    public String getType() {
        return "red-tower";
    }
}
