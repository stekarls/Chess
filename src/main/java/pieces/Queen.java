package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

public class Queen extends Piece{
    public Queen(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board){

        if (!targetSquare.legalPosition()) return false;

        int rankPos = this.position.getRank();
        int filePos = this.position.getFile();

        int rankDifference = targetSquare.getRank() - this.position.getRank();
        int fileDifference = targetSquare.getFile() - this.position.getFile();

        if (rankDifference == 0 && fileDifference == 0) return false;

        int rankStep = Integer.compare(rankDifference, 0);
        int fileStep = Integer.compare(fileDifference, 0);

        int rankAbs = Math.abs(rankDifference);
        int fileAbs = Math.abs(fileDifference);

        boolean isStraight = (rankDifference == 0) ^ (fileDifference == 0);
        boolean isDiagonal = rankAbs == fileAbs;

        if (!isStraight && !isDiagonal) return false;

        int steps = Math.max(rankAbs, fileAbs);

        for (int i = 1; i < steps; i++){
            rankPos += rankStep;
            filePos += fileStep;
            if (board.getBOARD()[rankPos][filePos] != null){
                return false;
            }
        }

        Piece destination = board.getBOARD()[targetSquare.getRank()][targetSquare.getFile()];
        if (destination != null && destination.getColor() == this.color){
            return false;
        }

        return true;














        //Moves diagonally
//        if (rankDifference != 0 && fileDifference != 0){
//            if (rankDifference > 0 && fileDifference > 0){
//                for (int i = 0; i < rankDifference - 1; i++){
//                    if (board.getBOARD()[++rankPos][++filePos] != null){
//                        return false;
//                    }
//                }
//            } else if (rankDifference > 0){
//                for (int i = 0; i < rankDifference - 1; i++){
//                    if (board.getBOARD()[++rankPos][--filePos] != null){
//                        return false;
//                    }
//                }
//            } else if (fileDifference > 0){
//                for (int i = 0; i < rankDifference - 1; i++){
//                    if (board.getBOARD()[--rankPos][++filePos] != null){
//                        return false;
//                    }
//                }
//            }else {
//                for (int i = 0; i < rankDifference - 1; i++){
//                    if (board.getBOARD()[--rankPos][--filePos] != null){
//                        return false;
//                    }
//                }
//            }
//        } else if (rankDifference != 0 || fileDifference != 0) {
//            if(rankDifference > 0){
//                for (int i = 0; i < rankDifference - 1; i++){
//                    if (board.getBOARD()[++rankPos][filePos] != null){
//                        return false;
//                    }
//                }
//            }else {
//                for (int i = 0; i > rankDifference + 1; i--){
//                    if (board.getBOARD()[--rankPos][filePos] != null){
//                        return false;
//                    }
//                }
//            }
//            if(fileDifference > 0){
//                for (int i = 0; i < fileDifference - 1; i++){
//                    if (board.getBOARD()[rankPos][++filePos] != null){
//                        return false;
//                    }
//                }
//
//            }else {
//                for (int i = 0; i > fileDifference + 1; i--){
//                    if (board.getBOARD()[rankPos][--filePos] != null){
//                        return false;
//                    }
//                }
//            }
//
//        }
//        return true;
    }

    @Override
    public String toString(){
        return "Queen";
    }
}
