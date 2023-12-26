package comp1110.ass2;

/*
@author：<u7751035> Xi Ding
 */
public class Board {
    // Define the size of the game board
    public static final int BOARD_SIZE = 7;

    // Define the Square game board
    private Square[][] board;

    // Check whether the location has been accessed
    private boolean[][] checkIfCalled = new boolean[BOARD_SIZE][BOARD_SIZE];

    // Default constructor
    public Board() {
        this.board = new Square[BOARD_SIZE][BOARD_SIZE];
        // Here, I call the initializeBoard method below to batch add Squares
        // to make the constructor more clear
        initializeBoard();
    }

    //Iterate over each location on the game board
    //and create a new Square object for each location
    //that contains the coordinates for that location
    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Square();
            }
        }
    }

    //Updates the state of the game board based on the given board string
    public void updateBoardState(String boardString) {
        // Validate
        if (boardString == null || boardString.length() != 1+BOARD_SIZE * BOARD_SIZE * 3) {
            throw new IllegalArgumentException("Invalid board string");
        }

        for (int i = 1; i <= BOARD_SIZE; i++) {

            int start = 1+(i-1)*BOARD_SIZE*3;
            String columString = "";

            if((1+i*BOARD_SIZE*3) != boardString.length()){
                columString = boardString.substring(start,1+i*7*3);

            }else{
                columString = boardString.substring(start);
            }

            for (int j = 1; j <=BOARD_SIZE ; j++) {

                int start_r = (j-1)*3;
                String rowString = "";

                if((1+j*3) != columString.length()){
                    rowString = columString.substring(start_r,j*3);

                }else{
                    rowString = columString.substring(start_r);
                }
                board[i-1][j-1].setState(rowString);
            }
        }
    }

    // Get the Square object at the specific position on the game board
    public Square getSquare(IntPair position) {
        int x = position.getX();
        int y = position.getY();

        // Here, I add an exception to alert the illegal argument
        // to make the codes more secure
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        return board[x][y];
    }

    // Check if a given Rug is present on the board
    public Boolean RugInBoard(Rug newRug){
        String abb = newRug.getAbb();
        String boardString = getBoardStringOOP();

        if(boardString.indexOf(abb)== -1){
            return false;

        }else{
            return true;
        }
    }

    // Get the string representation of the board from OOP
    public String getBoardStringOOP() {
        String boardString = "B";

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                IntPair pos = new IntPair(i,j);
                // Use the toString measure of the object of Square.
                boardString = boardString + getSquare(pos).toString();
            }
        }
        return boardString;
    }

    // For this method, I use the DFS algorithm to find the number of squares
    // of the same color connected to squares in a given position.
    // It starts from a given starting position and recursively explores all
    // possible directions.
    // Besides, ensure that it does not visit the same square twice
    // and increases the count number when it finds a square
    // that matches the color of the search.
    public int countConnectedSquaresColor(IntPair position, Color color) {
        // Check if the given position is within
        // the boundaries of the board.
        // If not, returns 0
        if (position.getX() < 0 || position.getX() >= BOARD_SIZE
                 || position.getY() >= BOARD_SIZE || position.getY() < 0) {
            return 0;
        }

        // Check if already be called on
        // If called on, return 0
        if (checkIfCalled[position.getX()][position.getY()]) {
            return 0;
        }

        // Check if color matched
        // Not matched, then returns 0
        Square square = getSquare(position);
        if (square.getRugColor() != color) {
            return 0;
        }

        // Marked
        checkIfCalled[position.getX()][position.getY()] = true;

        int count = 1; // Current square.

        // Recursively search for adjacent squares in the
        // up, down, left, and right directions.
        count += countConnectedSquaresColor(new IntPair(position.getX(), position.getY() + 1), color);
        count += countConnectedSquaresColor(new IntPair(position.getX(), position.getY() - 1), color);
        count += countConnectedSquaresColor(new IntPair(position.getX() + 1, position.getY()), color);
        count += countConnectedSquaresColor(new IntPair(position.getX() - 1, position.getY()), color);

        // Get the result
        return count;
    }

    // Count all squares of a specific color on the board
    public int countColor(Color color) {
        int count = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].getRugColor() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    // Reset the matrix called on after counting
    public void reset() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                checkIfCalled[i][j] = false;
            }
        }
    }
}
