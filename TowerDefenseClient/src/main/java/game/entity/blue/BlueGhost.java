package game.entity.blue;

import game.entity.Ghost;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BlueGhost extends Ghost {
    public BlueGhost() {
        this(UUID.randomUUID());
    }
    public BlueGhost(UUID uuid) {
        super(uuid, new ImageView("blue/ghost.png"));
    }

    @Override
    public String getType() {
        return "blue-ghost";
    }
}
