package game.entity.blue;

import game.entity.Archer;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BlueArcher extends Archer {
    public BlueArcher() {
        this(UUID.randomUUID());
    }
    public BlueArcher(UUID uuid) {
        super(uuid, new ImageView("blue/archer.png"));
    }

    @Override
    public String getType() {
        return "blue-archer";
    }
}
