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
    public boolean isPathClear(ChessBoard chessBoard, int startX, int startY, int endX, int endY) {
        int dx = Integer.compare(endX, startX); // направление по X (-1, 0, 1)
        int dy = Integer.compare(endY, startY); // направление по Y (-1, 0, 1)

        /// Начинаем с клетки после начальной позиции
        int x = startX + dx;
        int y = startY + dy;

        /// Проходим по пути, пока не дойдем до конечной позиции
        while (x != endX || y != endY) {
            if (x < 0 || x > 7 || y < 0 || y > 7) { // Добавляем проверку выхода за пределы доски
                return false;
            }

            if (chessBoard.board[x][y] != null) {
                return false; // Если по пути есть фигура, возвращаем false
            }
            x += dx;
            y += dy;
        }

        return true;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        /// проверяем, что фигура не стоит на месте
        if (line == toLine && column == toColumn) {
            return false;
        }

        /// проверяем выход за пределы доски
        if ((line < 0 || line > 7) && (column < 0 || column > 7) ||
                (toLine < 0 || toLine > 7) && (toColumn < 0 || toColumn > 7)) {
            return false;
        }

        /// проверяем возможность хода по горизонтали или вертикали
        if (!(line == toLine ^ column == toColumn)) {
            return false;
        }

        if (chessBoard.board[toLine][toColumn] != null && chessBoard.board[toLine][toColumn].getColor().equals(this.color)) {
            return false;  // Нельзя бить свою же фигуру
        }

        return isPathClear(chessBoard, line, column, toLine, toColumn);
    }
}
