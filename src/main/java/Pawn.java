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
    public boolean isPathClear(ChessBoard chessBoard,int startX, int startY, int endX, int endY) {
        int dx = Integer.compare(endX, startX); // направление по X (-1, 0, 1)
        int dy = Integer.compare(endY, startY); // направление по Y (-1, 0, 1)

        // Начинаем с клетки после начальной позиции
        int x = startX + dx;
        int y = startY + dy;

        // Проходим по пути, пока не дойдем до конечной позиции
        while (x != endX || y != endY) {

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
            if (chessBoard.board[toLine][toColumn] != null && chessBoard.board[toLine][toColumn].getColor() == this.color) {
                return false;
            }
        }

        if (!isPathClear(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        return true;
    }
}
