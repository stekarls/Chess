package pieces;

import utils.Color;
import utils.Position;

public class Knight extends Piece{
    public Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMove() {
        return false;
    }
}
