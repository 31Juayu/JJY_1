package comp1110.ass2;

import java.util.ArrayList;
/*
@author：<u7731262> jiayu Jian
 */
public class GameControl {

    // The width of the board (left to right)
    public final static int BOARD_WIDTH = 7;

    // The height of the board (top to bottom)
    public final static int BOARD_HEIGHT = 7;

    // Orientation of Assam
    private Direction assamOrient;

    // List of player names
    private ArrayList<String> playerNames;

    // List of players' money
    private ArrayList<String> playerDirhams;

    // List of rugs left for each player
    private ArrayList<String> playerLeftRugs;

    // List indicating if a player is still in the game
    private ArrayList<String> ifPlayerStillInGame;

    // Instance of the Marrakech game
    private Marrakech newGame;

    // // Constructor to initialize the game controller
    public GameControl(Marrakech newGame){
        this.newGame = newGame;
        playerNames = new ArrayList<>(4);
        playerDirhams = new ArrayList<>(4);
        playerLeftRugs = new ArrayList<>(4);
        ifPlayerStillInGame = new ArrayList<>(4);
    }

    // Set a new game
    public void setNewGame(Marrakech newGame){
        this.newGame = newGame;
    }

    // Decode the gameString, method to populate the board based on a game string
    public void populateBoard(String gameString){
        newGame.setStateMar(gameString);

        assamOrient = newGame.getAssamOOP().getDirection();
        ArrayList<Player> ps = newGame.getPlayerS();
        playerDirhams.clear();
        playerNames.clear();
        playerLeftRugs.clear();
        ifPlayerStillInGame.clear();

        for (Player p: ps) {
            playerDirhams.add("" + p.getMoney());
            playerNames.add(p.getColor().toString());
            playerLeftRugs.add("" +p.getRugsLeft());
            ifPlayerStillInGame.add(p.getStringInOrOUt());
        }

    }

    // Getter methods
    public Direction getAssamOrient(){
        return assamOrient;
    }

    public ArrayList<String> getPlayerNames(){
        return playerNames;
    }

    public ArrayList<String> getPlayerDirhams(){
        return playerDirhams;
    }

    public ArrayList<String> getPlayerLeftRugs(){
        return playerLeftRugs;
    }

    public ArrayList<String> getIfPlayerStillInGame(){
        return ifPlayerStillInGame;
    }

    public Board getBoard(){
        return newGame.getBoardOOP();
    }

    // Get the index of a player based on their color
    public int getIndexByColor(Color thisColor){
        //c y p r
        if(thisColor.toString().equals(Color.c.toString())){
            return 0;

        } else if (thisColor.toString().equals(Color.y.toString())) {
            return 1;

        } else if (thisColor.toString().equals(Color.p.toString())) {
            return 2;

        } else if (thisColor.toString().equals(Color.r.toString())) {
            return 3;
        }

        return -1;
    }
}
