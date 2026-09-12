package utils;

import enums.SquareColor;

import java.util.Objects;

public class Position {

    private int rank;
    private int file;
    private final SquareColor squareColor;

    public Position(int rank, int file){
        this.rank = rank;
        this.file = file;
        this.squareColor = calculateSquareColor(this.getRank(), this.getFile());
    }

    public Position(char file, int rank){
        this.rank = inputToBoardNumber(rank);
        this.file = getFile(file);
        this.squareColor = calculateSquareColor(this.getRank(), this.getFile());
    }

    public Position(char file, char rank){
        this.rank = inputToBoardNumber(Character.getNumericValue(rank));
        this.file = getFile(file);
        this.squareColor = calculateSquareColor(this.getRank(), this.getFile());
    }

    public Position (String chessNotation){
        char file = chessNotation.charAt(0);
        char rank = chessNotation.charAt(1);
        this.rank = inputToBoardNumber(Character.getNumericValue(rank));
        this.file = getFile(file);
        this.squareColor = calculateSquareColor(this.getRank(), this.getFile());
    }

    public int getFile(char letter){
        return switch (Character.toUpperCase(letter)){
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            case 'E' -> 4;
            case 'F' -> 5;
            case 'G' -> 6;
            case 'H' -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + letter);
        };
    }

    public int inputToBoardNumber(int rank){
        return switch (rank){
            case 8 -> 0;
            case 7 -> 1;
            case 6 -> 2;
            case 5 -> 3;
            case 4 -> 4;
            case 3 -> 5;
            case 2 -> 6;
            case 1 -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + rank);
        };
    }

    public int boardNumberToOutput(int rank){
        return switch (rank){
            case 7 -> 1;
            case 6 -> 2;
            case 5 -> 3;
            case 4 -> 4;
            case 3 -> 5;
            case 2 -> 6;
            case 1 -> 7;
            case 0 -> 8;
            default -> throw new IllegalStateException("Unexpected value: " + rank);
        };
    }

    public char boardCharacter(int file){
        return switch (file){
            case 0 -> 'A';
            case 1 -> 'B';
            case 2 -> 'C';
            case 3 -> 'D';
            case 4 -> 'E';
            case 5 -> 'F';
            case 6 -> 'G';
            case 7 -> 'H';
            default -> throw new IllegalStateException("Unexpected value: " + file);
        };
    }

    public int getRank() {
        return this.rank;
    }

    public int getFile() {
        return this.file;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public SquareColor getSquareColor() {
        return squareColor;
    }

    public boolean legalPosition(){
        return (this.rank < 8 && this.rank >= 0) && (this.file < 8 && this.file >= 0);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;
        return rank == position.rank && file == position.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }

    public String chessNotation(){
        return boardCharacter(this.file) + inputToBoardNumber(this.rank) + "";
    }

    private SquareColor calculateSquareColor(int rank, int file){
        if (rank % 2 == file % 2){
            return SquareColor.LIGHT;
        }
        return SquareColor.DARK;
    }

    @Override
    public String toString() {
        String file = Character.toString(boardCharacter(this.file));
        int rank = boardNumberToOutput(this.rank);

        return file + rank + "";
    }
}
