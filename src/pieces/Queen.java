package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

public class Queen extends Piece{
    public Queen(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMove(Position position) {
        return false;
    }
    @Override
    public String toString(){
        return "Queen";
    }
}
