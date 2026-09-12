package chessBot;

import pieces.Piece;
import utils.Position;

public class MoveInfo {

    private final Piece piece;
    private final Position targetSquare;
    private final int enemyValue;
    private final boolean threatensKing;
    private final boolean canBeCaptured;

    public MoveInfo(Piece piece, Position targetSquare, int enemyValue, boolean threatensKing, boolean canBeCaptured){
        this.piece = piece;
        this.targetSquare = targetSquare;
        this.enemyValue = enemyValue;
        this.threatensKing = threatensKing;
        this.canBeCaptured = canBeCaptured;
    }


    public Piece getPiece() {
        return piece;
    }

    public Position getTargetSquare() {
        return targetSquare;
    }

    public int getEnemyValue() {
        return enemyValue;
    }

    public boolean isThreatensKing() {
        return threatensKing;
    }

    public boolean isCanBeCaptured() {
        return canBeCaptured;
    }
}
