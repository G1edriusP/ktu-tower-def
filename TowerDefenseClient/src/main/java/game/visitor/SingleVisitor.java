package game.visitor;

import game.prototype.Tile;

public class SingleVisitor extends Visitor{
    @Override
    public Tile visit(Tile start, boolean isRed) {
        if (start == null)
            return null;
        return isRed ? start.getRedPath() : start.getBluePath();
    }
}
