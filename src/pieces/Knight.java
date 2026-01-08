package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board){
        if (!targetSquare.legalPosition()) return false;

        int rankDifference = Math.abs(targetSquare.getRank() - this.position.getRank());
        int fileDifference = Math.abs(targetSquare.getFile() - this.position.getFile());

        boolean lPattern1 = (rankDifference == 2) && (fileDifference == 1);
        boolean lPattern2 = (rankDifference == 1) && (fileDifference == 2);

        return lPattern1 || lPattern2;
    }

    @Override
    public String toString(){
        return "Knight";
    }
}
