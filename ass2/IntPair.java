package comp1110.ass2;
/*
@author：<u7731262> jiayu Jian
 */

public class IntPair {
    // x int
    private int x;
    // y int
    private int y;

    // Constructor to initialize IntPair with x and y
    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getter method for the x-coordinate
    public int getX() {
        return x;
    }

    // Getter method for the y-coordinate
    public int getY() {
        return y;
    }

    // Override the toString method to represent the IntPair as a string
    @Override
    public String toString() {
        return String.valueOf(x) + y;
    }

    // Setter method to update the x and y coordinates
    public void setPos(int x,int y){
        this.x = x;
        this.y = y;
    }
}
