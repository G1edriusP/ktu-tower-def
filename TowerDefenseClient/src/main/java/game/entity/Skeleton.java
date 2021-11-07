package game.entity;

import game.bridge.Bone;
import game.strategy.Melee;
import game.strategy.Teleport;
import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Skeleton extends Soldier {
    protected Skeleton(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        this.movement = new Teleport();
        this.attack = new Melee();
        this.health = 70;
        this.weapon = new Bone();
    }
}
