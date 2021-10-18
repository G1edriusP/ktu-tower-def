package game.entity.blue;

import game.entity.Barbarian;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BlueBarbarian extends Barbarian {
    public BlueBarbarian() {
        this(UUID.randomUUID());
    }
    public BlueBarbarian(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("blue/barbarian.png")));
    }

    @Override
    public String getType() {
        return "blue-barbarian";
    }
}
