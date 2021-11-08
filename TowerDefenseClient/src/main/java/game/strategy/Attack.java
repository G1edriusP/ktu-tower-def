package game.strategy;

import game.entity.Soldier;

public abstract class Attack {
    public abstract boolean attack(Soldier attacker, Soldier target);
}
