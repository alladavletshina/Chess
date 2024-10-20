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
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        // Проверка того, что фигура не стоит на месте
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Проверка выхода за пределы доски
        if ((line < 0 || line > 7) || (toLine < 0 || toLine > 7) || (column < 0 || column > 7) || (toColumn < 0 || toColumn > 7)) {
            return false;
        }

        // Проверка движения короля на одну клетку в любом направлении
        if (Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1) {
            if (chessBoard.board[toLine][toColumn] == null) {
                return true;
            } else if (chessBoard.board[toLine][toColumn].getColor() != this.color) {
                return true;
            } else {
                return false;
            }
        }

        return false;
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