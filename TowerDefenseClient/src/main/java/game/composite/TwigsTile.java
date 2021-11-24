package game.composite;

import game.prototype.Tile;
import game.singleton.ImageStore;
import javafx.scene.image.ImageView;

import java.util.UUID;

public class TwigsTile extends Tile {
    public TwigsTile() {
        this(UUID.randomUUID());
    }
    public TwigsTile(UUID uuid) {
        super(uuid, new ImageView(ImageStore.getInstance().getImage("tiles/twigs.png")));
    }
    @Override
    public String getType() {
        return "twigs-tile";
    }
}