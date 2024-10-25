
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

    public abstract boolean isPathClear(ChessBoard chessBoard,int startX, int startY, int endX, int endY);

    public abstract String getSymbol();

    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);
}