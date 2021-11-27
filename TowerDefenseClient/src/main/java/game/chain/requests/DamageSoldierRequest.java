package game.chain.requests;

import game.entity.Soldier;

public class DamageSoldierRequest implements Request{
    private Soldier soldier;
    private int damage;

    public DamageSoldierRequest(Soldier soldier, int damage) {
        this.soldier = soldier;
        this.damage = damage;
    }

    public Soldier getSoldier() {
        return this.soldier;
    }

    public int getDamage() {
        return this.damage;
    }
}
