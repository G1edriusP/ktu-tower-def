package game.visitor;

import game.prototype.Tile;

public abstract class Visitor {
    public abstract Tile visit(Tile start, boolean isRed);
}
