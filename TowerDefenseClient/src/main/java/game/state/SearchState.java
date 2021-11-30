package game.state;

import game.entity.Soldier;

public class SearchState extends State{
    @Override
    public State doOperation(Soldier soldier) {
        Soldier target = soldier.getTarget();
        if (target != null) {
            return new AttackState(target);
        }
        return new MoveState();
    }
}
