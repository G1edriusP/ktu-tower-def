package game.entity;

import game.strategy.Melee;
import game.strategy.Range;
import game.strategy.Teleport;
import game.strategy.Walk;
import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Ghost extends Soldier {
    protected Ghost(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        this.movement = new Teleport();
        this.attack = new Range();
    }
}
