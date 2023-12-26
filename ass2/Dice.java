package comp1110.ass2;

import java.util.Random;
/*
@author：<u7751035> Xi Ding
 */

// The Dice class is designed for rolling the dice
// In Marrakech, we can create a die object to call the roll method for the task 6
public class Dice {
    // Instance variable to generate random numbers
    private final Random r = new Random();

    // Default constructor for create a die
    public Dice(){}

    // Method to roll the dice when playing
    public int roll(){
        int randomNumber = r.nextInt(6);

        // Particular dice designed for the game
        return switch (randomNumber) {
            case 0 -> 1;
            case 1, 2 -> 2;
            case 3, 4 -> 3;
            case 5 -> 4;
            default -> throw new IllegalStateException("Unexpected value: " + randomNumber);
        };
    }
}
