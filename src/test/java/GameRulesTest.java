import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pieces.*;
import utils.ChessBoard;
import utils.Color;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

public class GameRulesTest {

    private ChessBoard board;


    @Nested
    class Castling{

        @BeforeEach
        void setupChessBoardForCastlingk(){
            board = new ChessBoard();
            board.clearBoard();

            board.insertPiece(new King(Color.WHITE, new Position("E1")), new Position("E1"));
            board.insertPiece(new Rook(Color.WHITE, new Position("A1")), new Position("A1"));
            board.insertPiece(new Rook(Color.WHITE, new Position("H1")), new Position("H1"));

            board.insertPiece(new King(Color.BLACK, new Position("E8")), new Position("E8"));
            board.insertPiece(new Rook(Color.BLACK, new Position("A8")), new Position("A8"));
            board.insertPiece(new Rook(Color.BLACK, new Position("H8")), new Position("H8"));
        }

        @Test
        public void castleQueenSideWhite(){
            Piece king = board.getPieceAt(new Position("E1"));
            board.movePiece(king.getPosition(), new Position("C1"));
            boolean kingPos = king.getPosition().equals(new Position("C1"));
            boolean rookPos = board.getPieceAt(new Position("D1")) != null;
            assertTrue(kingPos && rookPos);
        }

        @Test
        public void castleQueenSideBlack(){
            board.setPlayerTurn(2); //Is this needed for all tests?
            Piece king = board.getPieceAt(new Position("E8"));
            board.movePiece(king.getPosition(), new Position("C8"));
            boolean kingPos = king.getPosition().equals(new Position("C8"));
            boolean rookPos = board.getPieceAt(new Position("D8")) != null;
            assertTrue(kingPos && rookPos);

        }

        @Test
        public void castleKingSideWhite(){
            Piece king = board.getPieceAt(new Position("E1"));
            board.movePiece(king.getPosition(), new Position("G1"));
            boolean kingPos = king.getPosition().equals(new Position("G1"));
            boolean rookPos = board.getPieceAt(new Position("F1")) != null;
            assertTrue(kingPos && rookPos);
        }

        @Test
        public void castleKingSideBlack(){
            board.setPlayerTurn(2); //Is this needed for all tests?
            Piece king = board.getPieceAt(new Position("E8"));
            board.movePiece(king.getPosition(), new Position("G8"));
            boolean kingPos = king.getPosition().equals(new Position("G8"));
            boolean rookPos = board.getPieceAt(new Position("F8")) != null;
            assertTrue(kingPos && rookPos);


        }

    }
    @Nested
    class Check{

        @BeforeEach
        void setupChessBoardForCheck(){
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
        public void canNotExposeOwnKingToCheckWhenPinned(){
            King king = new King(Color.BLACK, new Position("E8"));
            Rook rook = new Rook(Color.BLACK, new Position("E7"));
            board.insertPiece(king, king.getPosition());
            board.insertPiece(rook, rook.getPosition());
            board.insertPiece(new Rook(Color.WHITE, new Position("E6")), new Position("E6"));
            board.insertPiece(new King(Color.WHITE, new Position("H2")), new Position("H2"));
            assertFalse(board.movePiece(rook.getPosition(), new Position("A7")));
        }
    }

    @Nested
    class Checkmate{

        @BeforeEach
        void setupChessBoardForCheckmate(){
            board = new ChessBoard();
            board.clearBoard();
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
        public void ladderCheckMateOtherPlayersTurn(){
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
            assertFalse(board.checkGameEnded());
        }

        @Test
        public void bishopCanCaptureAttackerToPreventCheck(){
            Piece[] list = new Piece[]{
                    new King(Color.BLACK, new Position("H8")),
                    new Rook(Color.BLACK, new Position("F6")),
                    new Bishop(Color.BLACK, new Position("A8")),
                    new Bishop(Color.WHITE, new Position("B2")),
                    new King(Color.WHITE, new Position("A1")),
                    new Queen(Color.WHITE, new Position("H1")),
                    new Rook(Color.WHITE, new Position("G1"))
            };
            board.insertPieces(list);
            assertFalse(board.checkGameEnded());
        }

        //TODO: ADD advanced check test where legalmoves look like it is checkmate, but pawn can promote to save
    }

    @Nested
    class Remis{

