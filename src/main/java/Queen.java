import java.util.List;
import java.util.ArrayList;

public class Queen extends ChessPiece {
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
