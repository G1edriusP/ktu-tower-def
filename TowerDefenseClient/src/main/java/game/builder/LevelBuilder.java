package game.builder;

import game.level.Level;
import game.prototype.Obstacle;
import game.prototype.Tile;

public class LevelBuilder {
    private TileBuilder builder = new TileBuilder();

    private Tile path;   // 2
    private Tile noWalk; // 1
    private Tile decor;  // 3

    public LevelBuilder newSavannah() {
        this.path = builder.newDirt().build();
        this.noWalk = builder.newSand().build();
        this.decor = builder.newSand().addObstacle(Obstacle.Twigs).build();
        return this;
    }

    public LevelBuilder newGrasslands() {
        this.path = builder.newDirt().build();
        this.noWalk = builder.newGrass().build();
        this.decor = builder.newGrass().addObstacle(Obstacle.Tree).build();
        return this;
    }

    public Level level1() {
        int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1},
            {1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 3, 1, 1, 1},
            {1, 1, 1, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1},
            {1, 1, 1, 3, 2, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 3, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1},
            {1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        return make(map);
    }

    private Level make(int[][] map) {
        Level level = new Level();
        double x = 0;
        double y = 0;
        for (int[] line: map) {
            for (int tileType: line) {
                Tile tile = null;
                switch (tileType) {
                case 1:
                    tile = this.noWalk.copyDeep();
                    break;
                case 2:
                    tile = this.path.copyDeep();
                    break;
                case 3:
                    tile = this.decor.copyDeep();
                    break;
                }
                tile.setX(x);
                tile.setY(y);
                level.append(tile);
                x += 64;
            }
            x = 0;
            y += 64;
        }
        return level;
    }
}
