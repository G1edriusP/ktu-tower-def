package game.prototype;

import game.net.Image;
import javafx.scene.image.ImageView;

import java.util.UUID;

abstract public class Tile extends Image implements Cloneable {
    protected Tile redPath = null;
    protected Tile bluePath = null;

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

    public void setRedPath(Tile tile) {
        this.redPath = tile;
    }

    public void setBluePath(Tile tile) {
        this.bluePath = tile;
    }

    public void setObstacle(Obstacle obstacle) { this.obstacle = obstacle; }

    public Tile getRedPath() {
        return this.redPath;
    }

    public Tile getBluePath() {
        return this.bluePath;
    }
}
