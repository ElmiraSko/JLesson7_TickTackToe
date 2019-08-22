package Game;

public class GamePlayers {
    private char playerSign;
    private boolean realPlayer = true;

    public GamePlayers(boolean isRealPlayer, char playerSign){
        this.realPlayer = isRealPlayer;
        this.playerSign = playerSign;
    }
    public boolean isRealPlayer(){ // позволяет получить текущего игрока
        return realPlayer;
    }
    public char getPlayerSign(){ //позволяет получить символ игрока
        return playerSign;
    }

}
