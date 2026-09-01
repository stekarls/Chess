package utils;

import pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private final int RANKS = 8;
    private final int FILES = 8;
    private final Piece[][] BOARD = new Piece[RANKS][FILES];
    private final List<Piece> whitePieces = new ArrayList<>();
    private final List<Piece> blackPieces = new ArrayList<>();
    private final King whiteKing;
    private final King blackKing;
    private King kingInCheck;

    public ChessBoard(){

        //Build board

        //Pawns
        for (int i = 0; i < 8; i++){
            BOARD[1][i] = new Pawn(Color.BLACK, new Position(1,i));
            BOARD[6][i] = new Pawn(Color.WHITE, new Position(6,i));
        }

        int whiteRank = 7;
        int blackRank = 0;

        //White pieces
        BOARD[whiteRank][0] = new Rook(Color.WHITE, new Position(whiteRank,0));
        BOARD[whiteRank][7] = new Rook(Color.WHITE, new Position(whiteRank,7));
        BOARD[whiteRank][1] = new Knight(Color.WHITE, new Position(whiteRank,1));
        BOARD[whiteRank][6] = new Knight(Color.WHITE, new Position(whiteRank,6));
        BOARD[whiteRank][2] = new Bishop(Color.WHITE, new Position(whiteRank,2));
        BOARD[whiteRank][5] = new Bishop(Color.WHITE, new Position(whiteRank,5));
        BOARD[whiteRank][3] = new Queen(Color.WHITE, new Position(whiteRank,3));

        King whiteKing = new King(Color.WHITE, new Position(whiteRank,4));
        this.whiteKing = whiteKing;
        BOARD[whiteRank][4] = whiteKing;

        //Black Pieces
        BOARD[blackRank][0] = new Rook(Color.BLACK, new Position(blackRank,0));
        BOARD[blackRank][7] = new Rook(Color.BLACK, new Position(blackRank,7));
        BOARD[blackRank][1] = new Knight(Color.BLACK, new Position(blackRank,1));
        BOARD[blackRank][6] = new Knight(Color.BLACK, new Position(blackRank,6));
        BOARD[blackRank][2] = new Bishop(Color.BLACK, new Position(blackRank,2));
        BOARD[blackRank][5] = new Bishop(Color.BLACK, new Position(blackRank,5));
        BOARD[blackRank][3] = new Queen(Color.BLACK, new Position(blackRank,3));

        King blackKing = new King(Color.BLACK, new Position(blackRank,4));
        this.blackKing = blackKing;
        BOARD[blackRank][4] = blackKing;

        //Add players pieces to their array
        for (Piece[] pieces : BOARD) {
            for (Piece piece : pieces) {
                if (piece != null) {
                    if (piece.getColor().equals(Color.WHITE)) {
                        whitePieces.add(piece);
                    } else {
                        blackPieces.add(piece);
                    }
                }
            }
        }
    }

    public void printBoard() {

        for (int i = 0; i < 3; i++){
            System.out.println();
        }

        int count = 8;
        String space = "  ";
        for (Piece[] pieces : BOARD) {
            System.out.print("\n" + count-- + "     ");
            for (int file = 0; file < BOARD.length; file++) {
                Piece piece = pieces[file];
                if (piece == null) {
                    System.out.print("." + space);
                    continue;
                }
                char symbol = piece.getSymbol();
                if (piece.getColor().equals(Color.BLACK)) {
                    System.out.print(symbol + space);
                } else {
                    System.out.print(symbol + space);
                }
            }
        }
        System.out.println("\n\n    " + space + "A" + space + "B" + space + "C" + space + "D" + space + "E" + space + "F" + space + "G" + space + "H" + "\n");
        //System.out.println("White pieces: " + whitePieces);
        //System.out.println("Black pieces: " + blackPieces);
    }


    private boolean squareIsEmpty(Position position){
        return BOARD[position.getRank()][position.getFile()] == null;
    }

    public Piece getPieceAt(Position position){
        return BOARD[position.getRank()][position.getFile()];
    }

    public boolean movePiece(Position originalSquare, Position targetSquare){

        Piece piece = getPieceAt(originalSquare);

        if (piece == null){
            System.out.println("No piece is in " + originalSquare.boardCharacter(originalSquare.getFile()) + originalSquare.getRank());
            return false;
        }

        Piece targetPiece = getPieceAt(targetSquare);

        //TODO: squareIsEmpty is also checked in canCaptureOrMove, redundant check
        //Move or capture square
        if (canCaptureOrMove(piece, targetSquare)){
            if (squareIsEmpty(targetSquare)){
                move(piece, targetSquare);
            }else{
                capture(piece, targetPiece);
            }
        }else {
            return false;
        }

        //Reverse move or reverse capture if own king is checked
        if (isMyKingChecked(piece)){
            if (targetPiece != null){
                reverseCapture(targetPiece, originalSquare);
            }else {
                move(piece, originalSquare);
            }
            System.out.println("You left your own king vulnerable");
            return false;
        }

        switch (piece) {
                case Pawn p -> p.setHasMoved(true);
                case King k -> k.setHasMoved(true);
                case Rook r -> r.setHasMoved(true);
                default -> {}
            }
            return true;
    }

    private boolean canCaptureOrMove(Piece myPiece, Position targetSquare){

        if (!myPiece.legalMovement(targetSquare, this)){
            return false;
        }

        if (squareIsEmpty(targetSquare)){
            return true;
        } else{
            Piece targetPiece = getPieceAt(targetSquare);
            if (!(targetPiece.getColor().equals(myPiece.getColor()))){
                return true;
            }
            System.out.println("Cannot capture piece of same color");
        }
        return false;
    }

    private void move(Piece piece, Position targetSquare){
        removePiece(piece.getPosition());
        insertPiece(piece, targetSquare);
    }

    private void capture(Piece myPiece, Piece targetPiece){
        move(myPiece, targetPiece.getPosition());
    }

    private void reverseCapture(Piece capturedPiece, Position originalSquare){
        Piece myPiece = getPieceAt(capturedPiece.getPosition());
        move(myPiece, originalSquare);
    }

    public void insertPiece(Piece piece, Position targetSquare){
        BOARD[targetSquare.getRank()][targetSquare.getFile()] = piece;
        piece.setPosition(targetSquare);
        Color pieceColor = piece.getColor();
        List<Piece> list = pieceColor.equals(Color.WHITE) ? whitePieces : blackPieces;
        if (!list.contains(piece)) list.add(piece);

        if (piece instanceof King){
            if (pieceColor.equals(Color.WHITE)){
                whiteKing.setPosition(piece.getPosition());
            }
            else {
                blackKing.setPosition(piece.getPosition());
            }
        }
    }

    public void insertPieces(Piece ... pieces){
        for (Piece piece : pieces){
            insertPiece(piece, piece.getPosition());
        }
    }

    public void removePiece(Position targetSquare){
        Piece piece = getPieceAt(targetSquare);
        if (piece == null) return;
        BOARD[targetSquare.getRank()][targetSquare.getFile()] = null;
        (piece.getColor().equals(Color.WHITE) ? whitePieces : blackPieces).remove(piece);
    }

    private boolean isKingChecked(){
        Position blackKingPos = blackKing.getPosition();
        Position whiteKingPos = whiteKing.getPosition();

        for (Piece piece : whitePieces){
            if (canCaptureOrMove(piece, blackKingPos)){
                System.out.println(piece + " can capture king");
                return true;
            }
        }

        for (Piece piece : blackPieces){
            if (canCaptureOrMove(piece, whiteKingPos)){
                System.out.println(piece + " can capture king");
                return true;
            }
        }

        return false;
    }

    private Color checkedKingColor(){
        Position blackKingPos = blackKing.getPosition();
        Position whiteKingPos = whiteKing.getPosition();

        for (Piece piece : whitePieces){
            if (canCaptureOrMove(piece, blackKingPos)){
                System.out.println(piece + " can capture king");
                return Color.BLACK;
            }
        }

        for (Piece piece : blackPieces){
            if (canCaptureOrMove(piece, whiteKingPos)){
                System.out.println(piece + " can capture king");
                return Color.BLACK;
            }
        }
        return null;
    }

    private boolean isMyKingChecked(Piece myPiece){

        //TODO: Maybe isMyKingChecked kan take color instead of piece, so we dont have to figure out color every time.

        Position blackKingPos = blackKing.getPosition();
        Position whiteKingPos = whiteKing.getPosition();

        if (myPiece.getColor().equals(Color.WHITE)){
            for (Piece piece : blackPieces){
                if (canCaptureOrMove(piece, whiteKingPos)){
                    return true;
                }
            }
        }

        if (myPiece.getColor().equals(Color.BLACK)){
            for (Piece piece : whitePieces){
                if (canCaptureOrMove(piece, blackKingPos)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInCheck(Color color){
        return isMyKingChecked(color.equals(Color.WHITE) ? whiteKing : blackKing);
    }

    public boolean checkGameEnded(){
        return !kingCanMoveFromCheck();
        //TODO: add interception
    }

    private boolean kingCanMoveFromCheck(){
        Color checkedKingColor = checkedKingColor();
        if (checkedKingColor == null) return true;

        if (checkedKingColor.equals(Color.WHITE)) {
            Position kingPos = whiteKing.getPosition();
            for (Position pos : whiteKing.getMoves(this)){
                movePiece(whiteKing.getPosition(), pos);
                if (!isMyKingChecked(whiteKing)){
                    reverseCapture(whiteKing, kingPos);
                    return true;
                }
                reverseCapture(whiteKing, kingPos);
            }

        }else {
            Position kingPos = blackKing.getPosition();
            for (Position pos : blackKing.getMoves(this)){
                movePiece(blackKing.getPosition(), pos);
                if (!isMyKingChecked(blackKing)){
                    reverseCapture(blackKing, kingPos);
                    return true;
                }
                reverseCapture(blackKing, kingPos);
            }
        }
        return false;
    }

    private boolean checkInterception(Piece myPiece){
        return false;
    }

    public Piece promotePawn(Pawn pawn, String newPiece){

        Color color = pawn.getColor();
        Position position = pawn.getPosition();

        Piece promoted = null;


        switch (newPiece.toUpperCase()){
            case "QUEEN" -> {
                promoted = new Queen(color, position);
            }
            case "BISHOP" -> {
                promoted = new Bishop(color, position);
            }
            case "KNIGHT" -> {
                promoted = new Knight(color, position);
            }
            case "ROOK" -> {
                Rook rook = new Rook(color, position);
                rook.setHasMoved(true);
                promoted = rook;
            }
            default -> {
            }
        }

        if (color.equals(Color.WHITE)){
            whitePieces.remove(pawn);
            whitePieces.add(promoted);
        }else {
            blackPieces.remove(pawn);
            blackPieces.add(promoted);
        }

        insertPiece(promoted, position);

        return promoted;
    }


    public boolean isEmpty(Piece[][] board){
        return false;
    }

    public Piece[][] getBOARD(){
        return this.BOARD;
    }

    public Piece[][] clearBoard(){
        for (int i = 0; i < this.BOARD.length; i++){
            for (int j = 0; j < this.BOARD.length; j++){
                this.BOARD[i][j] = null;
            }
        }
        blackPieces.clear();
        whitePieces.clear();
        return this.BOARD;
    }
}

