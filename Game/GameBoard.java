package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {    // игровое поле
    static int dimension = 3;                             // размерность
    static  int cellSize = 150;                             // размер одной клетки
    static char nullSimbol = '\u0000';
    private char[][] gameField;                         // матрица игры
    private GameButton[] gameButtons;                                  // массив кнопок
    private Game game;                                             //ссылка на игру

    public GameBoard(Game game){
        this.game = game;
        initField();
    }

    void initField(){
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(cellSize*dimension, cellSize*dimension, 400, 300);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(); //панель управления игрой
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
                System.out.println(game.getCurrentPlayer().getPlayerSign());

            }
        });
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize*dimension, 150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize*dimension, cellSize*dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension*dimension];

        for (int i = 0; i < (dimension*dimension); i++){
            GameButton button = new GameButton(i, this);
            gameFieldPanel.add(button);
            gameButtons[i] = button;
        }
        getContentPane().add(controlPanel, "North");
        getContentPane().add(gameFieldPanel, "Center");
        setVisible(true);
    }
//очищает кнопки и игровое поле
    void emptyField() {
        for (int i = 0; i < (dimension * dimension); i++) {
            gameButtons[i].setText("");
            int x = i / dimension;
            int y = i % dimension;
            gameField[x][y] = nullSimbol;
        }
    }

    Game getGame(){
            return game;
    }// метод возвращает объект - игру

    public GameButton getButton(int index){ //возвращает кнопку из массива кнопок по указанному индексу
        return gameButtons[index];
    }
