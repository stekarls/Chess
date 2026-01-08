package pieces;

import utils.ChessBoard;
import utils.Color;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    private boolean hasMoved = false;

    public King(Color color, Position position) {
        super(color, position);

    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board){
        if (!targetSquare.legalPosition()) return false;

        int rankDifference = targetSquare.getRank() - this.position.getRank();
        int fileDifference = targetSquare.getFile() - this.position.getFile();

        if (rankDifference == 0 && fileDifference == 0) return false;

        return Math.abs(rankDifference) <= 1 && Math.abs(fileDifference) <= 1;
    }

    public List<Position> getMoves(ChessBoard board){
        List<Position> moves = new ArrayList<>();

        //Diagonal
        moves.add(new Position(this.position.getRank() - 1, this.position.getFile() - 1));
        moves.add(new Position(this.position.getRank() - 1, this.position.getFile() + 1));
        moves.add(new Position(this.position.getRank() + 1, this.position.getFile() - 1));
        moves.add(new Position(this.position.getRank() + 1, this.position.getFile() + 1));

        //Straight
        moves.add(new Position(this.position.getRank() - 1, this.position.getFile()));
        moves.add(new Position(this.position.getRank() + 1, this.position.getFile()));
        moves.add(new Position(this.position.getRank(), this.position.getFile() - 1));
        moves.add(new Position(this.position.getRank(), this.position.getFile() + 1));

        moves.removeIf(pos -> !pos.legalPosition());

        List<Position> reachable = new ArrayList<>();
        for (Position pos : moves){
            Piece square = board.getBOARD()[pos.getRank()][pos.getFile()];
            if ((square == null)) {
                reachable.add(pos);
            } else if (!square.getColor().equals(this.color)){
                reachable.add(pos);
            }
        }
        return reachable;
    }

    public boolean canMove(ChessBoard chessboard){
        List<Position> moves = getMoves(chessboard);
        return !moves.isEmpty();
    }



    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved(){
        return hasMoved;
    }
    @Override
    public String toString(){
        return "King";
    }
}
