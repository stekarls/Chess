import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import utils.ChessBoard;
import utils.Color;
import utils.Position;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
