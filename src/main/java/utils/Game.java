package utils;

import chessBot.ChessBot;
import enums.Color;
import pieces.*;
import pieces.Piece;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Game {

    private final ChessBoard board;

    public Game(){
        this.board = new ChessBoard();
    }

    public void start(){

        Color playerTurn;
        Scanner input = new Scanner(System.in);

//        board.clearBoard();
//
//        Piece[] pieces = new Piece[] {
//                new Pawn(Color.WHITE, new Position("G7")),
//                new King(Color.WHITE, new Position("A1")),
//                new King(Color.BLACK, new Position("A8")),
//                new Pawn(Color.BLACK, new Position("E2"))
//        };
//        board.insertPieces(pieces);

        System.out.println("\n\n\n\n\n\n------Welcome to Command Line Chess------");
        System.out.println("Valid move format: FROM-TO. EXAMPLE: A2-A4");
        board.printBoard();

        while (true){
            playerTurn = board.getTurnColor();
            System.out.print("(" + playerTurn + ") " + "Enter a valid move: ");
            String move = input.nextLine();

            if (move.equals("exit")){
                break;
            }
            if (move.equalsIgnoreCase("undo")){
                if (!board.undo()){
                    System.out.println("No more moves to undo");
                    continue;
                }
                board.printBoard();
                continue;
            }

//            if (move.equalsIgnoreCase("REDO")){
//                if (!board.redo()){
//                    System.out.println("No more moves to undo");
//                    continue;
//                }
//                board.printBoard();
//                continue;
//            }

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
                       board.printBoard();
                   }
               }else {
                   System.out.println("Not this player's turn");
                    board.printBoard();
               }
            }else {
                System.out.println("Move is not written in right format, example: A4-C2");
            }


        }
    }

    public void playWithBot(Color chosenColor){

        Color botColor = chosenColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        ChessBot chessBot = new ChessBot(botColor, board);
        Scanner input = new Scanner(System.in);

        board.clearBoard();

        Piece[] pieces = new Piece[] {
                new King(Color.WHITE, new Position("G1")),
                new Pawn(Color.WHITE, new Position("A7")),
                new Pawn(Color.WHITE, new Position("B6")),
                new Pawn(Color.WHITE, new Position("C2")),
                new Pawn(Color.WHITE, new Position("F2")),
                new Pawn(Color.WHITE, new Position("G2")),
                new Pawn(Color.WHITE, new Position("H3")),
                new Rook(Color.WHITE, new Position("C6")),


                new King(Color.BLACK, new Position("B7")),
                new Pawn(Color.BLACK, new Position("D4")),
                new Pawn(Color.BLACK, new Position("H4")),
                new Rook(Color.BLACK, new Position("G8")),
        };
        board.insertPieces(pieces);

        System.out.println("\n\n\n\n\n\n------Welcome to Command Line Chess------");
        System.out.println("Valid move format: FROM-TO. EXAMPLE: A2-A4");
        board.printBoard();

        while (true){
            Color playerTurn = board.getTurnColor();
            System.out.print("(" + playerTurn + ") " + "Enter a valid move: ");
            String move = input.nextLine();

            if (move.equals("exit")){
                break;
            }
            if (move.equalsIgnoreCase("undo")){
                if (!board.undo()){
                    System.out.println("No more moves to undo");
                    continue;
                }
                board.printBoard();
                continue;
            }

//            if (move.equalsIgnoreCase("REDO")){
//                if (!board.redo()){
//                    System.out.println("No more moves to undo");
//                    continue;
//                }
//                board.printBoard();
//                continue;
//            }

            if (Pattern.matches("[A-Ha-h][1-8]-[A-Ha-h][1-8]", move)){
                Position fromPos = new Position(move.charAt(0), move.charAt(1));
                Position toPos = new Position(move.charAt(3), move.charAt(4));

                if (board.getPieceAt(fromPos).getColor().equals(chosenColor)){
                    if (board.movePiece(fromPos, toPos)){
                        if (board.checkGameEnded()){
                            System.out.println("Game Over");
                            break;
                        }
                        chessBot.play();
                        if (board.checkGameEnded()){
                            System.out.println("Game Over");
                            break;
                        }
                        board.printBoard();

                    }
                }else {
                    System.out.println("You cannot move another player's pieces");
                }
            }else {
                System.out.println("Move is not written in right format, example: A4-C2");
            }


        }
    }

    public void simulateChessGame(){

        ChessBot chessBotWhite = new ChessBot(Color.WHITE, board);
        ChessBot chessBotBlack = new ChessBot(Color.BLACK, board);
        while (true){
            chessBotWhite.play();
            if (board.checkGameEnded()){
                System.out.println("Game Over");
                break;
            }
            chessBotBlack.play();
            if (board.checkGameEnded()){
                System.out.println("Game Over");
                break;
            }

            board.printBoard();

        }
    }


    private void playerTurn(){

    }

    private boolean verifyPlayerTurn(ChessBoard board, Position position, Color playerTurn){
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
