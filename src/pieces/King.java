package pieces;

import utils.Color;
import utils.Position;

public class King extends Piece{
    private boolean hasMoved = false;

    public King(Color color, Position position) {
        super(color, position);

    }

    @Override
    public boolean legalMove(Position position) {
        if (!position.legalPosition()) return false;

        int rankPos = this.position.getRank();
        int filePos = this.position.getFile();

        int rankDifference = position.getRank() - this.position.getRank();
        int fileDifference = position.getFile() - this.position.getFile();

        int rankAbs = Math.abs(rankDifference);
        int fileAbs = Math.abs(fileDifference);

        return rankDifference <= 1 && fileDifference <= 1;
    }
    @Override
    public String toString(){
        return "King";
    }
}
