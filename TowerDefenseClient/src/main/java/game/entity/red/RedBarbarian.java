package game.entity.red;

import game.entity.Barbarian;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class RedBarbarian extends Barbarian {
    public RedBarbarian() {
        this(UUID.randomUUID());
    }
    public RedBarbarian(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("red/barbarian.png")));
    }

    @Override
    public String getType() {
        return "red-barbarian";
    }
}
