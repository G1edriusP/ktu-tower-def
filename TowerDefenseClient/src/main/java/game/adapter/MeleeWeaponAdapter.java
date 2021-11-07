package game.adapter;

import game.bridge.Bone;
import game.bridge.Weapon;

public class MeleeWeaponAdapter extends Weapon {
    private MeleeWeapon weapon;

    public MeleeWeaponAdapter(MeleeWeapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public int getDamage() {
        return this.weapon.getMeleeDamage();
    }
}
