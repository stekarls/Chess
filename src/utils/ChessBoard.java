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

        int white = 7;
        int black = 0;

        //White pieces
        BOARD[white][0] = new Rook(Color.WHITE, new Position(white,0));
        BOARD[white][7] = new Rook(Color.WHITE, new Position(white,7));
        BOARD[white][1] = new Knight(Color.WHITE, new Position(white,1));
        BOARD[white][6] = new Knight(Color.WHITE, new Position(white,6));
        BOARD[white][2] = new Bishop(Color.WHITE, new Position(white,2));
        BOARD[white][5] = new Bishop(Color.WHITE, new Position(white,5));
        BOARD[white][3] = new Queen(Color.WHITE, new Position(white,3));

        King whiteKing = new King(Color.WHITE, new Position(white,4));
        this.whiteKing = whiteKing;
        BOARD[white][4] = whiteKing;

        //Black Pieces
        BOARD[black][0] = new Rook(Color.BLACK, new Position(black,0));
        BOARD[black][7] = new Rook(Color.BLACK, new Position(black,7));
        BOARD[black][1] = new Knight(Color.BLACK, new Position(black,1));
        BOARD[black][6] = new Knight(Color.BLACK, new Position(black,6));
        BOARD[black][2] = new Bishop(Color.BLACK, new Position(black,2));
        BOARD[black][5] = new Bishop(Color.BLACK, new Position(black,5));
        BOARD[black][3] = new Queen(Color.BLACK, new Position(black,3));

        King blackKing = new King(Color.BLACK, new Position(black,4));
        this.blackKing = blackKing;
        BOARD[black][4] = blackKing;

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

    /*
    public ChessBoard(String fen){
        char[] letters = fen.toCharArray();
        List<Character> letter = List.of(letters);


        int rank = 0;
        for (int i = 0; i < letters.length; i++){
            if (letters[i] == )
        }


        return;
    }
*/
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





//        if (canCapture(piece, targetSquare)){
//            Position originalSquare = piece.getPosition();
//            Piece captured = capture(piece, targetSquare);
//            if (isMyKingChecked(piece)){
//                System.out.println("You left your king vulnerable");
//                capture(piece,originalSquare);
//                if (captured != null){
//                    if (captured.getColor().equals(Color.WHITE)){
//                        whitePieces.add(captured);
//                    }else {
//                        blackPieces.add(captured);
//                    }
//                    insertPiece(captured, targetSquare);
//                }
//                return false;
//            }
//            switch (piece) {
//                case Pawn p -> p.setHasMoved(true);
//                case King k -> k.setHasMoved(true);
//                case Rook r -> r.setHasMoved(true);
//                default -> {}
//            }
//            return true;
//        }


//        System.out.println("Error");
//        return false;


//        System.out.println("\nYou moved " + piece + " from " + piece.getPosition().getX().boardCharacter(from.getY()) + from.boardNumber(from.getX()));

    }

    private void move(Piece piece, Position targetSquare){
        BOARD[piece.getPosition().getRank()][piece.getPosition().getFile()] = null;
        piece.setPosition(targetSquare);
        BOARD[targetSquare.getRank()][targetSquare.getFile()] = piece;

    }

    private void capture(Piece myPiece, Piece targetPiece){

        Color targetColor = targetPiece.getColor();

        if (targetColor.equals(Color.WHITE)){
            whitePieces.remove(targetPiece);
        }else {
            blackPieces.remove(targetPiece);
        }

        move(myPiece, targetPiece.getPosition());



//        if (targetSquare != null){
//            if (targetSquare.getColor().equals(Color.WHITE)){
//                whitePieces.remove(targetSquare);
//            }else {
//                blackPieces.remove(targetSquare);
//            }
//        }
//
//        BOARD[myPos.getRank()][myPos.getFile()] = null;
//        myPiece.setPosition(targetPos);
//        BOARD[targetPos.getRank()][targetPos.getFile()] = myPiece;
    }

    private void reverseCapture(Piece capturedPiece, Position originalSquare){

        Piece myPiece = getPieceAt(capturedPiece.getPosition());

        Color capturedColor = capturedPiece.getColor();

        if (capturedColor.equals(Color.WHITE)){
            whitePieces.add(capturedPiece);
        }else {
            blackPieces.add(capturedPiece);
        }

        move(myPiece, originalSquare);
        insertPiece(capturedPiece, capturedPiece.getPosition());


    }

    private boolean canCaptureOrMove(Piece myPiece, Position targetSquare){

        Position piecePos = myPiece.getPosition();

        if (!myPiece.legalMovement(targetSquare, this)){
            return false;
        }

        if (squareIsEmpty(targetSquare)){
            if(myPiece instanceof Pawn){ //Stops diagonal movement of pawn if there are no enemy pieces at targetsquare
                return targetSquare.getFile() == piecePos.getFile();
            }
            return true;
        } else{
            Piece targetPiece = getPieceAt(targetSquare);
            if (!(targetPiece.getColor().equals(myPiece.getColor()))){
                if (myPiece instanceof  Pawn){   //Stops pawn capturing frontally
                    return piecePos.getFile() != targetSquare.getFile();
                }
                return true;
            }
            System.out.println("Cannot capture piece of same color");
        }
        return false;
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

    public void insertPiece(Piece piece, Position position){
        BOARD[position.getRank()][position.getFile()] = piece;
    }

    public boolean isEmpty(Piece[][] board){
        return false;
    }
    public Piece[][] getBOARD(){
        return this.BOARD;
    }
}

