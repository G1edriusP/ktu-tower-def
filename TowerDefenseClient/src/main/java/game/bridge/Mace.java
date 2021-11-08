package game.bridge;

import game.adapter.MeleeWeapon;

public class Mace implements MeleeWeapon {
    final int damage = 69;
    final int damageFalloff = 2;
    final int minDamage = 50;

    @Override
    public int getBaseMeleeDamage() {
        return this.damage;
    }

    @Override
    public int getDamageFalloff() {
        return this.damageFalloff;
    }

    @Override
    public int getMinimumDamage() {
        return this.minDamage;
    }
}
