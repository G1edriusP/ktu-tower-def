package game.entity.red;

import game.entity.Ghost;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class RedGhost extends Ghost {
    public RedGhost() {
        this(UUID.randomUUID());
    }
    public RedGhost(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("red/ghost.png")));
    }

    @Override
    public String getType() {
        return "red-ghost";
    }
}
