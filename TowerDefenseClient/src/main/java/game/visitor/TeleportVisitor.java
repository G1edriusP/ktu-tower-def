package game.visitor;

import game.prototype.Tile;

public class TeleportVisitor extends Visitor {
    @Override
    public Tile visit(Tile start, boolean isRed) {
        if (start == null)
            return null;

        Tile path = isRed ? start.getRedPath() : start.getBluePath();
        if (path == null)
            return null;

        Tile next = isRed ? path.getRedPath() : path.getBluePath();
        if (next != null)
            path = next;
        return path;
    }
}
