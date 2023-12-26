package comp1110.ass2;
import static comp1110.ass2.Color.getEnumByChar;
import java.util.ArrayList;

/*
@author：<u7773637> Wenhui Shi
 */

public class Rug {
    // Color of the rug
    private Color color;

    // Order of the rug
    private int order;

    // Position of the rug
    private IntPair position1;
    private IntPair position2;

    // Constructor to initialize the rug with color, order, and positions
    public Rug(Color color, int order, IntPair position1, IntPair position2){
        this.color = color;
        this.order = order;
        this.position1 = position1;
        this.position2 = position2;
    }

    // Constructor to initialize the rug from a string representation of its state
    public Rug(String state){
        this.color =getEnumByChar(state.toCharArray()[0]);
        this.order =Integer.parseInt(state.substring(1,3));
        int x1 = Integer.parseInt(state.substring(3,4));
        int y1 = Integer.parseInt(state.substring(4,5));
        IntPair pos1 = new IntPair(x1,y1);
        int x2 = Integer.parseInt(state.substring(5,6));
        int y2 = Integer.parseInt(state.substring(6));
        IntPair pos2 = new IntPair(x2,y2);
        this.position1 = pos1;
        this.position2 = pos2;
    }

    // Some getter methods
    public String getColor() {
        return this.color.getColor();
    }

    public String getAbb(){
        return this.color
                + String. format("%02d", this.order);
    }

    public String getOrder() {
        return String. format("%02d", this.order);
    }

    public String getPosition() {
        return this.position1.toString()+this.position2.toString();
    }

    // Method to get the positions as a list of IntPair objects
    public ArrayList<IntPair> getIntPairPos(){
        ArrayList<IntPair> list = new ArrayList<>(4);
        list.add(position1);
        list.add(position2);

        return list;
    }

    // Get the string representation of the rug's state
    @Override
    public String toString() {
        return this.color
                + String. format("%02d", this.order)
                + this.position1.toString()
                + this.position2.toString();
    }

    // Method to set the rug's state based on a string representation
    public void setRugState(String state){
        int x1 = Integer.parseInt(state.substring(3,4));
        int y1 = Integer.parseInt(state.substring(4,5));
        IntPair pos1 = new IntPair(x1,y1);
        int x2 = Integer.parseInt(state.substring(5,6));
        int y2 = Integer.parseInt(state.substring(6));
        IntPair pos2 = new IntPair(x2,y2);
        this.position1 = pos1;
        this.position2 = pos2;
    }

    // Method to set the rug's state information
    public void setSateInfo(Color color, int order, IntPair position1, IntPair position2){
        this.color = color;
        this.order = order;
        this.position1 = position1;
        this.position2 = position2;
    }

    // Get the positions as a list of integers
    public ArrayList<Integer> rugGetIntPos(){
        //x1,y1,x2,y2
        ArrayList<Integer> list = new ArrayList<>(4);
        list.add(position1.getX());
        list.add(position1.getY());
        list.add(position2.getX());
        list.add(position2.getY());
        return list;
    }

    // Check if the rug contains a specific position
    public boolean containsPosition(int x, int y) {
        return (position1.getX() == x && position1.getY() == y) ||
                (position2.getX() == x && position2.getY() == y);
    }

    // Check if the rug has been placed on the board
    public boolean hasBeenPut(){
        if(toString().substring(3).equals("7777")){
            return false;
        }else{
            return true;
        }
    }
}

