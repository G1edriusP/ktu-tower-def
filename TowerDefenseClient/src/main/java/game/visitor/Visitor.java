package game.visitor;

import game.prototype.Tile;

public interface Visitor {
    Tile visit(Tile start, boolean isRed);
}
