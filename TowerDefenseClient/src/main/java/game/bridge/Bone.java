package game.bridge;

public class Bone extends Weapon {
    final int damage = 50;

    @Override
    public int getDamage() {
        return damage;
    }
}
