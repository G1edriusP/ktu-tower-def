package game.interpreter;

public class LiteralExpression implements Expression {
    private String literal;

    public LiteralExpression(String literal) {
        this.literal = literal;
    }

    @Override
    public String execute() {
        return this.literal;
    }
}
