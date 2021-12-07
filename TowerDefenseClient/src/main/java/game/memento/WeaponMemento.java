package game.memento;

import game.adapter.AdaptiveWeapon;

public class WeaponMemento {
    private AdaptiveWeapon weapon;
    private int usesCount;

    public WeaponMemento(AdaptiveWeapon weapon) {
        this.weapon = weapon;
        this.usesCount = weapon.getUsesCount();
    }

    public void getState(WeaponOriginator originator) {
        this.weapon.setUsesCount(this.usesCount);
        originator.setState(weapon);
    }
}
