package game.prototype;

import com.fasterxml.jackson.annotation.JsonIgnore;
import game.net.Image;
import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Tile extends Image implements Cloneable {
    protected Direction redPath = Direction.None;  // Where red goes to
    protected Direction bluePath = Direction.None; // Where blue goes to
    protected Obstacle obstacle = Obstacle.None;   // How much it slows down WALKING soldiers

    protected Tile(UUID uuid, ImageView imageView) {
        super(uuid, imageView);
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);
    }

    @Override
    public Tile clone() {
        try {
            Tile tile = (Tile) super.clone();
            tile.setUUID(UUID.randomUUID());
            return tile;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Tile copyDeep()
    {
        Tile copy = this.clone();
        copy.setImageView(new ImageView(this.imageView.getImage()));
        copy.setX(this.getX());
        copy.setY(this.getY());
        return copy;
    }

    public void setRedPath(Direction direction) {
        this.redPath = direction;
    }

    public void setBluePath(Direction direction) {
        this.bluePath = direction;
    }

    public void setObstacle(Obstacle obstacle) { this.obstacle = obstacle; }
}
