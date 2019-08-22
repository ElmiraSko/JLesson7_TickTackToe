package Game;

import javax.swing.*;

public class GameButton extends JButton {
    private int buttonIndex; // имеет свой индекс
    private GameBoard board; //имеет ссылку на игровое поле
//каждая кнопка при создании получает свой индекс и взависимости от него получает координаты
    public GameButton(int currentButtonIndex, GameBoard currentGameBoard){ // конструктор, получает индекс и игровое поле
        buttonIndex = currentButtonIndex;         // индекс кнопки: 0, 1, 2, 3, 4, 5, 6, 7, 8
        board = currentGameBoard;            //  ссылке board класса GameButton присваиваем ссылку на текущий объект gameBoard
// координата по х: индекс кнопки делим на размерность и получаем ее целую часть
        int rowNum = buttonIndex/board.dimension;
        int cellNum = buttonIndex%board.dimension; // координата по у

        setSize(GameBoard.cellSize-5, GameBoard.cellSize-5);  //задаем размер кнопки
        addActionListener(new GameActionListener(rowNum, cellNum, this));
        //при создании кнопки, вешаем нового слушателя на каждую новую кнопку. Слушатель "знает кнопку"
    }

    public  GameBoard getBoard(){ //кнопка может вернуть ссылку на игровое поле
        // ссылка свяжет слушатель с необходимыми методами игры
        return board;
    }
    public int getButtonIndex(){
        return buttonIndex;
    }

}
