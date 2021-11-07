package game.bridge;

import game.adapter.MeleeWeapon;

public class Mace implements MeleeWeapon {
    final int damage = 69;

    @Override
    public int getMeleeDamage() {
        return this.damage;
    }
}
