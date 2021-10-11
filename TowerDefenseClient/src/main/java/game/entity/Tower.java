package game.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import game.factory.AbstractSoldierFactory;
import game.net.Image;
import javafx.scene.image.ImageView;

import java.util.UUID;

public abstract class Tower extends Image {
    protected Tower(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
    }

    @JsonIgnore
    abstract public AbstractSoldierFactory getAbstractSoldierFactory();
}
