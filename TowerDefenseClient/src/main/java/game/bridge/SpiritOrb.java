package game.bridge;

import game.adapter.RangeWeapon;

public class SpiritOrb implements RangeWeapon {
    final int damage = 24;
    final int damageIncrease = 6;
    final int maxDamage = 60;

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
