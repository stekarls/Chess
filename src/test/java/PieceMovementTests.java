import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pieces.*;
import utils.ChessBoard;
import utils.Color;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

public class PieceMovementTests {

    private ChessBoard board;

    @Nested
    class PawnRules {

        @Nested
        class Moving{

            @BeforeEach
            void setupChessBoard(){
                board = new ChessBoard();
            }

            @Test void pawnCanMoveOneSquareForward(){
                Piece pawn = board.getPieceAt(new Position("A2"));
                assertTrue(pawn.legalMovement(new Position("A3"), board));
            }
            @Test
            public void pawnCanMoveTwoSquaresOnFirstMove(){
                Piece pawn = board.getPieceAt(new Position("A2"));
                assertTrue(pawn.legalMovement(new Position("A4"), board));
            }

            @Test
            public void pawnCanNotMoveMoreThanTwoSquares(){
                Piece pawn = board.getPieceAt(new Position("A2"));
                assertFalse(pawn.legalMovement(new Position("A6"), board));
            }

            @Test
            public void setHasMovedIsSetAfterMovingFirstTime(){
                Pawn pawn = (Pawn) board.getPieceAt(new Position("A2"));
                board.movePiece(pawn.getPosition(), new Position("A3"));
                assertTrue(pawn.getHasMoved());
            }

            @Test
            public void pawnCanNotMoveTwoSquaresTwice(){
                Pawn pawn = (Pawn) board.getPieceAt(new Position("A2"));
                pawn.setHasMoved(true);
                assertFalse(pawn.legalMovement(new Position("A4"), board));
            }

            @Test
            public void pawnCanNotMoveBackwards(){
                Piece pawn = board.getPieceAt(new Position("A2"));
                board.movePiece(pawn.getPosition(), new Position("A3"));
                board.movePiece(pawn.getPosition(), new Position("A2"));
                board.movePiece(pawn.getPosition(), new Position("A4"));
                assertEquals(new Position("A4"), pawn.getPosition());
            }

            @Test
            public void pawnCanNotMoveDiagonallyWithoutCapturing(){
                Pawn pawn = (Pawn) board.getPieceAt(new Position("A2"));
                assertFalse(board.movePiece(pawn.getPosition(), new Position("B3")));
            }
        }

        @Nested
        class Capturing {

            @BeforeEach
            void setupChessBoardCaptureSituation(){
                board = new ChessBoard();
                board.clearBoard();

                Piece[] pieces = new Piece[] {
                        new Pawn(Color.WHITE, new Position("B2")),
                        new Pawn(Color.WHITE, new Position("A3")),
                        new Pawn(Color.BLACK, new Position("B3")),
                        new Pawn(Color.BLACK, new Position("C3"))
                };
                board.insertPieces(pieces);

//                    8     .  .  .  .  .  .  .  .
//                    7     .  .  .  .  .  .  .  .
//                    6     .  .  .  .  .  .  .  .
//                    5     .  .  .  .  .  .  .  .
//                    4     .  .  .  .  .  .  .  .
//                    3     P  p  p  .  .  .  .  .
//                    2     .  P  .  .  .  .  .  .
//                    1     .  .  .  .  .  .  .  .
//
//                          A  B  C  D  E  F  G  H
            }


            @Test
            public void pawnCanNotCaptureVertically(){
                Piece piece = board.getPieceAt(new Position("B2"));
                assertFalse(board.movePiece(piece.getPosition(), new Position("B3")));
            }

            @Test
            public void pawnCanCaptureDiagonally(){
                Piece piece = board.getPieceAt(new Position("B2"));
                assertTrue(board.movePiece(piece.getPosition(), new Position("C3")));
            }

            @Test
            public void pawnCanNotCaptureHorizontally(){
                Piece piece = board.getPieceAt(new Position("B2"));
                board.movePiece(piece.getPosition(), new Position("C3"));
                assertFalse(board.movePiece(piece.getPosition(), new Position("B3")));
            }

            @Test
            public void pawnCanNotCaptureOwnColorDiagonally(){
                Piece piece = board.getPieceAt(new Position("B2"));
                assertFalse(board.movePiece(piece.getPosition(), new Position("A3")));
            }
        }

        @Nested
        class Promoting {

            @BeforeEach
            void setupChessBoardPromotingSituation(){
                board = new ChessBoard();
                board.clearBoard();

                Piece[] pieces = new Piece[] {
                        new Pawn(Color.WHITE, new Position("G7")),
                        new King(Color.WHITE, new Position("A1")),
                        new King(Color.BLACK, new Position("A7")),
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
            public void pawnPromotesToQueen(){
                Position position = new Position("G7");
                Piece piece = board.getPieceAt(position);
                board.movePiece(piece.getPosition(), new Position("G8"));
                Piece newPiece = board.promotePawn((Pawn) piece, "queen");
                assertInstanceOf(Queen.class, newPiece);
            }

            @Test
            public void pawnPromotesToKnight(){
                Position position = new Position("G7");
                Piece piece = board.getPieceAt(position);
                board.movePiece(piece.getPosition(), new Position("G8"));
                Piece newPiece = board.promotePawn((Pawn) piece, "knight");
                assertInstanceOf(Knight.class, newPiece);
            }

            @Test
            public void pawnPromotesToBishop(){
                Position position = new Position("G7");
                Piece piece = board.getPieceAt(position);
                board.movePiece(piece.getPosition(), new Position("G8"));
                Piece newPiece = board.promotePawn((Pawn) piece, "bishop");
                assertInstanceOf(Bishop.class, newPiece);
            }

            @Test
            public void pawnPromotesToRook(){
                Position position = new Position("G7");
                Piece piece = board.getPieceAt(position);
                board.movePiece(piece.getPosition(), new Position("G8"));
                Piece newPiece = board.promotePawn((Pawn) piece, "rook");
                assertInstanceOf(Rook.class, newPiece);
            }




        }






    }










}
