package utils;

import pieces.Piece;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ChessBot {

    private final Color myColor;
    private final Color enemyColor;
    private final ChessBoard board;
    private final List<Piece> myPieces;

    public ChessBot(Color myColor, ChessBoard board){
        this.myColor = myColor;
        this.board =board;
        this.myPieces = myColor.equals(Color.WHITE) ? board.getWhitePieces() : board.getBlackPieces();
        this.enemyColor = myColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    Comparator<MoveInfo> setPriorityOfMove = Comparator.comparingInt((MoveInfo move) -> {
        int priority = 0;
        priority += move.getEnemyValue();
        if (move.isThreatensKing()) priority += 10;
        if (move.isCanBeCaptured()) priority -= 20;
        return priority;

    }).reversed();

    public void play(){
        MoveInfo moveInfo = evaluateMoves();
        board.movePiece(moveInfo.getPiece().getPosition(), moveInfo.getTargetSquare());
    }

    private MoveInfo evaluateMoves(){
        PriorityQueue<MoveInfo> possibleMoves = getAllMoves();
        return possibleMoves.poll();
    }

    private PriorityQueue<MoveInfo> getAllMoves(){
        PriorityQueue<MoveInfo> possibleMoves = new PriorityQueue<>(setPriorityOfMove);
        List<Piece> myPieces = this.myPieces;

        for (Piece piece : myPieces){
            for (Position legalSquare : piece.getMoves(board)){
                if (board.movePiece(piece.getPosition(), legalSquare)){ //Or canMoveOrCapture
                    Piece enemyPiece = board.getPieceAt(legalSquare);
                    boolean canBeCaptured = !board.whoCanCapturePiece(piece).isEmpty();
                    boolean threatensKing = board.isKingChecked(enemyColor);
                    if (enemyPiece != null){
                        int enemyValue = enemyPiece.getPieceValue();
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

}
