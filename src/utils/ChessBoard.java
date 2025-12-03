package utils;

import pieces.*;

public class ChessBoard {

    public static void main(String[] args) {
        ChessBoard brett = new ChessBoard();
        brett.printBoard();

        brett.movePiece(new Position(6, 1), new Position(5, 1));
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

        //"Clear" history in console
        for (int i = 0; i < 50; i++){
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

    public boolean movePiece(Position from, Position to){
        Piece piece = getPieceAt(from);

        if (piece.legalMove(to)){
            captureSquare(piece, to);
            printBoard();
            return true;
        }else {
            System.out.println("Not a legal move for " + piece);
            return false;
        }

//        System.out.println("\nYou moved " + piece + " from " + piece.getPosition().getX().boardCharacter(from.getY()) + from.boardNumber(from.getX()));

    }

    private boolean captureSquare(Piece piece, Position to){
        Position piecePos = piece.getPosition();
        Piece targetPiece = getPieceAt(to);

        if (targetPiece == null){
            if(piece instanceof Pawn pawn){
                if(to.getFile() != piecePos.getFile()){
                    return false;
                }
                pawn.setHasMoved(true);
            }
            BOARD[piecePos.getRank()][piecePos.getFile()] = null;
            piece.setPosition(to);
            BOARD[to.getRank()][to.getFile()] = piece;

            return true;

        } else if (!(targetPiece.getColor().equals(piece.getColor()))){
            targetPiece.setPosition(null);
            BOARD[targetPiece.getPosition().getRank()][targetPiece.getPosition().getFile()] = null;
            return captureSquare(piece, to);
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

