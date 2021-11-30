package game.state;

import game.entity.Soldier;
import game.net.Session;

public class VictoryState extends State {
    @Override
    public State doOperation(Soldier soldier) {
        Session session = Session.getInstance();
        session.send("{\"action\":\"winner\",\"red\":"+(session.isRed()?"true":"false")+"}");
        return null;
    }
}