//возможность совершить ход
    boolean isTurnable(int x, int y){ // x==row==j, y==cell==i, index = dim*y + x
        return  (gameField[y][x] == nullSimbol)? true : false;
    }
    //метод возвращает индекс- index кнопки для хода компьютера,
    // координаты кнопки row -по горизонтали и cell - по вертикали
    public int getIndexForAi(){
        int index = -1;
        int row = -1; int cell = -1;
        for (int y = 0; y < dimension; y++) {
                for (int x = 0; x < dimension; x++) {
                    if (gameField[y][x] == nullSimbol) { //проверка матрицы на пустую клетку
//========================================================================================
//Так как игру всегда начинает человек gamePlayers[0], то в матрице уже есть символ X
                        //проверяем направление "вверх-влево"
                        if (y - 1 >= 0 && x - 1 >= 0){
                            if(gameField[y - 1][x - 1] == 'X' || gameField[y - 1][x - 1] == 'O') {
                                row = x;
                                cell = y; //для кнопки строка row - это столбец (х) в матрице

                                if (y + 1 < GameBoard.dimension && x + 1 < GameBoard.dimension) {//здесь же блокировка для Х
                                    if ((gameField[y - 1][x - 1] == 'O' && gameField[y + 1][x + 1] == 'O') ||
                                            (gameField[y - 1][x - 1] == 'X' && gameField[y + 1][x + 1] == 'X')){
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                                if (y - 2 >= 0 && x - 2 >= 0) {
                                    if((gameField[y - 1][x - 1] == 'O' && gameField[y - 2][x - 2] == 'O') ||
                                            ((gameField[y - 1][x - 1] == 'X' && gameField[y - 2][x - 2] == 'X'))) {
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                            }
                        }
                        //проверяем направление "вверх".Обязательно наличие: (y - 1 >= 0)
                        // или далее: (y - 1 >= 0 && y + 1< GameBoard.dimension), иначе выдает ошибку
                        if (((y - 1 >= 0) && gameField[y - 1][x] == 'X') ||
                                ((y - 1 >= 0) && gameField[y - 1][x] == 'O')) {
                            row = x; cell = y;
                            if ((y - 1 >= 0 && y + 1< GameBoard.dimension) && (gameField[y - 1][x] == 'O' && gameField[y + 1][x] == 'O') ||
                                    ((y - 1 >= 0 && y + 1< GameBoard.dimension) && (gameField[y - 1][x] == 'X' && gameField[y + 1][x] == 'X'))) {
                                row = x; cell = y;
                                y = dimension;
                                break;
                            }
                            if ((y - 2 >= 0) && (gameField[y - 1][x] == 'O' && gameField[y - 2][x] == 'O') ||
                                    ((y - 2 >= 0) && gameField[y - 1][x] == 'X' && gameField[y - 2][x] == 'X')) {
                                row = x; cell = y;
                                y = dimension;
                                break;
                            }
                        }
                        //направление "вверх-вправо"
                        if (y - 1 >= 0 && x + 1 < GameBoard.dimension){
                            if(gameField[y - 1][x + 1] == 'X' || gameField[y - 1][x + 1] == 'O') {
                                row = x;  cell = y;
                                if (y + 1 < GameBoard.dimension && x - 1 >= 0){
                                    if ((gameField[y - 1][x + 1] == 'O' && gameField[y + 1][x - 1] == 'O') ||
                                            (gameField[y - 1][x + 1] == 'X' && gameField[y + 1][x - 1] == 'X')){
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                                if (y - 2 >= 0 && x + 2 < GameBoard.dimension){
                                    if((gameField[y - 1][x + 1] == 'O' && gameField[y - 2][x + 2] == 'O') ||
                                            (gameField[y - 1][x + 1] == 'X' && gameField[y - 2][x + 2] == 'X')) {
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                            }
                        }
                        //направление "вправо"
                        if (x + 1 < GameBoard.dimension) {
                            if (gameField[y][x + 1] == 'X' || gameField[y][x + 1] == 'O'){
                                row = x; cell = y;
                                if (x + 2 < GameBoard.dimension){
                                    if ((gameField[y][x + 1] == 'O' && gameField[y][x + 2] == 'O') ||
                                            (gameField[y][x + 1] == 'X' && gameField[y][x + 2] == 'X')) {
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                                if (x-1 >= 0) {
                                    if ((gameField[y][x - 1] == 'O' && gameField[y][x + 1] == 'O') ||
                                            (gameField[y][x - 1] == 'X' && gameField[y][x + 1] == 'X')) {
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                            }
                        }
                        //направление "вправо -вниз"
                        if (y + 1 < GameBoard.dimension && x + 1 < GameBoard.dimension) {
                            if (gameField[y + 1][x + 1] == 'X' || gameField[y + 1][x + 1] == 'O'){
                                row = x; cell = y;
                                if (y-1>=0 && x-1>=0) {
                                    if ((gameField[y + 1][x + 1] == 'O' && gameField[y - 1][x - 1] == 'O') ||
                                            (gameField[y + 1][x + 1] == 'X' && gameField[y - 1][x - 1] == 'X')) {
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }

                                if (y + 2 < GameBoard.dimension && x + 2< GameBoard.dimension) {
                                    if ((gameField[y + 1][x + 1] == 'O' && gameField[y + 2][x + 2] == 'O') ||
                                            (gameField[y + 1][x + 1] == 'X' && gameField[y + 2][x + 2] == 'X')) {
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                            }
                        }
                        //направление "вниз"
                        if (y + 1 < GameBoard.dimension) {
                            if (gameField[y + 1][x] == 'X' || gameField[y + 1][x] == 'O'){
                                row = x; cell = y;
                                if (y + 2 < GameBoard.dimension){
                                    if ((gameField[y + 1][x] == 'X' &&  gameField[y + 2][x] == 'X') ||
                                            (gameField[y + 1][x] == 'O' &&  gameField[y + 2][x] == 'O')){
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                                if (y - 1 >=0){
                                    if ((gameField[y - 1][x] == 'X' && gameField[y + 1][x] == 'X') ||
                                            (gameField[y - 1][x] == 'O' && gameField[y + 1][x] == 'O')){
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }

                            }
                        }
                        //направление "вниз - влево"
                        if (y + 1 < GameBoard.dimension && x - 1 >= 0) {
                            if (gameField[y + 1][x - 1] == 'X' || gameField[y + 1][x - 1] == 'O'){
                                row = x; cell = y;
                                if (y + 2 < GameBoard.dimension && (x - 2) >= 0){
                                    if ((gameField[y + 1][x - 1] == 'X' && gameField[y + 2][x - 2] == 'X') ||
                                            (gameField[y + 1][x - 1] == 'O' && gameField[y + 2][x - 2] == 'O')){
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                                if (x + 1 < GameBoard.dimension && y - 1 >=0 ){
                                    if ((gameField[y + 1][x - 1] == 'X' && gameField[y + 1][x - 1] == 'X') ||
                                            ((gameField[y + 1][x - 1] == 'O' && gameField[y + 1][x - 1] == 'O'))){
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }

                            }
                        }
                        //направление "влево"
                        if (x - 1 >= 0) {
                            if (gameField[y][x - 1] == 'X' || gameField[y][x - 1] == 'O') {
                                row = x; cell = y;
                                if (x - 2 >= 0){
                                    if ((gameField[y][x - 1] == 'X' && gameField[y][x - 2] == 'X') ||
                                            ((gameField[y][x - 1] == 'O' && gameField[y][x - 2] == 'O'))){
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }
                                if (x + 1 < dimension){
                                    if ((gameField[y][x - 1] == 'X' && gameField[y][x + 1] == 'X') ||
                                            (gameField[y][x - 1] == 'O' && gameField[y][x + 1] == 'O')){
                                        row = x; cell = y;
                                        y = dimension;
                                        break;
                                    }
                                }

                            }
                        }
                    }
                }
        }
        index = dimension * row + cell;
        gameField[cell][row] = 'O';//сразу в нужную клетку матрицы присваиваем значение
        return index;
    }
    /**
     * Обновление матрицы игры после хода
     * @param x - по горизонтали
     * @param y -   по вертикали
     */
    void updateGameField(int x, int y){
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }
    /***
     * Проверка победы
     * @return флаг победы
     */
    boolean checkWin(){
        boolean result = false;
        char playerSimbol = getGame().getCurrentPlayer().getPlayerSign();
        if (checkWinDiogonal(playerSimbol) || checkWinLine(playerSimbol)){
            result = true;
        }
        return result;
    }

    boolean checkWinLine(char playerSimbol) {
        boolean rows, cols, result;
        result = false;
        for (int i = 0; i < dimension; i++) {
            cols = true;
            rows = true;
            for (int j = 0; j < dimension; j++) {
                cols = cols & (gameField[j][i] == playerSimbol);
                rows &= (gameField[i][j] == playerSimbol);
            }
            if (cols || rows) {
                result = true;
                break;
            }
        }
        return result;
    }

    boolean checkWinDiogonal(char playerSimbol){
        boolean result = false;
        boolean diagonal = true;
        boolean diagonal2 = true;

        for (int i = 0; i < dimension; i++) {
                diagonal = diagonal & (gameField[i][i] == playerSimbol);
                diagonal2 &= (gameField[i][(dimension-i)-1] == playerSimbol);
            }

        if (diagonal || diagonal2) {
            result = true;
        }

         return  result;
    }

    boolean isFull(){ // заполнено ли игровое поле
        boolean result = true;
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                if (gameField[i][j] == nullSimbol)
                    result = false;
            }
        }
        return result;
    }
}
