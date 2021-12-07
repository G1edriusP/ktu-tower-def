package game.memento;

import game.adapter.AdaptiveWeapon;
import game.bridge.Weapon;

public class WeaponOriginator {
    private AdaptiveWeapon weapon;

    public WeaponOriginator(AdaptiveWeapon weapon) {
        this.weapon = weapon;
    }

    public WeaponMemento createMemento() {
        return new WeaponMemento(weapon);
    }

    public void restore(WeaponMemento memento) {
        memento.getState(this);
    }

    public void setState(AdaptiveWeapon weapon) {
        this.weapon = weapon;
        System.out.println("weapon reset");
    }

    public Weapon getWeapon() {
        return this.weapon;
    }
}
