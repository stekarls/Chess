import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pieces.*;
import utils.ChessBoard;
import utils.Color;
import utils.Position;
import utils.SquareColor;

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

            //TODO: Add En Passant rules




        }






    }

    @Nested
    class BishopRules{

        @BeforeEach
        public void boardSetup(){
            board = new ChessBoard();
            board.clearBoard();
        }

        @Test
        public void bishopCanMoveFromOneCornerToAnother(){
            Bishop bishop = new Bishop(Color.WHITE, new Position("A1"));
            board.insertPiece(bishop, new Position("A1"));
            assertTrue(board.movePiece(bishop.getPosition(), new Position("H8")));
        }

        @Test
        public void bishopCanNotMoveVertically(){
            Bishop bishop = new Bishop(Color.WHITE, new Position("A1"));
            board.insertPiece(bishop, new Position("A1"));
            assertFalse(board.movePiece(bishop.getPosition(), new Position("A2")));
        }

        @Test
        public void bishopCanNotMoveHorizontally(){
            Bishop bishop = new Bishop(Color.WHITE, new Position("A1"));
            board.insertPiece(bishop, new Position("A1"));
            assertFalse(board.movePiece(bishop.getPosition(), new Position("")));
        }


        @Test
        public void insertedBishopGetsCorrectSquareColor(){
            Bishop bishop = new Bishop(Color.WHITE, new Position("A1"));
            board.insertPiece(bishop, new Position("A1"));
            assertEquals(SquareColor.DARK, bishop.getPosition().getSquareColor());
        }

        @Test
        public void bishopCanNotMoveThroughOtherPieces(){
            Bishop bishop = new Bishop(Color.WHITE, new Position("A1"));
            board.insertPiece(bishop, new Position("A1"));
            board.insertPiece(new Rook(Color.BLACK, new Position("G7")), new Position("G7"));
            assertFalse(board.movePiece(bishop.getPosition(), new Position("H8")));
        }

        @Test
        public void bishopCanNotMoveThroughMyPieces(){
            Bishop bishop = new Bishop(Color.WHITE, new Position("A1"));
            board.insertPiece(bishop, new Position("A1"));
            board.insertPiece(new Rook(Color.WHITE, new Position("G7")), new Position("G7"));
            assertFalse(board.movePiece(bishop.getPosition(), new Position("H8")));
        }

        @Test
        public void bishopCanCapturePiece(){
            Bishop bishop = new Bishop(Color.WHITE, new Position("A1"));
            board.insertPiece(bishop, new Position("A1"));
            board.insertPiece(new Rook(Color.BLACK, new Position("A1")), new Position("F6"));
            assertTrue(board.movePiece(bishop.getPosition(), new Position("F6")));
        }

    }

    @Nested
    class RookRules{

        @BeforeEach
        public void boardSetup(){
            board = new ChessBoard();
            board.clearBoard();
        }

        @Test
        public void rookCanMoveFromOneEdgeToAnother(){
            Rook rook = new Rook(Color.WHITE, new Position("C1"));
            board.insertPiece(rook, new Position("C1"));
            assertTrue(board.movePiece(rook.getPosition(), new Position("C8")));
        }

        @Test
        public void rookCanNotMoveDiagonally(){
            Rook rook = new Rook(Color.WHITE, new Position("C1"));
            board.insertPiece(rook, new Position("C1"));
            assertFalse(board.movePiece(rook.getPosition(), new Position("D2")));
        }

        @Test
        public void rookCanNotMoveThroughOtherPieces(){
            Rook rook = new Rook(Color.WHITE, new Position("A1"));
            board.insertPiece(rook, new Position("A1"));
            board.insertPiece(new Rook(Color.BLACK, new Position("A7")), new Position("A7"));
            assertFalse(board.movePiece(rook.getPosition(), new Position("A8")));
        }

        @Test
        public void bishopCanNotMoveThroughMyPieces(){
            Rook rook = new Rook(Color.WHITE, new Position("A1"));
            board.insertPiece(rook, new Position("A1"));
            board.insertPiece(new Rook(Color.WHITE, new Position("A7")), new Position("A7"));
            assertFalse(board.movePiece(rook.getPosition(), new Position("A8")));
        }

        @Test
        public void rookCanCapturePiece(){
            Rook rook = new Rook(Color.WHITE, new Position("E1"));
            board.insertPiece(rook, new Position("E1"));
            board.insertPiece(new Bishop(Color.BLACK, new Position("E4")), new Position("E4"));
            assertTrue(board.movePiece(rook.getPosition(), new Position("E4")));
        }
    }

    @Nested
    class QueenRules{}

    @Nested
    class KnightRules{}

    @Nested
    class KingRules{}










}
