package game.entity;

import java.util.UUID;
import javafx.scene.image.ImageView;
import game.net.ServersideImage;

abstract public class Soldier extends ServersideImage {
    protected Soldier(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
    }
}
