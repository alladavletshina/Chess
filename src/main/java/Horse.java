import java.util.ArrayList;
import java.util.List;

public class Horse extends ChessPiece {
    public Horse(String color) {
        super(color);
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return "H";
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
        if (line == toLine && column == toColumn) {
            return false;
        }

        /// проверяю,что фигура не выходит за пределы доски
        if((line < 0 || line > 7)||(toLine < 0 || toLine > 7)||(column < 0 || column > 7)||(toColumn < 0 || toColumn > 7)){
            return false;
        }

        /// Логика хода конем по схеме "Г"
        if (Math.abs(line - toLine) == 2 && Math.abs(column - toColumn) == 1 || Math.abs(line - toLine) == 1 && Math.abs(column - toColumn) == 2) {
            if (chessBoard.board[toLine][toColumn] == null) {
                return true;
            } else if (chessBoard.board[toLine][toColumn].getColor() != this.color) {
                return true;
            } else {return false;}
        }

        return false;
    }
}
