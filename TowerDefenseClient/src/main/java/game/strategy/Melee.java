package game.strategy;

import game.entity.Soldier;

public class Melee extends Attack{
    @Override
    public boolean attack(Soldier attacker, Soldier target) {
        target.doDamage(attacker.getWeaponOriginator().getDamage());
        if (target.isDead())
            return true;

        target.send();
        return false;
    }
}
