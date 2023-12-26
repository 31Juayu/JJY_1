package comp1110.ass2.gui;
import comp1110.ass2.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static comp1110.ass2.Color.getEnumByChar;
import static comp1110.ass2.Marrakech.*;

/*
@author：group
 */

public class Game extends Application {
    /*Task 17 logic:
    The main logic of this algorithm is based on the Monte Carlo method.
    When the computer (virtual player) decides which way to move the merchant,
    the autoDirectionDis() method simulates 1000 times in each direction,
    calculating the amount the merchant needs to pay after the predicted movement,
    and sums up these 1000 amounts as the total payment.
    The virtual player will choose the direction with the minimum total payment.
    When the virtual player decides how to place the carpet, it first enumerates every possible position and simulates the possible actions of a real player.
    It runs simulations 100 times, selecting the most favorable scenario for the virtual player.
    The logic of "simulating real player behavior" is implemented in the mainSimulation() method.
    Initially, the simulation assumes that the real player chooses the optimal rotation method (calls autoDirectionDis with 5 epochs),
    and then simulates the amount the real player needs to pay after moving the merchant. The more money paid, the more advantageous the situation is for the virtual player.
    The "most advantageous" scenario refers to summing up the costs generated in these 100 simulations; if a certain placement generates the highest cost,
    it indicates that this placement method is more advantageous for the virtual player.
    */


    //create a new instance of the Group class called "root"
    private final Group root = new Group();

    //create another Group called "board" to hold the game board elements
    private final Group board =  new Group();

    //create a Group called "virtualRug" to hold a virtual representation of rug
    private final Group virtualRug = new Group();

    //set the width and height of the application window
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    // Group to hold control nodes (e.g. buttons, text fields)
    private final Group controls = new Group();

    //represent the number of turns in the game
    private int index = 1;

    private ArrayList<Boolean> playerState;

    //represent the size of each tile on the game board
    private double Tile_Size = 80;

    //how much the blue background extends past the tiles
    private int BOARD_BORDER = 10;

    //The start of the board in the x-direction (ie: x = 0)
    private double START_X = 20.0;

    //The start of the board in the y-direction (ie: y = 0)
    private double START_Y = 20.0;

    //the number of players
    public int players;

    //creates a Rectangle object called "aVirtualRug" (represents a virtual rug)
    private Rectangle aVirtualRug = new Rectangle(Tile_Size*2, Tile_Size);

    //the coordinate of left and upper corner.
    private int LU_X,LU_Y;

    //the coordinate of right and upper corner.
    private int RU_X,RU_Y;

    //the coordinate of right and button corner.
    private int RB_X,RB_Y;

    //the coordinate of left and button corner.
    private int LB_X,LB_Y;


    private String gameString;
    private boolean placeValidate = false;
    private String thisRugStringAbb = null;
    private String newValidRUgString = null;

    //the number of times the "move Ass" action is performed
    private int count = 0;

    boolean ifIsPVC = false;

    //private int epoch = 1000;

    private int epoch_2 = 100;

    private int rPX;

    private int rPy;

    private int vPX;

    private int vPY;
    //private comp1110.ass2.Color anotherColor;

    private comp1110.ass2.Color rColor;

    private comp1110.ass2.Color vColor;

    /**
     This method is responsible for drawing an image on the game board and applying a rotation to it.
     The image specified by the provided path is loaded and displayed on the board at the given coordinates (x, y).
     The size of the image is determined by the Tile_Size parameter, ensuring it fits within a single tile.
     Additionally, the image is rotated by the specified angle around its center point.
     @param path The path to the image file.
     @param START_X The starting x-coordinate of the game board.
     @param START_Y The starting y-coordinate of the game board.
     @param x The x-coordinate where the image should be placed on the board.
     @param y The y-coordinate where the image should be placed on the board.
     @param Tile_Size The size of each tile on the board.
     @param angle The rotation angle in degrees.
     */

    void drawPictureRotate(String path,double START_X,double START_Y,int x,int y,double Tile_Size,int angle){
        Node view;
        Image image = new Image(path);
        ImageView imageView = new ImageView();

        //Set properties for the ImageView
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(Tile_Size);
        imageView.setFitWidth(Tile_Size);
        imageView.setImage(image);
        view = imageView;

        // Set the position of the view based on the given coordinates and tile size
        view.setLayoutX(START_X + (x* Tile_Size));
        view.setLayoutY(START_Y + (y * Tile_Size));

        Rotate rotate = new Rotate();

        //Set the rotation angle
        rotate.setAngle(angle);

        //Set the pivot point for the rotation
        rotate.setPivotX(Tile_Size/2);
        rotate.setPivotY(Tile_Size/2);

        //Apply the rotation to the view
        view.getTransforms().addAll(rotate);

        //Add the view to the board group
        board.getChildren().add(view);
    }


    /**
     This method is responsible for drawing an image outside of the game board.
     The image specified by the provided path is loaded and displayed at the given coordinates (x, y).
     The size of the image is determined by the Tile_Size parameter, ensuring it fits within the specified dimensions.
     The image is added to the controls group, allowing it to be displayed outside of the game board.
     @param path The path to the image file.
     @param x The x-coordinate where the image should be placed.
     @param y The y-coordinate where the image should be placed.
     @param Tile_Size The size of the image.
     */
    void drawPictureOutside(String path,int x,int y,double Tile_Size){
        Node view;
        Image image = new Image(path);
        ImageView imageView = new ImageView();

        //Set properties for the ImageView
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(Tile_Size);
        imageView.setFitWidth(Tile_Size);
        imageView.setImage(image);
        view = imageView;

        //Set the position of the view based on the given coordinates
        view.setLayoutX(x);
        view.setLayoutY(y);

        //Add the view to the controls group
        controls.getChildren().add(view);
    }

