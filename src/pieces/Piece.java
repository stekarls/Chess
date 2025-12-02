package pieces;
import utils.Color;
import utils.Position;

public abstract class Piece {

    protected Color color;
    protected Position position;

    public Piece(Color color, Position position){
        this.color = color;
        this.position = position;
    }


    public abstract boolean legalMove();

}
