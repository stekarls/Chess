package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

public class Bishop extends Piece{
    private final ChessBoard board;
    public Bishop(Color color, Position position, ChessBoard board) {
        super(color, position);
        this.board = board;
    }

    @Override
    public boolean legalMove(Position position) {

        if (!position.legalPosition()) return false;

        int rankPos = this.position.getRank();
        int filePos = this.position.getFile();

        int rankSteps = position.getRank() - this.position.getRank();
        int fileSteps = position.getFile() - this.position.getFile();

        if (Math.abs(rankSteps) != Math.abs(fileSteps)){
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
