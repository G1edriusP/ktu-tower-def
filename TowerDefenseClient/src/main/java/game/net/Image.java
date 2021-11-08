package game.net;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.ImageView;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract public class Image implements ISubject {
    private double x;
    private double y;

    /**
     * uuid is used to safely identify each Object uniquely.
     */
    protected UUID uuid;

    @JsonIgnore
    protected ImageView imageView;

    protected Image() {
        this(UUID.randomUUID(), null);
    }

    protected Image(UUID uuid) {
        this(uuid, null);
    }

    protected Image(ImageView imageView) {
        this(UUID.randomUUID(), imageView);
    }

    public void send() {
        try {
            Session.getInstance().send(new ObjectMapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void sendDelete() {
        Session session = Session.getInstance();
        session.send("{\"action\":\"delete\",\"uuid\":\"" + this.uuid + "\"}");
        session.unregister(getUUID());
    }

    public void register() {
        Session.getInstance().register(this);
    }

    public Image(UUID uuid, ImageView imageView) {
        this.uuid = uuid;
        this.imageView = imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    @Override
    public void receive(String json)  {
        try {
            Image img = new ObjectMapper().readValue(json, this.getClass());
            this.setX(img.getX());
            this.setY(img.getY());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }
}
