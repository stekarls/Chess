package pieces;

import utils.ChessBoard;
import enums.Color;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(Color color, Position position) {
        super(color, position);
        super.pieceValue = 3;
    }

    @Override
    public boolean legalMovement(Position targetSquare, ChessBoard board){
        if (!targetSquare.legalPosition()) return false;

        int rankDifference = Math.abs(targetSquare.getRank() - this.position.getRank());
        int fileDifference = Math.abs(targetSquare.getFile() - this.position.getFile());

        boolean lPattern1 = (rankDifference == 2) && (fileDifference == 1);
        boolean lPattern2 = (rankDifference == 1) && (fileDifference == 2);

        return lPattern1 || lPattern2;
    }

    @Override
    public List<Position> getMoves(ChessBoard board) {
        List<Position> moveList = new ArrayList<>();
        Position myPiece = this.getPosition();

        int[][] offsets = {
                { 2, 1}, { 2, -1},
                {-2, 1}, {-2, -1},
                { 1, 2}, { 1, -2},
                {-1, 2}, {-1, -2}
        };

        for (int[] offset : offsets){
            Position targetSquare = new Position(myPiece.getRank() + offset[0], myPiece.getFile() + offset[1]);
            if (!targetSquare.legalPosition()){
                continue;
            }

            Piece targetPiece = board.getPieceAt(targetSquare);
            if (targetPiece == null || !targetPiece.getColor().equals(this.color)){
                moveList.add(targetSquare);
            }
        }
        return moveList;
    }


    @Override
    public String toString(){
        return "Knight";
    }
}
