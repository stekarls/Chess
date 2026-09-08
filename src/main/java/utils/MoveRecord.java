package utils;

import pieces.Piece;

public record MoveRecord (
        Piece piece,
        Position fromPos,
        Position toPos,
        Piece captured,
        boolean promoted,
        boolean firstMove){}
//TODO: ADD enpassant field,


