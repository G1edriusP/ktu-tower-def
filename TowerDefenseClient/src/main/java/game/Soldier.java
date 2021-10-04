package game;

import java.util.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.ImageView;
import net.ServersideImage;

public class Soldier extends ServersideImage {
    private double x;
    private double y;

    public Soldier() {
        this(UUID.randomUUID());
    }

    public Soldier(UUID uuid) {
        super(
            uuid,
            new ImageView("ethan.jpeg")
        );
    }

    @Override
    public String getType() {
        return "troop"; // Our set type
    }

    @Override
    public void receive(String json) throws JsonProcessingException {
        Soldier troop = new ObjectMapper().readValue(json, Soldier.class);
        this.setX(troop.getX());
        this.setY(troop.getY());
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
