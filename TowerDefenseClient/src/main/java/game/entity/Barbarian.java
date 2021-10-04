package game.entity;

import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Barbarian extends Soldier {
    protected Barbarian(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
    }
}
