package game;

import java.util.UUID;

import net.Serverside;

public class Troop extends Serverside {
    private float x;
    private float y;

    public Troop() {
        super();
    }

    public Troop(UUID uuid) {
        super(uuid);
    }

    @Override
    public String getType() {
        return "troop"; // Our set type
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        System.out.println(uuid + ": setX: " + x);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        System.out.println(uuid + ": setY: " + y);
    }
}
