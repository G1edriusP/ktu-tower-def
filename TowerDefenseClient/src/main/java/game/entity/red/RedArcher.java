package game.entity.red;

import game.entity.Archer;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class RedArcher extends Archer {
    public RedArcher() {
        this(UUID.randomUUID());
    }
    public RedArcher(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("red/archer.png")));
    }

    @Override
    public String getType() {
        return "red-archer";
    }
}
