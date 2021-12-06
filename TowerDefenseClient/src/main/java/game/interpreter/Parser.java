package game.interpreter;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;

public class Parser {
    public static Expression parseNode(ObjectNode node) {
        try{
            if (node.has("action")) {
                String type = node.get("action").asText("");
                String arg;

                if (Objects.equals(type, "delete")) {
                    arg = node.get("uuid").asText();
                } else {
                    arg = node.get("red").asBoolean()? "red" : "blue";
                }
                return new ActionExpression(new LiteralExpression(type), new LiteralExpression(arg));
            }

            if (!node.has("uuid")) {
                return null;
            }
            return new UUIDExpression(node.get("uuid").asText());

        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
