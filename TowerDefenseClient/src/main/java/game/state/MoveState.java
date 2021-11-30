package game.state;

import game.entity.Soldier;

public class MoveState extends State {
    @Override
    public State doOperation(Soldier soldier) {
        boolean moved = soldier.move();
        if (!moved) {
            return new VictoryState();
        }
        return null;
    }
}
