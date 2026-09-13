package chessBot;

import enums.Color;
import pieces.Piece;
import utils.ChessBoard;
import utils.MoveRecord;
import utils.Position;

import java.util.*;


//TODO: Implement attacking patterns
public class ChessBot {

    private final Color color;
    private final Color enemyColor;
    private final ChessBoard board;
    private final List<Piece> myPieces;
    //TODO: implement randomness

    public ChessBot(Color color, ChessBoard board){
        this.board = board;
        this.myPieces = color.equals(Color.WHITE) ? board.getWhitePieces() : board.getBlackPieces();
        this.enemyColor = color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        this.color = color;
    }

    Comparator<MoveInfo> movePriority = Comparator.comparingInt((MoveInfo move) -> {
        int priority = 0;
        priority += move.enemyValue();
        if (move.threatensKing()) priority += 10;
        if (move.canBeCaptured()) priority -= 20;
        return priority;

    }).reversed();

    public MoveInfo play(){
        MoveInfo moveInfo = evaluateBestMove();
        Position from = moveInfo.piece().getPosition();
        Position to = moveInfo.targetSquare();
        board.movePiece(from, to);
        return moveInfo;
    }

    public MoveInfo evaluateBestMove(){
        PriorityQueue<MoveInfo> possibleMoves = getAllMoves();
        return possibleMoves.poll();
    }

    public PriorityQueue<MoveInfo> getAllMoves(){
        PriorityQueue<MoveInfo> possibleMoves = new PriorityQueue<>(movePriority);
        List<Piece> myPieces = new ArrayList<>(this.myPieces);

        for (Piece piece : myPieces){
            for (Position legalSquare : piece.getMoves(board)){
                if (board.movePiece(piece.getPosition(), legalSquare)){ //Or canMoveOrCapture?
                    boolean canBeCaptured = !board.whoCanCapturePiece(piece).isEmpty();
                    boolean threatensKing = board.isKingChecked(enemyColor);
                    MoveRecord moveHistory = board.getMoveHistoryStack().peek();
                    if (moveHistory.captured() != null){
                        int enemyValue = moveHistory.captured().getPieceValue();
                        possibleMoves.add(new MoveInfo(piece, legalSquare, enemyValue, threatensKing, canBeCaptured));
                    }else {
                        possibleMoves.add(new MoveInfo(piece, legalSquare, 0, false, canBeCaptured));
                    }
                    board.reverseMovePiece();
                }
            }
        }
        return possibleMoves;
    }

    public Color getColor() {
        return color;
    }
}
