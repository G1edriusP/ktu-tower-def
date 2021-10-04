package game.net;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class ServersideImage extends Serverside{
    @JsonIgnore
    protected ImageView imageView;

    public ServersideImage() {
        this(UUID.randomUUID(), null);
    }

    public ServersideImage(UUID uuid) {
        this(uuid, null);
    }

    public ServersideImage(ImageView imageView) {
        this(UUID.randomUUID(), imageView);
    }

    public ServersideImage(UUID uuid, ImageView imageView) {
        super(uuid);
        this.setImageView(imageView);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public void addToGroup(Group group) {
        group.getChildren().add(this.getImageView());
    }
}
