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

        if (this.color.equals(Color.WHITE)){
            legalMoves.add(new Position(this.position.getRank() - 1, this.position.getFile()));
            legalMoves.add(new Position(this.position.getRank() - 1, this.position.getFile() - 1));
            legalMoves.add(new Position(this.position.getRank() - 1, this.position.getFile() + 1));
            if(!this.hasMoved) legalMoves.add(new Position(this.position.getRank() - 2, this.position.getFile()));
            
        }else{
            legalMoves.add(new Position(this.position.getRank() + 1, this.position.getFile()));
            legalMoves.add(new Position(this.position.getRank() + 1, this.position.getFile() - 1));
            legalMoves.add(new Position(this.position.getRank() + 1, this.position.getFile() + 1));
            if(!this.hasMoved) legalMoves.add(new Position(this.position.getRank() + 2, this.position.getFile()));
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
