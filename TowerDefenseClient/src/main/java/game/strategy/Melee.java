package game.strategy;

import game.entity.Soldier;

public class Melee extends Attack{
    @Override
    public void attack(Soldier target) {
        target.doDamage(60);

        if (target.isDead()) {
            target.sendDelete();
        } else {
            target.send();
        }
    }
}
