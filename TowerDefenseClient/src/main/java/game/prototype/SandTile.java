package game.prototype;

import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class SandTile extends Tile{
    public SandTile() {
        this(UUID.randomUUID());
    }
    public SandTile(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("tiles/sand.jpg")));
    }
    @Override
    public String getType() {
        return "sand-tile";
    }
}
