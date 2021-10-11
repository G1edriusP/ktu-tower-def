package game.entity;

import java.util.UUID;

import game.strategy.Attack;
import game.strategy.Movement;
import javafx.scene.image.ImageView;
import game.net.Image;

abstract public class Soldier extends Image {
    protected Movement movement;
    protected Attack attack;

    protected Soldier(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
    }

    public Attack getAttack() {
        return this.attack;
    }

    public Movement getMovement() {
        return this.movement;
    }
}
