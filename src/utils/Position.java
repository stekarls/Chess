package utils;

import java.util.Objects;

public class Position {

    private int rank;
    private int file;

    public Position(int rank, int file){
        this.rank = rank;
        this.file = file;
    }

    public Position(char file, int rank){
        this.rank = rank;
        this.file = getRank(file);
    }

    public int getRank(char letter){
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

    public char boardCharacter(int number){
        return switch (number){
            case 0 -> 'A';
            case 1 -> 'B';
            case 2 -> 'C';
            case 3 -> 'D';
            case 4 -> 'E';
            case 5 -> 'F';
            case 6 -> 'G';
            case 7 -> 'H';
            default -> throw new IllegalStateException("Unexpected value: " + number);
        };
    }

    public int boardNumber(int number){
        return switch (number){
            case 7 -> 1;
            case 6 -> 2;
            case 5 -> 3;
            case 4 -> 4;
            case 3 -> 5;
            case 2 -> 6;
            case 1 -> 7;
            case 0 -> 8;
            default -> throw new IllegalStateException("Unexpected value: " + number);
        };
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;
        return rank == position.rank && file == position.file;
    }

    public boolean legalPosition(){
        return (this.rank < 8 && this.rank >= 0) && (this.file < 7 && this.file >= 0);
    }
    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }
}