        @BeforeEach
        void setupChessBoardForRemis(){
            board = new ChessBoard();
            board.clearBoard();
            Piece blackKing = new King(Color.BLACK, new Position("G7"));
            Piece whiteKing = new King(Color.WHITE, new Position("B2"));
            blackKing.setHasMoved(true);
            whiteKing.setHasMoved(true);
            board.insertPiece(blackKing, blackKing.getPosition());
            board.insertPiece(whiteKing, whiteKing.getPosition());
            board.setPlayerTurn(2); //TODO: Could i refactor so this is not needed?
            board.calculatePlayerTurn();
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
        public void remisKingAndBishopVsKingAndBishopOpositeSquareColorBishops(){
            board.insertPiece(new Bishop(Color.WHITE, new Position("A4")), new Position("A4"));
            board.insertPiece(new Bishop(Color.BLACK, new Position("H1")), new Position("H2"));
            assertFalse(board.checkGameEnded());
        }

        @Test
        public void remisStalemateKingAndPawnVsKing(){
            board.clearBoard();
            board.insertPiece(new King(Color.WHITE, new Position("F6")), new Position("F6"));
            board.insertPiece(new Pawn(Color.WHITE, new Position("F7")), new Position("F7"));
            board.insertPiece(new King(Color.BLACK, new Position("F8")), new Position("F8"));

            board.getPieceAt(new Position("F6")).setHasMoved(true);
            board.getPieceAt(new Position("F8")).setHasMoved(true);
            board.getPieceAt(new Position("F7")).setHasMoved(true);
            assertTrue(board.checkGameEnded());
        }
        @Test
        public void remisStalemate(){
            board.clearBoard();
            board.insertPiece(new King(Color.WHITE, new Position("D3")), new Position("D3"));
            board.insertPiece(new Queen(Color.WHITE, new Position("G4")), new Position("G4"));
            board.insertPiece(new Queen(Color.WHITE, new Position("F8")), new Position("F8"));
            board.insertPiece(new King(Color.BLACK, new Position("H7")), new Position("H7"));
            board.getPieceAt(new Position("D3")).setHasMoved(true);
            board.getPieceAt(new Position("H7")).setHasMoved(true);
            assertTrue(board.checkGameEnded());
        }

        @Test
        public void remisStalematePawnCanMoveButIsPinned(){
            board.clearBoard();
            board.insertPiece(new King(Color.WHITE, new Position("D1")), new Position("D1"));
            board.insertPiece(new Rook(Color.WHITE, new Position("G1")), new Position("G1"));
            board.insertPiece(new Rook(Color.WHITE, new Position("A7")), new Position("A7"));
            board.insertPiece(new Queen(Color.WHITE, new Position("B1")), new Position("B1"));
            board.insertPiece(new Queen(Color.WHITE, new Position("A2")), new Position("A2"));
            board.insertPiece(new Bishop(Color.WHITE, new Position("A1")), new Position("A1"));
            board.insertPiece(new King(Color.BLACK, new Position("H8")), new Position("H8"));
            board.insertPiece(new Pawn(Color.BLACK, new Position("G7")), new Position("G7"));

            board.getPieceAt(new Position("D1")).setHasMoved(true);
            board.getPieceAt(new Position("H8")).setHasMoved(true);
            assertTrue(board.checkGameEnded());
        }

        @Test
        public void shouldNotTriggerFiftyTurnRule(){

            Piece rook = new Rook(Color.BLACK, new Position("E5"));
            board.insertPiece(rook, rook.getPosition());


            Position oldPositionBlack = new Position("G7");
            Position newPositionBlack = new Position("H7");

            Position oldPositionWhite = new Position("B2");
            Position newPositionWhite = new Position("A1");

            for (int i = 0; i < 24; i++){
                board.movePiece(oldPositionBlack, newPositionBlack);
                board.checkGameEnded();
                board.movePiece(newPositionBlack, oldPositionBlack);
                board.checkGameEnded();
                board.movePiece(oldPositionWhite, newPositionWhite);
                board.checkGameEnded();
                board.movePiece(newPositionWhite, oldPositionWhite);
                board.checkGameEnded();
            }
            assertFalse(board.checkGameEnded());
        }

        @Test
        public void shouldTriggerFiftyTurnRule(){
            Piece rook = new Rook(Color.BLACK, new Position("E5"));
            board.insertPiece(rook, rook.getPosition());

            Position oldPositionBlack = new Position("G7");
            Position newPositionBlack = new Position("H7");

            Position oldPositionWhite = new Position("B2");
            Position newPositionWhite = new Position("A1");

            for (int i = 0; i < 25; i++){
                board.movePiece(oldPositionBlack, newPositionBlack);
                board.checkGameEnded();
                board.movePiece(newPositionBlack, oldPositionBlack);
                board.checkGameEnded();
                board.movePiece(oldPositionWhite, newPositionWhite);
                board.checkGameEnded();
                board.movePiece(newPositionWhite, oldPositionWhite);
                board.checkGameEnded();
            }
            assertTrue(board.checkGameEnded());
        }


    }

    @Nested
    class EnPassant {

        @BeforeEach
        void setupChessBoardForEnPassant(){
            board = new ChessBoard();
        }


        @Test
        public void checkCorrectEnPassantPositionAfterTwoPawnStepsWhite(){
            board.movePiece(new Position("A2"), new Position("A4"));
            board.checkGameEnded();
            assertEquals(new Position("A3"), board.getEnPassant());
        }

        @Test
        public void checkCorrectEnPassantPositionAfterTwoPawnStepsBlack(){
            board.setPlayerTurn(2);
            board.movePiece(new Position("B7"), new Position("B5"));
            board.checkGameEnded();
            assertEquals(new Position("B6"), board.getEnPassant());
        }

