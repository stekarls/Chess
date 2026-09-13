package chessBot;

import pieces.Piece;
import utils.Position;

public record MoveInfo(Piece piece, Position targetSquare, int enemyValue, boolean threatensKing,
                       boolean canBeCaptured) {

}
