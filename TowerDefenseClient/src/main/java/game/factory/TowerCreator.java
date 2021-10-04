package game.factory;

import game.entity.blue.BlueTower;
import game.entity.red.RedTower;
import game.entity.Tower;
import game.net.Session;

import java.net.URISyntaxException;

public class TowerCreator extends Creator {
    @Override
    public Tower createTower() {
        try {
            if (Session.getInstance().isRed()) {
                return new RedTower();
            }
            return new BlueTower();

        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
