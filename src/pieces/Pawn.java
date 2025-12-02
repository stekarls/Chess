package pieces;

import utils.Color;
import utils.Position;

public class Pawn extends Piece{

    private boolean hasMoved = false;

    public Pawn(Color color, Position position) {
        super(color, position);
    }

    @Override
    public boolean legalMove(Position position) {
        if (this.position.equals(position)){
            System.out.println("Piece already at same position, try again");
            return false;
        }
        return true;
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
