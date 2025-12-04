package pieces;

import utils.Color;
import utils.Position;

import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMove(Position targetPosition) {
        if (!position.legalPosition()) return false;

        int rankDifference = Math.abs(targetPosition.getRank() - this.position.getRank());
        int fileDifference = Math.abs(targetPosition.getFile() - this.position.getFile());

        boolean lPattern1 = (rankDifference == 2) && (fileDifference == 1);
        boolean lPattern2 = (rankDifference == 1) && (fileDifference == 2);

        return lPattern1 || lPattern2;
    }

    @Override
    public String toString(){
        return "Knight";
    }
}
