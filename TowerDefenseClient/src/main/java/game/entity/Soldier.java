package game.entity;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.bridge.Weapon;
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
    @JsonIgnore
    protected Tile tile;
    @JsonIgnore
    protected Weapon weapon;

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
        this.attack.attack(this, target);
    }

    public void move() {
        this.movement.move(this);
    }

    public boolean isRed() {
        return this.getType().toUpperCase().contains("RED");
    }

    public boolean isBlue() {
        return this.getType().toUpperCase().contains("BLUE");
    }

    @Override
    public void receive(String json) {
        super.receive(json);
        try {
            Soldier soldier = new ObjectMapper().readValue(json, this.getClass());
            this.health = soldier.health;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void doDamage(int damage) {
        this.health -= damage;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return this.tile;
    }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public Weapon getWeapon() { return this.weapon; };
}
