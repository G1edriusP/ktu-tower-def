package game.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import game.factory.AbstractSoldierFactory;
import game.net.ServersideImage;
import javafx.scene.image.ImageView;

import java.util.UUID;

public abstract class Tower extends ServersideImage {
    protected Tower(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
    }

    @JsonIgnore
    abstract public AbstractSoldierFactory getAbstractSoldierFactory();
}
