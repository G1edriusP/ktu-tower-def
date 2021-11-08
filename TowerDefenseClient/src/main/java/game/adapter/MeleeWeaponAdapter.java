package game.adapter;

import game.bridge.Weapon;

public class MeleeWeaponAdapter extends Weapon {
    private int usesCount;
    private MeleeWeapon weapon;

    public MeleeWeaponAdapter(MeleeWeapon weapon) {
        this.usesCount = 0;
        this.weapon = weapon;
    }

    @Override
    public int getDamage() {
        int damage = weapon.getBaseMeleeDamage() - weapon.getDamageFalloff()*usesCount;
        if (damage < weapon.getMinimumDamage())
            damage = weapon.getMinimumDamage();
        usesCount++;
        return damage;
    }
}
