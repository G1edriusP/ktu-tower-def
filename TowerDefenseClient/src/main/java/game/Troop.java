package game;

import java.net.URISyntaxException;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import net.Serverside;
import net.Session;

public class Troop extends Serverside {
    private double x;
    private double y;
    @JsonIgnore
    private Circle object;

    public Troop() {
        super();
        this.object = new Circle(x, y, 20, Paint.valueOf("#556a32"));
        this.object.setOpacity(0.7);
    }

    public Troop(UUID uuid) {
        super(uuid);
        this.object = new Circle(x, y, 20, Paint.valueOf("#556a32"));
        this.object.setOpacity(0.7);
    }

    @Override
    public String getType() {
        return "troop"; // Our set type
    }

    @Override
    public void receive(String json) throws JsonProcessingException {
        Troop troop = new ObjectMapper().readValue(json, Troop.class);
        this.setX(troop.getX());
        this.setY(troop.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        this.object.setCenterX(x);
        System.out.println(uuid + ": setX: " + x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        this.object.setCenterY(y);
        System.out.println(uuid + ": setY: " + y);
    }

    public Circle getObject() {

        return object;
    }

    public void setObject(Circle object) {
        this.object = object;
        System.out.println(uuid + ": setObject: " + object);
    }
}
