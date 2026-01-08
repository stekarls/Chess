package utils;

import pieces.Piece;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Game {

    public static void main(String[] args) {

        int totalMoves = 1;
        Scanner input = new Scanner(System.in);

        ChessBoard board = new ChessBoard();
        board.printBoard();


        while (true){
            //System.out.println("\n------ Valid move format: [letter][number]-[letter][number]. EXAMPLE: A2-A4 ------");
            System.out.print("Enter a valid move: ");
            String move = input.nextLine();

            if (move.equals("exit")){
                break;
            }

            if (Pattern.matches("[A-Ha-h][1-8]-[A-Ha-h][1-8]", move)){
                char[] letters = move.toCharArray();
                Position piecePos = new Position(letters[0], Character.getNumericValue(letters[1]));

                if (checkPlayerTurn(board, piecePos, totalMoves)){
                   if (board.movePiece(piecePos, new Position(letters[3], Character.getNumericValue(letters[4])))){
                       totalMoves++;
                       board.printBoard();
                   }
               }else {
                   System.out.println("Not this player's turn");
               }
            }else {
                System.out.println("Move is not written in right format, example: A4-C2");
            }

        }
    }

    private static boolean checkPlayerTurn(ChessBoard board, Position position, int totalMoves){
        Piece piece = board.getPieceAt(position);
        if (piece != null){
            Color pieceColor = piece.getColor();
            boolean isWhite = pieceColor.equals(Color.WHITE);
            if (isWhite && totalMoves % 2 != 0){
                return true;
            } else return !isWhite && totalMoves % 2 == 0;
        }
        return false;

    }



}
