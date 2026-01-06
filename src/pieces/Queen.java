package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

public class Queen extends Piece{
    private final ChessBoard board;
    public Queen(Color color, Position position, ChessBoard board) {
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

        int rankAbs = Math.abs(rankSteps);
        int fileAbs = Math.abs(fileSteps);

        if((rankAbs != fileAbs) && (rankSteps != 0 && fileSteps != 0)){
            return false;
        }

        if (rankAbs == fileAbs){
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
        }else{
            if(rankSteps > 0){
                for (int i = 0; i < rankSteps - 1; i++){
                    if (board.getBOARD()[++rankPos][filePos] != null){
                        return false;
                    }
                }
            }else {
                for (int i = 0; i > rankSteps + 1; i--){
                    if (board.getBOARD()[--rankPos][filePos] != null){
                        return false;
                    }
                }
            }
            if(fileSteps > 0){
                for (int i = 0; i < fileSteps - 1; i++){
                    if (board.getBOARD()[rankPos][++filePos] != null){
                        return false;
                    }
                }

            }else {
                for (int i = 0; i > fileSteps + 1; i--){
                    if (board.getBOARD()[rankPos][--filePos] != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString(){
        return "Queen";
    }
}
