package pieces;
import utils.ChessBoard;
import utils.Color;
import utils.Position;

public abstract class Piece {

    protected Color color;
    protected Position position;
    protected char symbol;

    public Piece(Color color, Position position){
        this.color = color;
        this.position = position;


        char symbol = 0;
        switch (this) {
            case King king -> symbol = 'k';
            case Queen queen -> symbol = 'q';
            case Rook rook -> symbol = 'r';
            case Bishop bishop -> symbol = 'b';
            case Knight knight -> symbol = 'n';
            case Pawn pawn -> symbol = 'p';
            default -> {
            }
        }
        this.symbol = color.equals(Color.WHITE) ? Character.toUpperCase(symbol) : symbol;
    }



    public abstract boolean legalMove(Position position);


    public Color getColor(){
        return this.color;
    }
    public char getSymbol(){
        return this.symbol;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public Position getPosition(){
        return this.position;
    }

}
