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
    public boolean legalMove(Position position, ChessBoard board) {
        if (!position.legalPosition()){
            return false;
        }else if (this.position.equals(position)){
            System.out.println("Piece already at same position, try again");
            return false;
        }

        ArrayList<Position> legalMoves = new ArrayList<>();

        if (this.color.equals(Color.WHITE)){
            legalMoves.add(new Position(this.position.getX() - 1, this.position.getY()));
            legalMoves.add(new Position(this.position.getX() - 1, this.position.getY() - 1));
            legalMoves.add(new Position(this.position.getX() - 1, this.position.getY() + 1));
            if(!this.hasMoved) legalMoves.add(new Position(this.position.getX() - 2, this.position.getY()));
            
        }else{
            legalMoves.add(new Position(this.position.getX() + 1, this.position.getY()));
            legalMoves.add(new Position(this.position.getX() + 1, this.position.getY() - 1));
            legalMoves.add(new Position(this.position.getX() + 1, this.position.getY() + 1));
            if(!this.hasMoved) legalMoves.add(new Position(this.position.getX() + 2, this.position.getY()));
        }
                       
        if(!legalMoves.contains(position)){
            System.out.prinln("Not a legal move, try again");
            return false;
        }

        if(legalMoves.contains(position)){
            Piece targetPiece = pieceAtPosition(position);
            if(targetPiece == null){
                this.hasMoved = true;
                return true;
            }else if(targetPiece.getColor.equals(this.color)){ //Target piece is same color
                System.out.println("Du har en brikke her fra før");
                return false;
            }else if (targetPiece.getY() == this.position.getY()){ //Check if targetPiece is on same file
                System.out.println("Cannot capture enemy piece in this way");
                return false;
            }else{
                this.hasMoved = true;
                return true;
            }
        }    
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
