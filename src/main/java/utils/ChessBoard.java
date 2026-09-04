package utils;

import pieces.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

public class ChessBoard {
    private final int RANKS = 8;
    private final int FILES = 8;
    private final Piece[][] BOARD = new Piece[RANKS][FILES];
    private final List<Piece> whitePieces = new ArrayList<>();
    private final List<Piece> blackPieces = new ArrayList<>();
    private final King whiteKing;
    private final King blackKing;
    private boolean isKingChecked;
    private int turns = 1;
    private int fiftyTurnRule = 0;
    private final Deque<MoveRecord> moveHistoryStack = new ArrayDeque<>();

    public ChessBoard(){

        //Build board

        //Pawns
        for (int i = 0; i < 8; i++){
            BOARD[1][i] = new Pawn(Color.BLACK, new Position(1,i));
            BOARD[6][i] = new Pawn(Color.WHITE, new Position(6,i));
        }

        int whiteRank = 7;
        int blackRank = 0;

        //White pieces
        BOARD[whiteRank][0] = new Rook(Color.WHITE, new Position(whiteRank,0));
        BOARD[whiteRank][7] = new Rook(Color.WHITE, new Position(whiteRank,7));
        BOARD[whiteRank][1] = new Knight(Color.WHITE, new Position(whiteRank,1));
        BOARD[whiteRank][6] = new Knight(Color.WHITE, new Position(whiteRank,6));
        BOARD[whiteRank][2] = new Bishop(Color.WHITE, new Position(whiteRank,2));
        BOARD[whiteRank][5] = new Bishop(Color.WHITE, new Position(whiteRank,5));
        BOARD[whiteRank][3] = new Queen(Color.WHITE, new Position(whiteRank,3));

        King whiteKing = new King(Color.WHITE, new Position(whiteRank,4));
        this.whiteKing = whiteKing;
        BOARD[whiteRank][4] = whiteKing;

        //Black Pieces
        BOARD[blackRank][0] = new Rook(Color.BLACK, new Position(blackRank,0));
        BOARD[blackRank][7] = new Rook(Color.BLACK, new Position(blackRank,7));
        BOARD[blackRank][1] = new Knight(Color.BLACK, new Position(blackRank,1));
        BOARD[blackRank][6] = new Knight(Color.BLACK, new Position(blackRank,6));
        BOARD[blackRank][2] = new Bishop(Color.BLACK, new Position(blackRank,2));
        BOARD[blackRank][5] = new Bishop(Color.BLACK, new Position(blackRank,5));
        BOARD[blackRank][3] = new Queen(Color.BLACK, new Position(blackRank,3));

        King blackKing = new King(Color.BLACK, new Position(blackRank,4));
        this.blackKing = blackKing;
        BOARD[blackRank][4] = blackKing;

        //Add players pieces to their array
        for (Piece[] pieces : BOARD) {
            for (Piece piece : pieces) {
                if (piece != null) {
                    if (piece.getColor().equals(Color.WHITE)) {
                        whitePieces.add(piece);
                    } else {
                        blackPieces.add(piece);
                    }
                }
            }
        }
    }

    public void printBoard() {

        for (int i = 0; i < 3; i++){
            System.out.println();
        }

        int count = 8;
        String space = "  ";
        for (Piece[] pieces : BOARD) {
            System.out.print("\n" + count-- + "     ");
            for (int file = 0; file < BOARD.length; file++) {
                Piece piece = pieces[file];
                if (piece == null) {
                    System.out.print("." + space);
                    continue;
                }
                char symbol = piece.getSymbol();
                System.out.print(symbol + space);
            }
        }
        System.out.println("\n\n    " + space + "A" + space + "B" + space + "C" + space + "D" + space + "E" + space + "F" + space + "G" + space + "H" + "\n");
    }

    private boolean squareIsEmpty(Position position){
        return BOARD[position.getRank()][position.getFile()] == null;
    }

    public Piece getPieceAt(Position position){
        return BOARD[position.getRank()][position.getFile()];
    }

    public boolean movePiece(Position originalSquare, Position targetSquare){

        Piece piece = getPieceAt(originalSquare);

        if (piece == null){
            System.out.println("No piece is in " + originalSquare.boardCharacter(originalSquare.getFile()) + originalSquare.getRank());
            return false;
        }

        Piece targetPiece = getPieceAt(targetSquare);

        if (canCaptureOrMove(piece, targetSquare)){
            move(piece, targetSquare);
        }else {
            return false;
        }

        //Reverse move or reverse capture if own king is checked
        if (isMyKingChecked()){
            if (targetPiece != null){
                reverseCapture(targetPiece, originalSquare);
            }else {
                move(piece, originalSquare);
            }
            System.out.println("You left your own king vulnerable");
            return false;
        }

        switch (piece) {
                case Pawn p -> p.setHasMoved(true);
                case King k -> k.setHasMoved(true);
                case Rook r -> r.setHasMoved(true);
                default -> {}
            }

        moveHistoryStack.push(new MoveRecord(originalSquare, targetSquare, targetPiece));
        turns++;

        return true;
    }

