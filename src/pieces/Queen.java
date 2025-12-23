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

        int rankDifference = position.getRank() - this.position.getRank();
        int fileDifference = position.getFile() - this.position.getFile();

        int rankAbs = Math.abs(rankDifference);
        int fileAbs = Math.abs(fileDifference);

        //Moves diagonally
        if (rankDifference != 0 && fileDifference != 0){
            if (rankDifference > 0 && fileDifference > 0){
                for (int i = 0; i < rankDifference - 1; i++){
                    if (board.getBOARD()[++rankPos][++filePos] != null){
                        return false;
                    }
                }
            } else if (rankDifference > 0){
                for (int i = 0; i < rankDifference - 1; i++){
                    if (board.getBOARD()[++rankPos][--filePos] != null){
                        return false;
                    }
                }
            } else if (fileDifference > 0){
                for (int i = 0; i < rankDifference - 1; i++){
                    if (board.getBOARD()[--rankPos][++filePos] != null){
                        return false;
                    }
                }
            }else {
                for (int i = 0; i < rankDifference - 1; i++){
                    if (board.getBOARD()[--rankPos][--filePos] != null){
                        return false;
                    }
                }
            }
        } else if (rankDifference != 0 || fileDifference != 0) {
            if(rankDifference > 0){
                for (int i = 0; i < rankDifference - 1; i++){
                    if (board.getBOARD()[++rankPos][filePos] != null){
                        return false;
                    }
                }
            }else {
                for (int i = 0; i > rankDifference + 1; i--){
                    if (board.getBOARD()[--rankPos][filePos] != null){
                        return false;
                    }
                }
            }
            if(fileDifference > 0){
                for (int i = 0; i < fileDifference - 1; i++){
                    if (board.getBOARD()[rankPos][++filePos] != null){
                        return false;
                    }
                }

            }else {
                for (int i = 0; i > fileDifference + 1; i--){
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
