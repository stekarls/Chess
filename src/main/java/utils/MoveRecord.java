package utils;

import pieces.Piece;

public record MoveRecord (
        Position fromPos,
        Position toPos,
        Piece captured){}


