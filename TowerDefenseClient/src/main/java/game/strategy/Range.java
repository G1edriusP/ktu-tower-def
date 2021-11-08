package game.strategy;

import game.entity.Soldier;

public class Range extends Attack{
    @Override
    public void attack(Soldier attacker, Soldier target) {
        target.doDamage(attacker.getWeapon().getDamage());
        if (target.isDead()) {
            target.sendDelete();
        } else {
            target.send();
        }
    }
}
