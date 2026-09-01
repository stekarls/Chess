package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

import java.util.ArrayList;

public class Pawn extends Piece{

    private boolean hasMoved = false;

    public Pawn(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board) {
        if (!targetSquare.legalPosition()) return false;

        ArrayList<Position> legalMoves = new ArrayList<>();

        Color color = this.getColor();
        int value = color.equals(Color.WHITE) ? 1 : -1;



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

    public boolean getHasMoved(){
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    @Override
    public String toString(){
        return "Pawn";
    }
}
