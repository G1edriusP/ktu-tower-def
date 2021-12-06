package game.template;

import game.builder.LevelBuilder;

public final class SavannahLevel extends LevelBuilder {
    public SavannahLevel() {
        this.path = builder.newDirt().build();
        this.noWalk = builder.newSand().build();
        this.decor = builder.newSand().addObstacle().build();
    }

    @Override
    public int[][] level() {
        return new int[][]{
            {0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0},
            {0, 1, 1, 1, 1, 1, 3, 0, 0, 4, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0},
            {0, 1, 2, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0},
            {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 2, 1, 0},
            {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 2},
            {0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0},
            {0, 1, 1, 1, 0, 1, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 2, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 2, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 1, 1, 1, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        };
    }
}
