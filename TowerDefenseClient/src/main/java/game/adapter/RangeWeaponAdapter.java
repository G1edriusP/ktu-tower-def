package game.adapter;

import game.bridge.Weapon;

public class RangeWeaponAdapter extends Weapon {
    private int usesCount;
    private RangeWeapon weapon;

    public RangeWeaponAdapter(RangeWeapon weapon) {
        this.usesCount = 0;
        this.weapon = weapon;
    }

    @Override
    public int getDamage() {
        int damage = weapon.getBaseRangeDamage() + weapon.getDamageIncrease()*usesCount;
        if (damage > weapon.getMaximumDamage())
            damage = weapon.getMaximumDamage();
        usesCount++;
        return damage;
    }
}
