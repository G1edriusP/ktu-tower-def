package game.net;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.net.URISyntaxException;
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

    public void send()  {
        try {
            Session.getInstance().send(new ObjectMapper().writeValueAsString(this));
        } catch (URISyntaxException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void register() throws URISyntaxException, InterruptedException, JsonProcessingException{
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

    public void addToGroup(Group group) {
        group.getChildren().add(this.getImageView());
    }

    @Override
    public void receive(String json) throws JsonProcessingException {
        Image img = new ObjectMapper().readValue(json, this.getClass());
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

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }
}
