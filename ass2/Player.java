package comp1110.ass2;

import gittest.C;

import java.util.ArrayList;

import static comp1110.ass2.Color.c;
import static comp1110.ass2.Color.getEnumByChar;
/*
@author：<u7731262> jiayu Jian
 */


public class Player {
    // Initialize every player's dirhams to 30
    private int money = 30;

    // Pleyer's rug color
    private Color color;

    //rugsIdStart does not exceed 14
    private int rugsIdStart = 0;

    // Number of rugs left for the player, initialized to 15
    private int rugsLeft = 15;

    // Player's status in the game, either in or out
    private boolean inOrOUt = false;

    // Default constructor
    public Player(){
    }

    // Constructor which initializes the player with a color
    public Player(Color color){
        this.color = color;
    }

    // Constructor that initializes the player with color, money, rugs left, and in/out status
    public Player(Color color, int money, int rugsLeft, boolean inOrOUt){
        this.color = color;
        this.money = money;
        this.rugsLeft = rugsLeft;
        this.inOrOUt = inOrOUt;
    }

    // Place a rug on the board
    public void placeRug(int x1,int y1,int x2,int y2){
        if(rugsIdStart == 15){
            System.out.println("No rugs left");

        }else{
            rugsIdStart++;
            rugsLeft--;
        }
    }

    // Method to pay other players
    public void payOtherPlayers(Player opposite,int payment){
        int newMoney = opposite.getMoney();
        int moneyLeft = money - payment;

        if(moneyLeft < 0){
            newMoney = newMoney + money;
            money = 0;

        }else{
            money = moneyLeft;
            newMoney = newMoney+ payment;
        }

        opposite.setMoney(newMoney);
    }

    // Setter method for inOrOut status
    public void setPlayerInOrOut(boolean inOrOut){
        this.inOrOUt = inOrOut;
    }

    // Set money
    public void setMoney(int money){
        this.money = money;
    }

    // Getter methods
    public int getMoney(){
        return money;
    }

    public Color getColor(){
        return color;
    }

    public int getRugsLeft(){
        return rugsLeft;
    }

    public String getStringInOrOUt(){

        if(this.inOrOUt == false){
            return "o";

        }else{
            return "i";
        }
    }

    // Method to get the string representation of the player's state
    public String toString(){
        String colorString = color.getColor();
        String rugsLeftS = "";

        if(rugsLeft < 10){
            rugsLeftS = "0" +""+ rugsLeft;

        }else{
            rugsLeftS = "" + rugsLeft;
        }

        String moneyS = "";

        if(money < 100 && money >= 10){
            moneyS = "0" +"" + money;

        } else if (money < 10) {
            moneyS = "00" + "" + money;

        }else{
            moneyS = "" + money;
        }

        String io = "";
        if(inOrOUt == false){
            io = "o";

        }else{
            io = "i";
        }

        String playerString = "P" + colorString + moneyS + rugsLeftS + io;

        return playerString;
    }

    // Set the player's state based on a string representation
    public void setState(String state){
        this.color = getEnumByChar(state.substring(1,2).charAt(0));
        this.money = Integer.parseInt(state.substring(2,5));
        this.rugsLeft = Integer.parseInt(state.substring(5,7));

        if(state.substring(7).equals("i")){
            this.inOrOUt = true;

        }else{
            this.inOrOUt = false;
        }
    }

    // Get a new player object based on a string representation of state
    public Player getState(String state){
        this.color = getEnumByChar(state.substring(1,2).charAt(0));
        this.money = Integer.parseInt(state.substring(2,5));
        this.rugsLeft = Integer.parseInt(state.substring(5,7));

        if(state.substring(7).equals("i")){
            this.inOrOUt = true;

        }else{
            this.inOrOUt = false;
        }
        
        return new Player(this.color, this.money, this.rugsLeft, this.inOrOUt);
    }

    // Method to add money to the player
    public void addMoney(int amount) {
        this.money += amount;
    }

    // Method to subtract money from the player
    public void subtractMoney(int amount) {
        this.money -= amount;
    }
}