    /**
     This method is responsible for drawing a white rectangle on the screen, with the ability to specify its size and position.
     A rectangle object is created with the specified width (sizeX) and height (sizeY).
     The rectangle is filled with a white color and has a white border.
     The position of the rectangle is set based on the provided coordinates (x, y).
     The rectangle is added to the controls group, allowing it to be displayed on the screen.
     @param sizeX The width of the rectangle.
     @param sizeY The height of the rectangle.
     @param x The x-coordinate where the rectangle should be placed.
     @param y The y-coordinate where the rectangle should be placed.
     */
    void rectangleCanCover(int sizeX,int sizeY,int x,int y){
        Rectangle rectangle = new Rectangle(sizeX, sizeY);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.WHITE);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        controls.getChildren().addAll(rectangle);
    }

    /**
     This method retrieves the coordinates of the four corners of a given rectangle in the scene coordinates.
     The coordinates are returned as an array of doubles, with each pair of values representing an x-coordinate and a y-coordinate.
     The top-left corner coordinates are obtained using the localToScene method on the rectangle's bounds in the local coordinate system.
     The top-right corner coordinates are obtained by replacing the minimum x-coordinate with the maximum x-coordinate.
     The bottom-right corner coordinates are obtained by replacing the minimum y-coordinate with the maximum y-coordinate.
     The bottom-left corner coordinates are obtained by replacing the maximum x-coordinate with the minimum x-coordinate.
     The resulting array of corner coordinates is returned.
     @param rectangle The rectangle for which to retrieve the corner coordinates.
     @return An array of doubles representing the coordinates of the four corners of the rectangle in the scene.
     */
    private double[] getRectangleCorners(Rectangle rectangle) {
        //store the coordinates of the rectangle's corners, 4 corners and each corner has x and y
        double[] corners = new double[8];

        // the coordinates of the top-left corner
        corners[0] = rectangle.localToScene(rectangle.getBoundsInLocal()).getMinX();
        corners[1] = rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY();

        // the coordinates of the top-right corner
        corners[2] = rectangle.localToScene(rectangle.getBoundsInLocal()).getMaxX();
        corners[3] = rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY();

        // the coordinates of the bottom-right corner
        corners[4] = rectangle.localToScene(rectangle.getBoundsInLocal()).getMaxX();
        corners[5] = rectangle.localToScene(rectangle.getBoundsInLocal()).getMaxY();

        // the coordinates of the bottom-left corner
        corners[6] = rectangle.localToScene(rectangle.getBoundsInLocal()).getMinX();
        corners[7] = rectangle.localToScene(rectangle.getBoundsInLocal()).getMaxY();

        return corners;
    }


    /**
     This method generates two pairs of coordinates based on the provided parameters.
     The first pair of coordinates represents the top-left position and is set to (leftTopX, leftTopY).
     The second pair of coordinates depends on the difference between rightTopX and leftTopX:
     If the difference is 2, indicating adjacent positions horizontally, the second pair is set to (leftTopX + 1, leftTopY).
     If the difference is not 2, indicating adjacent positions vertically, the second pair is set to (leftTopX, leftTopY + 1).
     The generated pairs of coordinates are stored in an array of IntPair objects and returned.
     @param leftTopX The x-coordinate of the top-left position.
     @param leftTopY The y-coordinate of the top-left position.
     @param rightTopX The x-coordinate of the top-right position.
     @return An array of IntPair objects representing the two pairs of coordinates.
     */
    private IntPair[] getTwoPos(int leftTopX,int leftTopY,int rightTopX) {
        IntPair[] twoPairs = new IntPair[2];

        // Check if the difference between rightTopX and leftTopX is 2
        if(Math.abs(rightTopX - leftTopX) == 2){
            // If the difference is 2, set the first pair of coordinates as (leftTopX, leftTopY)
            twoPairs[0] = new IntPair(leftTopX,leftTopY);
            twoPairs[1] = new IntPair((leftTopX + 1),leftTopY);
        }else{
            // If the difference is not 2, set the first pair of coordinates as (leftTopX, leftTopY)
            twoPairs[0] = new IntPair(leftTopX,leftTopY);
            // Set the second pair of coordinates as (leftTopX, leftTopY + 1)
            twoPairs[1] = new IntPair(leftTopX,leftTopY+1);
        }
        return twoPairs;
    }


    /**
     The DraggablePiece class extends the Group class and represents a draggable piece on the screen.
     It consists of a Rectangle object and provides functionality for dragging and handling various operations related to the piece.
     Constructor:
     The constructor DraggablePiece takes a Rectangle object, rectangle, and initial position coordinates posX and posY as parameters.
     It initializes the DraggablePiece by setting the layout position and adding the Rectangle to its children.
     Event Handlers:
     The setOnMousePressed event handler captures the initial mouse click position on the DraggablePiece.
     The setOnMouseDragged event handler handles the dragging behavior of the DraggablePiece.
     It calculates the difference in mouse position from the initial click and updates the layout position accordingly.
     The setOnMouseReleased event handler handles the release of the mouse button after dragging.
     Within this handler, various calculations and operations are performed:
     The getRectangleCorners method is called with the rectangle to retrieve an array of corner coordinates.
     The corner coordinates are rounded and assigned to variables representing the coordinates of the corners.
     The getTwoPos method is called to obtain two positions based on the corner coordinates.
     The resulting positions are extracted and stored in variables.
     A thisRugString is constructed by concatenating string representations of the corner positions.
     The isPlacementValid method is called to check if the placement is valid.
     If the placement is valid, flags are set and newValidRUgString is updated with the current thisRugString.
     */
    class DraggablePiece extends Group {

        final Rectangle rectangle;

        double mouseX,mouseY;
        public DraggablePiece(Rectangle rectangle,int posX,int posY) {
            super();
            this.rectangle = rectangle;

            //System.out.println("the rotation is + " +this.rectangle.getRotate());

            this.setLayoutX(posX);
            this.setLayoutY(posY);
            this.getChildren().add(rectangle);

            this.setOnMousePressed(event -> {

                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();

            });

            this.setOnMouseDragged(event -> {

                double diffX = event.getSceneX() - mouseX;
                double diffY = event.getSceneY() - mouseY;
                this.setLayoutX(this.getLayoutX() + diffX);
                this.setLayoutY(this.getLayoutY() + diffY);

                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
            });

            this.setOnMouseReleased(event -> {
                root.setOnKeyPressed(keyEvent -> {
                });

                double[] corners = getRectangleCorners(this.rectangle);

                //LU_X,LU_Y;
                LU_X = (int) Math.round(corners[0]);
                LU_Y = (int) Math.round(corners[1]);
                //System.out.println("left top is  " + (int) Math.round((LU_X-START_X)/Tile_Size) + " , " +(int) Math.round((LU_Y-START_X)/Tile_Size));
                RU_X = (int) Math.round(corners[2]);
                RU_Y = (int) Math.round(corners[3]);
                //System.out.println("right top is  " + (int) Math.round((RU_X-START_X)/Tile_Size) + " , " +(int) Math.round((RU_Y-START_X)/Tile_Size));
                RB_X = (int) Math.round(corners[4]);
                RB_Y = (int) Math.round(corners[5]);
                //System.out.println("right button is  " + (int) Math.round((RB_X-START_X)/Tile_Size) + " , " +(int) Math.round((RB_Y-START_X)/Tile_Size));
                LB_X = (int) Math.round(corners[6]);
                LB_Y = (int) Math.round(corners[7]);
                //System.out.println("left button is  " + (int) Math.round((LB_X-START_X)/Tile_Size) + " , " +(int) Math.round((LB_Y-START_X)/Tile_Size));

                //System.out.println("_______________________________________________________________");
                //int leftTopX,int leftTopY,int rightTopX,int leftBottomY
                IntPair ps[] = getTwoPos((int) Math.round((LU_X-START_X)/Tile_Size)
                        ,(int) Math.round((LU_Y-START_X)/Tile_Size)
                        ,(int) Math.round((RB_X-START_X)/Tile_Size));

                int cor1_x = ps[0].getX();
                int cor1_y = ps[0].getY();

                //System.out.print("first " + cor1_x + " , " + cor1_y);

                int cor2_x = ps[1].getX();
                int cor2_y = ps[1].getY();

                String thisRugString = thisRugStringAbb
                        + (new IntPair(cor1_x,cor1_y)).toString()
                        + (new IntPair(cor2_x,cor2_y)).toString();

                //System.out.println(" second " + cor2_x + " , " + cor2_y);
                //System.out.println();
                //System.out.println();
                if(isPlacementValid(gameString,thisRugString)){
                    placeValidate = true;
                    newValidRUgString = thisRugString;
                }

            });
        }
    }

    /**
     * Create a Rectangle and Button for placing a rug on the game board.
     * The method takes parameters for the position (x and y), the game instance (newGame),
     * the game control instance (control), and the rug color (thisColor).
     *
     * The method performs the following steps:
     * - Create a Rectangle with width 150 and height 100. The Rectangle is filled and stroked with white color,
     *   and positioned based on the provided x and y coordinates.
     * - Create a Button labeled "place rug" and set an event handler for the button click.
     * - Inside the event handler, check if the rug placement is valid. If it is, perform the following actions:
     *   - Clear the children of virtualRug.
     *   - Create a new Rug object (newRug) based on newValidRUgString.
     *   - Update the game state and rug state based on the placement of the new rug.
     *   - Display the board and Assam based on the updated game state.
     *   - Display player information using the displayPlayerInfo method.
     *   - Set the placeValidate flag to false and reset the count to 0.
     *   - Move to the next player's turn by incrementing the index (or resetting it if it exceeds the number of players).
     *   - Start the next turn using the startNextTurn method with the updated game and control instances.
     * - If the rug placement is not valid, increment the count and call the buttonRollDiceAndMove method with the provided parameters
     *   to handle rolling the dice and moving the player's piece.
     * - Finally, add the Rectangle and Button to the controls.
     */

    public static int findNextTrueIndex(ArrayList<Boolean> list, int index) {
        int size = list.size();

        for (int i = (index + 1) % size; i != index; i = (i + 1) % size) {
            if (list.get(i)) {
                return i;
            }
        }

        for (int i = 0; i < size; i++) {
            if (list.get(i)) {
                return i;
            }
        }

        return -1;
    }
    void buttonPlaceRug(int x, int y, Marrakech newGame, GameControl control, comp1110.ass2.Color thisColor){
        Rectangle rectangle = new Rectangle(150, 100);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.WHITE);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);


        Button button_1 = new Button("place rug");
        button_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Check if the rug placement is valid
                if(placeValidate){
                    virtualRug.getChildren().clear();
                    // Create a new Rug object and update the game state and rug state
                    Rug newRug = new Rug(newValidRUgString);
                    newGame.setStateMar(newGame.makePlacement(newGame.getGameString(), newRug.toString()));
                    newGame.findAndSetRugState(newRug);
                    // Display the updated board and Assam
                    displayBoardAndAssam(newGame.makePlacement(newGame.getGameString(), newRug.toString()));
                    // Display player information
                    if(ifIsPVC == true){
                        displayPlayerInfo(control,x,y,thisColor);
                        displayPlayerInfo(control,vPX,vPY,vColor);
                    }else{
                        displayPlayerInfo(control,x,y,thisColor);
                    }

                    placeValidate = false;
                    count = 0;
                    // Move to the next player's turn
                    int order = 0;
                    if(thisColor.toString().equals(comp1110.ass2.Color.c.toString())){
                        order = 0;
                    }else if(thisColor.toString().equals(comp1110.ass2.Color.y.toString())){
                        order = 1;
                    }else if(thisColor.toString().equals(comp1110.ass2.Color.p.toString())){
                        order = 2;
                    }else if(thisColor.toString().equals(comp1110.ass2.Color.r.toString())){
                        order = 3;
                    }
                    index = findNextTrueIndex(playerState,order) + 1;

                    startNextTurn(newGame,control);
                }else{
                    // Increment the count and handle rolling the dice and moving the player's piece
                    count++;
                    buttonRollDiceAndMove(x,y,newGame,control,thisColor);

                }
            }
        });
        button_1.setLayoutX(x);
        button_1.setLayoutY(y);

        controls.getChildren().addAll(rectangle,button_1);
    }

    private int mainSimulation(Marrakech simulate, comp1110.ass2.Color personColor){
        int paymentAmount = 0;
        //rotation
        String newStateOfAssam = autoDirectionDis(simulate,personColor,5);
        simulate.setAssamByString(newStateOfAssam);
        //move
        int movement = simulate.rollDie();
        newStateOfAssam = simulate.moveAssam(simulate.getAssamString(),movement);
        simulate.setAssamByString(newStateOfAssam);

        String assamState = simulate.getAssamString();
        int assamX = Character.getNumericValue(assamState.charAt(1));
        int assamY = Character.getNumericValue(assamState.charAt(2));
        IntPair assamPosition = new IntPair(assamX, assamY);

        Square assamSquare = simulate.getBoardOOP().getSquare(assamPosition);

        if (assamSquare.isHasRug()) {

            comp1110.ass2.Color rugColor = assamSquare.getRugColor();

            if (!rugColor.equals(personColor)) {
                paymentAmount = simulate.getPaymentAmount(simulate.getGameString());

            }
        }
        return paymentAmount;

    }

    private boolean isInBoard(Rug rug){
        ArrayList<Integer> list = rug.rugGetIntPos();
        int x1 = list.get(0);
        int y1 = list.get(1);
        int x2 = list.get(2);
        int y2 = list.get(3);
        if((x1 <= 6 && x1 >= 0) && (x2 <= 6 && x2 >= 0) && (y1 <= 6 && y1 >= 0)&& (y2 <= 6 && y2 >= 0)){
            return true;
        }else{
            return false;
        }
    }

    private ArrayList<IntPair> findBestPos(Marrakech newGame){
        ArrayList<IntPair> poss = new ArrayList<>();
        int maxH_1_X = -1;
        int maxH_1_Y = -1;
        int maxV_1_X = -1;
        int maxV_1_Y = -1;

        int maxH = -1;
        int maxV = -1;
        IntPair H1;
        IntPair H2;
        IntPair V1;
        IntPair V2;
        //Horizantel rug
        ArrayList<Integer> payH = new ArrayList<>();
        ArrayList<Integer> payV = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Marrakech simulateH = new Marrakech();
                simulateH.setStateMar(newGame.getGameString());
                H1 = new IntPair(i,j);
                H2 = new IntPair(i+1,j);
                String RugS = thisRugStringAbb + H1.toString() + H2.toString();
                Rug HRug = new Rug(RugS);
                if(isInBoard(HRug)){
                    if(simulateH.isPlacementValid(simulateH.getGameString(),RugS)){
                        int sum = 0;
                        for (int k = 0; k < epoch_2; k++) {
                            simulateH.makePlacement(simulateH.getGameString(),RugS);
                            int money = mainSimulation(simulateH,rColor);
                            sum = sum + money;
                        }
                        if(maxH < sum){
                            maxH = sum;
                            maxH_1_X = i;
                            maxH_1_Y = j;
                        }
                        payH.add(sum);
                    }else{
                        payH.add(-1);
                    }
                }else{
                    payH.add(-1);
                }

                Marrakech simulateV = new Marrakech();
                simulateV.setStateMar(newGame.getGameString());
                V1 = new IntPair(i,j);
                V2 = new IntPair(i,j+1);
                String RugV = thisRugStringAbb + V1.toString() + V2.toString();
                Rug VRug = new Rug(RugV);
                if(isInBoard(VRug)){
                    if(simulateV.isPlacementValid(simulateV.getGameString(),RugV)){
                        int sum = 0;
                        for (int k = 0; k < epoch_2; k++) {
                            simulateV.makePlacement(simulateV.getGameString(),RugV);
                            int money = mainSimulation(simulateV,rColor);
                            sum = sum + money;
                        }
                        if(maxV < sum){
                            maxV = sum;
                            maxV_1_X = i;
                            maxV_1_Y = j;
                        }
                        payV.add(sum);
                    }else{
                        payV.add(-1);
                    }
                }else{
                    payV.add(-1);
                }
            }
        }
        if(maxH >= maxV){
            IntPair pos_1 = new IntPair(maxH_1_X,maxH_1_Y);
            IntPair pos_2 = new IntPair(pos_1.getX()+1,pos_1.getY());
            poss.add(pos_1);
            poss.add(pos_2);
        }else{
            IntPair pos_1 = new IntPair(maxV_1_X,maxV_1_Y);
            IntPair pos_2 = new IntPair(pos_1.getX(),pos_1.getY()+1);
            poss.add(pos_1);
            poss.add(pos_2);
        }
        return poss;
    }

    void autoPlaceRug(int x, int y, Marrakech newGame, GameControl control, comp1110.ass2.Color thisColor){
        ArrayList<IntPair> poss = findBestPos(newGame);
        String rugString = thisRugStringAbb + poss.get(0).toString() + poss.get(1).toString();
        Rug newRug = new Rug(rugString);
        newGame.setStateMar(newGame.makePlacement(newGame.getGameString(), newRug.toString()));
        newGame.findAndSetRugState(newRug);
        displayBoardAndAssam(newGame.makePlacement(newGame.getGameString(), newRug.toString()));
        displayPlayerInfo(control,x,y,thisColor);
        displayPlayerInfo(control,vPX,vPY,vColor);
        int order = 0;
        if(thisColor.toString().equals(comp1110.ass2.Color.c.toString())){
            order = 0;
        }else if(thisColor.toString().equals(comp1110.ass2.Color.y.toString())){
            order = 1;
        }else if(thisColor.toString().equals(comp1110.ass2.Color.p.toString())){
            order = 2;
        }else if(thisColor.toString().equals(comp1110.ass2.Color.r.toString())){
            order = 3;
        }
        index = findNextTrueIndex(playerState,order) + 1;
        startNextTurn(newGame,control);
    };

    /**
     * Create a Rectangle and Button for rolling the dice and moving the player's piece on the game board.
     * The method takes parameters for the position (x and y), the game instance (newGame),
     * the game control instance (control), and the rug color (thisColor).
     *
     * The method performs the following steps:
     * - Create a Rectangle with width 100 and height 100. The Rectangle is filled and stroked with white color,
     *   and positioned based on the provided x and y coordinates.
     * - Create a Button labeled "roll Dice and move" if count is 0, or "try again" otherwise.
     * - Set an event handler for the button click.
     * - Inside the event handler, check if count is 0. If it is, perform the following actions:
     *   - Roll the die using the rollDie method of newGame.
     *   - Move Assam on the game board using the moveAssam method of newGame, based on the obtained movement.
     *   - Update the Assam state in newGame with the result of the movement.
     *   - Display the updated game board and Assam state using the displayBoardAndAssam method.
     *   - Retrieve the current player based on the provided thisColor.
     *   - Get the position of Assam on the board.
     *   - Get the square where Assam is located.
     *   - Check if the square has a rug and if the rug color is different from thisColor.
     *   - If a different-colored rug is present, calculate the number of connected rugs and the payment amount.
     *   - Update the players' money based on the payment amount.
     *   - If the current player cannot pay the required amount, clear their money and set them as out of the game.
     *   - Generate a new game state in newGame.
     *   - Update the gameString variable with the new game state.
     *   - Display the player information using the displayPlayerInfo method.
     *   - Check if the current player has remaining money.
     *   - If they do, set up a draggable rug on the virtualRug element and select the first available rug of the current player's color.
     *   - Update thisRugStringAbb with the abbreviation of the selected rug.
     *   - Update the player information display using the displayPlayerInfo method.
     *   - Handle placing the rug using the buttonPlaceRug method.
     * - If count is not 0, handle placing the rug using the buttonPlaceRug method.
     * - Finally, add the Rectangle and Button to the controls element.
     */
    void buttonRollDiceAndMove(int x, int y, Marrakech newGame, GameControl control, comp1110.ass2.Color thisColor){

        Rectangle rectangle = new Rectangle(100, 100);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.WHITE);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        Button button_1 = null;
        Text message = new Text("Invalid place, please try again !");
        message.setFill(Color.RED);
        message.setLayoutX(x);
        message.setLayoutY(y-10);

        if(count == 0){
            button_1 =  new Button("roll Dice and move");
            controls.getChildren().remove(message);
        }else{
            button_1 =  new Button("try again");
            controls.getChildren().add(message);
        }


        button_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(count == 0){
                    int movement = newGame.rollDie();
                    String newStateOfAssam = newGame.moveAssam(newGame.getAssamString(),movement);
                    newGame.setAssamByString(newStateOfAssam);
                    displayBoardAndAssam(newGame.getGameString());
                    //pay money
                    Player thisPlayer = newGame.getPlayerByColor(thisColor);

                    // Get Assam's position
                    String assamState = newGame.getAssamString();
                    int assamX = Character.getNumericValue(assamState.charAt(1));
                    int assamY = Character.getNumericValue(assamState.charAt(2));
                    IntPair assamPosition = new IntPair(assamX, assamY);

                    // Get Assam's square
                    Square assamSquare = newGame.getBoardOOP().getSquare(assamPosition);

                    // Check the rugs
                    if (assamSquare.isHasRug()) {

                        comp1110.ass2.Color rugColor = assamSquare.getRugColor();
                        if (!rugColor.equals(thisColor)) {
                            // Count the number of carpets of the same color
                            // connected to the square on which Assam is located
                            int connectedRugsCount = newGame.getPaymentAmount(newGame.getGameString());

                            //
                            //System.out.println("the payment is " +connectedRugsCount);
                            // Count the payment
                            int paymentAmount = connectedRugsCount;

                            // The rug's owner
                            Player rugOwner = newGame.getPlayerByColor(rugColor);

                            // Check if the current player can pay the required amount of dirhams
                            if (thisPlayer.getMoney() >= paymentAmount) {
                                // Make the payment
                                thisPlayer.subtractMoney(paymentAmount);
                                rugOwner.addMoney(paymentAmount);

                            } else {
                                // The player cannot pay the full amount, clear out the dirhams
                                rugOwner.addMoney(thisPlayer.getMoney());
                                thisPlayer.setMoney(0);
                                // Make the player out
                                thisPlayer.setPlayerInOrOut(false);

                            }
                            //set state to the object: newGame
                            newGame.generate();
                            gameString  = newGame.getGameString();
                            if(ifIsPVC == true){
                                displayPlayerInfo(control,x,y,thisColor);
                                displayPlayerInfo(control,vPX,vPY,vColor);
                            }else{
                                displayPlayerInfo(control,x,y,thisColor);
                            }
                        }
                    }

                    if(thisPlayer.getMoney() > 0){
                        //drag and place a rug
                        aVirtualRug.setFill(Color.TRANSPARENT);
                        aVirtualRug.setStroke(Color.RED);
                        DraggablePiece ps = new DraggablePiece(aVirtualRug, 400, 600);
                        virtualRug.getChildren().add(ps);
                        ArrayList<Rug> rugs = newGame.getRugsArray();
                        ArrayList<Rug> ownRugs  = new ArrayList<>(rugs.size());
                        for (Rug arug:rugs) {
                            if(arug.getColor().toString().equals(thisColor.toString())){
                                ownRugs.add(arug);
                            }
                        }
                        Rug currentRug = null;
                        for (Rug arug:ownRugs) {
                            if(!arug.hasBeenPut()){
                                currentRug = arug;
                                break;
                            }
                        }
                        thisRugStringAbb = currentRug.getAbb();
                        if(ifIsPVC == true){
                            displayPlayerInfo(control,x,y,thisColor);
                            displayPlayerInfo(control,vPX,vPY,vColor);

                        }else{
                            displayPlayerInfo(control,x,y,thisColor);
                        }
                        buttonPlaceRug(x,y,newGame,control,thisColor);

                    }else{
                        thisPlayer.setPlayerInOrOut(false);
                        //c y p r
                        int order = 0;
                        if(thisColor.toString().equals(comp1110.ass2.Color.c.toString())){
                            playerState.set(0,false);
                            order = 0;
                        }else if(thisColor.toString().equals(comp1110.ass2.Color.y.toString())){
                            playerState.set(1,false);
                            order = 1;
                        }else if(thisColor.toString().equals(comp1110.ass2.Color.p.toString())){
                            playerState.set(2,false);
                            order = 2;
                        }else if(thisColor.toString().equals(comp1110.ass2.Color.r.toString())){
                            playerState.set(3,false);
                            order = 3;
                        }
                        index = findNextTrueIndex(playerState,order) + 1;
                        startNextTurn(newGame,control);
                    }
                }else{
                    buttonPlaceRug(x,y,newGame,control,thisColor);
                }
            }
        });
        button_1.setLayoutX(x);
        button_1.setLayoutY(y);

        controls.getChildren().addAll(rectangle,button_1);
    }


    void autoRollDiceAndMove (int x, int y, Marrakech newGame, GameControl control, comp1110.ass2.Color thisColor){
        int movement = newGame.rollDie();
        String newStateOfAssam = newGame.moveAssam(newGame.getAssamString(),movement);
        newGame.setAssamByString(newStateOfAssam);
        displayBoardAndAssam(newGame.getGameString());

        Player thisPlayer = newGame.getPlayerByColor(thisColor);

        String assamState = newGame.getAssamString();
        int assamX = Character.getNumericValue(assamState.charAt(1));
        int assamY = Character.getNumericValue(assamState.charAt(2));
        IntPair assamPosition = new IntPair(assamX, assamY);

        Square assamSquare = newGame.getBoardOOP().getSquare(assamPosition);

        if (assamSquare.isHasRug()) {

            comp1110.ass2.Color rugColor = assamSquare.getRugColor();

            if (!rugColor.equals(thisColor)) {
                int paymentAmount = newGame.getPaymentAmount(newGame.getGameString());


                Player rugOwner = newGame.getPlayerByColor(rugColor);

                if (thisPlayer.getMoney() >= paymentAmount) {
                    thisPlayer.subtractMoney(paymentAmount);
                    rugOwner.addMoney(paymentAmount);

                } else {
                    rugOwner.addMoney(thisPlayer.getMoney());
                    thisPlayer.setMoney(0);
                    thisPlayer.setPlayerInOrOut(false);
                }
                newGame.generate();
                gameString  = newGame.getGameString();

                displayPlayerInfo(control,x,y,thisColor);
                displayPlayerInfo(control,rPX,rPy,rColor);

            }
        }

        if(thisPlayer.getMoney() > 0){
            System.out.println("yes");
            ArrayList<Rug> rugs = newGame.getRugsArray();
            ArrayList<Rug> ownRugs  = new ArrayList<>(rugs.size());
            for (Rug arug:rugs) {
                if(arug.getColor().toString().equals(thisColor.toString())){
                    ownRugs.add(arug);
                }
            }
            Rug currentRug = null;
            for (Rug arug:ownRugs) {
                if(!arug.hasBeenPut()){
                    currentRug = arug;
                    break;
                }
            }
            thisRugStringAbb = currentRug.getAbb();

            displayPlayerInfo(control,x,y,thisColor);
            displayPlayerInfo(control,rPX,rPy,rColor);

            System.out.println(thisRugStringAbb);
            //go to next auto state
            autoPlaceRug(x, y, newGame, control, thisColor);
        }else{
            //if virtual player dose not have left money, go to the state of the real player directly.
            //according to the rule, it is impossible that playerState of the real player become false.
            //otherwise, virtual player dose not need to pay money
            thisPlayer.setPlayerInOrOut(false);
            playerState.set(1,false);
            index = 1;
            startNextTurn(newGame,control);
        }
    }

    /**
     * Create three buttons for rotating the game board's Assam piece.
     * The method takes parameters for the position (x and y), the game instance (newGame),
     * the game control instance (control), and the rug color (thisColor).
     *
     * The method performs the following steps:
     * - Display the game board and Assam state using the displayBoardAndAssam method.
     * - Create a "rotate left" button and set an event handler for its click event.
     *   Inside the event handler:
     *   - Rotate Assam left by 270 degrees using the rotateAssam method of newGame.
     *   - Update the Assam state in newGame with the new rotated state.
     *   - Display the updated game board and Assam state using the displayBoardAndAssam method.
     *   - Call the buttonRollDiceAndMove method with the provided parameters to handle rolling the dice and moving the player's piece.
     * - Create a "rotate right" button and set an event handler for its click event.
     *   Inside the event handler:
     *   - Rotate Assam right by 90 degrees using the rotateAssam method of newGame.
     *   - Update the Assam state in newGame with the new rotated state.
     *   - Display the updated game board and Assam state using the displayBoardAndAssam method.
     *   - Call the buttonRollDiceAndMove method with the provided parameters to handle rolling the dice and moving the player's piece.
     * - Create a "Do not rotate" button and set an event handler for its click event.
     *   Inside the event handler, call the buttonRollDiceAndMove method with the provided parameters to handle rolling the dice and moving the player's piece.
     * - Create a VBox container and add the buttons to it.
     * - Set the spacing between the buttons in the VBox.
     * - Set the layout position of the VBox based on the provided x and y coordinates.
     * - Add the VBox to the controls element.
     */
    void buttonsRotate (int x, int y, Marrakech newGame, GameControl control, comp1110.ass2.Color thisColor){
        //System.out.println(newGame.getAssamOOP().getDirection().toString());
        displayBoardAndAssam(newGame.getGameString());
        Button button_1 = new Button("rotate left");
        button_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String newStateOfAssam = newGame.rotateAssam(newGame.getAssamString(),270);
                newGame.setAssamByString(newStateOfAssam);
                displayBoardAndAssam(newGame.getGameString());
                buttonRollDiceAndMove(x,y,newGame,control,thisColor);
            }
        });

        Button button_2 = new Button("rotate right");
        button_2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String newStateOfAssam = newGame.rotateAssam(newGame.getAssamString(),90);
                newGame.setAssamByString(newStateOfAssam);
                displayBoardAndAssam(newGame.getGameString());
                buttonRollDiceAndMove(x,y,newGame,control,thisColor);
            }
        });

        Button button_3 = new Button("Do not rotate");
        button_3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                buttonRollDiceAndMove(x,y,newGame,control,thisColor);
            }
        });

        VBox b12 = new VBox();
        b12.getChildren().addAll(button_1,button_2,button_3);
        b12.setSpacing(1);
        b12.setLayoutX(x);
        b12.setLayoutY(y);
        controls.getChildren().add(b12);
    }

    private int predictResult(Marrakech simulate,comp1110.ass2.Color thisColor){
        int paymentAmount = 0;
        int movement = simulate.rollDie();
        String newStateOfAssam = simulate.moveAssam(simulate.getAssamString(),movement);
        simulate.setAssamByString(newStateOfAssam);

        // Get Assam's position
        String assamState = simulate.getAssamString();
        int assamX = Character.getNumericValue(assamState.charAt(1));
        int assamY = Character.getNumericValue(assamState.charAt(2));
        IntPair assamPosition = new IntPair(assamX, assamY);

        // Get Assam's square
        Square assamSquare = simulate.getBoardOOP().getSquare(assamPosition);

        // Check the rugs
        if (assamSquare.isHasRug()) {
            comp1110.ass2.Color rugColor = assamSquare.getRugColor();
            if (!rugColor.equals(thisColor)) {
                paymentAmount = simulate.getPaymentAmount(simulate.getGameString());
            }
        }
        return paymentAmount;
    }
    private int findMinimum(int a, int b, int c) {
        int min = a;
        if (b < min) {
            min = b;
        }
        if (c < min) {
            min = c;
        }
        return min;
    }
    private String autoDirectionDis(Marrakech newGame,comp1110.ass2.Color thisColor,int epoch){
        // Count, calculate the total amount of money the merchant needs to pay after the predicted movement.
        // Simulate 1000 times in each direction to find the direction with the minimum total payment.
        // Check if payment is required before using the getPaymentAmount method.
        // This is just a prediction, does not modify the member variables (does not call the set method of newGame).
        // No rotation.
        Marrakech simulate = new Marrakech();
        simulate.setStateMar(newGame.getGameString());
        String assamString = simulate.getAssamString();
        //rotate right
        Marrakech simulateRight = new Marrakech();
        simulateRight.setStateMar(newGame.getGameString());
        String rightAssam = simulateRight.rotateAssam(assamString,90);
        simulateRight.setAssamByString(rightAssam);
        //rotate left
        Marrakech simulateLeft = new Marrakech();
        simulateLeft.setStateMar(newGame.getGameString());
        String leftAssam = simulateLeft.rotateAssam(assamString,270);
        simulateLeft.setAssamByString(leftAssam);
        //
        int sumRight = 0;
        int sumLeft = 0;
        int sumNon = 0;

        for (int i = 0; i < epoch; i++) {
            int resultN = predictResult(simulate,thisColor);
            sumNon = sumNon + resultN;
            int resultR = predictResult(simulateRight,thisColor);
            sumRight = sumRight + resultR;
            int resultL = predictResult(simulateLeft,thisColor);
            sumLeft = sumLeft + resultL;

        }
        int resultNum = findMinimum(sumRight,sumLeft,sumNon);

        if(resultNum == sumRight){
            return rightAssam;
        } else if (resultNum == sumNon) {
            return assamString;
        } else if (resultNum == sumLeft) {
            return leftAssam;
        }
        throw new IllegalStateException();
    }
    void autoRotate (int x, int y,Marrakech newGame, GameControl control, comp1110.ass2.Color thisColor){
        //System.out.println(newGame.getAssamOOP().getDirection().toString());
        displayBoardAndAssam(newGame.getGameString());
        //autoDirectionDis(newGame,thisColor);
        String newStateOfAssam = autoDirectionDis(newGame,thisColor,1000);
        newGame.setAssamByString(newStateOfAssam);
        displayBoardAndAssam(newGame.getGameString());
        autoRollDiceAndMove(x,y,newGame,control,thisColor);
    }

    /**
     * Display the player information on the game board.
     * The method takes parameters for the game control instance (control), the position (x and y),
     * and the rug color (thisColor).
     *
     * The method performs the following steps:
     * - Clear the controls element.
     * - Populate the game board using the populateBoard method of the control instance, passing the gameString.
     * - Get the index of the player based on the provided thisColor using the getIndexByColor method of control.
     * - Create a rectangle using the rectangleCanCover method with width 150 and height 100, positioned based on the provided x and y coordinates shifted upwards by 100.
     * - Create a label for displaying the player information.
     * - Set the maximum size of the label to be 150x100.
     * - Set the layout position of the label based on the provided x and y coordinates shifted upwards by 100.
     * - Check if the player is still in the game by comparing the value in the ifPlayerStillInGame list at the retrieved index.
     *   - If the value is "i", set ifInGameC to true.
     *   - If the value is "o", set ifInGameC to false.
     * - Set the text of the label to display the player's name, left dirhams, remaining rugs, and whether the player is still in the game.
     * - Add the label to the controls element.
     *
     */
    void displayPlayerInfo(GameControl control, int x, int y, comp1110.ass2.Color thisColor){
        //controls.getChildren().clear();
        control.populateBoard(gameString);
        int index = control.getIndexByColor(thisColor);
        //
        //System.out.println(control.getPlayerLeftRugs());
        rectangleCanCover(150,100,x,y-100);
        Label newLabel = new Label();
        newLabel.setMaxSize(150,100);
        newLabel.setLayoutX(x);
        newLabel.setLayoutY(y-100);

        boolean ifInGameC = false;
        String ifInGame = "";
        if(control.getIfPlayerStillInGame().get(index).equals("i")){
            ifInGameC = true;
            ifInGame = "in";
        } else if (control.getIfPlayerStillInGame().get(index).equals("o")) {
            ifInGameC = false;
            ifInGame = "out";
        }
        newLabel.setText("Name : " + control.getPlayerNames().get(index) + " player " + "\n" +
                "Left Dirhams : " + control.getPlayerDirhams().get(index) +"\n" +
                "Remaining rugs : " + control.getPlayerLeftRugs().get(index) + "\n" +
                "Player " + control.getPlayerNames().get(index) +" game state: " + ifInGame);
        controls.getChildren().add(newLabel);
    }

    /**
     * Start the next turn of the game.
     * The method takes parameters for the newGame instance (Marrakech), and the control instance (GameControl).
     *
     * The method performs the following steps:
     * - Clear the controls and board elements.
     * - Check if the game is not over by calling the isGameOver method of newGame with the gameString.
     *   - If the game is not over, proceed with the next steps based on the index.
     *     -for example,
     *     -If the index is 1:
     *       - Set the x and y coordinates for player C.
     *       - Set the playerPathC to "comp1110/ass2/gui/picture/cPlayer.png".
     *       - Call the drawPictureOutside method with playerPathC, x, y - 150, and Tile_Size / 2 as parameters.
     *       - Call the displayPlayerInfo method with the provided parameters for player C.
     *       - Call the buttonsRotate method with the provided parameters for player C.
     * - If the game is over:
     *   - Determine the winner by calling the getWinner method of newGame with the gameString.
     *   - Convert the winnerColor character to the corresponding comp1110.ass2.Color.
     *   - Retrieve the player instance of the winner.
     *   - Get the score of the winner.
     *   - Set the x and y coordinates for displaying the winner information.
     *   - Set the playerPath based on the winner's color.
     *   - Call the drawPictureOutside method with playerPath, x, y - 150, and Tile_Size as parameters.
     *   - Create a new label for displaying the winner and score information.
     *   - Set the layout position of the label based on the provided x and y coordinates.
     *   - Set the text of the label to display the winner and score.
     *   - Add the label to the controls element.
     *
     */
    void startNextTurn(Marrakech newGame, GameControl control) {
        controls.getChildren().clear();
        board.getChildren().clear();
        if (!newGame.isGameOver(newGame.getGameString())) {
            if(ifIsPVC == true){
                int x = 1000;
                int y = 150;
                rPX = x;
                rPy = y;
                rColor = comp1110.ass2.Color.c;
                String playerPathC = "comp1110/ass2/gui/picture/cPlayer.png";
                drawPictureOutside(playerPathC, x, y - 150, Tile_Size / 2);
                displayPlayerInfo(control, x, y, comp1110.ass2.Color.c);

                int x2 = 800;
                int y2 = 150;
                vPX = x2;
                vPY = y2;
                vColor = comp1110.ass2.Color.y;
                String playerPathB = "comp1110/ass2/gui/picture/yPlayer.png";
                drawPictureOutside(playerPathB, x2, y2 - 150, Tile_Size / 2);
                displayPlayerInfo(control, x2, y2, comp1110.ass2.Color.y);

                if (index == 1) {
                    buttonsRotate(x, y, newGame, control, comp1110.ass2.Color.c);
                } else if (index == 2) {
                    autoRotate(x2,y2,newGame, control, comp1110.ass2.Color.y);
                }

            }else{
                if (index == 1) {
                    int x = 1000;
                    int y = 150;
                    String playerPathC = "comp1110/ass2/gui/picture/cPlayer.png";
                    drawPictureOutside(playerPathC, x, y - 150, Tile_Size / 2);
                    displayPlayerInfo(control, x, y, comp1110.ass2.Color.c);
                    buttonsRotate(x, y, newGame, control, comp1110.ass2.Color.c);
                } else if (index == 2) {
                    int x = 800;
                    int y = 150;
                    String playerPathB = "comp1110/ass2/gui/picture/yPlayer.png";
                    drawPictureOutside(playerPathB, x, y - 150, Tile_Size / 2);
                    displayPlayerInfo(control, x, y, comp1110.ass2.Color.y);
                    buttonsRotate(x, y, newGame, control, comp1110.ass2.Color.y);
                } else if (index == 3) {
                    int x = 1000;
                    int y = 410;
                    String playerPathP = "comp1110/ass2/gui/picture/pPlayer.png";
                    drawPictureOutside(playerPathP, x, y - 150, Tile_Size / 2);
                    displayPlayerInfo(control, x, y, comp1110.ass2.Color.p);
                    buttonsRotate(x, y, newGame, control, comp1110.ass2.Color.p);
                } else if (index == 4) {
                    int x = 800;
                    int y = 410;
                    String playerPathR = "comp1110/ass2/gui/picture/rPlayer.png";
                    drawPictureOutside(playerPathR, x, y - 150, Tile_Size / 2);
                    displayPlayerInfo(control, x, y, comp1110.ass2.Color.r);
                    buttonsRotate(x, y, newGame, control, comp1110.ass2.Color.r);
                }
            }

        } else {
            //find the winner, display the winner and score
            char winnerColor = getWinner(newGame.getGameString());
            comp1110.ass2.Color winner = null;
            boolean isTie = false;
            int x = 600;
            int y = 350;

            switch (winnerColor) {
                case 'c' -> winner = comp1110.ass2.Color.c;
                case 'y' -> winner = comp1110.ass2.Color.y;
                case 'n' -> winner = comp1110.ass2.Color.n;
                case 'p' -> winner = comp1110.ass2.Color.p;
                case 't' -> isTie = true;
            }

            if(!isTie) {
                Player win = newGame.getPlayerByColor(winner);
                int score = win.getMoney() + newGame.getBoardOOP().countColor(win.getColor());

                String playerPath = " ";

                if (winner.equals(comp1110.ass2.Color.c)) {
                    playerPath = "comp1110/ass2/gui/picture/cPlayer.png";

                } else if (winner.equals(comp1110.ass2.Color.y)) {
                    playerPath = "comp1110/ass2/gui/picture/yPlayer.png";

                } else if (winner.equals(comp1110.ass2.Color.r)) {
                    playerPath = "comp1110/ass2/gui/picture/rPlayer.png";

                } else if (winner.equals(comp1110.ass2.Color.p)) {
                    playerPath = "comp1110/ass2/gui/picture/pPlayer.png";
                }


                drawPictureOutside(playerPath, x, y - 150, Tile_Size);
                Label newLabel = new Label();

                newLabel.setLayoutX(x);
                newLabel.setLayoutY(y);
                newLabel.setText("Winner is player " + winnerColor + " !\n" + " The score is " + score + " !");
                controls.getChildren().add(newLabel);
            }else {
                Label newLabel = new Label();
                newLabel.setLayoutX(x);
                newLabel.setLayoutY(y);
                newLabel.setText("Tie! Don't have winner!");
                controls.getChildren().add(newLabel);
            }

        }
    }

    /**
     * Display the board and the position of the Assam based on the provided game state.
     *
     * The method takes a 'state' parameter, representing the game state.
     *
     * The method performs the following steps:
     * - Assign the 'state' parameter to the 'gameString' variable.
     * - Create a rectangle 'boardBack' to represent the yellow background of the board.
     *   - Set the position and size of 'boardBack' based on the board dimensions and 'BOARD_BORDER' constant.
     *   - Set the fill color to '#FF8C00' (yellow).
     *   - Set the arc height and width to 30.0d for rounded corners.
     *   - Add 'boardBack' to the 'board' group.
     * - Create grey rectangles 'tileShadow' to indicate the tile positions on the board.
     *   - Use nested loops to iterate over the board positions.
     *   - Set the position and size of 'tileShadow' based on the tile size, 'START_X', 'START_Y', and loop indices.
     *   - Set the fill color to transparent, stroke width to 10, stroke color to grey, and opacity to 0.5.
     *   - Add 'tileShadow' to the 'board' group.
     * - Create an instance of 'Marrakech' class as 'newGame'.
     * - Create an instance of 'GameControl' class as 'control' with 'newGame' as a parameter.
     * - Call the 'populateBoard' method of 'control' with the 'state' parameter to populate the board.
     * - Get the current state of the board as 'newBoard' using the 'getBoard' method of 'control'.
     * - Initialize 'posAssX' and 'posAssY' variables to 0.
     * - Iterate over the board positions using nested loops.
     *   - Set the current position in the 'pos' variable.
     *   - Based on the color of the square at the position:
     *     - Set the 'rugPath' string with the corresponding image path.
     *     - Call the 'drawPictureRotate' method with 'rugPath', 'START_X', 'START_Y', 'i', 'j', 'Tile_Size', and 0 as parameters to display the rug at the position with no rotation.
     *   - If the square at the position has the Assam:
     *     - Update 'posAssX' and 'posAssY' with the current position.
     * - Set the 'imagePath' variable with the image path of the Assam.
     * - Determine the 'angle' based on the orientation of the Assam obtained from the 'getAssamOrient' method of 'control'.
     * - Call the 'drawPictureRotate' method with 'imagePath', 'START_X', 'START_Y', 'posAssX', 'posAssY', 'Tile_Size', and 'angle' as parameters to display the Assam at its position with the appropriate rotation.
     */
    void displayBoardAndAssam(String state){
        gameString = state;
        double boardWidth = GameControl.BOARD_WIDTH*Tile_Size;
        double boardHeight = GameControl.BOARD_HEIGHT*Tile_Size;
        //_________________________________________________________________
        // creating rectangle to represent the yellow background of the board
        Rectangle boardBack = new Rectangle(
                START_X-BOARD_BORDER,
                START_Y-BOARD_BORDER,
                boardWidth+(2*BOARD_BORDER),
                boardHeight+(2*BOARD_BORDER));
        boardBack.setFill(Color.web("#FF8C00"));
        boardBack.setArcHeight(30.0d);
        boardBack.setArcWidth(30.0d);
        // adding the rectangle to the board group
        board.getChildren().add(boardBack);

        // creating the grey rectangles to indicate where the tiles are
        for (int x = 0; x < GameControl.BOARD_WIDTH; x++) {
            for (int y = 0; y < GameControl.BOARD_HEIGHT; y++) {
                Rectangle tileShadow = new Rectangle(
                        START_X+(x*Tile_Size),
                        START_Y+(y*Tile_Size),
                        Tile_Size,
                        Tile_Size);

                tileShadow.setFill(Color.TRANSPARENT);
                tileShadow.setStrokeWidth(10);
                tileShadow.setStroke(Color.GREY);
                tileShadow.setOpacity(0.5);
                board.getChildren().add(tileShadow);
            }
        }

        Marrakech newGame = new Marrakech();
        GameControl control = new GameControl(newGame);
        control.populateBoard(state);
        Board newBoard = control.getBoard();

        int posAssX = 0;
        int posAssY = 0;
        IntPair pos = new IntPair(-1,-1);
        //Show the rug and record the assam coordinates
        for (int i = 0; i < GameControl.BOARD_WIDTH; i++) {
            for (int j = 0; j < GameControl.BOARD_HEIGHT; j++) {
                pos.setPos(i,j);
                if(newBoard.getSquare(pos).getColor().equals(comp1110.ass2.Color.c)){
                    String rugPath = "comp1110/ass2/gui/picture/c.png";
                    drawPictureRotate(rugPath,START_X,START_Y,i,j,Tile_Size,0);
                }else if(newBoard.getSquare(pos).getColor().equals(comp1110.ass2.Color.y)){
                    String rugPath = "comp1110/ass2/gui/picture/y.png";
                    drawPictureRotate(rugPath,START_X,START_Y,i,j,Tile_Size,0);
                }else if(newBoard.getSquare(pos).getColor().equals(comp1110.ass2.Color.r)){
                    String rugPath = "comp1110/ass2/gui/picture/r.png";
                    drawPictureRotate(rugPath,START_X,START_Y,i,j,Tile_Size,0);
                }else if(newBoard.getSquare(pos).getColor().equals(comp1110.ass2.Color.p)){
                    String rugPath = "comp1110/ass2/gui/picture/p.png";
                    drawPictureRotate(rugPath,START_X,START_Y,i,j,Tile_Size,0);
                }

                if(newBoard.getSquare(pos).isHasAssam()){
                    //System.out.println("yes, it is " + i + " " + j);
                    posAssX = i;
                    posAssY = j;
                }
            }
        }

        String imagePath = "comp1110/ass2/gui/picture/assam.png";

        int angle = 0;
        Direction orient = control.getAssamOrient();
        if(orient.equals(comp1110.ass2.Direction.S)){
            angle = 0;
        } else if (orient.equals(comp1110.ass2.Direction.W)) {
            angle = 90;
        } else if (orient.equals(comp1110.ass2.Direction.N)) {
            angle = 180;
        } else if (orient.equals(comp1110.ass2.Direction.E)) {
            angle = 270;
        }
        drawPictureRotate(imagePath,START_X,START_Y,posAssX,posAssY,Tile_Size,angle);
    }

    /**
     * Display the game state by showing the board, player information, and initiating the next turn.
     *
     * The method takes 'newGame' and 'control' as parameters, representing an instance of the 'Marrakech' class and the 'GameControl' class, respectively.
     *
     * The method performs the following steps:
     * - Define 'p1', 'p2', 'p3', and 'p4' variables representing the four players in the game, based on the 'getPlayerS' method of 'newGame'.
     * - Based on the number of players:
     *   - If there are two players:
     *     - Set the 'playerInOrOut' attribute of 'p1' and 'p2' to true.
     *   - If there are three players:
     *     - Set the 'playerInOrOut' attribute of 'p1', 'p2', and 'p3' to true.
     *   - If there are four players:
     *     - Set the 'playerInOrOut' attribute of 'p1', 'p2', 'p3', and 'p4' to true.
     * - Call the 'displayBoardAndAssam' method with the 'gameString' attribute of 'newGame' to display the board and the position of the Assam.
     * - Call the 'populateBoard' method of 'control' with the 'gameString' attribute of 'newGame' to populate the board based on the game state.
     * - Call the 'startNextTurn' method with 'newGame' and 'control' to initiate the next turn.
     */
    void displayState(Marrakech newGame, GameControl control) {
        //Define the player string in the order of c y p r
        Player p1 = newGame.getPlayerS().get(0);
        Player p2 = newGame.getPlayerS().get(1);
        Player p3 = newGame.getPlayerS().get(2);
        Player p4 = newGame.getPlayerS().get(3);
        playerState = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerState.add(false);
        }
        if(players == 2){
            p1.setPlayerInOrOut(true);
            p2.setPlayerInOrOut(true);
            playerState.set(0,true);
            playerState.set(1,true);
        } else if (players == 3) {
            p1.setPlayerInOrOut(true);
            p2.setPlayerInOrOut(true);
            p3.setPlayerInOrOut(true);
            playerState.set(0,true);
            playerState.set(1,true);
            playerState.set(2,true);
        } else if (players == 4) {
            p1.setPlayerInOrOut(true);
            p2.setPlayerInOrOut(true);
            p3.setPlayerInOrOut(true);
            p4.setPlayerInOrOut(true);
            playerState.set(0,true);
            playerState.set(1,true);
            playerState.set(2,true);
            playerState.set(3,true);
        }

        displayBoardAndAssam(newGame.getGameString());

        control.populateBoard(newGame.getGameString());

        startNextTurn(newGame,control);
    }
    /**
     * Display the second screen of the game based on the provided model.
     *
     * The method takes a 'model' parameter, representing the game model.
     *
     * The method performs the following steps:
     * - If the 'model' is equal to "PVC":
     *   - No action is taken.
     * - Otherwise:
     *   - Create a new instance of the 'Marrakech' class as 'newGame'.
     *   - Create an instance of the 'GameControl' class as 'control' with 'newGame' as a parameter.
     *   - Call the 'rectangleCanCover' method with the dimensions 200, 200, 50, and 50 to display a rectangle that can cover an area on the screen.
     *   - Create three buttons:
     *     - 'button_1' with the label "Two player" and an event handler that sets the 'players' variable to 2 and calls the 'displayState' method with 'newGame' and 'control'.
     *     - 'button_2' with the label "Three player" and an event handler that sets the 'players' variable to 3 and calls the 'displayState' method with 'newGame' and 'control'.
     *     - 'button_3' with the label "Four player" and an event handler that sets the 'players' variable to 4 and calls the 'displayState' method with 'newGame' and 'control'.
     *   - Create an 'HBox' layout container 'b12' and add the buttons 'button_1', 'button_2', and 'button_3' to it.
     *   - Set the spacing of 'b12' to 10.
     *   - Set the layout position of 'b12' to (200, 300).
     *   - Add 'b12' to the 'controls' group.
     */
    private void displaySecond(String model){
        if(model.equals("PVC")){
            Marrakech newGame = new Marrakech();
            GameControl control = new GameControl(newGame);
            ifIsPVC = true;
            //rectangleCanCover(300,300,0,0);
            //controls.getChildren().clear();

            Button start = new Button("Start");
            start.setLayoutX(500);
            start.setLayoutY(200);
            start.setStyle("-fx-text-fill: blue; -fx-font-weight: 800; -fx-font-size: 45px; -fx-background-color: transparent; -fx-padding: 10px 20px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");
            start.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    players = 2;
                    displayState(newGame,control);
                }
            });
            controls.getChildren().add(start);
        }else{

            Marrakech newGame = new Marrakech();
            GameControl control = new GameControl(newGame);
            //rectangleCanCover(300,300,0,0);


            Button button_1 = new Button("Two player");
            button_1.setStyle("-fx-text-fill: blue; -fx-font-weight: 800; -fx-font-size: 45px; -fx-background-color: transparent; -fx-padding: 10px 20px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");
            button_1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    players = 2;
                    displayState(newGame,control);
                }
            });

            Button button_2 = new Button("Three player");
            button_2.setStyle("-fx-text-fill: blue; -fx-font-weight: 800; -fx-font-size: 45px; -fx-background-color: transparent; -fx-padding: 10px 20px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");
            button_2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    players = 3;
                    displayState(newGame,control);
                }
            });

            Button button_3 = new Button("Four player");
            button_3.setStyle("-fx-text-fill: blue; -fx-font-weight: 800; -fx-font-size: 45px; -fx-background-color: transparent; -fx-padding: 10px 20px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");
            button_3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    players = 4;
                    displayState(newGame,control);
                }
            });

            VBox b12 = new VBox();
            b12.getChildren().addAll(button_1,button_2,button_3);
            b12.setSpacing(15);
            b12.setLayoutX(450);
            b12.setLayoutY(150);
            b12.setAlignment(Pos.CENTER);
            controls.getChildren().add(b12);
        }

    }

    /**
     * Create and set up the controls for the game.
     *
     * The method performs the following steps:
     * - Create a 'Button' 'button_1' with the label "PVP" and an event handler that calls the 'displaySecond' method with the argument "PVP".
     * - Create a 'Button' 'button_2' with the label "PVC" and an event handler that calls the 'displaySecond' method with the argument "PVC".
     * - Create an 'HBox' layout container 'b12' and add the buttons 'button_1' and 'button_2' to it.
     * - Set the spacing of 'b12' to 10.
     * - Set the layout position of 'b12' to (50, 50).
     * - Add 'b12' to the 'controls' group.
     */
    private void makeControls() {

        Button button_1 = new Button("pvp");
        Button button_2 = new Button("pvc");


        // Apply CSS styles to the button text
        button_1.setStyle("-fx-text-fill: blue; -fx-font-weight: 800; -fx-font-size: 45px; -fx-background-color: transparent; -fx-padding: 10px 20px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");
        button_2.setStyle("-fx-text-fill: blue; -fx-font-weight: 800; -fx-font-size: 45px; -fx-background-color: transparent; -fx-padding: 10px 20px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");


        HBox b12 = new HBox();
        b12.getChildren().addAll(button_1,button_2);
        b12.setSpacing(60);
        b12.setLayoutX(430);
        b12.setLayoutY(250);


        // Add back image
        Image image = new Image("comp1110/ass2/gui/picture/back.png"); // Replace "path/to/your/image.jpg" with the actual path to your image file
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1200); // Adjust the width of the image view based on your requirements
        imageView.setFitHeight(700); // Adjust the height of the image view based on your requirements
        imageView.setX(0);
        imageView.setY(0);

        controls.getChildren().add(0,imageView);
        controls.getChildren().add(1,b12);
        // Store buttons in a final reference
        final Button finalButton_1 = button_1;
        final Button finalButton_2 = button_2;

        // Remove buttons after they are clicked
        button_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displaySecond("PVP");
                b12.getChildren().remove(finalButton_1);
                b12.getChildren().remove(finalButton_2);
            }
        });

        button_2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displaySecond("PVC");
                b12.getChildren().remove(finalButton_1);
                b12.getChildren().remove(finalButton_2);
            }
        });
    }


    /**
     * Set up and display the main stage of the game.
     *
     * The method takes a 'Stage' parameter named 'stage'.
     *
     * The method performs the following steps:
     * - Create a new 'Scene' named 'scene' with 'this.root' (assumed to be the root node of the scene graph), 'WINDOW_WIDTH', and 'WINDOW_HEIGHT'.
     * - Add 'board', 'virtualRug', and 'controls' nodes to 'this.root'.
     * - Call the 'makeControls' method to create and set up the controls.
     * - Set an event listener on the 'scene' for the 'OnMousePressed' event. Inside the event listener, set an event listener on the 'root' for the 'OnKeyPressed' event.
     *   - If the pressed key is 'R', rotate 'aVirtualRug' by 90 degrees.
     * - Set the 'scene' on the 'stage'.
     * - Show the 'stage'.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().add(board);
        root.getChildren().add(virtualRug);
        root.getChildren().add(controls);
        makeControls();
        scene.setOnMousePressed(event -> {
            root.setOnKeyPressed(keyEvent -> {
                // rotate if the 'r' key is pressed
                if (keyEvent.getCode().equals(KeyCode.R)) {
                    aVirtualRug.setRotate(aVirtualRug.getRotate() + 90);

                }
            });
        });

        stage.setScene(scene);
        stage.show();
    }
}

