package game.entity.blue;

import game.entity.Archer;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BlueArcher extends Archer {
    public BlueArcher() {
        this(UUID.randomUUID());
    }
    public BlueArcher(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("blue/archer.png")));
    }

    @Override
    public String getType() {
        return "blue-archer";
    }
}
