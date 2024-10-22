import java.util.ArrayList;
import java.util.List;

public abstract class ChessPiece {
    String color;
    boolean check = true;
    public String symbol;

    public ChessPiece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract List<ChessPiece> findPathPieces(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    public abstract String getSymbol();

    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

}