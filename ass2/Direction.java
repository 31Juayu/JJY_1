package comp1110.ass2;
/*
@author：<u7773637> Wenhui Shi
 */

public enum Direction {
    W('W'),
    S('S'),
    N('N'),
    E('E');

    // Instance variable to store the character representation of the direction
    private final char direction;

    // Constructor to initialize the direction
    Direction(char direction) {
        this.direction = direction;
    }

    // Get the value of the direction
    public char getValue() {
        return direction;
    }

    // Static method to get the enum value by its character representation
    public static Direction getDriByChar(char c) {
        for (Direction myEnum : Direction.values()) {
            if (myEnum.getValue() == c) {
                return myEnum;
            }
        }

        // Return null if no matching enum value is found
        return null;
    }

    // Override the toString method to return a string
    @Override
    public String toString() {
        return "" + direction;
    }

    // Method to return the character representation of the direction
    public char charDirection(){return direction;}
}
