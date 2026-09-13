package pieces;

import utils.ChessBoard;
import enums.Color;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(Color color, Position position) {
        super(color, position);
        super.pieceValue = 9;
    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board) {

        if (!targetSquare.legalPosition()) return false;

        if (!targetSquare.getSquareColor().equals(this.position.getSquareColor())) return false;

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
                if (board.getBoard()[++rankPos][++filePos] != null){
                    return false;
                }
            }
        } else if (rankSteps > 0 && fileSteps < 0){
            for (int i = 0; i < rankSteps - 1; i++){
                if (board.getBoard()[++rankPos][--filePos] != null){
                    return false;
                }
            }
        } else if (rankSteps < 0 && fileSteps > 0){ // -7
            for (int i = 0; i > rankSteps + 1; i--){
                if (board.getBoard()[--rankPos][++filePos] != null){
                    return false;
                }
            }
        }else {
            for (int i = 0; i > rankSteps + 1; i--){
                if (board.getBoard()[--rankPos][--filePos] != null){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<Position> getMoves(ChessBoard board) {
        List<Position> moveList = new ArrayList<>();
        addMovesToMoveList(board, moveList, 1, 1);
        addMovesToMoveList(board, moveList, 1, -1);
        addMovesToMoveList(board, moveList, -1, 1);
        addMovesToMoveList(board, moveList, -1, -1);

        return moveList;
    }

    private void addMovesToMoveList(ChessBoard board, List<Position> moveList, int rankDelta, int fileDelta){
        Position pieceSquare = this.getPosition();
        int rank = pieceSquare.getRank() + rankDelta;
        int file = pieceSquare.getFile() + fileDelta;

        while (true){
            Position nextSquare = new Position(rank, file);
            if (!nextSquare.legalPosition()) break;

            Piece enemyPiece = board.getPieceAt(nextSquare);
            if (enemyPiece != null){
                if (!enemyPiece.getColor().equals(this.color)){
                    moveList.add(nextSquare);
                }
                break;
            }
            moveList.add(nextSquare);
            rank += rankDelta;
            file += fileDelta;
        }
    }


    @Override
    public String toString(){
        return "Bishop";
    }
}
