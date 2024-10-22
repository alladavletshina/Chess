import java.util.List;
import java.util.ArrayList;

public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return super.getColor();
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    @Override
    public List<ChessPiece> findPathPieces(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        List<ChessPiece> pathPieces = new ArrayList<>();

        for (int i = line; i <= toLine; i++) {
            for (int j = column; j <= toColumn; j++) {

                if (chessBoard.board[i][j] != null) {
                    pathPieces.add(chessBoard.board[i][j]);
                }
            }
        }

        return pathPieces;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (line == toLine && column == toColumn) {
            return false;
        }

        if ((line < 0 || line > 7) || (toLine < 0 || toLine > 7) || (column < 0 || column > 7) || (toColumn < 0 || toColumn > 7)) {
            return false;
        }

        if (Math.abs(line - toLine) != Math.abs(column - toColumn)) {
            return false;
        }

        if (chessBoard.board[toLine][toColumn] != null && chessBoard.board[toLine][toColumn].getColor() == this.color) {
            return false;
        }

        List<ChessPiece> pathPieces = findPathPieces(chessBoard, line, column, toLine, toColumn);
        return pathPieces.isEmpty();
    }
}