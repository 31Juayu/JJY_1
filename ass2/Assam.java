package comp1110.ass2;

import java.util.ArrayList;

import static comp1110.ass2.Direction.*;

/*
@author: <u7773637> Wenhui Shi
 */
public class Assam {
    // Starting character for Assam
    private final char start;

    // Position of Assam as an (x,y) pair
    private IntPair position;

    // The direction of Assam when performing
    private Direction direction;

    // Default constructor
    public Assam(){
        this.start = 'A';
        this.position = new IntPair(3,3);
        this.direction = Direction.E;
    }

    // Constructor to initialize Assam
    public Assam(char start, IntPair position, Direction direction){
        this.start = start;
        this.position = position;
        this.direction = direction;
    }

    // Getter methods
    public int getX() { return position.getX();}

    public int getY() { return position.getY();}

    public String getDirectionString() {
        return direction.toString();
    }

    public Direction getDirection(){
        return direction;
    }

    public String getPosition() {
        return position.toString();
    }

    public IntPair getInPair(){
        return position;
    }

    // Get Assam's position as a list of integers
    public ArrayList<Integer> getIntPos(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(position.getX());
        list.add(position.getY());
        return list;
    }

    // Get a string representation of Assam
    public Assam getAssamString(String state){
        String ori = state.substring(3);
        this.direction = getDriByChar(ori.charAt(0));
        int x = Integer.parseInt(state.substring(1,2));
        int y = Integer.parseInt(state.substring(2,3));
        IntPair pos = new IntPair(x,y);
        this.position = pos;

        return new Assam('A',this.position,this.direction);
    }

    // Override the toString for Assam
    @Override
    public String toString() {
        return this.start+this.position.toString()+this.direction.toString();
    }

    // Set Assam from a string representation
    public void setAssam(String state){
        String ori = state.substring(3);
        this.direction = getDriByChar(ori.charAt(0));
        int x = Integer.parseInt(state.substring(1,2));
        int y = Integer.parseInt(state.substring(2,3));
        IntPair pos = new IntPair(x,y);
        this.position = pos;

    }

    // Check if Assam is on the board
    public boolean onBoard(IntPair position){
        int x = position.getX();
        int y = position.getY();

        return x < 7 && x >= 0&& y < 7 && y >= 0;
    }

    // Update methods for position and directions
    public void updateXY(IntPair pos){
        this.position = pos;
    }
    public void updateDirection(Direction d){
        this.direction = d;
    }

    // Count the steps left for Assam to move
    public int leftSteps(IntPair position){
        int x = position.getX();
        int y = position.getY();
        int leftSteps = 0;

        // Check conditions based on position and update steps accordingly
        if(x < 0 ){
            leftSteps = -x;

        } else if (x>= 7) {
            leftSteps = x-6;

        } else if (y < 0) {
            leftSteps = -y;

        } else if (y>=7) {
            leftSteps = y-6;
        }

        return leftSteps;
    }

    // Check conditions based on position and update direction accordingly
    public Direction findNewDirection(IntPair position){
        int x = position.getX();
        int y = position.getY();
        Direction newDirection = null;

        if(x < 0 ){
            if(y == 6){
                newDirection = N;

            }else {
                newDirection = E;
            }
        }

        else if (x>= 7) {
           if(y == 0){
               newDirection = S;

           }else{
               newDirection = W;

           }

        } else if (y < 0) {
          if(x==6){
              newDirection = W;

          }else{
              newDirection = S;
          }

        } else if (y>=7) {
            if(x==0){
                newDirection = E;

            }else {
                newDirection = N;
            }
        }

        return newDirection;
    }
}
