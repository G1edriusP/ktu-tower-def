package game.builder;

import game.entity.Tower;
import game.factory.Creator;
import game.factory.TowerCreator;
import game.level.Level;
import game.net.Session;
import game.prototype.Obstacle;
import game.prototype.Tile;

import java.net.URISyntaxException;

public class LevelBuilder {
    private TileBuilder builder = new TileBuilder();

    private Tile path;   // 1
    private Tile noWalk; // 0
    private Tile decor;  // 2
    // 3 - red tower
    // 4 - blue tower

    public LevelBuilder newSavannah() {
        this.path = builder.newDirt().build();
        this.noWalk = builder.newSand().build();
        this.decor = builder.newSand().addObstacle(Obstacle.Twigs).build();
        return this;
    }

    public LevelBuilder newGrasslands() {
        this.path = builder.newDirt().build();
        this.noWalk = builder.newGrass().build();
        this.decor = builder.newGrass().addObstacle(Obstacle.Bush).build();
        return this;
    }

    public Level level1() {
        int[][] map = {
            {0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 4, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 2, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 2, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 3, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        Level level = null;
        try {
            level = make(map);
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        return level;
    }

    private Level make(int[][] map) throws URISyntaxException, InterruptedException {
        Creator creator = new TowerCreator();
        Level level = new Level();
        double x = 0;
        double y = 0;
        for (int[] line: map) {
            for (int tileType: line) {
                Tile tile = null;
                switch (tileType) {
                case 0:
                    tile = this.noWalk.copyDeep();
                    break;
                case 1:
                    tile = this.path.copyDeep();
                    break;
                case 2:
                    tile = this.decor.copyDeep();
                    break;
                case 3, 4:
                    tile = this.path.copyDeep();

                    // 3 - red tower
                    // 4 - blue tower
                    boolean friendly = (tileType == 3 && Session.getInstance().isRed()) ||
                            (tileType == 4 && Session.getInstance().isBlue());

                    if (friendly) {
                        Tower tower = creator.createFriendlyTower();
                        tower.setX(x);
                        tower.setY(y);
                        tower.setTroopSpawnTile(tile);
                        level.setFriendlyTower(tower);
                    } else {
                        Tower tower = creator.createEnemyTower();
                        tower.setX(x);
                        tower.setY(y);
                        level.setEnemyTower(tower);
                    }
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
        makePath(level, map);
        return level;
    }

    private void makePath(Level level, int[][] map) {
        // dumdum idea of walking from red tower to a blue one, linking tiles together to make a path.
        int index = getTileIndex(map, 3);
        if (index < 0) {
            return;
        }
        int prev = -1;
        while (true) {
            int next = getAdjacentPathIndex(map, index, prev);
            if (next < 0) {
                break;
            }

            Tile a = level.getTiles().get(index);
            Tile b = level.getTiles().get(next);
            a.setRedPath(b);
            b.setBluePath(a);

            prev = index;
            index = next;
        }
    }

    private static int getTileIndex(int[][] map, int target) {
        int i = 0;
        for (int[] line: map) {
            for (int tileType: line) {
                if (tileType == target) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    private static int getAdjacentPathIndex(int[][] map, int fromIndex, int excludeIndex) {
        int x = fromIndex % map[0].length;
        int y = fromIndex / map[0].length;
        for (int i = -1 ; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == j || i == -j) {
                    continue; // we don't care about diagonals
                }
                if (x + i < 0 || x + i >= map[0].length) {
                    continue; // out of range x
                }
                if (y + j < 0 || y + j >= map.length) {
                    continue; // out of range y
                }
                int tileType = map[y+j][x+i];
                if (tileType != 1 && tileType != 3 && tileType != 4) {
                    continue; // not path or a tower
                }
                int index = (y+j) *map[0].length + (x + i);
                if (index == excludeIndex) {
                    continue;
                }
                return index;
            }
        }
        return -1;
    }
}
