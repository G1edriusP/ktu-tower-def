package game.factory;

import game.entity.Tower;

abstract public class Creator {
    abstract public Tower createFriendlyTower();
    abstract public Tower createEnemyTower();
}
