public class Rook extends ChessPiece{
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
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {

        /// проверяю,что фигура не стоит на месте
        if (line == toLine && column == toColumn) {
            return false;
        }

        /// проверяю,что фигура не выходит за пределы доски
        if((line < 0 || line > 7)||(toLine < 0 || toLine > 7)||(column < 0 || column > 7)||(toColumn < 0 || toColumn > 7)){
            return false;
        }

        /// проверяю, что ладья ходит по прямой линии
        /// проверяю, что фигура на целевой позиции имеет другой цвет
        if ((line != toLine && column == toColumn ) || (line == toLine && column != toColumn)) {
            if (chessBoard.board[toLine][toColumn] == null) {
                return true;
            } else if (chessBoard.board[toLine][toColumn].getColor() != this.color) {
                return true;
            } else {return false;}
        }

        return false;
    }
}