    public void reverseMovePiece(){

        MoveRecord lastTurn = moveHistoryStack.pop();
        Position originalSquare = lastTurn.fromPos();
        Position capturedSquare = lastTurn.toPos();
        Piece capturedPiece = lastTurn.captured();


        if (capturedPiece != null){
            reverseCapture(capturedPiece, originalSquare);
        }else {
            move(getPieceAt(capturedSquare), originalSquare);
        }

        //TODO: Logic for reversing has moved
        turns--;
    }

    private boolean canCaptureOrMove(Piece myPiece, Position targetSquare){

        if (!myPiece.legalMovement(targetSquare, this)){
            return false;
        }

        if (squareIsEmpty(targetSquare)){
            return true;
        } else{
            Piece targetPiece = getPieceAt(targetSquare);
            if (!(targetPiece.getColor().equals(myPiece.getColor()))){
                return true;
            }
            System.out.println("Cannot capture piece of same color");
        }
        return false;
    }

    private void move(Piece piece, Position targetSquare){
        removePiece(piece.getPosition());
        insertPiece(piece, targetSquare);
    }

    private void reverseCapture(Piece capturedPiece, Position originalSquare){
        Piece myPiece = getPieceAt(capturedPiece.getPosition());
        move(myPiece, originalSquare);
        insertPiece(capturedPiece, capturedPiece.getPosition());
    }

    public void insertPiece(Piece piece, Position targetSquare){
        BOARD[targetSquare.getRank()][targetSquare.getFile()] = piece;
        piece.setPosition(targetSquare);
        Color pieceColor = piece.getColor();
        List<Piece> list = pieceColor.equals(Color.WHITE) ? whitePieces : blackPieces;
        if (!list.contains(piece)) list.add(piece);

        if (piece instanceof King){
            if (pieceColor.equals(Color.WHITE)){
                whiteKing.setPosition(piece.getPosition());
            }
            else {
                blackKing.setPosition(piece.getPosition());
            }
        }
    }

    public void insertPieces(Piece ... pieces){
        for (Piece piece : pieces){
            insertPiece(piece, piece.getPosition());
        }
    }

    public void removePiece(Position targetSquare){
        Piece piece = getPieceAt(targetSquare);
        if (piece == null) return;
        BOARD[targetSquare.getRank()][targetSquare.getFile()] = null;
        (piece.getColor().equals(Color.WHITE) ? whitePieces : blackPieces).remove(piece);
    }

    private boolean isMyKingChecked(){

        Color colorTurn = calculatePlayerTurn();

        Position myKingPos = colorTurn.equals(Color.WHITE) ? whiteKing.getPosition() : blackKing.getPosition();
        List<Piece> enemyPieces = colorTurn.equals(Color.WHITE) ? blackPieces : whitePieces;
        List<Piece> kingThreats = enemyPieces.stream().filter(piece -> canCaptureOrMove(piece, myKingPos)).toList();

        return !kingThreats.isEmpty();
    }

    private ArrayList<Piece> piecesAttackingKing(){
        return null;
    }

    public boolean checkGameEnded(){


        //TODO: fix nullpointer on captured an then merge two if statements
        MoveRecord moveRecord = moveHistoryStack.peek();
        if (moveRecord != null && moveRecord.captured() == null){
            fiftyTurnRule++;
        }
        if (moveRecord != null && getPieceAt(moveRecord.toPos()) instanceof Pawn){
            fiftyTurnRule++;
        }

        if (fiftyTurnRule >= 100){
            return true;
        }

        if (isMyKingChecked()){
            if (!kingCanMoveFromCheck()){
                return !pieceCanInterceptCheck();
            }
        }else {
            if (isStaleMate()){
                return true;
            }else if (whitePieces.size() + blackPieces.size() < 5){
                return isInsufficientMaterial();
            }
        }

        return false;
        //TODO: If total piece count is less than (number) start checking for insufficient material
    }

    private boolean isInsufficientMaterial(){

        int blackPieceSize = blackPieces.size();
        int whitePieceSize = whitePieces.size();

        if (blackPieceSize > 2 || whitePieceSize > 2){
            return false;
        }

        if (blackPieces.size() == 1 && whitePieces.size() == 1){
            return true;
        }

        List <Piece> allPieces = Stream.concat(blackPieces.stream(), whitePieces.stream()).toList();
        List <Piece> withoutKing = allPieces.stream().filter(p -> !(p instanceof King)).toList();
        List <Piece> whiteWithoutKing = whitePieces.stream().filter(p -> !(p instanceof King)).toList();
        List <Piece> blackWithoutKing = blackPieces.stream().filter(p -> !(p instanceof King)).toList();



        //TODO: Maybe check if there is an instance of something else than bishop and knight first to skip following tests

        //Checks if it is king and (bishop or knight) vs lone king.
        if (whitePieceSize != blackPieceSize){
            if (whitePieceSize > blackPieceSize){
                Piece lastPiece = whiteWithoutKing.getFirst();
                return lastPiece instanceof Knight || lastPiece instanceof Bishop;
            }else {
                Piece lastPiece = blackWithoutKing.getFirst();
                return lastPiece instanceof Knight || lastPiece instanceof Bishop;
            }
        }else {
            Piece whitePiece = whiteWithoutKing.getFirst();
            Piece blackPiece = blackWithoutKing.getFirst();

            if (whitePiece instanceof Bishop && blackPiece instanceof Bishop){
                return whitePiece.getPosition().getSquareColor().equals(blackPiece.getPosition().getSquareColor());
            }
        }





        return false;
    }

