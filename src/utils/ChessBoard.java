package utils;

import javafx.geometry.Pos;
import pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private final int RANKS = 8;
    private final int FILES = 8;
    final Piece[][] BOARD = new Piece[RANKS][FILES];
    private List<Piece> whitePieces = new ArrayList<>();
    private List<Piece> blackPieces = new ArrayList<>();
    private Piece whiteKing;
    private Piece blackKing;

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

        Piece whiteKing = new King(Color.WHITE, new Position(white,4));
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

        Piece blackKing = new King(Color.BLACK, new Position(black,4));
        this.blackKing = blackKing;
        BOARD[black][4] = blackKing;

        //Add players pieces to their array
        for (int i = 0; i < BOARD.length; i++){
            for (int j = 0; j < BOARD[i].length; j++){
                Piece piece = BOARD[i][j];
                if (piece != null){
                    if (BOARD[i][j].getColor().equals(Color.WHITE)){
                        whitePieces.add(piece);
                    }else {
                        blackPieces.add(piece);
                    }
                }
            }
        }
        System.out.println("White pieces: " + whitePieces);
        System.out.println("Black pieces: " + blackPieces);

    }

    public void printBoard() {

        //"Clear" history in console
        for (int i = 0; i < 10; i++){
            System.out.println();
        }

        int count = 8;
        String space = "  ";
        for (int rank = 0; rank < BOARD.length; rank++){
            System.out.print("\n" + count-- + "     ");
            for (int file = 0; file < BOARD.length; file++){
                Piece piece = BOARD[rank][file];
                if (piece == null){
                    System.out.print("." + space);
                    continue;
                }
                char symbol = piece.getSymbol();
                if (piece.getColor().equals(Color.BLACK)){
                    System.out.print(symbol + space);
                }else {
                    System.out.print(symbol + space);
                }
            }
        }
        System.out.println("\n\n    " + space + "A" + space + "B" + space + "C" + space + "D" + space + "E" + space + "F" + space + "G" + space + "H");
    }



    public Piece getPieceAt(Position position){
        return BOARD[position.getRank()][position.getFile()];
    }

    public boolean movePiece(Position from, Position targetSquare){
        Piece piece = getPieceAt(from);

        if (piece == null){
            System.out.println("No piece is in " + from.boardCharacter(from.getFile()) + from.getRank());
            return false;
        }

        if (canCapture(piece, targetSquare)){
            Position originalSquare = piece.getPosition();
            capture(piece, targetSquare);
            if (isMyKingChecked(piece)){
                System.out.println("You left your king vulnerable");
                capture(piece,originalSquare);
                return false;
            }
            if (piece instanceof Pawn){
                ((Pawn) piece).setHasMoved(true);
            }else if (piece instanceof King){
                ((King) piece).setHasMoved(true);
            } else if (piece instanceof Rook){
                ((Rook) piece).setHasMoved(true);

            }
            return true;
        }


        System.out.println("Error");
        return false;


//        System.out.println("\nYou moved " + piece + " from " + piece.getPosition().getX().boardCharacter(from.getY()) + from.boardNumber(from.getX()));

    }


    private boolean isKingChecked(){
        Position blackKingPos = blackKing.getPosition();
        Position whiteKingPos = whiteKing.getPosition();

        for (Piece piece : whitePieces){
            if (canCapture(piece, blackKingPos)){
                System.out.println(piece + " can capture");
                return true;
            }
        }

        for (Piece piece : blackPieces){
            if (canCapture(piece, whiteKingPos)){
                System.out.println(piece + " can capture");
                return true;
            }
        }

        return false;
    }

    private boolean isMyKingChecked(Piece myPiece){

        Position blackKingPos = blackKing.getPosition();
        Position whiteKingPos = whiteKing.getPosition();

        if (myPiece.getColor().equals(Color.WHITE)){
            for (Piece piece : blackPieces){
                if (canCapture(piece, whiteKingPos)){
                    System.out.println(piece + " From the debug!");
                    return true;
                }
            }
        }

        if (myPiece.getColor().equals(Color.BLACK)){
            for (Piece piece : whitePieces){
                if (canCapture(piece, blackKingPos)){
                    System.out.println(piece + " From the debug!");
                    return true;
                }
            }
        }
        return false;
    }


    private void capture(Piece myPiece, Position targetPos){
        Position myPos = myPiece.getPosition();
        Piece targetSquare = getPieceAt(targetPos);

        //TODO: Chek if this actually works, that it finds right piece
        if (targetSquare != null){
            if (targetSquare.getColor().equals(Color.WHITE)){
                whitePieces.remove(targetSquare);
            }else {
                blackPieces.remove(targetSquare);
            }
        }

        BOARD[myPos.getRank()][myPos.getFile()] = null;
        myPiece.setPosition(targetPos);
        BOARD[targetPos.getRank()][targetPos.getFile()] = myPiece;
    }

    private boolean canCapture(Piece myPiece, Position square){

        Position piecePos = myPiece.getPosition();
        Piece targetSquare = getPieceAt(square);

        if (!myPiece.legalMovement(square, this)){
            return false;
        }

        if (targetSquare == null){
            if(myPiece instanceof Pawn){ //Stops diagonal movement of pawn if there are no enemy pieces
                return square.getFile() == piecePos.getFile();
            }
            return true;
        } else if(!(targetSquare.getColor().equals(myPiece.getColor()))){
            if (myPiece instanceof  Pawn){   //Stops pawn capturing frontally
                return piecePos.getFile() != square.getFile();
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty(Piece[][] board){
        return false;
    }
    public Piece[][] getBOARD(){
        return this.BOARD;
    }
}

