package game.strategy;

import game.entity.Soldier;

public abstract class Attack {
    public abstract void attack(Soldier attacker, Soldier target);
}
