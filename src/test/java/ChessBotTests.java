import chessBot.ChessBot;
import chessBot.MoveInfo;
import enums.Color;
import org.junit.jupiter.api.Test;
import pieces.*;
import utils.ChessBoard;
import utils.Position;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChessBotTests {


    @Test
    public void chessBotShouldRegisterTwentyMovesOnFreshBoard(){
        ChessBoard chessBoard = new ChessBoard();
        ChessBot chessBot = new ChessBot(Color.WHITE, chessBoard);

        PriorityQueue<MoveInfo> position = chessBot.getAllMoves();
        assertEquals(20, position.size());
    }

    @Test
    public void chessBotShouldRegisterSeventeenMovesAvailable(){
        ChessBoard chessBoard = new ChessBoard();
        ChessBot chessBot = new ChessBot(Color.WHITE, chessBoard);

        chessBoard.insertPieces(new Queen(Color.BLACK, new Position("C3")));
        chessBoard.insertPieces(new Pawn(Color.BLACK, new Position("E3")));

        PriorityQueue<MoveInfo> position = chessBot.getAllMoves();
        assertEquals(17, position.size());
    }

    @Test
    public void chessBotShouldHaveSeventeenMoves(){
        ChessBoard board = new ChessBoard();
        ChessBot chessBot = new ChessBot(Color.BLACK, board);
        board.clearBoard();

        Piece[] pieces = new Piece[] {
                new King(Color.WHITE, new Position("G1")),
                new Pawn(Color.WHITE, new Position("A7")),
                new Pawn(Color.WHITE, new Position("B6")),
                new Pawn(Color.WHITE, new Position("C2")),
                new Pawn(Color.WHITE, new Position("F2")),
                new Pawn(Color.WHITE, new Position("G2")),
                new Pawn(Color.WHITE, new Position("H3")),
                new Rook(Color.WHITE, new Position("C6")),


                new King(Color.BLACK, new Position("B7")),
                new Pawn(Color.BLACK, new Position("D5")),
                new Pawn(Color.BLACK, new Position("H4")),
                new Rook(Color.BLACK, new Position("G8")),
        };
        board.insertPieces(pieces);
        board.movePiece(board.getPieceAt(new Position("D5")).getPosition(), new Position("D4"));


        PriorityQueue<MoveInfo> positions = chessBot.getAllMoves();
        assertEquals(17, positions.size());
    }

    @Test
    public void chessBotShouldHaveEighteenMovesByRegisteringEnPassantSquare(){
        ChessBoard board = new ChessBoard();
        ChessBot chessBot = new ChessBot(Color.BLACK, board);
        board.clearBoard();

        Piece[] pieces = new Piece[] {
                new King(Color.WHITE, new Position("G1")),
                new Pawn(Color.WHITE, new Position("A7")),
                new Pawn(Color.WHITE, new Position("B6")),
                new Pawn(Color.WHITE, new Position("C2")),
                new Pawn(Color.WHITE, new Position("F2")),
                new Pawn(Color.WHITE, new Position("G2")),
                new Pawn(Color.WHITE, new Position("H3")),
                new Rook(Color.WHITE, new Position("C6")),

                new King(Color.BLACK, new Position("B7")),
                new Pawn(Color.BLACK, new Position("D4")),
                new Pawn(Color.BLACK, new Position("H4")),
                new Rook(Color.BLACK, new Position("G8")),
        };
        board.insertPieces(pieces);
        board.movePiece(board.getPieceAt(new Position("C2")).getPosition(), new Position("C4"));
        board.checkGameEnded();
        board.getPieceAt(new Position("D4")).setHasMoved(true);
        PriorityQueue<MoveInfo> positions = chessBot.getAllMoves();
        board.printBoard();
        assertEquals(18, positions.size());
    }

    @Test
    public void chessBotShouldRegisterTwentyMovesAvailable(){
        ChessBoard chessBoard = new ChessBoard();
        ChessBot chessBot = new ChessBot(Color.WHITE, chessBoard);

        chessBoard.insertPieces(new Queen(Color.BLACK, new Position("B3")));

        PriorityQueue<MoveInfo> position = chessBot.getAllMoves();
        assertEquals(20, position.size());
    }

    @Test
    public void chessBotShouldPrioritizeCapturingQueenWithB2Pawn(){
        ChessBoard chessBoard = new ChessBoard();
        ChessBot chessBot = new ChessBot(Color.WHITE, chessBoard);

        chessBoard.insertPieces(new Queen(Color.BLACK, new Position("C3")));
        chessBoard.insertPieces(new Pawn(Color.BLACK, new Position("E3")));

        MoveInfo bestMove = chessBot.evaluateBestMove();
        Position bestSquare = bestMove.getPiece().getPosition();
        assertEquals(chessBoard.getPieceAt(new Position("B2")).getPosition(), bestSquare);
    }

}
