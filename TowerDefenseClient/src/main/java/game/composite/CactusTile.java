package game.composite;

import game.prototype.Tile;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class CactusTile extends Tile {
    public CactusTile() {
        this(UUID.randomUUID());
    }
    public CactusTile(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("tiles/cactus.png")));
    }
    @Override
    public String getType() {
        return "cactus-tile";
    }
}