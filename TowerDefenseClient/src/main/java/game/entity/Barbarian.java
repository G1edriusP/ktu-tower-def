package game.entity;

import game.bridge.Mace;
import game.strategy.Melee;
import game.strategy.Walk;
import javafx.scene.image.ImageView;
import java.util.UUID;

abstract public class Barbarian extends Soldier {
    protected Barbarian(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        this.movement = new Walk();
        this.attack = new Melee();
        this.health = 150;
        this.weapon = new Mace();
    }
}
