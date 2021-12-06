package game.interpreter;

import game.net.Session;

import java.util.UUID;

public class ActionExpression implements Expression {
    private Expression type, arg;

    public ActionExpression(Expression type) {
        this(type, null);
    }

    public ActionExpression(Expression type, Expression arg) {
        this.type = type;
        this.arg = arg;
    }

    public String execute() {
        Session session = Session.getInstance();
        String team;

        switch (type.execute()) {
            case "sessionStart":
                team = arg.execute();

                session.setTeam(team == "red");
                session.getStarted().setValue(true);
                return "start:" + team;

            case "winner":
                team = arg.execute();

                session.setTeam(team == "red");
                session.getStarted().setValue(false);
                return "stop";

            case "delete":
                UUID uuid = UUID.fromString(arg.execute());
                session.unregister(uuid);
                return "delete:"+uuid.toString();

            default:
                return "<INVALID_ACTION>";
        }
    }
}
