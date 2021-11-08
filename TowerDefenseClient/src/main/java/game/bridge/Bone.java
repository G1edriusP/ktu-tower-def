package game.bridge;

import game.adapter.MeleeWeapon;

public class Bone implements MeleeWeapon {
    final int damage = 50;
    final int damageFalloff = 5;
    final int minDamage = 30;

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
