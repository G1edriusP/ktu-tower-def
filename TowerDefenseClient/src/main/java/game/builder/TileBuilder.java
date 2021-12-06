package game.builder;

import game.composite.*;
import game.prototype.*;

public class TileBuilder {
    protected CompositeTile tile;

    public TileBuilder newGrass() {
        this.tile = new CompositeTile(new GrassTile());
        return this;
    }

    public TileBuilder newDirt() {
        this.tile = new CompositeTile(new DirtTile());
        return this;
    }

    public TileBuilder newSand() {
        this.tile = new CompositeTile(new SandTile());
        return this;
    }

    public TileBuilder addObstacle() {
        boolean random = this.tile.getUUID().getMostSignificantBits() % 2 == 0;
        switch (this.tile.getType()) {
            case "grass-tile":
                this.tile.addTile(random ? new BushTile() : new PuddleTile());
                break;
            case "sand-tile":
                this.tile.addTile(random ? new CactusTile() : new TwigsTile());
                break;
            default:
                this.tile.addTile(new TwigsTile());
                break;
        }
        return this;
    }

    public CompositeTile build() {
        return this.tile;
    }
}
