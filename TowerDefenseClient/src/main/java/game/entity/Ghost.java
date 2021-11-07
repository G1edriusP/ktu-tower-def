package game.entity;

import game.bridge.Mace;
import game.bridge.SpiritOrb;
import game.strategy.Range;
import game.strategy.Teleport;
import javafx.scene.image.ImageView;
import java.util.UUID;

abstract public class Ghost extends Soldier {
    protected Ghost(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        this.movement = new Teleport();
        this.attack = new Range();
        this.health = 130;
        this.weapon = new SpiritOrb();
    }
}
