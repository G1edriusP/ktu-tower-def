package game.strategy;

import game.entity.Soldier;

public class Range extends Attack{
    @Override
    public void attack(Soldier target) {
        target.doDamage(39);

        if (target.isDead()) {
            target.sendDelete();
        } else {
            target.send();
        }
    }
}
