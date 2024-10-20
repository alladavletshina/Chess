public class Queen extends ChessPiece{
    public Queen(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return super.getColor();
    }

    @Override
    public String getSymbol() {
        return "Q";
    }


    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        /// проверяю,что фигура не стоит на месте

        ChessBoard chessBoard1 = chessBoard;

        if (line == toLine && column == toColumn) {
            return false;
        }

        /// проверяю,что фигура не выходит за пределы доски
        if((line < 0 || line > 7)||(toLine < 0 || toLine > 7)||(column < 0 || column > 7)||(toColumn < 0 || toColumn > 7)){
            return false;
        }

        /// ферзь ходит по диагонали и вертикали
        if ((line != toLine && column == toColumn)||(line == toLine && column != toColumn)||(Math.abs(line - toLine) == Math.abs(column - toColumn))) {
            if (chessBoard.board[toLine][toColumn] == null) {
                return true;
            } else if (chessBoard.board[toLine][toColumn].getColor() != this.color) {
                return true;
            } else {return false;}
        }

        return false;
    }
}
