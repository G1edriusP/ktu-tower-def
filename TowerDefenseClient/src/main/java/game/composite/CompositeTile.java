package game.composite;

import game.prototype.Tile;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CompositeTile extends Tile {
    private List<Tile> tiles = new ArrayList<>();
    private String type;

    public CompositeTile(Tile tile) {
        super(UUID.randomUUID(), tile.getImageView());
        this.type = tile.getType();
    }

    public Tile copyDeep()
    {
        CompositeTile copy = (CompositeTile) this.clone();
        copy.setImageView(new ImageView(this.imageView.getImage()));
        copy.setX(this.getX());
        copy.setY(this.getY());
        copy.tiles = new ArrayList<>();
        tiles.forEach(tile -> copy.tiles.add(tile.copyDeep()));
        return copy;
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    public void removeTile(Tile tile) {
        this.tiles.remove(tile);
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        this.tiles.forEach(tile -> tile.setY(y));
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        this.tiles.forEach(tile -> tile.setX(x));
    }
}