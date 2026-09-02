import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pieces.*;
import utils.ChessBoard;
import utils.Color;
import utils.Position;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckTests {

    private ChessBoard board;

    @Nested
    class Check{

        @BeforeEach
        void setupChessBoardForCheck(){
            board = new ChessBoard();
            board.clearBoard();
            board.setPlayerTurn(2); //Blacks turn
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
            assertFalse(board.checkGameEnded());
        }

        @Test
        public void rookCanInterceptCheckButLeavesKingInAnotherCheck(){
            Piece[] list = new Piece[]{
                    new King(Color.BLACK, new Position("H8")),
                    new Rook(Color.BLACK, new Position("F6")),
                    new Knight(Color.BLACK, new Position("F7")),
                    new Bishop(Color.WHITE, new Position("B2")),
                    new King(Color.WHITE, new Position("A1")),
                    new Queen(Color.WHITE, new Position("H1")),
                    new Rook(Color.WHITE, new Position("G1"))
            };
            board.insertPieces(list);
            board.printBoard();
            assertFalse(board.checkGameEnded());

        }
    }


    @Nested
    class Remis{
        @BeforeEach
        void setupChessBoardForRemis(){
            board = new ChessBoard();
            board.clearBoard();
            Piece[] list = new Piece[]{
                    new King(Color.BLACK, new Position("G7")),
                    new King(Color.WHITE, new Position("B2")),
            };
            board.insertPieces(list);
            board.setPlayerTurn(2); //Blacks turn
        }

        @Test
        public void remisKingVsKing(){
            assertTrue(board.checkGameEnded());
        }

        @Test
        public void remisKingAndBishopVsKing(){
            board.insertPiece(new Bishop(Color.WHITE, new Position("A5")), new Position("A5"));
            assertTrue(board.checkGameEnded());
        }

        @Test
        public void remisKingAndKnightVsKing(){
            board.insertPiece(new Knight(Color.WHITE, new Position("A5")), new Position("A5"));
            assertTrue(board.checkGameEnded());
        }

        @Test
        public void remisKingAndBishopVsKingAndBishopSameSquareColorBishops(){
            board.insertPiece(new Bishop(Color.WHITE, new Position("A5")), new Position("A5"));
            board.insertPiece(new Bishop(Color.BLACK, new Position("H1")), new Position("H2"));
            assertTrue(board.checkGameEnded());
        }

        @Test
        public void remisStalemateKingAndPawnVsKing(){
            board.clearBoard();
            board.insertPiece(new King(Color.WHITE, new Position("F6")), new Position("F6"));
            board.insertPiece(new Pawn(Color.WHITE, new Position("F7")), new Position("F7"));
            board.insertPiece(new King(Color.BLACK, new Position("F8")), new Position("F8"));
            assertTrue(board.checkGameEnded());
        }
        @Test
        public void remisStalemate(){
            board.clearBoard();
            board.insertPiece(new King(Color.WHITE, new Position("D3")), new Position("D3"));
            board.insertPiece(new Queen(Color.WHITE, new Position("G4")), new Position("G4"));
            board.insertPiece(new Queen(Color.WHITE, new Position("F8")), new Position("F8"));
            board.insertPiece(new King(Color.BLACK, new Position("H7")), new Position("H7"));
            board.printBoard();
            assertTrue(board.checkGameEnded());
        }

        //TODO: EDGE CASES: King and Two Knights vs. King:
        // (Note: Checkmate is legally possible if the lone king walks into it, but it cannot be forced.
        // FIDE rules state that if the lone king runs out of time here, the game is an automatic draw).
        // Blocked Pawn Chains: All pawns are locked face-to-face, and neither king can bypass them to
        // attack or promote (e.g., White pawns on a4, b4, c4; Black pawns on a5, b5, c5, with kings stuck behind them).


    }


}
