package pieces;

import utils.ChessBoard;
import enums.Color;
import utils.MoveRecord;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    public Pawn(Color color, Position position) {
        super(color, position);
        super.pieceValue = 1;
    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board) {
        if (!targetSquare.legalPosition()) return false;

        ArrayList<Position> legalMoves = new ArrayList<>();

        Color color = this.getColor();
        int value = color.equals(Color.WHITE) ? 1 : -1;


        MoveRecord lastMove = board.getMoveHistoryStack().peek();
        if (board.getEnPassant() != null && lastMove != null){
            if (!board.getPieceAt(lastMove.toPos()).getColor().equals(color)){
                int rankDiff = Math.abs(targetSquare.getRank() - this.getPosition().getRank());
                int fileDiff = Math.abs(targetSquare.getFile() - this.getPosition().getFile());
                if (rankDiff < 2 && fileDiff < 2){
                    legalMoves.add(board.getEnPassant());
                }
            }
        }

        Piece enemyPiece = board.getPieceAt(targetSquare);
        if (enemyPiece != null){
            if (!enemyPiece.color.equals(this.color)){
                legalMoves.add(new Position(this.position.getRank() - value, this.position.getFile() - value));
                legalMoves.add(new Position(this.position.getRank() - value, this.position.getFile() + value));
            }

            //Stops vertically capturing
            if (position.getFile() != enemyPiece.getPosition().getFile()){
                legalMoves.add(new Position(this.position.getRank() - value, this.position.getFile()));

                if(!this.hasMoved){
                    legalMoves.add(new Position(this.position.getRank() - 2 * value, this.position.getFile()));
                }
            }
        }else {
            legalMoves.add(new Position(this.position.getRank() - value, this.position.getFile()));

            if(!this.hasMoved){
                legalMoves.add(new Position(this.position.getRank() - 2 * value, this.position.getFile()));
            }
        }

        return legalMoves.contains(targetSquare);

    }

    @Override
    public List<Position> getMoves(ChessBoard board) {

        List<Position> moveList = new ArrayList<>();
        Position myPos = this.getPosition();
        Color color = this.getColor();
        int value = color.equals(Color.WHITE) ? -1 : 1;

        Position oneStep = new Position(myPos.getRank() + value, myPos.getFile());

        if (legalMovement(oneStep, board)){
            moveList.add(oneStep);

            Position twoStep = new Position(myPos.getRank() + (value) * 2, myPos.getFile());
            if (legalMovement(twoStep, board)){
                moveList.add(twoStep);
            }
        }

        Position captureLeft = new Position(myPos.getRank() + value, myPos.getFile() - 1);
        if(legalMovement(captureLeft, board)){
            moveList.add(captureLeft);
        }

        Position captureRight = new Position(myPos.getRank() + value, myPos.getFile() + 1);
        if(legalMovement(captureRight, board)){
            moveList.add(captureRight);
        }

        return moveList;
    }


    public boolean getHasMoved(){
        return this.hasMoved;
    }

    @Override
    public String toString(){
        return "Pawn";
    }
}
