public class King extends ChessPiece {

    private final String color;
    private boolean inCheck;

    public King(String color) {
        super(color);
        this.color = color;
    }

    public void setCheck(boolean inCheck) {
        this.inCheck = inCheck;
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

        // Проверяем конечную позицию
        return chessBoard.board[endX][endY] == null;
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
        // Проверка всех клеток на доске
        /*for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {

                if (chessBoard.board[i][j] != null && !chessBoard.board[i][j].getColor().equals(this.color)) {
                    if (chessBoard.board[i][j].canMoveToPosition(chessBoard, i, j, line, column)) {
                        return true; // Позиция под атакой, прерываем поиск
                    }
                }
            }
        }*/
        return false; // Позиция не под атакой
    }
}