package game.bridge;

import game.adapter.RangeWeapon;

public class SpiritOrb implements RangeWeapon {
    final int damage = 24;

    @Override
    public int getRangeDamage() {
        return this.damage;
    }
}
