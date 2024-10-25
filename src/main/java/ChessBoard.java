public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }


    /// Метод для превращения пешки в ферзя
    public void promoteToQueen(int line, int column) {
        if (board[line][column] instanceof Pawn) {
            String pawnColor = board[line][column].getColor(); // Получаем цвет пешки
            if ((pawnColor.equals("White") && line == 7) || (pawnColor.equals("Black") && line == 0)) {
                board[line][column] = new Queen(pawnColor); // Замена пешки на ферзя
            }
        }
    }

    /// Найти короля определенного цвета
    public int[] findKing(String color) {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return new int[] {i, j}; // Возвращаем координаты короля
                }
            }
        }
        return null;
    }

    ///Теперь нужно реализовать метод для проверки, находится ли король под атакой.
    /// Это делается путем проверки всех возможных ходов противника, чтобы узнать, может ли одна из его фигур атаковать клетку, где находится король.

    public boolean isKingInCheck(String color) {
        int[] kingPos = findKing(color);

        if (kingPos == null) {
            return false; // Если король не найден, значит, он не под шахом
        }

        int kingX = kingPos[0]; // x-координата короля
        int kingY = kingPos[1]; // y-координата короля

        String opponentColor = color.equals("White") ? "Black" : "White";

        // Проверяем все клетки на доске
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];

                // Если на клетке есть фигура противника
                if (piece != null && piece.getColor().equals(opponentColor)) {
                    try {
                        // Проверяем, может ли она атаковать короля
                        if (piece.canMoveToPosition(this, i, j, kingX, kingY)) {
                            return true; // Шах
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Неверные координаты: (" + i + ", " + j + ")");
                        throw new RuntimeException(e); // Перехватываем исключение и бросаем новое
                    }
                }
            }
        }
        return false; // Шаха нет
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn)) {

            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return false;

            if (board[startLine][startColumn] instanceof King) {
                if (new King("White").isUnderAttack(this, endLine, endColumn) || new King("Black").isUnderAttack(this, endLine, endColumn)) {
                    System.out.println("Нельзя ходить под шах!");
                    return false;
                }

                /// Обновляем переменную check у короля
                board[startLine][startColumn].check = false;
            }

            /// Обновляем переменную check у ладьи
            if (board[startLine][startColumn] instanceof Rook) {
                board[startLine][startColumn].check = false;}

            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                board[endLine][endColumn] = board[startLine][startColumn];
                promoteToQueen(endLine, endColumn);
                board[startLine][startColumn] = null;
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";

            // Проверяем, находится ли король под шахом после хода
            /*String opponentColor = nowPlayer.equals("White") ? "Black" : "White"; // Определяем цвет противника
            int[] kingPos = findKing(nowPlayer); // Находим короля текущего игрока
            if (kingPos != null) {
                int kingX = kingPos[0], kingY = kingPos[1];
                if (isKingInCheck(nowPlayer)) {
                    // Король под шахом, устанавливаем флаг
                    ((King) board[kingX][kingY]).setCheck(true);
                    System.out.println("Шах!");
                } else {
                    // Король вне опасности, сбрасываем флаг
                    ((King) board[kingX][kingY]).setCheck(false);
                }*/


                return true;
            } else return false;
        } else return false;
    }

    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0(){
        if (nowPlayer.equals("White")) {
            if (board[0][0] == null || board[0][4] == null) return false;
            if (board[0][0].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && board[0][1] == null && board[0][2] == null && board[0][3] == null) {
                if (board[0][0].getColor().equals("White") && board[0][4].getColor().equals("White") && board[0][0].check && board[0][4].check && !new King("White").isUnderAttack(this,0,2)) {
                    board[0][4] = null;
                    board[0][2] = new King("White");
                    board[0][2].check = false;
                    board[0][0] = null;
                    board[0][3] = new Rook("White");
                    board[0][3].check = false;
                    nowPlayer = "Black";
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][0] == null || board[7][4] == null) return false;
            if (board[7][0].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && board[7][1] == null && board[7][2] == null && board[7][3] == null) {
                if (board[7][0].getColor().equals("Black") && board[7][4].getColor().equals("Black") && board[7][0].check && board[7][4].check && !new King("Black").isUnderAttack(this,7,2)) {
                    board[7][4] = null;
                    board[7][2] = new King("Black");
                    board[7][2].check = false;
                    board[7][0] = null;
                    board[7][3] = new Rook("Black");
                    board[7][3].check = false;
                    nowPlayer = "White";
                    return true;
                } else return false;
            } else return false;
        }
    }

    public boolean castling7(){
        if (nowPlayer.equals("White")) {
            if (board[0][7] == null || board[0][4] == null) return false;
            if (board[0][7].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") && board[0][6] == null && board[0][5] == null) {
                if (board[0][7].getColor().equals("White") && board[0][4].getColor().equals("White") && board[0][7].check && board[0][4].check && !new King("White").isUnderAttack(this,0,6)) {
                    board[0][4] = null;
                    board[0][6] = new King("White");
                    board[0][6].check = false;
                    board[0][7] = null;
                    board[0][5] = new Rook("White");
                    board[0][5].check = false;
                    nowPlayer = "Black";
                    return true;
                } else return false;
            } else return false;
        } else {
            if (board[7][7] == null || board[7][4] == null) return false;
            if (board[7][7].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") && board[7][6] == null && board[7][5] == null) {
                if (board[7][7].getColor().equals("Black") && board[7][4].getColor().equals("Black") && board[7][7].check && board[7][4].check && !new King("Black").isUnderAttack(this,7,6)) {
                    board[7][4] = null;
                    board[7][6] = new King("Black");
                    board[7][6].check = false;
                    board[7][7] = null;
                    board[7][5] = new Rook("Black");
                    board[7][5].check = false;
                    nowPlayer = "White";
                    return true;
                } else return false;
            } else return false;
        }
    }
}
