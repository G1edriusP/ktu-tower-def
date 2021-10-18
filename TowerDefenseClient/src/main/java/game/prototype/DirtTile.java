package game.prototype;

import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class DirtTile extends Tile {
    public DirtTile() {
        this(UUID.randomUUID());
    }
    public DirtTile(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("tiles/dirt.jpg")));
    }
    @Override
    public String getType() {
        return "dirt-tile";
    }
}
