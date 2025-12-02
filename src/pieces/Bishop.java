package pieces;

import utils.Color;
import utils.Position;

public class Bishop extends Piece{
    public Bishop(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMove(Position position) {
        return false;
    }
}
