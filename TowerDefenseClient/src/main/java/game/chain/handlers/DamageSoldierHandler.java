package game.chain.handlers;

import game.chain.requests.DamageSoldierRequest;
import game.chain.requests.Request;

public class DamageSoldierHandler extends Handler {
    @Override
    public void handle(Request request) {
        if (!(request instanceof DamageSoldierRequest)) {
            this.handleNext(request);
            return;
        }

        DamageSoldierRequest damageRequest = (DamageSoldierRequest) request;
        damageRequest.getSoldier()
            .doDamage(damageRequest.getDamage());
    }
}