    private boolean isStaleMate(){

        Color checkedKingColor = calculatePlayerTurn();
        King myKing = checkedKingColor.equals(Color.WHITE) ? whiteKing : blackKing;
        Position kingPos = myKing.getPosition();

        List<Piece> myPieces = new ArrayList<>(myKing.getColor().equals(Color.WHITE) ? whitePieces : blackPieces);
        for (Piece piece : myPieces){
            if (canPieceMove(piece)){
                return false;
            }
        }

        return true;
    }

    private boolean canPieceMove(Piece piece){
        for (int i = 0; i < BOARD.length; i++){
            for (int j = 0; j < BOARD[i].length; j++){
                if (movePiece(piece.getPosition(), new Position(i, j))){
                    reverseMovePiece();
                    return true;
                }
            }
        }
        return false;

    }

    //TODO: Duplicate, same as canKingMove
    private boolean kingCanMoveFromCheck(){

        Color checkedKingColor = calculatePlayerTurn();
        King checkedKing = checkedKingColor.equals(Color.WHITE) ? whiteKing : blackKing;
        Position kingPos = checkedKing.getPosition();

        return canKingMove(checkedKing, kingPos);
    }

    private boolean canKingMove(King king, Position kingPos){
        for (Position pos : king.getMoves(this)){
            if (movePiece(kingPos, pos)){
                reverseMovePiece();
                return true;
            }
        }
        return false;
    }

    private boolean pieceCanInterceptCheck(){
        Color colorTurn = calculatePlayerTurn();

        Position myKingPos = colorTurn.equals(Color.WHITE) ? whiteKing.getPosition() : blackKing.getPosition();
        List<Piece> enemyPieces = colorTurn.equals(Color.WHITE) ? blackPieces : whitePieces;
        List<Piece> myPieces = new ArrayList<>(colorTurn.equals(Color.WHITE) ? whitePieces : blackPieces);
        List<Piece> kingThreats = enemyPieces.stream().filter(piece -> canCaptureOrMove(piece, myKingPos)).toList();
        List<Position> squaresToIntercept = new ArrayList<>();

        if ((kingThreats.size() > 1)){
            return false;
        }

        for (Piece piece : kingThreats){
            if (!(piece instanceof Knight)){
                int rankDifference = myKingPos.getRank() - piece.getPosition().getRank();
                int fileDifference = myKingPos.getFile() - piece.getPosition().getFile();

                int rankStep = Integer.compare(rankDifference, 0);
                int fileStep = Integer.compare(fileDifference, 0);

                Position square = piece.getPosition();
                do {
                    squaresToIntercept.add(new Position(square.getRank(), square.getFile()));
                    square.setRank(square.getRank() + rankStep);
                    square.setFile(square.getFile() + fileStep);
                } while (getPieceAt(square) == null);
            }
        }

        for (Piece piece : myPieces){
            if (piece instanceof King){
                continue;
            }
            for (Position square : squaresToIntercept){
                if (canCaptureOrMove(piece, square)){
                    if (movePiece(piece.getPosition(), square)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Piece promotePawn(Pawn pawn, String newPiece){

        Color color = pawn.getColor();
        Position position = pawn.getPosition();

        Piece promoted = null;


        switch (newPiece.toUpperCase()){
            case "QUEEN" -> promoted = new Queen(color, position);
            case "BISHOP" -> promoted = new Bishop(color, position);
            case "KNIGHT" -> promoted = new Knight(color, position);
            case "ROOK" -> {
                Rook rook = new Rook(color, position);
                rook.setHasMoved(true);
                promoted = rook;
            }
            default -> {
                return null;
            }
        }

        List<Piece> list = color.equals(Color.WHITE) ? whitePieces : blackPieces;
        list.remove(pawn);
        insertPiece(promoted, position);

        return promoted;
    }

    public Piece[][] getBOARD(){
        return this.BOARD;
    }

    public Piece[][] clearBoard(){
        for (int i = 0; i < this.BOARD.length; i++){
            for (int j = 0; j < this.BOARD.length; j++){
                this.BOARD[i][j] = null;
            }
        }
        blackPieces.clear();
        whitePieces.clear();
        return this.BOARD;
    }

    public Color calculatePlayerTurn(){
        return this.turns % 2 == 0 ? Color.BLACK : Color.WHITE;
    }

    public int getPlayerTurn() {
        return turns;
    }

    public void setPlayerTurn(int num){
        this.turns = num;
    }
}

