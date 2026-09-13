import enums.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;
import utils.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTests {

    ChessBoard board;

    @BeforeEach
    public void setupBoard(){
        board = new ChessBoard();
        board.clearBoard();
    }

    @Test
    public void reverseCaptureInsertsCapturedPieceBackOnBoard(){
        Piece[] pieces = new Piece[] {
                new King(Color.BLACK, new Position("E8")),
                new Pawn(Color.BLACK, new Position("F7")),
                new Pawn(Color.WHITE, new Position("E6")),
                new Queen(Color.WHITE, new Position("H5"))
        };
        board.insertPieces(pieces);
        board.movePiece(board.getPieceAt(new Position("H5")).getPosition(), new Position("G6"));
        board.movePiece(board.getPieceAt(new Position("F7")).getPosition(), new Position("E6"));
        assertNotNull(board.getPieceAt(new Position("E6")));
    }


    @Test
    public void undoPawmPromotionDemotesPawn(){
        Piece[] pieces = new Piece[] {
                new King(Color.BLACK, new Position("A8")),
                new King(Color.BLACK, new Position("A1")),
                new Pawn(Color.BLACK, new Position("F2")),
                new Pawn(Color.WHITE, new Position("E7"))
        };

        board.insertPieces(pieces);
        board.movePiece(board.getPieceAt(new Position("E7")).getPosition(), new Position("E8"));
        board.undo();
        assertInstanceOf(Pawn.class, board.getPieceAt(new Position("E7")));
    }

    //TODO: Move to appropriate place
    @Test
    public void getBishopMovesShouldBeFive(){
        ChessBoard chessBoard = new ChessBoard();
        Piece piece = chessBoard.getPieceAt(new Position("C1"));
        chessBoard.removePiece(new Position("D2"));
        assertEquals(5, piece.getMoves(chessBoard).size());
    }

    @Test
    public void getBishopMovesShouldBeFour(){
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.insertPiece(new Bishop(Color.WHITE, new Position("H3")), new Position("H3"));
        assertEquals(4, chessBoard.getPieceAt(new Position("H3")).getMoves(chessBoard).size());
    }

    @Test
    public void getKnightMovesShouldBeTwo(){
        ChessBoard chessBoard = new ChessBoard();
        assertEquals(2, chessBoard.getPieceAt(new Position("B1")).getMoves(chessBoard).size());
    }

    @Test
    public void pawnShouldHaveThreeMoves(){
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.insertPieces(new Queen(Color.BLACK, new Position("B3")));
        assertEquals(3, chessBoard.getPieceAt(new Position("A2")).getMoves(chessBoard).size());
    }


    @Test
    public void pawnCanCaptureQueen(){
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.insertPieces(new Queen(Color.BLACK, new Position("C3")));
        chessBoard.insertPieces(new Pawn(Color.BLACK, new Position("E3")));

        assertTrue(chessBoard.movePiece(chessBoard.getPieceAt(new Position("D2")).getPosition(), new Position("C3")));
    }

    @Test
    public void testSpecificBugWhereBlackRookMovesByItself(){
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.movePiece(chessBoard.getPieceAt(new Position("A2")).getPosition(), new Position("A4"));
        chessBoard.checkGameEnded();
        chessBoard.movePiece(chessBoard.getPieceAt(new Position("B8")).getPosition(), new Position("C6"));
        chessBoard.checkGameEnded();
        chessBoard.movePiece(chessBoard.getPieceAt(new Position("D2")).getPosition(), new Position("D3"));
        chessBoard.checkGameEnded();
        chessBoard.movePiece(chessBoard.getPieceAt(new Position("H7")).getPosition(), new Position("H6"));
        chessBoard.checkGameEnded();

        Piece blackKing = chessBoard.getPieceAt(new Position("E8"));
        assertNotNull(blackKing);
    }

    @Test
    public void testSpecificBugWhereBlackCastlesByItself(){
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.movePiece(chessBoard.getPieceAt(new Position("A2")).getPosition(), new Position("A3"));
        chessBoard.checkGameEnded();
        chessBoard.movePiece(chessBoard.getPieceAt(new Position("B8")).getPosition(), new Position("C6"));
        chessBoard.checkGameEnded();
        chessBoard.movePiece(chessBoard.getPieceAt(new Position("A3")).getPosition(), new Position("A4"));
        chessBoard.checkGameEnded();
        chessBoard.movePiece(chessBoard.getPieceAt(new Position("G8")).getPosition(), new Position("H6"));
        chessBoard.checkGameEnded();

        Piece blackKing = chessBoard.getPieceAt(new Position("E8"));
        assertNotNull(blackKing);
    }

    //TODO: TEST FULLMOVE and halfmoveclock and player color updating correctly every turn

    //TODO: REDO MUST CHANGE HASMOVED if it was first move
}
