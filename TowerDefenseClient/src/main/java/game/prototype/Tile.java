package game.prototype;

import game.composite.CompositeTile;
import game.net.Image;
import game.singleton.ImageStore;
import javafx.scene.Group;
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

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
        if (this instanceof SandTile) {
            javafx.scene.image.Image image;
            switch (obstacle) {
                case Twigs:
                    image = ImageStore.getInstance().getImage("tiles/sand-twigs.jpg");
                    break;
                default:
                    image = ImageStore.getInstance().getImage("tiles/sand.jpg");
            }
            this.setImageView(new ImageView(image));

        } else if (this instanceof GrassTile) {
            javafx.scene.image.Image image;
            switch (obstacle) {
                case Bush:
                    image = ImageStore.getInstance().getImage("tiles/grass-bush.jpg");
                    break;
                default:
                    image = ImageStore.getInstance().getImage("tiles/grass.jpg");
            }
            this.setImageView(new ImageView(image));
        }
    }

    public Tile getRedPath() {
        return this.redPath;
    }

    public Tile getBluePath() {
        return this.bluePath;
    }

    public void addToGroup(Group group) {
        group.getChildren().add(this.imageView);

        if (this instanceof CompositeTile) {
            ((CompositeTile) this).getTiles().forEach(tile -> tile.addToGroup(group));
        }
    }
}
