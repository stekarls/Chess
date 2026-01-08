package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

import java.util.ArrayList;

public class Rook extends Piece{
    private boolean hasMoved = false;


    public Rook(Color color, Position position){
        super(color, position);
    }


    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board){

        if (!targetSquare.legalPosition()) return false;

        int rankPos = this.position.getRank();
        int filePos = this.position.getFile();

        int rankSteps = targetSquare.getRank() - this.position.getRank();
        int fileSteps = targetSquare.getFile() - this.position.getFile();

        if (rankSteps != 0 && fileSteps != 0){
            return false;
        }

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
        return true;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    @Override
    public String toString(){
        return "Rook";
    }
}
