package comp1110.ass2;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import static comp1110.ass2.Direction.*;
import static comp1110.ass2.Color.*;
/*
@author：group
 */
public class Marrakech {

    private String gameString = "";

    private ArrayList<Player> playerS;

    private ArrayList<Rug> oopRug;

    private Board board;

    private Assam assam;

    private static boolean ifStart;


    public Marrakech(){
        //initiate
        ifStart = true;
        rugsGenerate();
        playerGenerate();
        assamGenerate();
        boardGenerateOOP();
        generate();
    }

    /**
     * Generate players for the game.
     *
     * The method performs the following steps:
     * - Create a new 'ArrayList' named 'playerS' with an initial capacity of 4.
     * - Add four players to the 'playerS' list with the following colors:
     *   - Player 1: Color.c
     *   - Player 2: Color.y
     *   - Player 3: Color.p
     *   - Player 4: Color.r
     */
    public void playerGenerate(){
        //4 players
        //P r 008 03 i
        //c y p r
        playerS= new ArrayList<>(4);
        playerS.add(new Player(Color.c));
        playerS.add(new Player(Color.y));
        playerS.add(new Player(Color.p));
        playerS.add(new Player(Color.r));
    }

    /**
     * Generate a list of rugs for the game.
     *
     * The method performs the following steps:
     * - Create a new 'ArrayList' named 'oopRug' with an initial capacity of 60.
     * - Create an 'IntPair' named 'posRI' with the values (7, 7).
     * - Use nested loops to iterate over the indices of the rugs in the list:
     *   - The outer loop iterates from 0 to 45 (increments by 15).
     *   - The inner loop iterates from 0 to 14.
     *   - Inside the inner loop, determine the color of the rug based on the value of 'i' divided by 15:
     *     - If 'i' divided by 15 is 0, create a rug with the color Color.c.
     *     - If 'i' divided by 15 is 1, create a rug with the color Color.y.
     *     - If 'i' divided by 15 is 2, create a rug with the color Color.p.
     *     - If 'i' divided by 15 is 3, create a rug with the color Color.r.
     *     - Add the created rug to the 'oopRug' list.
     */
    public void rugsGenerate(){
        //p014445
        //Generate rugs list, do not participate in creating gameState
        //The initial state of each rug is 7777 (out of the board)
        oopRug = new ArrayList<>(60);

        IntPair posRI = new IntPair(7,7);

        // Define the player string in the order of c y p r
        for (int i = 0; i < 60; i=i+15) {
            for (int j = 0; j < 15; j++) {
                if(i/15 == 0){
                    oopRug.add(new Rug(Color.c,j,posRI,posRI));
                }else if(i/15 == 1){
                    oopRug.add(new Rug(Color.y,j,posRI,posRI));
                }else if(i/15 ==2){
                    oopRug.add(new Rug(Color.p,j,posRI,posRI));
                }else if(i/15 ==3){
                    oopRug.add(new Rug(Color.r,j,posRI,posRI));
                }
            }
        }
    }

    /**
     * Generate an instance of the Assam class for the game.
     *
     * The method performs the following steps:
     * - Create a new instance of the Assam class and assign it to the 'assam' variable.
     */
    public void assamGenerate(){

        assam = new Assam();
    }

    /**
     * Generate an instance of the Board class using object-oriented programming principles.
     *
     * The method performs the following steps:
     * - Create a new instance of the Board class and assign it to the 'board' variable.
     */
    public void boardGenerateOOP() {
        board = new Board();
    }

    /**
     * Generate a representation of the game state as a string.
     *
     * The method performs the following steps:
     * - Initialize an empty string named 'gameString'.
     * - Iterate over the 'playerS' list and concatenate the string representation of each player to 'gameString'.
     * - Concatenate the string representation of the 'assam' object to 'gameString'.
     * - Concatenate the string representation of the board obtained from the 'getBoardStringOOP' method to 'gameString'.
     * - Assign the final 'gameString' to the 'gameString' variable.
     */
    public void generate() {
        // Combining playerString
        gameString = "";
        for (int i = 0; i < 4; i++) {
            gameString = gameString + playerS.get(i).toString();
        }
        // Combining assam
        gameString = gameString + assam.toString();
        // Combining board and gain the representation of string
        gameString = gameString + board.getBoardStringOOP();
    }

