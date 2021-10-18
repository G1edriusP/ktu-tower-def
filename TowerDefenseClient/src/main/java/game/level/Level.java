package game.level;

import game.prototype.Tile;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Tile> tiles;

    public Level() {
        this.tiles = new ArrayList<>();
    }

    public void append(Tile tile) {
        this.tiles.add(tile);
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
