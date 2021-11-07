package game.entity;

import game.strategy.Range;
import game.strategy.Walk;
import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Archer extends Soldier {
    protected Archer(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        this.movement = new Walk();
        this.attack = new Range();
        this.health = 80;
    }
}
