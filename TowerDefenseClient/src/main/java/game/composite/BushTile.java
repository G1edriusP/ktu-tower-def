package game.composite;

import game.prototype.Tile;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class BushTile extends Tile {
    public BushTile() {
        this(UUID.randomUUID());
    }
    public BushTile(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("tiles/bush.png")));
    }
    @Override
    public String getType() {
        return "bush-tile";
    }
}