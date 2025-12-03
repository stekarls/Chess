package pieces;

import utils.Color;
import utils.Position;

import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMove(Position position) {
        if (!position.legalPosition()) return false;
        
        ArrayList<Position> legalMoves = new ArrayList<>();

        legalMoves.add(new Position(this.position.getRank() + 2, this.position.getFile() - 1));
        legalMoves.add(new Position(this.position.getRank() + 2, this.position.getFile() + 1));
        legalMoves.add(new Position(this.position.getRank() - 2, this.position.getFile() - 1));
        legalMoves.add(new Position(this.position.getRank() - 2, this.position.getFile() + 1));
        legalMoves.add(new Position(this.position.getRank() + 1, this.position.getFile() + 2));
        legalMoves.add(new Position(this.position.getRank() - 1, this.position.getFile() + 2));
        legalMoves.add(new Position(this.position.getRank() + 1, this.position.getFile() - 2));
        legalMoves.add(new Position(this.position.getRank() - 1, this.position.getFile() - 2));


        return legalMoves.contains(position);
//        if(legalMoves.contains(position)){
//            Piece targetPiece = board.getPieceAt(position);
//            if(targetPiece == null) return true;
//            else if(!(targetPiece.getColor().equals(this.color))) return true;
//        }
    }

    @Override
    public String toString(){
        return "Knight";
    }
}
