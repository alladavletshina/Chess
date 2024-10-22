import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return "P";
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

        /// проверяю,что фигура не стоит на месте
        if (line == toLine && column == toColumn) {
            return false;
        }

        /// проверяю,что фигура не выходит за пределы доски
        if ((line < 0 || line > 7) || (toLine < 0 || toLine > 7) || (column < 0 || column > 7) || (toColumn < 0 || toColumn > 7)) {
            return false;
        }

        /// Проверка, что пешка может ходить только на 1 клетку вперед, а при первом ходе на 2 клетки вперед.
        if ((Math.abs(line - toLine) == 1 && column == toColumn) || (line == 1 && toLine == 3 && column == toColumn) || (line == 6 && toLine == 4 && column == toColumn)) {
            if (chessBoard.board[toLine][toColumn] == null) {
                return true;
            } else {
                return false;
            }
        }

        /// Проверка, что пешка может есть только по диагонали на 1 клетку и не фигуры своего цвета.
        if (Math.abs(line - toLine) == 1 && Math.abs(column - toColumn) == 1 && chessBoard.board[toLine][toColumn].getColor() != this.color){
            return true;
        }

        return false;
    }
}
