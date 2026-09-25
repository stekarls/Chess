# Command-Line Chess

A chess engine and playable command-line game written in plain Java (no external chess libraries), modeling the full board, piece movement rules, and check detection using object-oriented design. Was initially a challenge to write a complete chess game just from my knowledge of Java without aid or AI. The project has been developed and tested with the aid of the JUnit testing framework.

```


8   r  n  b  q  k  b  n  r
7   p  p  p  p  p  p  p  p
6   .  .  .  .  .  .  .  .
5   .  .  .  .  .  .  .  .
4   .  .  .  .  .  .  .  .
3   .  .  .  .  .  .  .  .
2   P  P  P  P  P  P  P  P
1   R  N  B  Q  K  B  N  R

    A  B  C  D  E  F  G  H
```

## About

This project was built to practice object-oriented design in Java by modeling a non-trivial rule system from scratch — move legality, turn order, and check detection — without relying on a chess library. Moves are entered in the console using standard algebraic-style coordinates (e.g. `A2-A4`).

## How to run

Requires Java 21 or newer.

1. Download `chess.jar` from the [latest release](https://github.com/stekarls/Chess/releases/latest).
2. Open a terminal in the folder where you saved it and run:

```
java -jar chess.jar
```

## Features

-  Full 8x8 board setup and ASCII rendering in the console
-  Movement rules for all six piece types (Pawn, Rook, Knight, Bishop, Queen, King)
-  Turn-based play with move validation (correct player, correct format, legal target square)
-  Capturing, including special-cased pawn capture rules (no forward capture, diagonal-only)
-  Check detection — a move that would leave your own king in check is rejected
-  Basic checkmate detection (ends the game when the king in check has no legal square to move to)
-  Castling and enPassant rules
-  Basic enemy cpu to play against

## Known limitations / in progress

This is an active work-in-progress project, not a finished game. Currently missing or incomplete:

- Pawn always promotes to queen, need to have options to choose 
- No threefold repetition detection
- FEN position import and export
- Need more detailed feedback on errors or bad input/moves

See [`src/main/java/todo.txt`](src/main/java/todo.txt) for the running list of planned fixes and features.

**Planned next steps:** a Spring Boot backend exposing the game engine over an API, with a React frontend for a graphical board — turning this from a CLI tool into a full web app.

## Project structure

```
├── pom.xml                      # Maven build (Java 21, JUnit, runnable jar config)
├── .github/workflows/
│   └── chess-tests.yml          # CI: runs the test suite
└── src/
    ├── main/java/
    │   ├── chessBot/
    │   │   ├── ChessBot.java    # CPU opponent: scores candidate moves and picks the best
    │   │   └── MoveInfo.java    # Record describing a candidate move and its evaluation
    │   ├── enums/
    │   │   ├── Color.java       # Enum for White / Black
    │   │   └── SquareColor.java # Enum for Dark / Light squares
    │   ├── pieces/
    │   │   ├── Piece.java       # Abstract base class shared by all pieces
    │   │   ├── King.java
    │   │   ├── Queen.java
    │   │   ├── Rook.java
    │   │   ├── Bishop.java
    │   │   ├── Knight.java
    │   │   └── Pawn.java
    │   ├── utils/
    │   │   ├── Main.java        # Entry point
    │   │   ├── Game.java        # Game loop: mode selection, input parsing and turns
    │   │   ├── ChessBoard.java  # Board state, move execution, capture logic, check detection
    │   │   ├── MoveRecord.java  # Record of a played move (used for undo / en passant)
    │   │   └── Position.java    # Board coordinate representation
    │   └── todo.txt             # Running list of planned fixes and features
    └── test/java/
        ├── ChessBotTests.java
        ├── GameLogicTests.java
        ├── GameRulesTest.java
        └── PieceMovementTests.java
```

## License

This project is for personal/portfolio use.
