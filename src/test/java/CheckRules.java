import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;
import utils.ChessBoard;
import utils.Color;
import utils.Position;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckRules {

    private ChessBoard board;

    @BeforeEach
    void setupChessBoard(){
        board = new ChessBoard();
        board.clearBoard();
    }

    @Test
    public void kingCanMoveAwayFromCheck(){
        King king = new King(Color.BLACK, new Position("E8"));
        board.insertPiece(king, king.getPosition());
        board.insertPiece(new Rook(Color.WHITE, new Position("E1")), new Position("E1"));
        assertTrue(board.movePiece(king.getPosition(), new Position("D8")));
    }

    @Test
    public void canNotMoveOtherPiecesWhileInCheck(){
        King king = new King(Color.BLACK, new Position("E8"));
        Pawn pawn = new Pawn(Color.BLACK, new Position("A5"));
        board.insertPiece(king, king.getPosition());
        board.insertPiece(pawn, pawn.getPosition());
        board.insertPiece(new Rook(Color.WHITE, new Position("E1")), new Position("E1"));
        assertFalse(board.movePiece(pawn.getPosition(), new Position("D8")));
    }

    @Test
    public void canNotExposeOwnKingToCheck(){
        King king = new King(Color.BLACK, new Position("E8"));
        Rook rook = new Rook(Color.BLACK, new Position("E7"));
        board.insertPiece(king, king.getPosition());
        board.insertPiece(rook, rook.getPosition());
        board.insertPiece(new Rook(Color.WHITE, new Position("E6")), new Position("E6"));
        board.insertPiece(new King(Color.WHITE, new Position("H2")), new Position("H2"));
        assertFalse(board.movePiece(rook.getPosition(), new Position("A7")));
    }

    @Test
    public void ladderCheckMate(){
        Piece[] list = new Piece[]{
                new King(Color.BLACK, new Position("H8")),
                new King(Color.WHITE, new Position("A1")),
                new Queen(Color.WHITE, new Position("H1")),
                new Rook(Color.WHITE, new Position("G1"))
        };
        board.insertPieces(list);
        assertTrue(board.checkGameEnded());
    }

    @Test
    public void notCheckMateKnightCanDefend(){
        Piece[] list = new Piece[]{
                new King(Color.BLACK, new Position("H8")),
                new Knight(Color.BLACK, new Position("F7")),
                new King(Color.WHITE, new Position("A1")),
                new Queen(Color.WHITE, new Position("H1")),
                new Rook(Color.WHITE, new Position("G1"))
        };
        board.insertPieces(list);
        board.printBoard();
        assertFalse(board.checkGameEnded());

    }

}
