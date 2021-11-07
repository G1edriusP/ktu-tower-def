package game.entity;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import game.strategy.Attack;
import game.strategy.Movement;
import javafx.scene.image.ImageView;
import game.net.Image;

abstract public class Soldier extends Image {
    @JsonIgnore
    protected Movement movement;
    @JsonIgnore
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

    public void attack() {
        this.attack.attack();
    }

    public void move() {
        this.movement.move();
    }
}
