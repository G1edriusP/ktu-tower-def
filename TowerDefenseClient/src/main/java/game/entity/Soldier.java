package game.entity;

import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.adapter.AdaptiveWeapon;
import game.adapter.RangeWeaponAdapter;
import game.bridge.Weapon;
import game.memento.WeaponMemento;
import game.memento.WeaponOriginator;
import game.net.ISubject;
import game.net.Session;
import game.prototype.Tile;
import game.state.*;
import game.strategy.Attack;
import game.strategy.Movement;
import game.visitor.SingleVisitor;
import game.visitor.Visitor;
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
    protected WeaponOriginator weaponOriginator;
    @JsonIgnore
    protected WeaponMemento weaponStartingState;
    @JsonIgnore
    protected State currentState;

    protected int health;

    protected Soldier(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        this.health = 100;
    }

    public boolean attack(Soldier target) {
        return this.attack.attack(this, target);
    }

    public boolean move() {
        return this.movement.move(this);
    }

    @JsonIgnore
    public boolean isRed() {
        return this.getType().toUpperCase().contains("RED");
    }

    @JsonIgnore
    public boolean isBlue() {
        return this.getType().toUpperCase().contains("BLUE");
    }

    @JsonIgnore
    public boolean isOurControlled() {
        if (Session.getInstance().isRed()) {
            return this.isRed();
        }
        return this.isBlue();
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

    @JsonIgnore
    public Soldier getTarget() {
        boolean isRange = this.weaponOriginator.getWeapon() instanceof RangeWeaponAdapter;
        Visitor visitor = new SingleVisitor();

        Tile tile = this.tile;
        if (tile == null)
            return null;
        for (int i = 0; i < (isRange ? 6 : 2); i++) {
            tile = visitor.visit(tile, isRed());
            if (tile == null) {
                return null;
            }
            Soldier target = enemyOnTile(tile);
            if (target != null) {
                return target;
            }
        }
        return null;
    }

    private Soldier enemyOnTile(Tile tile) {
        for (Map.Entry<UUID, ISubject> entry : Session.getInstance().getObjects().entrySet()) {
            if (!(entry.getValue() instanceof Soldier)) {
                continue;
            }
            Soldier soldier = (Soldier) entry.getValue();
            if (soldier.isOurControlled())
                continue;

            if (soldier.getX() == tile.getX() && soldier.getY() == tile.getY()) {
                return soldier;
            }
        }
        return null;
    }

    public void doDamage(int damage) {
        this.weaponOriginator.restore(this.weaponStartingState);
        this.health -= damage;
    }

    @JsonIgnore
    public boolean isDead() {
        return this.health <= 0;
    }

    public int getHealth() {
        return this.health;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    @JsonIgnore
    public Tile getTile() {
        return this.tile;
    }

    public void setWeaponOriginator(AdaptiveWeapon weaponOriginator) {
        this.weaponOriginator = new WeaponOriginator(weaponOriginator);
        this.weaponStartingState = this.weaponOriginator.createMemento();
    }

    @JsonIgnore
    public Weapon getWeaponOriginator() { return this.weaponOriginator.getWeapon(); };

    @JsonIgnore
    public void setState(State state) { this.currentState = state; };

    @JsonIgnore
    public State getState() { return this.currentState; };

    public State operate() {
        return this.currentState.doOperation(this);
    }
}
