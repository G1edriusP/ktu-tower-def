package game.bridge;

import game.adapter.RangeWeapon;

public class Bow implements RangeWeapon {
    final int damage = 39;

    @Override
    public int getRangeDamage() {
        return this.damage;
    }
}
