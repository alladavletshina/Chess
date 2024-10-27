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
    public boolean isPathClear(ChessBoard chessBoard, int startX, int startY, int endX, int endY) {
        int dx = Integer.compare(endX, startX); // направление по X (-1, 0, 1)
        int dy = Integer.compare(endY, startY); // направление по Y (-1, 0, 1)

        // Начинаем с клетки после начальной позиции
        int x = startX + dx;
        int y = startY + dy;

        // Проходим по пути, пока не дойдем до конечной позиции
        while (x != endX || y != endY) {
            if (x < 0 || x > 7 || y < 0 || y > 7) {
                return false; // Если вышли за пределы доски, возвращаем false
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

        // Проверяем, что фигура не стоит на месте
        if (line == toLine && column == toColumn) {
            return false;
        }

        // Проверяем, что фигура не выходит за пределы доски
        if ((line < 0 || line > 7) || (toLine < 0 || toLine > 7) || (column < 0 || column > 7) || (toColumn < 0 || toColumn > 7)) {
            return false;
        }

        // Проверка направления движения пешки
        if (this.color.equals("White")) {
            if (toLine <= line) { // Белая пешка движется вниз, поэтому toLine должно быть больше line
                return false; // Белая пешка не может идти назад или оставаться на месте
            }
        } else {
            if (toLine >= line) { // Чёрная пешка движется вверх, поэтому toLine должно быть меньше line
                return false; // Чёрная пешка не может идти назад или оставаться на месте
            }
        }

        // Проверка, что пешка может ходить только на 1 клетку вперед, а при первом ходе на 2 клетки вперед.
        if (this.color.equals("White")) {
            if (line == 1 && toLine == 3 && column == toColumn && chessBoard.board[line + 1][column] == null) {
                return true;
            } else if (toLine - line == 1 && column == toColumn && chessBoard.board[toLine][toColumn] == null) {
                return true;
            }
        } else {
            if (line == 6 && toLine == 4 && column == toColumn && chessBoard.board[line - 1][column] == null) {
                return true;
            } else if (line - toLine == 1 && column == toColumn && chessBoard.board[toLine][toColumn] == null) {
                return true;
            }
        }

        // Проверка, что пешка может бить фигуру по диагонали
        if (Math.abs(column - toColumn) == 1 && Math.abs(line - toLine) == 1) {
            if (chessBoard.board[toLine][toColumn] != null && !chessBoard.board[toLine][toColumn].getColor().equals(this.color)) {
                return true;
            }
        }

        // Запрещаем ходьбу вбок
        if (column != toColumn && line == toLine) {
            return false;
        }

        if (!isPathClear(chessBoard, line, column, toLine, toColumn)) {
            return false;
        }

        return false;
    }
}