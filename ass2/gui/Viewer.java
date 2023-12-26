package comp1110.ass2.gui;
import comp1110.ass2.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
/*
@author：group
 */
public class Viewer extends Application {
    // Constants for viewer dimensions
    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    // Root group to hold all nodes
    private final Group root = new Group();
    // Group to hold control nodes (e.g. buttons, text fields)
    private final Group controls = new Group();
    // Text field to input board details (not used in the provided code)
    private TextField boardTextField;



    void drawPictureRotate(String path,double START_X,double START_Y,int x,int y,double Tile_Size,int angle){
        Node view;
        Image image = new Image(path);
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(Tile_Size);
        imageView.setFitWidth(Tile_Size);
        imageView.setImage(image);
        view = imageView;

        view.setLayoutX(START_X + (x* Tile_Size));
        view.setLayoutY(START_Y + (y * Tile_Size));
        Rotate rotate = new Rotate();
        //Setting the angle for the rotation
        rotate.setAngle(angle);
        //Setting pivot points for the rotation
        rotate.setPivotX(Tile_Size/2);
        rotate.setPivotY(Tile_Size/2);

        view.getTransforms().addAll(rotate);
        root.getChildren().add(view);
    }



    void drawPictureOutside(String path,int x,int y,double Tile_Size){
        Node view;
        Image image = new Image(path);
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(Tile_Size);
        imageView.setFitWidth(Tile_Size);
        imageView.setImage(image);
        view = imageView;

        view.setLayoutX(x);
        view.setLayoutY(y);

        root.getChildren().add(view);
    }




    void rectangleCanCover(int size,int x,int y){
        Rectangle rectangle = new Rectangle(size, size);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        root.getChildren().addAll(rectangle);
    }


    void displayState(String state) {
        // Height and width of each tile
        double Tile_Size = 80;

        // how much the blue background extends past the tiles
        int BOARD_BORDER = 10;

        // The start of the board in the x-direction (ie: x = 0)
        double START_X = 20.0;

        // The start of the board in the y-direction (ie: y = 0)
        double START_Y = 20.0;

        Node view;

        double boardWidth = GameControl.BOARD_WIDTH*Tile_Size;
        double boardHeight = GameControl.BOARD_HEIGHT*Tile_Size;
        Group board = new Group();
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
        root.getChildren().add(board);

        Marrakech newGame = new Marrakech();
        GameControl control = new GameControl(newGame);
        control.populateBoard(state);
        Board newBoard = control.getBoard();

        int posAssX = 0;
        int posAssY = 0;
        IntPair pos = new IntPair(-1,-1);
        // Show the rug and record the assam coordinates
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

        String playerPathC = "comp1110/ass2/gui/picture/cPlayer.png";
        drawPictureOutside(playerPathC,1000,100,Tile_Size);
        rectangleCanCover(150,990,190);
        Label labelC = new Label();
        labelC.setMaxSize(200,200);
        labelC.setLayoutX(1000);
        labelC.setLayoutY(190);
        boolean ifInGameC = false;
        if(control.getIfPlayerStillInGame().get(0).equals("i")){
            ifInGameC = true;
        } else if (control.getIfPlayerStillInGame().get(0).equals("o")) {
            ifInGameC = false;
        }
        labelC.setText("Name : " + control.getPlayerNames().get(0) + " player" + "\n" +
                "Left Dirhams : " + control.getPlayerDirhams().get(0) +"\n" +
                "Remaining rugs : " + control.getPlayerLeftRugs().get(0) + "\n" +
                "Player " + control.getPlayerNames().get(0) +" in game? " + ifInGameC);
        root.getChildren().add(labelC);


        String playerPathY = "comp1110/ass2/gui/picture/yPlayer.png";
        drawPictureOutside(playerPathY,800,100,Tile_Size);
        rectangleCanCover(150,790,190);
        Label labelY = new Label();
        labelY.setMaxSize(200,200);
        labelY.setLayoutX(800);
        labelY.setLayoutY(190);
        boolean ifInGameY = false;
        if(control.getIfPlayerStillInGame().get(1).equals("i")){
            ifInGameY = true;
        } else if (control.getIfPlayerStillInGame().get(1).equals("o")) {
            ifInGameY = false;
        }
        labelY.setText("Name : " + control.getPlayerNames().get(1) + " player" + "\n" +
                "Left Dirhams : " + control.getPlayerDirhams().get(1)+"\n" +
                "Remaining rugs : " + control.getPlayerLeftRugs().get(1) + "\n" +
                "Player " + control.getPlayerNames().get(1) +" in game? " + ifInGameY);
        root.getChildren().add(labelY);

        String playerPathP = "comp1110/ass2/gui/picture/pPlayer.png";
        drawPictureOutside(playerPathP,1000,400,Tile_Size);
        rectangleCanCover(150,990,490);
        Label labelP = new Label();
        labelP.setMaxSize(200,200);
        labelP.setLayoutX(1000);
        labelP.setLayoutY(490);
        boolean ifInGameP = false;
        if(control.getIfPlayerStillInGame().get(2).equals("i")){
            ifInGameP = true;
        } else if (control.getIfPlayerStillInGame().get(2).equals("o")) {
            ifInGameP = false;
        }
        labelP.setText("Name : " + control.getPlayerNames().get(2) + " player" + "\n" +
                "Left Dirhams : " + control.getPlayerDirhams().get(2) +"\n" +
                "Remaining rugs : " + control.getPlayerLeftRugs().get(2) + "\n" +
                "Player " + control.getPlayerNames().get(2) +" in game? " + ifInGameP);
        root.getChildren().add(labelP);

        String playerPathR = "comp1110/ass2/gui/picture/rPlayer.png";
        drawPictureOutside(playerPathR,800,400,Tile_Size);
        rectangleCanCover(150,790,490);
        Label labelR = new Label();
        labelR.setMaxSize(200,200);
        labelR.setLayoutX(800);
        labelR.setLayoutY(490);
        boolean ifInGameR = false;
        if(control.getIfPlayerStillInGame().get(3).equals("i")){
            ifInGameR = true;
        } else if (control.getIfPlayerStillInGame().get(3).equals("o")) {
            ifInGameR = false;
        }
        labelR.setText("Name : " + control.getPlayerNames().get(3) + " player" + "\n" +
                "Left Dirhams : " + control.getPlayerDirhams().get(3) +"\n" +
                "Remaining rugs : " + control.getPlayerLeftRugs().get(3) + "\n" +
                "Player " + control.getPlayerNames().get(3) +" in game? " + ifInGameR);
        root.getChildren().add(labelR);


        // FIXME Task 5: implement the simple state viewer

    }


    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());

            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
