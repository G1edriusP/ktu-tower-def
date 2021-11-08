package game.bridge;

import game.adapter.RangeWeapon;

public class Bow implements RangeWeapon {
    final int damage = 39;
    final int damageIncrease = 4;
    final int maxDamage = 50;

    @Override
    public int getBaseRangeDamage() {
        return this.damage;
    }

    @Override
    public int getDamageIncrease() {
        return this.damageIncrease;
    }

    @Override
    public int getMaximumDamage() {
        return this.maxDamage;
    }
}
