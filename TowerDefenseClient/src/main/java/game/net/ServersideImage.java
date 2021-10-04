package game.net;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.entity.Soldier;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class ServersideImage extends Serverside{
    private double x;
    private double y;

    @JsonIgnore
    protected ImageView imageView;

    protected ServersideImage() {
        this(UUID.randomUUID(), null);
    }

    protected ServersideImage(UUID uuid) {
        this(uuid, null);
    }

    protected ServersideImage(ImageView imageView) {
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

    @Override
    public void receive(String json) throws JsonProcessingException {
        ServersideImage img = new ObjectMapper().readValue(json, this.getClass());
        this.setX(img.getX());
        this.setY(img.getY());
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
        this.imageView.setX(x);
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
        this.imageView.setY(y);
    }
}
