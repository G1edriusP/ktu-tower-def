package game.interpreter;

import java.util.UUID;

public class UUIDExpression implements  Expression {
    private UUID uuid;

    public UUIDExpression(UUID uuid) {
        this.uuid = uuid;
    }

    public UUIDExpression(String uuid) {
        this(UUID.fromString(uuid));
    }

    @Override
    public String execute() {
        return uuid.toString();
    }
}
