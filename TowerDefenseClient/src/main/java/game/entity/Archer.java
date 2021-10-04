package game.entity;

import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Archer extends Soldier {
    protected Archer(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
    }
}
