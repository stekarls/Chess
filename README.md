# Command-Line Chess

A chess engine and playable command-line game written in plain Java (no external chess libraries), modeling the full board, piece movement rules, and check detection using object-oriented design.

```
    A  B  C  D  E  F  G  H

8   r  n  b  q  k  b  n  r
7   p  p  p  p  p  p  p  p
6   .  .  .  .  .  .  .  .
5   .  .  .  .  .  .  .  .
4   .  .  .  .  .  .  .  .
3   .  .  .  .  .  .  .  .
2   P  P  P  P  P  P  P  P
1   R  N  B  Q  K  B  N  R
```

## About

This project was built to practice object-oriented design in Java by modeling a non-trivial rule system from scratch — move legality, turn order, and check detection — without relying on a chess library. Moves are entered in the console using standard algebraic-style coordinates (e.g. `A2-A4`).

## Features

-  Full 8x8 board setup and ASCII rendering in the console
-  Movement rules for all six piece types (Pawn, Rook, Knight, Bishop, Queen, King)
-  Turn-based play with move validation (correct player, correct format, legal target square)
-  Capturing, including special-cased pawn capture rules (no forward capture, diagonal-only)
-  Check detection — a move that would leave your own king in check is rejected
-  Basic checkmate detection (ends the game when the king in check has no legal square to move to)

## Known limitations / in progress

This is an active work-in-progress portfolio project, not a finished game. Currently missing or incomplete:

- **En passant** and **castling** are not yet implemented
- Checkmate detection only checks whether the king itself can move to safety — it does **not** yet account for another piece blocking the check or capturing the attacker, so it can report checkmate too early in some positions
- No pawn promotion
- No draw / stalemate / threefold repetition detection
- Limited input validation and error handling around edge cases

See [`src/todo.txt`](src/todo.txt) for the running list of planned fixes and features.

**Planned next steps:** a Spring Boot backend exposing the game engine over an API, with a React frontend for a graphical board — turning this from a CLI tool into a full web app.

## Project structure

```
src/
├── pieces/
│   ├── Piece.java      # Abstract base class shared by all pieces
│   ├── King.java
│   ├── Queen.java
│   ├── Rook.java
│   ├── Bishop.java
│   ├── Knight.java
│   └── Pawn.java
└── utils/
    ├── Game.java        # Entry point / game loop, handles input parsing and turns
    ├── ChessBoard.java  # Board state, move execution, capture logic, check detection
    ├── Position.java    # Board coordinate representation
    └── Color.java       # Enum for White / Black
```

## How to run

1. Clone the repo
2. Compile the source:
   ```bash
   javac -d out src/pieces/*.java src/utils/*.java
   ```
3. Run the game:
   ```bash
   java -cp out utils.Game
   ```
4. Enter moves in the format `FROM-TO`, e.g.:
   ```
   A2-A4
   ```
   Type `exit` to quit.

## License

This project is for personal/portfolio use.
