package game.bridge;

import game.adapter.MeleeWeapon;

public class Bone implements MeleeWeapon {
    final int damage = 50;

    @Override
    public int getMeleeDamage() {
        return this.damage;
    }
}
