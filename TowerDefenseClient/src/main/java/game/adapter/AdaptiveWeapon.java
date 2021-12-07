package game.adapter;

import game.bridge.Weapon;

public abstract class AdaptiveWeapon extends Weapon {
    protected int usesCount = 0;

    public int getUsesCount() {
        return usesCount;
    }

    public void setUsesCount(int usesCount) {
        this.usesCount = usesCount;
    }
}