    /**
     * Set the game state based on a provided string representation.
     * The status of all class except rug is updated here.
     * Rugs are updated when the "place" step is performed.
     *
     * The method performs the following steps:
     * - Assign the provided 'state' string to the 'gameString' attribute.
     * - Create a new ArrayList 'playerStr' to store the string representation of each player.
     * - Iterate over the 'gameString' and extract the player string representation for each player, storing them in the 'playerStr' list.
     * - Iterate over the 'playerS' list and set the state of each player using the corresponding string representation from 'playerStr'.
     * - Extract the string representation of the Assam state from 'gameString' and set the Assam state using the 'setAssam' method of the 'assam' object.
     * - Extract the position of Assam from the 'state' string and create a new 'IntPair' object with the extracted position values.
     * - Retrieve the square on the board corresponding to the Assam position and set the 'hasAssam' attribute of the square to true.
     * - Extract the board code from 'gameString'.
     * - Iterate over the board columns and rows, extracting the state of each square from the 'boardCode' string and setting the state of the corresponding square on the board.
     * - If the state of a square indicates it has a rug (not Color.n), set the 'hasRug' attribute of the square to true.
     */
    public void setStateMar(String state){
        this.gameString = state;
        //String playerStr[] = new String[4];
        ArrayList<String> playerStr = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            playerStr.add(gameString.substring(i*8,i*8+8));
        }

        for (int i = 0; i < 4; i++) {
            playerS.get(i).setState(playerStr.get(i));
        }
        //set Assam
        String stateA = gameString.substring(32,36);
        assam.setAssam(stateA);

        String subAssam = state.substring(32,36);
        String positionAssam = subAssam.substring(1,3);
        int xPos = Integer.parseInt(positionAssam.substring(0,1));
        int yPos = Integer.parseInt(positionAssam.substring(1));
        IntPair assPosPair = new IntPair(xPos,yPos);

        board.getSquare(assPosPair).setHasAssam(true);

        //set boards
        String boardCode = gameString.substring(36);

