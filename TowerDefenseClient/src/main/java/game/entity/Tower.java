package game.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import game.factory.AbstractSoldierFactory;
import game.net.Image;
import game.prototype.Tile;
import javafx.scene.image.ImageView;

import java.util.UUID;

public abstract class Tower extends Image {
    protected Tile troopSpawnTile;

    protected Tower(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
    }

    public void setTroopSpawnTile(Tile troopSpawnTile) {
        this.troopSpawnTile = troopSpawnTile;
    }

    @JsonIgnore
    abstract public AbstractSoldierFactory getAbstractSoldierFactory();
}
