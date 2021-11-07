package game.factory;

import game.entity.blue.BlueTower;
import game.entity.red.RedTower;
import game.entity.Tower;
import game.net.Session;

public class TowerCreator extends Creator {
    @Override
    public Tower createFriendlyTower() {
        if (Session.getInstance().isRed()) {
            return new RedTower();
        }
        return new BlueTower();
    }

    @Override
    public Tower createEnemyTower() {
        if (Session.getInstance().isRed()) {
            return new BlueTower();
        }
        return new RedTower();
    }
}
