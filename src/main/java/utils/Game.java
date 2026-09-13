package utils;

import chessBot.ChessBot;
import enums.Color;
import pieces.Piece;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Game {

    private final ChessBoard board;

    public Game(){
        this.board = new ChessBoard();
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\n\n\n\n\n------Welcome to Command Line Chess------");
        System.out.println("Enter number to select gamemode: \n\n1: [Against Bot] \n2: [2-Player game] \n3: [Quit]");

        int gameMode = handleIntInput(scanner, 1, 3);

        if (gameMode == 3){
            System.out.println("See you later!");
            return;
        }

        if (gameMode == 1) {
            System.out.println("Which color would you like to play as?");
            System.out.println("\n1: [White] \n2: [Black] \n3: [Quit]");

            int playerInput = handleIntInput(scanner, 1, 3);

            if (playerInput == 3) {
                System.out.println("See you later!");
                return;
            }
            playAgainstBot(playerInput == 1 ? Color.BLACK : Color.WHITE);
        } else {
            startTwoPlayerGame();
        }


    }

    public void startTwoPlayerGame(){

        board.printBoard();
        System.out.println("\nValid move format: FROM-TO. EXAMPLE: A2-A4");
        while (true){
            playerTurn();
            board.printBoard();
            calculatePieceValues();
            if (board.checkGameEnded()){
                board.printBoard();
                break;
            }
            playerTurn();
            board.printBoard();
            calculatePieceValues();
            if (board.checkGameEnded()){
                board.printBoard();
                break;
            }
            System.out.println(calculatePieceValues());
        }

    }

    public void playAgainstBot(Color color){

        ChessBot chessBot = new ChessBot(color, board);
        if (color.equals(Color.WHITE)){
            chessBot.play();
            board.checkGameEnded();
            board.printBoard();
            MoveRecord botMove = board.getMoveHistoryStack().peek();
            System.out.println("*** Bot played [" + botMove.piece() + "] " + botMove.fromPos() + " -> " + botMove.toPos() + " ***");
            System.out.println(calculatePieceValues());
        }else {
            board.printBoard();
        }

        System.out.println("\nValid move format: FROM-TO. EXAMPLE: A2-A4");
        while (true){
            playerTurn();
            if (board.checkGameEnded()){
                board.printBoard();
                break;
            }
            chessBot.play();
            if (board.checkGameEnded()){
                board.printBoard();
                break;
            }
            board.printBoard();
            MoveRecord botMove = board.getMoveHistoryStack().peek();
            System.out.println("*** Bot played [" + botMove.piece() + "] " + botMove.fromPos() + " -> " + botMove.toPos() + " ***");
            System.out.println(calculatePieceValues());
        }
    }

    public void playerTurn(){

        Scanner input = new Scanner(System.in);

        while (true){
            Color playerTurn = board.getTurnColor();
            System.out.print("(" + playerTurn + ") " + "Enter a valid move [FROM-TO]: ");
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

                if (board.getPieceAt(fromPos).getColor().equals(playerTurn)){
                    if (board.movePiece(fromPos, toPos)){
                        break;
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

    private String calculatePieceValues(){
        int whitePieces = board.getWhitePieces().stream().mapToInt(Piece::getPieceValue).sum();
        int blackPieces = board.getBlackPieces().stream().mapToInt(Piece::getPieceValue).sum();
        int diff = Math.abs(whitePieces - blackPieces);

        if (whitePieces > blackPieces) return "White is up " + diff + " points";
        if (blackPieces > whitePieces) return "Black is up " + diff + " points";
        return "(Black and white are equal on points)";
    }

    private int handleIntInput(Scanner scanner, int min, int max){
        while (true){
            System.out.print("\nEnter number: ");
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max){
                    return value;
                }
            }catch (Exception e){
                System.out.println("Invalid input, please enter a number between " + min + " and " + max);
            }
        }

    }



}
