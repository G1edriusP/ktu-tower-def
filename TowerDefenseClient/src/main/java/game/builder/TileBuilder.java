package game.builder;

import game.prototype.*;

public class TileBuilder {
    protected Tile tile;

    public TileBuilder newGrass() {
        this.tile = new GrassTile();
        return this;
    }

    public TileBuilder newDirt() {
        this.tile = new DirtTile();
        return this;
    }

    public TileBuilder newSand() {
        this.tile = new SandTile();
        return this;
    }

    public TileBuilder addRedPath(Direction direction) {
        this.tile.setRedPath(direction);
        return this;
    }

    public TileBuilder addBluePath(Direction direction) {
        this.tile.setBluePath(direction);
        return this;
    }

    public TileBuilder addObstacle(Obstacle obstacle) {
        this.tile.setObstacle(obstacle);
        return this;
    }

    public Tile build() {
        return this.tile;
    }
}
