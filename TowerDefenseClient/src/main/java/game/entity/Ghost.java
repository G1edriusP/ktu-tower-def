package game.entity;

import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Ghost extends Soldier {
    protected Ghost(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
    }
}
