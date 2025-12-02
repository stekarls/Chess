package utils;

import pieces.*;

public class ChessBoard {

    public static void main(String[] args) {
        ChessBoard brett = new ChessBoard();
        brett.printBoard();
    }

    private final int RANKS = 8;
    private final int FILES = 8;
    final Piece[][] BOARD = new Piece[RANKS][FILES];

    public ChessBoard(){

        //Build board
        for (int i = 0; i < 8; i++){
            BOARD[1][i] = new Pawn(Color.BLACK, new Position(1,i));
            BOARD[6][i] = new Pawn(Color.WHITE, new Position(6,i));
        }

        int white = 7;
        int black = 0;

        //Rooks
        BOARD[black][0] = new Rook(Color.BLACK, new Position(black,0));
        BOARD[black][7] = new Rook(Color.BLACK, new Position(black,7));

        BOARD[white][0] = new Rook(Color.WHITE, new Position(white,0));
        BOARD[white][7] = new Rook(Color.WHITE, new Position(white,7));


        //Knights
        BOARD[black][1] = new Knight(Color.BLACK, new Position(black,1));
        BOARD[black][6] = new Knight(Color.BLACK, new Position(black,6));

        BOARD[white][1] = new Knight(Color.WHITE, new Position(white,1));
        BOARD[white][6] = new Knight(Color.WHITE, new Position(white,6));

        //Bishops
        BOARD[black][2] = new Bishop(Color.BLACK, new Position(black,2));
        BOARD[black][5] = new Bishop(Color.BLACK, new Position(black,5));

        BOARD[white][2] = new Bishop(Color.WHITE, new Position(white,2));
        BOARD[white][5] = new Bishop(Color.WHITE, new Position(white,5));

        //King and queen
        BOARD[black][3] = new Queen(Color.BLACK, new Position(black,3));
        BOARD[black][4] = new King(Color.BLACK, new Position(black,4));

        BOARD[white][3] = new Queen(Color.WHITE, new Position(white,3));
        BOARD[white][4] = new King(Color.WHITE, new Position(white,4));















    }

    public void printBoard() {
        
    }



    public Piece getPieceAt(Position position){
        return null;
    }

    public boolean movePiece(Position from, Position to){
        return false;
    }

    public boolean isEmpty(Piece[][] board){
        return false;
    }
}
