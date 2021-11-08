package game.strategy;

import game.entity.Soldier;

public class Range extends Attack{
    @Override
    public boolean attack(Soldier attacker, Soldier target) {
        target.doDamage(attacker.getWeapon().getDamage());
        if (target.isDead())
            return true;

        target.send();
        return false;
    }
}
