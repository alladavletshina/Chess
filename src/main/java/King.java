import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    private final String color;

    public King(String color) {
        super(color);
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return "K";
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

        // Проверка того, что фигура не стоит на месте
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Проверка выхода за пределы доски
        if ((line < 0 || line > 7) || (toLine < 0 || toLine > 7) || (column < 0 || column > 7) || (toColumn < 0 || toColumn > 7)) {
            return false;
        }

        boolean foundOtherFigure = false;

        /// Проверка, что на пути нет других фигур
        for (int i = line; i <= toLine; i++) {
            for (int j = column; j <= toColumn; j++) {

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

        // Проверка движения короля на одну клетку в любом направлении
        if (Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1) {
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

    public boolean isUnderAttack(ChessBoard chessBoard, int line, int column) {

        // Проверка клеток вокруг короля
        /*for (int i = line - 1; i <= line + 1; ++i) {
            for (int j = column - 1; j <= column + 1; ++j) {
                if (i >= 0 && i < 8 && j >= 0 && j < 8) {
                    if (chessBoard.board[i][j] != null && chessBoard.board[i][j].getColor() != this.color) {
                        if (canMoveToPosition(chessBoard, i, j, line, column)) {
                            return true;
                        }
                    }
                }
            }
        }*/

        /// Проверка всех клеток на доске
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (chessBoard.board[i][j] != null && chessBoard.board[i][j].getColor() != this.color) {
                    if (canMoveToPosition(chessBoard, i, j, line, column)) {
                        return false;
                    } else return false;
                } else return false;
            }
        }

        return false;
    }
}