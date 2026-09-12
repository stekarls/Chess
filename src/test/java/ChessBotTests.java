import chessBot.ChessBot;
import chessBot.MoveInfo;
import enums.Color;
import org.junit.jupiter.api.Test;
import pieces.Pawn;
import pieces.Queen;
import utils.*;

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
