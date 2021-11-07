package game.adapter;

import game.bridge.Weapon;

public class RangeWeaponAdapter extends Weapon {
    private RangeWeapon weapon;

    public RangeWeaponAdapter(RangeWeapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public int getDamage() {
        return this.weapon.getRangeDamage();
    }
}
