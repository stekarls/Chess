package pieces;

import utils.Color;
import utils.Position;

public class Knight extends Piece{
    public Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMove(Position position) {
        if (!position.legalPosition()) return false;
        
        ArrayList<Position> legalMoves = new ArrayList<>();

        legalMoved.add(new Position(this.position.getX() + 2, this.position.getY() - 1));
        legalMoved.add(new Position(this.position.getX() + 2, this.position.getY() + 1));
        legalMoved.add(new Position(this.position.getX() - 2, this.position.getY() - 1));
        legalMoved.add(new Position(this.position.getX() - 2, this.position.getY() + 1));
        legalMoved.add(new Position(this.position.getX() + 1, this.position.getY() + 2));
        legalMoved.add(new Position(this.position.getX() - 1, this.position.getY() + 2));
        legalMoved.add(new Position(this.position.getX() + 1, this.position.getY() - 2));
        legalMoved.add(new Position(this.position.getX() - 1, this.position.getY() - 2));


        Piece target targetPiece = ChessBoard.getPieceAt(position);
        if(legalMoves.contains(position)){
            if(targetPiece == null) return true;
            else if(targetPiece.getColor().notEquals(this.color)) return true;   
        }else return false; 
    }
}
