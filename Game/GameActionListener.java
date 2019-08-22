package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int rowNum;
    private int cellNum;
    private GameButton button;

    public GameActionListener(int row, int cell, GameButton button) {
        rowNum = row; // y == i
        cellNum = cell;  //  x == j
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();

        if (board.isTurnable(rowNum, cellNum)) { //проверяем, можно ли ходить
            //ход человека
            updateByPlayerData(board);
            if (board.checkWin()) {
                button.getBoard().getGame().showMessage("Вы выиграли!");
                board.emptyField();
            } else {
                if (board.isFull()) {
                    board.getGame().showMessage("Ничья!");
                    board.emptyField();
                } else {
                    board.getGame().passTurn();
                    updateByAiData(board);//ход компьютера
                }
            }
        } else   board.getGame().showMessage("Некорректный ход!");

    }
//метод - ход человека
    void updateByPlayerData(GameBoard board) {
        //обновить матрицу игры
        board.updateGameField(rowNum, cellNum);
        //обновить содержимое кнопки
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
    }


    void updateByAiData(GameBoard board) {
        int x, y;
        //Закомментирован глупый компьютер
//        Random rnd = new Random();
//        do{
//            x = rnd.nextInt(GameBoard.dimension);
//            y = rnd.nextInt(GameBoard.dimension);
//        }while (!board.isTurnable(x, y));

        //обновить содержимое кнопки
        int cellIndex = board.getIndexForAi(); //метод getIndexForAi() вносит изменения в матрицу и
        //выдает индекс кнопки в которую проставляем символ О
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
        //проверяем победу
        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Компьютер выиграл!");
            board.emptyField(); // очистка матрицы и кнопок
            board.getGame().passTurn(); //передача хода (замена символа)
        } else {
            if (board.isFull()) {
                board.getGame().showMessage("Ничья!");
                board.emptyField();
            } else board.getGame().passTurn();  //передаем ход
        }
    }
}