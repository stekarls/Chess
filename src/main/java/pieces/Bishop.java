package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

public class Bishop extends Piece{
    public Bishop(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board) {

        if (!targetSquare.legalPosition()) return false;

        int rankPos = this.position.getRank();
        int filePos = this.position.getFile();

        int rankSteps = targetSquare.getRank() - this.position.getRank();
        int fileSteps = targetSquare.getFile() - this.position.getFile();

        int rankAbs = Math.abs(rankSteps);
        int fileAbs = Math.abs(fileSteps);

        if (rankAbs != fileAbs){
            return false;
        }
        if (rankSteps > 0 && fileSteps > 0){
            for (int i = 0; i < rankSteps - 1; i++){
                if (board.getBOARD()[++rankPos][++filePos] != null){
                    return false;
                }
            }
        } else if (rankSteps > 0 && fileSteps < 0){
            for (int i = 0; i < rankSteps - 1; i++){
                if (board.getBOARD()[++rankPos][--filePos] != null){
                    return false;
                }
            }
        } else if (rankSteps < 0 && fileSteps > 0){
            for (int i = 0; i < rankSteps - 1; i++){
                if (board.getBOARD()[--rankPos][++filePos] != null){
                    return false;
                }
            }
        }else {
            for (int i = 0; i < rankSteps - 1; i++){
                if (board.getBOARD()[--rankPos][--filePos] != null){
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public String toString(){
        return "Bishop";
    }
}
