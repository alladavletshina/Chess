import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return super.getColor();
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public List<ChessPiece> findPathPieces(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        return List.of();
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

        boolean foundOtherFigure = false;

        /// Проверка, что на пути нет других фигур
        for (int i = line; i < toLine; i++) {
            for (int j = column; j < toColumn; j++) {

                /// Проверка на пересечение с другим объектом
                if (chessBoard.board[i][j] != null) {
                    foundOtherFigure = true;
                    break;
                }
            }

            if (foundOtherFigure) {
                break;
            }
        }

        /// проверяю, что ладья ходит по прямой линии
        if (Math.abs(line - toLine) + Math.abs(column - toColumn) == 1) {
            if (foundOtherFigure) {
                return false;
            }

            /// проверяю, что фигура на целевой позиции имеет другой цвет
            if (chessBoard.board[toLine][toColumn] == null || chessBoard.board[toLine][toColumn].getColor() != this.color) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
