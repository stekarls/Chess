package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

public class King extends Piece{
    private boolean hasMoved = false;

    public King(Color color, Position position) {
        super(color, position);

    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board){
        if (!targetSquare.legalPosition()) return false;

        int rankDifference = targetSquare.getRank() - this.position.getRank();
        int fileDifference = targetSquare.getFile() - this.position.getFile();

        if (rankDifference == 0 && fileDifference == 0) return false;

        return Math.abs(rankDifference) <= 1 && Math.abs(fileDifference) <= 1;
    }


    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved(){
        return hasMoved;
    }
    @Override
    public String toString(){
        return "King";
    }
}
