package pieces;

import utils.Color;
import utils.Position;

import java.util.ArrayList;

public class Pawn extends Piece{

    private boolean hasMoved = false;

    public Pawn(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMove(Position position) {
        
        if (!position.legalPosition()) return false;
        
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

        return legalMoves.contains(position);
//
//        if(legalMoves.contains(position)){
//            Piece targetPiece = board.getPieceAt(position);
//
//            if(targetPiece == null){
//                this.hasMoved = true;
//                return true;
//            }else if(targetPiece.getColor().equals(this.color)){ //Target piece is same color
//                System.out.println("Du har en brikke her fra før");
//                return false;
//            }else if (targetPiece.getPosition().getX() == this.position.getX()){ //Check if targetPiece is on same file
//                System.out.println("Cannot capture enemy piece in this way");
//                return false;
//            }else{
//                this.hasMoved = true;
//                return true;
//            }
//        }
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
