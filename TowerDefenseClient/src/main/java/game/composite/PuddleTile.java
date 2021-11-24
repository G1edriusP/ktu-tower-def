package game.composite;

import game.prototype.Tile;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class PuddleTile extends Tile {
    public PuddleTile() {
        this(UUID.randomUUID());
    }
    public PuddleTile(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("tiles/puddle.png")));
    }
    @Override
    public String getType() {
        return "puddle-tile";
    }
}