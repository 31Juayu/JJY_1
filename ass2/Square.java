package comp1110.ass2;

import java.util.Objects;

import static comp1110.ass2.Color.getEnumByChar;
/*
@author：<u7751035> Xi Ding
 */

public class Square {
    // This 'info' is the numeric ID abbreviation of the rug string.
    // For example, if the rug string placed on this square is "p01 44 45",
    // where 'p' is represented by the color, then 'info' would be "01".
    private String info;

    // Check whether the square has a rug placed on it
    private boolean hasRug = false;

    // Check whether Assam is present on the square
    private boolean hasAssam = false;

    // Represents the color of the rug placed on the square
    private Color color;

    // Constructor to initialize the square with a given color and state.
    // 'state' is an abbreviated rug string like "n00", "p13", etc.
    public Square(String state) {
        this.color = getEnumByChar(state.substring(0,1).charAt(0));
        this.info = state.substring(1,3);
    }

    // Default constructor to initialize the square with default values
    public Square(){
        // Default constructor to initialize the square
        this.color = Color.n;
        this.info = "00";
    }

    // Setter method to update whether Assam is present on the square
    public void setHasAssam(boolean hasAssam) {
        this.hasAssam = hasAssam;
    }

    // Setter method to update whether the square has a rug
    public void setHasRug(boolean hasRug) {
        this.hasRug = hasRug;
    }

    // Set the state of the square based on a string representation
    public void setState(String state) {
        this.color = getEnumByChar(state.substring(0,1).charAt(0));
        this.info = state.substring(1);
        if(!color.equals(color.n)){
            hasRug = true;
        }
    }

    // Check if Assam is present on the square
    public boolean isHasAssam() {
        return hasAssam;
    }

    // Check if the square has a rug
    public boolean isHasRug() {
        return hasRug;
    }

    // Getter method to retrieve the color of the rug on the square
    public Color getColor() {
        return color;
    }

    // Overrides the toString method to return a string representation of the square
    @Override
    public String toString() {
        return color.toString() + info;
    }

    // Overrides the equals method to compare two Square objects based on their color and hasRug state
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Square s = (Square) obj;

        return Objects.equals(color, s.color) && hasRug == s.hasRug;
    }

    // Overrides the hashCode method to return a hash code based on the color and hasRug state of the square
    @Override
    public int hashCode() {
        return Objects.hash(color, hasRug);
    }

    // Get the color of the rug on the square
    public Color getRugColor() {
        char colorChar = this.toString().charAt(0);

        switch (colorChar) {
            case 'c':
                return Color.c;

            case 'y':
                return Color.y;

            case 'p':
                return Color.p;

            case 'r':
                return Color.r;

            default:
                return Color.n;
        }
    }
}

