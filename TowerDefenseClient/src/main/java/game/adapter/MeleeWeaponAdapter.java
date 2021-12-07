package game.adapter;

import game.memento.WeaponMemento;

public class MeleeWeaponAdapter extends AdaptiveWeapon {
    private MeleeWeapon weapon;

    public MeleeWeaponAdapter(MeleeWeapon weapon) {
        super();
        this.weapon = weapon;
    }

    @Override
    public int getDamage() {
        int damage = weapon.getBaseMeleeDamage() - weapon.getDamageFalloff()*this.usesCount;
        if (damage < weapon.getMinimumDamage())
            damage = weapon.getMinimumDamage();
        this.usesCount++;
        return damage;
    }
}
