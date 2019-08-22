package Game;

import javax.swing.*;

public class Game {
    private GameBoard board;                        // ссылка на игровое поле
    private GamePlayers[] gamePlayers = new GamePlayers[2]; // массив игроков
    private int playersTurn = 0;            // переменная определяет смену игрока, (смена проставляемого символа)

    public Game(){
        this.board = new GameBoard(this);
    }

   public void initGame(){
        gamePlayers[0] = new GamePlayers(true, 'X');
        gamePlayers[1] = new GamePlayers(false, 'O');
   }

    public void passTurn(){ // метод передачи хода, по замысу игру всегда начинает человек gamePlayers[0]
       if (playersTurn == 0){
           playersTurn = 1;
       }else
           playersTurn = 0;
   }
//получение объекта текущего игрока
    GamePlayers getCurrentPlayer(){return gamePlayers[playersTurn]; }

    void showMessage(String text){
        JOptionPane.showMessageDialog(board, text);
    }
}
