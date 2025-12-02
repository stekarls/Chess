import pieces.Piece;
import utils.Position;

public class Move{
  Position position;
  Piece piece;

  public Move(Piece piece, Position position){
    this.piece = piece;
    this.position = position;
  }

}
