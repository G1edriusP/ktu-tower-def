package game.chain.handlers;

import game.chain.requests.MoveSoldierRequest;
import game.chain.requests.Request;
import game.entity.Soldier;
import game.prototype.Tile;

public class MoveSoldierHandler extends Handler {
    @Override
    public void handle(Request request) {
        if (!(request instanceof MoveSoldierRequest)) {
            this.handleNext(request);
            return;
        }

        MoveSoldierRequest moveRequest = (MoveSoldierRequest) request;

        Soldier soldier = moveRequest.getSoldier();
        Tile tile = moveRequest.getTile();

        soldier.setX(tile.getX());
        soldier.setY(tile.getY());
        soldier.setTile(tile);
        soldier.send();
    }
}
