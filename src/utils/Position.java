package utils;

public class Position {

    int x;
    int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position(char y, int x){
        this.x = x;
        this.y = getRank(y);
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



}
