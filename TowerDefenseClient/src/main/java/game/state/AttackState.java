package game.state;

import game.chain.ArmyManager;
import game.chain.requests.RemoveSubjectRequest;
import game.entity.Soldier;

public class AttackState extends State {
    private Soldier target;

    public AttackState(Soldier target) {
        this.target = target;
    }

    @Override
    public State doOperation(Soldier soldier) {
        boolean killed = soldier.attack(this.target);
        if (killed) ArmyManager.getInstance().add(new RemoveSubjectRequest(target));
        return null;
    }
}