        IntPair pos = new IntPair(-1,-1);
        for (int i = 1; i <= 7; i++) {
            int start = 1+(i-1)*7*3;
            String columString = "";
            if((1+i*7*3) != boardCode.length()){
                columString = boardCode.substring(start,1+i*7*3);
            }else{
                columString = boardCode.substring(start);
            }

            for (int j = 1; j <=7 ; j++) {
                int start_r = (j-1)*3;
                String rowString = "";
                if((1+j*3) != columString.length()){
                    rowString = columString.substring(start_r,j*3);
                }else{
                    rowString = columString.substring(start_r);
                }
                pos.setPos(i-1,j-1);
                board.getSquare(pos).setState(rowString);

                if(!getEnumByChar(rowString.substring(0,1).charAt(0)).equals(Color.n)){
                    board.getSquare(pos).setHasRug(true);
                }

            }
        }
    }

    public Board getBoardOOP(){
        return board;
    }


    public String getGameString() {
        generate();
        return gameString;
    }

    public ArrayList<Player> getPlayerS(){
        return playerS;
    }


    public Player getPlayerByColor(Color color){
        Player op = null;
        for (Player p: playerS) {
            if(p.getColor().toString().equals(color.toString())){
                op = p;
            }
        }
        return op;
    }

    public Assam getAssamOOP(){
        return assam;
    }

    public ArrayList<Rug> getRugsArray(){
        return oopRug;
    }

    /**
     * Find and set the state of rugs that belong to a specific player.
     *
     * The method performs the following steps:
     * - Retrieve the abbreviation (abbreviated color) of the 'newRug' object.
     * - Iterate over each Rug object in the 'oopRug' list.
     *   - If the abbreviation of the current Rug object matches the abbreviation of 'newRug', set the rug state of the current Rug object to the string representation of 'newRug'.
     */
    public void findAndSetRugState(Rug newRug){
        //Color color, int rugsIdStart, IntPair pos1, IntPair pos2
        //find all rugs that belongs to this player
        String abb = newRug.getAbb();
        for (Rug e:oopRug) {
            if(e.getAbb().equals(abb)){
                e.setRugState(newRug.toString());
            }
        }

    }

    public String getAssamString(){
        return assam.toString();
    }

    public String getBoardString() {
        return board.toString();
    }

    public void setAssamByString(String newStateAssam){
        assam.setAssam(newStateAssam);
        generate();
    }

    //___________________________________________________________________________________________________




    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     *  - The String is 7 characters long
     *  - The first character in the String corresponds to the colour character of a player present in the game
     *  - The next two characters represent a 2-digit ID number
     *  - The next 4 characters represent coordinates that are on the board
     *  - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     *  - c023343 (Shares the colour but not the ID)
     *  - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     * @param gameString A String representing the current state of the game as per the README
     * @param rug A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameString, String rug) {
        // Check if the rug string length is valid
        if(rug.length() != 7){
            return false;
        }
        // Check if the first character of the rug string represents a valid color abbreviation
        if(!rug.substring(0,1).equals("c") &&
                !rug.substring(0,1).equals("y") &&
                !rug.substring(0,1).equals("r") && !rug.substring(0,1).equals("p") ){
            return false;
        }
        // Check if the next two characters represent a valid
        String subcode = rug.substring(1,3);
        try {
            Integer.parseInt(subcode);
        } catch (NumberFormatException e) {
            return false;
        }

        //The next 4 characters represent coordinates that are on the board
        String sub1 = rug.substring(3,5);
        String sub2 = rug.substring(5);
        int intValue1 = 0;
        // Check if the next four characters represent valid board coordinates
        try {
            intValue1 = Integer.parseInt(sub1);
            if((intValue1/10) > 6 || (intValue1/10) < 0 ){
                return false;
            }
            if((intValue1%10) > 6 || (intValue1%10) < 0){
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        int intValue2 = 0;
        try {
            intValue2 = Integer.parseInt(sub2);
            if((intValue2/10) > 6 || (intValue2/10) < 0 ){
                return false;
            }
            if((intValue2%10) > 6 || (intValue2%10) < 0){
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // Check if the combination of ID number and color is unique in the board
        String subRug = rug.substring(0,3);
        String boardCode = gameString.substring(36);
        for (int i = 1; i <= 7; i++) {
            int start = 1+(i-1)*7*3;
            String columString = "";
            if((1+i*7*3) != boardCode.length()){
                columString = boardCode.substring(start,1+i*7*3);
            }else{
                columString = boardCode.substring(start);
            }

            for (int j = 1; j <=7 ; j++) {
                int start_r = (j-1)*3;
                String rowString = "";
                if((1+j*3) != columString.length()){
                    rowString = columString.substring(start_r,j*3);
                }else{
                    rowString = columString.substring(start_r);
                }
                // Check if the rug ID and color already exist on the board
                if(subRug.equals(rowString)){
                    //System.out.println(false);
                    return false;
                }
            }
        }

        // FIXME: Task 4
        return true;
    }



    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     *  - One shows 1
     *  - Two show 2
     *  - Two show 3
     *  - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     * @return The result of the roll of the die meeting the criteria above
     */
    public static int rollDie() {
        // Call the method in the Dice class
        Dice d = new Dice();
        int num = d.roll();
        return num;
        // FIXME: Task 6
    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {
        // Create an array to store the string representation of each player
        String[] playerStr = new String[4];
        // Extract the player strings from the current game state
        for (int i = 0; i < playerStr.length; i++) {
            playerStr[i] = currentGame.substring(i*8,i*8+8);

        }
        // Create an array to store Player objects
        Player[] p = new Player[4];
        // Convert the player strings to Player objects
        for (int i = 0; i < playerStr.length; i++) {
            p[i] = new Player().getState(playerStr[i]);
            //System.out.println(playerStr[i]);
        }
        // Calculate the In player's total number of rugs left
        int rugsLeft = 0;
        for(int i = 0; i< 4;i++) {
            if(p[i].getStringInOrOUt().equals("i")){
                rugsLeft = rugsLeft + p[i].getRugsLeft();
            }
        }
        // Return true if there are no rugs left, indicating the game is over
        return rugsLeft == 0;
        // FIXME: Task 8
    }

    public static class AssamState {
        private String state;

        public AssamState(String state) {
            this.state = state;
        }

        public String getStringAssam() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    /**
     * Helper method to rotate the Assam state by the specified rotation angle.
     *
     * @param currentAssam The AssamState object representing the current state of the Assam.
     * @param rotation     The rotation angle in degrees.
     */
    private static void rotateHelp(AssamState currentAssam, int rotation){
        // No rotation needed, do nothing
        if(rotation == 0){
        }else{
            //System.out.println(currentAssam.getState());
            // Retrieve the Assam state string and convert it to a character array
            char assamArray[] = currentAssam.getStringAssam().toCharArray();
            // Determine the current direction of the Assam
            // Rotate the Assam clockwise by updating the last character in the array
            if(getDriByChar(assamArray[assamArray.length-1]).equals(comp1110.ass2.Direction.N)){
                assamArray[assamArray.length-1] = 'E';
            } else if (getDriByChar(assamArray[assamArray.length-1]).equals(comp1110.ass2.Direction.E)) {
                assamArray[assamArray.length-1] = 'S';
            } else if (getDriByChar(assamArray[assamArray.length-1]).equals(comp1110.ass2.Direction.S)) {
                assamArray[assamArray.length-1] = 'W';
            } else if (getDriByChar(assamArray[assamArray.length-1]).equals(comp1110.ass2.Direction.W)) {
                assamArray[assamArray.length-1] = 'N';
            }
            // Create a new state string by concatenating the updated character array
            String newState = "";
            for (int i = 0; i < assamArray.length; i++) {
                newState = newState + assamArray[i];
            }
            // Update the Assam state with the new rotated state
            currentAssam.setState(newState);
            // Recursively call the rotateHelp method with the remaining rotation angle
            rotateHelp(currentAssam,rotation-90);
        }
    }

    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     * @param currentAssam A String representing Assam's current state
     * @param rotation The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                 is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                 current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {

        if(rotation % 90 != 0){
            return currentAssam;
        }

        if((rotation / 180 != 0) && (rotation % 180 == 0)){
            return currentAssam;
        }
        AssamState s = new AssamState(currentAssam);
        rotateHelp(s,rotation);
        return s.getStringAssam();
        // FIXME: Task 9
    }

    /**
     * Checks if two given coordinates represent neighboring positions.
     *
     * @param xA The x-coordinate of the first position.
     * @param yA The y-coordinate of the first position.
     * @param x1 The x-coordinate of the second position.
     * @param y1 The y-coordinate of the second position.
     * @param x2 The x-coordinate of the third position.
     * @param y2 The y-coordinate of the third position.
     * @return True if the first position is a neighbor of either the second or third position, False otherwise.
     */
    private static boolean isNeighbor(int xA,int yA,int x1,int y1,int x2,int y2){
        if((((xA - 1) == x1)&&((yA)==y1)) || (((xA - 1) == x2)&&((yA)==y2))){
            return true;
        } else if ((((xA) == x1)&&((yA+1)==y1)) || (((xA) == x2)&&((yA+1)==y2))) {
            return true;
        } else if ((((xA+1) == x1)&&((yA)==y1)) || (((xA+1) == x2)&&((yA)==y2))) {
            return true;
        } else if ((((xA) == x1)&&((yA-1)==y1)) || (((xA) == x2)&&((yA-1)==y2))) {
            return true;
        }
        return false;
    }

    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     *   1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     *   2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     *      the new rug must not cover the entirety of another rug that's already on the board.
     * @param gameState A game string representing the current state of the game
     * @param rug A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rug) {
        // Check if the rug itself is valid
        if(!isRugValid(gameState,rug)){
            return false;
        }
        // Create a new Marrakech game instance and set its state
        Marrakech newGame = new Marrakech();
        newGame.setStateMar(gameState);
        // Create a new Rug instance
        Rug rg = new Rug(rug);
        // Get the squares on the board based on the rug's position
        Square tile1 = newGame.getBoardOOP().getSquare(rg.getIntPairPos().get(0));
        Square tile2 = newGame.getBoardOOP().getSquare(rg.getIntPairPos().get(1));
        // Get the integer positions of the rug's corners
        int x1 = rg.rugGetIntPos().get(0);
        int y1 = rg.rugGetIntPos().get(1);
        int x2 = rg.rugGetIntPos().get(2);
        int y2 = rg.rugGetIntPos().get(3);
        //// Check if the rug is trying to occupy a complete tile
        if(tile1.toString().equals(tile2.toString()) && !tile1.toString().equals("n00")){
            return false;
        }
        // Check if the rug is placed on top of the Assam
        int xA = newGame.getAssamOOP().getIntPos().get(0);
        int yA = newGame.getAssamOOP().getIntPos().get(1);
        if(((xA == x1)&&(yA == y1)) ||
                ((xA == x2)&&(yA==y2))){
            return false;
        }
        // Check if the rug is placed next to a Assam
        if(!isNeighbor(xA,yA,x1,y1,x2,y2)){
            return false;
        }
        // FIXME: Task 10
        return true;
    }

    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {
        // Create a new Marrakech game instance and set its state
        Marrakech marrakech = new Marrakech();
        marrakech.setStateMar(gameString);

        // Get the position of Assam
        List<Integer> assamPosList = marrakech.getAssamOOP().getIntPos();
        int assamPosX = assamPosList.get(0);
        int assamPosY = assamPosList.get(1);
        IntPair assamPosition = new IntPair(assamPosX, assamPosY);

        // Get the color of the rug on the square Assam landed on
        Color rugColor = marrakech.getBoardOOP().getSquare(assamPosition).getRugColor();

        // If there is no rug on the square Assam stand on, return 0 as the payment amount
        if (rugColor.equals(Color.n)) {
            return 0;
        }
        // Count the number of connected squares with the same color as the rug
        int connectedSquares = marrakech.getBoardOOP().countConnectedSquaresColor(assamPosition, rugColor);

        return connectedSquares;
        // FIXME: Task 11
    }

    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        // Initialize the game
        Marrakech marrakech = new Marrakech();
        marrakech.setStateMar(gameState);

        // If the game is not yet over, then return 'n'.
        if (!Marrakech.isGameOver(gameState)) {
            return 'n';
        }

        // Use topScorer to track the player who has the top scores
        Player topScorer = null;

        // Iterate through all players to determine who is the top scorer
        // Considering the four points below:
        // 1. Only care players who are still in the game.
        // 2. If the current player's score is higher than the
        // previous highest scoring player, set him as the new highest scoring player.
        // 3. If two players have the same score, compare their money.
        // The player with the most money wins.
        // 4. If both players have the same score and money, it's a tie
        for (Player player : marrakech.getPlayerS()) {
            if (player.getStringInOrOUt().equals("i")) {
                if (topScorer == null) {
                    topScorer = player;

                } else {
                    int currentScore = calculateScore(player, marrakech);
                    int topScore = calculateScore(topScorer, marrakech);

                    if (currentScore > topScore) {
                        topScorer = player;

                    } else if (currentScore == topScore) {
                        if (player.getMoney() > topScorer.getMoney()) {
                            topScorer = player;

                        } else if (player.getMoney() == topScorer.getMoney()) {
                            topScorer = null; // Set to null to indicate a tie
                        }
                    }
                }
            }
        }

        // It's a tie.
        if (topScorer == null) {
            return 't';
        }

        // Return the result based on the top player
        return topScorer.getColor().charColor();
        // FIXME: Task 12
    }

    /**
     * Calculates the score for a player in the Marrakech game.
     *
     * @param player The player for whom to calculate the score.
     * @param marrakech The Marrakech game instance.
     * @return The calculated score.
     */
    private static int calculateScore(Player player, Marrakech marrakech) {
        // The score is the sum of the player's money and the count of squares with the player's color on the board
        return player.getMoney() + marrakech.getBoardOOP().countColor(player.getColor());
    }

    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult){

        Assam assam1 = new Assam().getAssamString(currentAssam);
        int leftSteps;
        Direction newDirection;

        //get the initial x,y
        int x = assam1.getInPair().getX();
        int y = assam1.getInPair().getY();
        //update x,y after first move
        IntPair pos = updateIntPairSimple(assam1,x,y,dieResult);
        assam1.updateXY(pos);
        //if after the first move, assam is still on the board, just directly update the assam state
        //if not, go to the second move
        if(!assam1.onBoard(pos)){
            //second move,
            // first find the left step
            leftSteps = assam1.leftSteps(pos);
            //then find the new direction
            newDirection = assam1.findNewDirection(pos);
            assam1.updateDirection(newDirection);
            //update the x2, y2
            int x2 = assam1.getInPair().getX();
            int y2 = assam1.getInPair().getY();
            //System.out.println(x2+" "+y2);
            IntPair pos2 = updateIntPairComplex(x2,y2,leftSteps);
            assam1.updateXY(pos2);

            //System.out.println(assam1);
        }

        return assam1.toString();
        // FIXME: Task 13
    }


    //update the IntPair for the simple move
    public static IntPair updateIntPairSimple (Assam assam1, int x, int y, int step){

        if(assam1.getDirection().equals(Direction.N)){
            y = y - step;
        } else if (assam1.getDirection().equals(Direction.S)) {
            y = y + step;
        } else if (assam1.getDirection().equals(Direction.W)) {
            x = x - step;
        } else if (assam1.getDirection().equals(Direction.E)) {
            x = x + step;
        }

        return new IntPair(x,y);
    }

    //update the IntPair for the complex move
    public static IntPair updateIntPairComplex (int x2, int y2, int leftSteps){
        if(x2 < 0 ){
            if(y2 == 0 || y2 == 2 || y2 == 4){
                y2 = y2 + 1;
                x2 = x2 + 2*leftSteps - 1;
            }else if(y2 == 1 || y2 == 3 || y2 == 5){
                y2 = y2 - 1;
                x2 = x2 + 2*leftSteps - 1;
            }else {
                y2 = y2 - leftSteps + 1;
                x2 = 0;
            }

        } else if (x2>= 7) {
            if(y2 == 1 || y2 == 3 || y2 == 5){
                y2 = y2 + 1;
                x2 = x2 - 2*leftSteps + 1;
            }else if(y2 == 2 || y2 == 4 || y2 == 6){
                y2 = y2 - 1;
                x2 = x2 - 2*leftSteps + 1;
            }else {
                y2 = y2 + leftSteps - 1;
                x2 = 6;
            }

        } else if (y2 < 0) {
            if(x2 == 0 || x2 == 2 ||x2 == 4){
                x2 = x2 + 1;
                y2 = y2 + 2*leftSteps - 1;
            }else if(x2 == 1 || x2 == 3 || x2 == 5){
                x2 = x2 - 1;
                y2 = y2 + 2*leftSteps - 1;
            }else {
                x2 = x2 - leftSteps + 1;
                y2 = 0;
            }

        } else if (y2>=7) {
            if(x2 == 1 ||x2 == 3 || x2 == 5){
                x2 = x2 + 1;
                y2 = y2 - 2*leftSteps + 1;
            }else if(x2 == 2 ||x2 == 4 || x2 == 6){
                x2 = x2 - 1;
                y2 = y2 - 2*leftSteps + 1;
            }else {
                x2 = x2 + leftSteps - 1;
                y2 = 6;
            }
        }

        return new IntPair(x2,y2);
    }


    /**
     * Update the number of rugs left for a specific player in the game state string currentGame
     * and returns the updated game state string.
     *
     * @param ps     The list of players.
     * @param color  The color of the rugs to be set.
     * @param x1     The x-coordinate of the first rug.
     * @param y1     The y-coordinate of the first rug.
     * @param x2     The x-coordinate of the second rug.
     * @param y2     The y-coordinate of the second rug.
     * @return       The updated game state string after updating the rugs.
     */
    private static String setRugsLeft(ArrayList<Player> ps, Color color,int x1,int y1,int x2,int y2){
        String states = "";
        for (Player p:ps) {
            if(p.getColor().toString().equals(color.toString())){
                p.placeRug(x1,y1,x2,y2);
                states = states + p;
            }else{
                states = states + p;
            }
        }
        return states;
    }

    public static String boardSate(String abbRugCode,Board newBoard,int x1,int y1,int x2,int y2){
        IntPair pos1 = new IntPair(x1,y1);
        IntPair pos2 = new IntPair(x2,y2);
        newBoard.getSquare(pos1).setState(abbRugCode);
        newBoard.getSquare(pos2).setState(abbRugCode);
        return newBoard.getBoardStringOOP();
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     * @param currentGame A String representation of the current state of the game.
     * @param rug A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rug) {
        if(!isRugValid(currentGame,rug)) {

            return currentGame;
        }
        
        if(isPlacementValid(currentGame,rug)){
            Rug newRug = new Rug(rug);
            int x1 = newRug.rugGetIntPos().get(0);
            int y1 = newRug.rugGetIntPos().get(1);
            int x2 = newRug.rugGetIntPos().get(2);
            int y2 = newRug.rugGetIntPos().get(3);

            Marrakech newGame = new Marrakech();
            newGame.setStateMar(currentGame);
            Board newBoard = newGame.getBoardOOP();

            ArrayList<Player> ps = newGame.getPlayerS();
            String pls = "";
            if(newRug.getColor().toString().equals(c.toString())){
                pls = setRugsLeft(ps,Color.c,x1,y1,x2,y2);
            }else if(newRug.getColor().toString().equals(y.toString())){
                pls = setRugsLeft(ps,Color.y,x1,y1,x2,y2);
            }else if(newRug.getColor().toString().equals(p.toString())){
                pls = setRugsLeft(ps,Color.p,x1,y1,x2,y2);
            }else if(newRug.getColor().toString().equals(r.toString())){
                pls = setRugsLeft(ps,Color.r,x1,y1,x2,y2);
            }

            String left_1 = currentGame.substring(32);
            currentGame = pls+left_1;

            String abbRugsCode = rug.substring(0,3);
            String left = currentGame.substring(0,36);
            String newboard = boardSate(abbRugsCode,newBoard,x1,y1,x2,y2);
            return left + newboard;
        }else{

            return currentGame;
        }
        // FIXME: Task 14
    }

    public static void main(String[] args) {
       /* String codeS ="Pc04000i" +
                "Py03900i" +
                "Pp01101i" +
                "Pr03001i" +
                "A10E" +
                "By21c23c23y24r13c16c20n00p16c30c30r29r21c20r18r18y11y11c29r21r20n00n00y22c26y15p15p15r05r05y22r28r28y20r15n00c28c28c13p20p20r15n00n00c05c11y12p02r10";

        //autoDirectionDis(codeS);*/
        IntPair ps = new IntPair(5,2);
        System.out.println(ps.toString());
    }

}
