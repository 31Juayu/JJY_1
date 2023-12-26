package comp1110.ass2;
/*
@author：<u7773637> Wenhui Shi
 */


// It is an Enum class representing colors
public enum Color {
    c('c'),
    y('y'),
    p('p'),
    r('r'),
    n('n'); // null

    // Instance variable to store the character representation of the color
    public final char color;

    // Constructor to initialize the color
    Color(char color) {
        this.color = color;
    }

    // Return the character representation of the color.
    public char charColor() {
        switch (this) {
            case c:
                return 'c';
            case y:
                return 'y';
            case p:
                return 'p';
            case r:
                return 'r';
            default:
                return 'n'; // For Color.n
        }
    }

    // Getter methods
    public String getColor() {
        return String.valueOf(color);
    }
    public char getValue() {
        return color;
    }

    // Static method to get the enum value by its character representation
    public static Color getEnumByChar(char c) {
        for (Color myEnum : Color.values()) {
            if (myEnum.getValue() == c) {
                return myEnum;
            }
        }

        // If no matching enum value is found then return null
        return null;
    }

    // Override the toString method to return a string
    @Override
    public String toString() {
        return "" + color;
    }
}
