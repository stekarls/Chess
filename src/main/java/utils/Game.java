package utils;

import pieces.Piece;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Game {

    public static void main(String[] args) {

        Color playerTurn;
        Scanner input = new Scanner(System.in);

        ChessBoard board = new ChessBoard();
        System.out.println("\n\n\n\n\n\n------Welcome to Command Line Chess------");
        System.out.println("Valid move format: FROM-TO. EXAMPLE: A2-A4");
        board.printBoard();

        while (true){
            playerTurn = board.calculatePlayerTurn();
            System.out.print("(" + playerTurn + ") " + "Enter a valid move: ");
            String move = input.nextLine();

            if (move.equals("exit")){
                break;
            }
            if (move.equals("regret")){
                board.reverseMovePiece();
                board.printBoard();
                continue;
            }

            if (Pattern.matches("[A-Ha-h][1-8]-[A-Ha-h][1-8]", move)){
                Position fromPos = new Position(move.charAt(0), move.charAt(1));
                Position toPos = new Position(move.charAt(3), move.charAt(4));

                if (verifyPlayerTurn(board, fromPos, playerTurn)){
                   if (board.movePiece(fromPos, toPos)){
                       board.printBoard();
                       if (board.checkGameEnded()){
                           System.out.println("King unable to move, checkmate"); //TODO: a game does not allways end in checkmate
                           break;
                       }
                   }
               }else {
                   System.out.println("Not this player's turn");
               }
            }else {
                System.out.println("Move is not written in right format, example: A4-C2");
            }

        }
    }

    private static boolean verifyPlayerTurn(ChessBoard board, Position position, Color playerTurn){
        Piece piece = board.getPieceAt(position);
        if (piece != null){
            return piece.getColor().equals(playerTurn);
        }
        return false;




//        Piece piece = board.getPieceAt(position);
//        if (piece != null){
//            Color pieceColor = piece.getColor();
//            boolean isWhite = pieceColor.equals(Color.WHITE);
//            if (isWhite && totalMoves % 2 != 0){
//                return true;
//            } else return !isWhite && totalMoves % 2 == 0;
//        }
//        return false;
    }



}
