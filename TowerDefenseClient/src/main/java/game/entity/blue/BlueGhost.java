package game.entity.blue;

import game.entity.Ghost;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BlueGhost extends Ghost {
    public BlueGhost() {
        this(UUID.randomUUID());
    }
    public BlueGhost(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("blue/ghost.png")));
    }

    @Override
    public String getType() {
        return "blue-ghost";
    }
}
