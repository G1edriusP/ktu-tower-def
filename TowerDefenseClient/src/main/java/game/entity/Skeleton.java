package game.entity;

import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Skeleton extends Soldier {
    protected Skeleton(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
    }
}
