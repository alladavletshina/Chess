public class ChessBoard {

    public boolean pawnPromotion;

    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public String getOpponentColor() {
        return this.nowPlayer.equals("White") ? "Black" : "White";
    }

    /// Метод для превращения пешки в ферзя
    public void promoteToQueen(int line, int column) {
        if (board[line][column] instanceof Pawn) {
            String pawnColor = board[line][column].getColor(); // Получаем цвет пешки
            if ((pawnColor.equals("White") && line == 7) || (pawnColor.equals("Black") && line == 0)) {
                board[line][column] = new Queen(pawnColor); // Замена пешки на ферзя
                pawnPromotion = true;
            } else pawnPromotion = false;
        }
    }

    /// Найти короля определенного цвета
    public int[] findKing(String playerColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.getColor().equals(playerColor)) {
                    return new int[] {i, j}; // Возвращаем координаты короля
                }
            }
        }
        return null; // Король не найден
    }

    /// Проверка, находится ли король под шахом
    public boolean isKingInCheck(String playerColor) {
        int[] kingPos = findKing(playerColor);

        try {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessPiece piece = board[i][j];
                    if (piece != null && !piece.getColor().equals(playerColor)) {
                        try {
                            if (piece.canMoveToPosition(this, i, j, kingPos[0], kingPos[1])) {
                                return true;
                            }
                        } catch (Exception e) {
                            // Обработка исключения, связанного с методом canMoveToPosition
                            System.err.printf("Ошибка при проверке угрозы королю: %s на позиции (%d, %d)%n", piece, i, j);
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            // Обработка возможного исключения, если piece окажется null
            System.err.println("Ошибка: NullPointerException при проверке угрозы королю.");
            e.printStackTrace();
        } catch (Exception e) {
            // Обработка других возможных исключений
            System.err.println("Произошла непредвиденная ошибка при проверке угрозы королю.");
            e.printStackTrace();
        }

        return false;
    }


    public boolean isCheckmate(String playerColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor().equals(playerColor)) {
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            if (piece.canMoveToPosition(this, i, j, x, y)) {
                                ChessPiece temp = board[x][y];
                                board[x][y] = piece;
                                board[i][j] = null;
                                boolean isInCheck = isKingInCheck(playerColor);
                                board[i][j] = piece;
                                board[x][y] = temp;
                                if (!isInCheck) return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn) && checkPos(endLine) && checkPos(endColumn)) {

            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return false;

            if (board[startLine][startColumn] instanceof King) {
                /// Обновляем переменную check у короля
                board[startLine][startColumn].check = false;
            }

            /// Обновляем переменную check у ладьи
            if (board[startLine][startColumn] instanceof Rook) {
                board[startLine][startColumn].check = false;}

            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                ChessPiece temp = board[endLine][endColumn]; // Сохраняем фигуру, которая была на целевой клетке
                board[endLine][endColumn] = board[startLine][startColumn]; // Делаем ход
                board[startLine][startColumn] = null;

                // Проверяем, не попал ли наш король под шах после хода
                if (isKingInCheck(nowPlayer)) {
                    // Откатываем ход назад, если король оказался под шахом
                    board[startLine][startColumn] = board[endLine][endColumn];
                    board[endLine][endColumn] = temp;
                    System.out.println("Невозможно сделать такой ход, потому что ваш король попадёт под шах.\nИли уже находится под шахом!");
                    return false;
                }

                promoteToQueen(endLine, endColumn);

                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";

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
        return pos >= 0 && pos < 8;
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