        @Test
        public void pawnOnlyMovesOneSquareShouldNotTriggerEnPassantWhite(){
            board.movePiece(new Position("A2"), new Position("A3"));
            board.checkGameEnded();
            assertNull(board.getEnPassant());
        }

        @Test
        public void pawnOnlyMovesOneSquareShouldNotTriggerEnPassantBlack(){
            board.setPlayerTurn(2); //Blacks turn
            board.movePiece(new Position("B7"), new Position("B6"));
            board.checkGameEnded();
            assertNull(board.getEnPassant());
        }

        @Test
        public void enPassantShouldBeSetToNullAfterOneTurn(){
            board.movePiece(new Position("A2"), new Position("A4"));
            board.checkGameEnded();
            board.movePiece(new Position("H7"), new Position("H6"));
            board.checkGameEnded();
            assertNull(board.getEnPassant());
        }

        @Test
        public void correctEnPassantAfterDoublePawnMoveMultipleTimes(){
            board.movePiece(new Position("A2"), new Position("A4"));
            board.checkGameEnded();
            board.movePiece(new Position("B7"), new Position("B6"));
            board.checkGameEnded();
            board.movePiece(new Position("B2"), new Position("B4"));
            board.checkGameEnded();
            assertNotNull(board.getPieceAt(new Position("B4")));
        }

        @Test
        public void testEnPassantCaptureWhite(){
            board.movePiece(new Position("A2"), new Position("A4"));
            board.checkGameEnded();
            board.movePiece(new Position("H7"), new Position("H5"));
            board.checkGameEnded();
            board.movePiece(new Position("A4"), new Position("A5"));
            board.checkGameEnded();
            board.movePiece(new Position("B7"), new Position("B5"));
            board.checkGameEnded();
            board.movePiece(new Position("A5"), new Position("B6"));
            board.checkGameEnded();
            assertNull(board.getPieceAt(new Position("B5")));
        }

//        @Test
//        public void testEnPassantCaptureBlack(){
//            board.movePiece(new Position("A2"), new Position("A4"));
//            board.checkGameEnded();
//            board.movePiece(new Position("C7"), new Position("C5"));
//            board.checkGameEnded();
//            board.movePiece(new Position("A4"), new Position("A5"));
//            board.checkGameEnded();
//            board.movePiece(new Position("B7"), new Position("B5"));
//            board.checkGameEnded();
//            board.movePiece(new Position("A5"), new Position("B6"));
//            board.checkGameEnded();
//            assertNull(board.getPieceAt(new Position("B4")));
//        }

    }

    @Nested
    class Promotion{

        @BeforeEach
        void setupChessBoardPromotingSituation(){
            board = new ChessBoard();
            board.clearBoard();

            Piece[] pieces = new Piece[] {
                    new Pawn(Color.WHITE, new Position("G7")),
                    new King(Color.WHITE, new Position("A1")),
                    new King(Color.BLACK, new Position("A8")),
                    new Pawn(Color.BLACK, new Position("E2"))
            };
            board.insertPieces(pieces);


//                8     .  .  .  .  .  .  .  .
//                7     k  .  .  .  .  .  P  .
//                6     .  .  .  .  .  .  .  .
//                5     .  .  .  .  .  .  .  .
//                4     .  .  .  .  .  .  .  .
//                3     .  .  .  .  .  .  .  .
//                2     .  .  .  .  p  .  .  .
//                1     K  .  .  .  .  .  .  .
//
//                      A  B  C  D  E  F  G  H
        }

        @Test
        void testPawnBeingPromotedByMovePieceMethodWhite(){
            Piece whitePawn = board.getPieceAt(new Position("G7"));
            board.movePiece(whitePawn.getPosition(), new Position("G8"));
            assertInstanceOf(Queen.class, board.getPieceAt(new Position("G8")));
        }

        @Test
        void testPawnBeingPromotedByMovePieceMethodBlack(){
            Piece blackPawn = board.getPieceAt(new Position("E2"));
            board.movePiece(blackPawn.getPosition(), new Position("E1"));
            assertInstanceOf(Queen.class, board.getPieceAt(new Position("E1")));
        }

        @Test
        void testPawnBeingPromotedCreatingCheckmatePosition(){
            board.insertPiece(new Rook(Color.WHITE, new Position("H7")), new Position("H7"));
            Piece whitePawn = board.getPieceAt(new Position("G7"));
            board.movePiece(whitePawn.getPosition(), new Position("G8"));
            assertTrue(board.checkGameEnded());

        }

        @Test
        void testReversingPromotionBecauseOfIllegalMove(){
            board.insertPiece(new Rook(Color.WHITE, new Position("H7")), new Position("H7"));
            board.insertPiece(new Bishop(Color.BLACK, new Position("H8")), new Position("H8"));
            Piece whitePawn = board.getPieceAt(new Position("G7"));
            board.movePiece(whitePawn.getPosition(), new Position("G8"));
            board.printBoard();
            assertFalse(board.checkGameEnded());
        }




    }
}
