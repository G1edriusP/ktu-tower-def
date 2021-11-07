package game.entity;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import game.prototype.Tile;
import game.strategy.Attack;
import game.strategy.Movement;
import javafx.scene.image.ImageView;
import game.net.Image;

abstract public class Soldier extends Image {
    @JsonIgnore
    protected Movement movement;
    @JsonIgnore
    protected Attack attack;

    protected int health;

    protected Soldier(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        this.health = 100;
    }

    public Attack getAttack() {
        return this.attack;
    }

    public Movement getMovement() {
        return this.movement;
    }

    public void attack(Soldier target) {
        this.attack.attack(target);
    }

    public void move(Tile tile) {
        this.movement.move(this, tile);
    }

    public boolean isRed() {
        return this.getType().toUpperCase().contains("RED");
    }

    public boolean isBlue() {
        return this.getType().toUpperCase().contains("BLUE");
    }

    public void doDamage(int damage) {
        this.health -= damage;
    }

    public boolean isDead() {
        return this.health <= 0;
    }
}
