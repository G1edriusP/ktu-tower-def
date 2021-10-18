package game.prototype;

import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class GrassTile extends Tile {
    public GrassTile() {
        this(UUID.randomUUID());
    }
    public GrassTile(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("tiles/grass.jpg")));
    }
    @Override
    public String getType() {
        return "grass-tile";
    }
}
