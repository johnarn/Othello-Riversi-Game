import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reversi (a.k.a. Othello) game written in java for AI project
 * Created By Symeon-Ioannis Arnokouros A.M.: 3130015
 * Created At 2018-11-19
 */

public class Board {
    private int rowBounds = 8;
    private int columnBounds = 8;
    private String[][] board = new String[rowBounds][columnBounds];
    private static String currentplayer = "X";
    private ArrayList<Board> children = new ArrayList<>();
    private int score = 0;
    private boolean playerX = false, playerO = false; // player X or O has not valid moves

    /**
     * Values of each square
     */
    private static int[][] tileValues = {
            {20, -3, 11,  8,  8, 11, -3, 20},
            {-3, -7, -4,  1,  1, -4, -7, -3},
            {11, -4,  2,  2,  2,  2, -4, 11},
            {8,   1,  2, -3, -3,  2,  1,  8},
            {8,   1,  2, -3, -3,  2,  1,  8},
            {11, -4,  2,  2,  2,  2, -4, 11},
            {-3, -7, -4,  1,  1, -4, -7, -3},
            {20, -3, 11,  8,  8, 11, -3, 20}
    };


    /**
     * Default Constructor
     */
    public Board() {
        initializeBoard();
    }


    /**
     * Constructor which copy all the sequins from gameboard to this board
     * @param gameboard Board
     */
    public Board(Board gameboard) {
        for (int i = 0; i < rowBounds; i++) {
            for (int j = 0; j < columnBounds; j++) {
                board[i][j] = gameboard.board[i][j];
            }
        }
    }


    /**
     * Evaluation function that measures several characteristics to compute board's final score
     * -Tiles values
     * -Piece difference
     * -Frontier tiles
     * -Corner occupancy
     * -Corner closeness
     * -Mobility
     * @param player player of the game
     */
    public void evaluate(String player){

        /*
         * They are used to find the indices of the eight neighboring cells of a given cell.
         * By cycling through their values and adding them to the indices of a given cell,
         * we can obtain the indices of the 8 neighbors.
         */
        int X1[] = {-1, -1, 0, 1, 1, 1, 0, -1};
        int Y1[] = {0, 1, 1, 1, 0, -1, -1, -1};

        /*
         * Find the opponent player
         */
        String noplayer;
        if(player.equals("X")){
            noplayer = "O";
        }else{
            noplayer = "X";
        }

        /*
         * Initialize variables
         */
        int x,y;
        double d=0 , c=0 , l = 0, m = 0, f = 0, p = 0;
        int my_tiles = 0, myfronttiles = 0;
        int opp_tiles = 0, oppfronttiles = 0;

        /*
         * Count the number of tiles each player has on board
         * d: Count the square value of each tile
         */
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j].equals(player)){
                    d+= tileValues[i][j];
                    my_tiles++;
                }else if(board[i][j].equals(noplayer)){
                    d -= tileValues[i][j];
                    opp_tiles++;
                }
                if(!board[i][j].equals(" ")){
                    for(int k=0; k<8; k++){
                        x = i+X1[k];
                        y = j + Y1[k];
                        if(x >= 0 && x < 8 && y >= 0 && y < 8 && board[x][y].equals(" ")){
                            if(board[i][j].equals(player)){
                                myfronttiles++;
                            }else{
                                oppfronttiles++;
                            }
                            break;
                        }
                    }
                }
            }
        }

        /*
         * Piece difference
         * Piece difference measures how many tiles of each color are on the board
         */
        if(my_tiles > opp_tiles)
            p = (100.0 * my_tiles)/(my_tiles + opp_tiles);
        else if(my_tiles < opp_tiles)
            p = -(100.0 * opp_tiles)/(my_tiles + opp_tiles);
        else p = 0;

        /*
         * Frontier tiles
         * A tile is a front tile if it is adjacent to an empty square and so can potentially be flipped
         */
        if(myfronttiles > oppfronttiles)
            f = -(100.0 * myfronttiles)/(myfronttiles + oppfronttiles);
        else if(myfronttiles < oppfronttiles)
            f = (100.0 * oppfronttiles)/(myfronttiles + oppfronttiles);
        else f = 0;

        /*
         * Corner occupancy
         * Corner occupancy measures how many corners are owned by each player
         */
        my_tiles = 0;
        opp_tiles = 0;
        if(board[0][0].equals(player)) my_tiles++;
        else if(board[0][0].equals(noplayer)) opp_tiles++;
        if(board[0][7].equals(player)) my_tiles++;
        else if(board[0][7].equals(noplayer)) opp_tiles++;
        if(board[7][0].equals(player)) my_tiles++;
        else if(board[7][0].equals(noplayer)) opp_tiles++;
        if(board[7][7].equals(player)) my_tiles++;
        else if(board[7][7].equals(noplayer)) opp_tiles++;
        c = 25 * (my_tiles - opp_tiles);

        /*
         * Corner closeness
         * Corner closeness measures the tiles adjacent to empty corners
         */
        my_tiles = 0;
        opp_tiles = 0;
        if(board[0][0].equals(" "))   {
            if(board[0][1].equals(player)) my_tiles++;
            else if(board[0][1].equals(noplayer)) opp_tiles++;
            if(board[1][1].equals(player)) my_tiles++;
            else if(board[1][1].equals(noplayer)) opp_tiles++;
            if(board[1][0].equals(player)) my_tiles++;
            else if(board[1][0].equals(noplayer)) opp_tiles++;
        }
        if(board[0][7].equals(" "))   {
            if(board[0][6].equals(player)) my_tiles++;
            else if(board[0][6].equals(noplayer)) opp_tiles++;
            if(board[1][6].equals(player)) my_tiles++;
            else if(board[1][6].equals(noplayer)) opp_tiles++;
            if(board[1][7].equals(player)) my_tiles++;
            else if(board[1][7].equals(noplayer)) opp_tiles++;
        }
        if(board[7][0].equals(" "))   {
            if(board[7][1].equals(player)) my_tiles++;
            else if(board[7][1].equals(noplayer)) opp_tiles++;
            if(board[6][1].equals(player)) my_tiles++;
            else if(board[6][1].equals(noplayer)) opp_tiles++;
            if(board[6][0].equals(player)) my_tiles++;
            else if(board[6][0].equals(noplayer)) opp_tiles++;
        }
        if(board[7][7].equals(" "))   {
            if(board[6][7].equals(player)) my_tiles++;
            else if(board[6][7].equals(noplayer)) opp_tiles++;
            if(board[6][6].equals(player)) my_tiles++;
            else if(board[6][6].equals(noplayer)) opp_tiles++;
            if(board[7][6].equals(player)) my_tiles++;
            else if(board[7][6].equals(noplayer)) opp_tiles++;
        }
        l = -12.5 * (my_tiles - opp_tiles);

        /*
         * Mobility
         * Mobility measures how many moves each player has
         */
        int playercount = 0;
        int noplayercount = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(playerHasLegalMove(i,j,player)) playercount++;
                if(playerHasLegalMove(i,j,noplayer)) noplayercount++;
            }
        }
        my_tiles = playercount;
        opp_tiles = noplayercount;
        if(my_tiles > opp_tiles)
            m = (100.0 * my_tiles)/(my_tiles + opp_tiles);
        else if(my_tiles < opp_tiles)
            m = -(100.0 * opp_tiles)/(my_tiles + opp_tiles);
        else m = 0;

        /*
         * Final weighted score
         */
        double dscore = (10 * p) + (801.724 * c) + (382.026 * l) + (78.922 * m) + (74.396 * f) + (10 * d);

        /*
         * Score of the current board
         */
        score = (int) dscore;
    }

    /**
     * Return the arrayList of children
     * @return children
     */
    public ArrayList<Board> getChildren() {
        return children;
    }

    /**
     * Create Children and adds it to children arrayList
     */
    public void createChildren(String player) {
        for (int i = 0; i < rowBounds; i++) {
            for (int j = 0; j < columnBounds; j++) {
                if (playerHasLegalMove(i, j, player)) {
                    Board childBoard = new Board(this);
                    childBoard.updateBoard(i, j, player);
                    children.add(childBoard);
                }
            }
        }
    }

    /**
     * Sets the value of the Board
     * @param value of the Board
     */
    public void setScore(int value) {
        this.score = value;
    }

    /**
     * Return value of the Board
     *
     * @return value
     */
    public int getScore() {
        return score;
    }

    /**
     * Initialization of the board
     * Four sequins will be added to the board by default
     * X is for black and O is for white
     * At squares [3,3] , [4,4] -> O
     * At squares [3,4] , [4,3] -> X
     */
    private void initializeBoard() {
        for (int i = 0; i < rowBounds; i++) {
            for (int j = 0; j < columnBounds; j++) {
                if (i == 3 && j == 3) {
                    board[i][j] = "O";
                } else if (i == 3 && j == 4) {
                    board[i][j] = "X";
                } else if (i == 4 && j == 3) {
                    board[i][j] = "X";
                } else if (i == 4 && j == 4) {
                    board[i][j] = "O";
                } else {
                    board[i][j] = " ";

                }
            }
        }
    }

    /**
     * Checks if a player has won
     * @return true if player has won
     */
    public boolean playerhasWon() {
        for (int row = 0; row < rowBounds; row++) {
            for (int col = 0; col < columnBounds; col++) {
                if (playerHasLegalMove(row, col, "X")) {
                    playerX = true;
                }
                if (playerHasLegalMove(row, col, "O")) {
                    playerO = true;
                }

            }
        }
        if (playerX && !playerO) {
            //Player O has not valid moves
            return  false;
        } else if (playerO && !playerX) {
            //Player X has not valid moves
            return false;
        } else if (!playerX && !playerO) {
            return true;
        }
        return false;
    }

    /**
     * Find if a player has Legal Move
     * @return true if current player has legal move else returns false
     */
    private boolean playerHasMove(){
        for (int row = 0; row < rowBounds; row++) {
            for (int col = 0; col < columnBounds; col++) {
                if (playerHasLegalMove(row, col, "X")) {
                    playerX = true;
                }
                if (playerHasLegalMove(row, col, "O")) {
                    playerO = true;
                }
            }
        }
        if(getCurrentplayer().equals("X")){
            return playerX;
        }
        return playerO;
    }

    /**
     * Find if move r,c is legal return true if yes and false if no
     */
    private boolean playerHasLegalMove(int r, int c, String player) {

        boolean found = false;
        boolean legal = true;
        boolean empty = false;
        boolean first = true;

        //the seat has already a sequin
        if (!board[r][c].equals(" ")) {
            return false;
        }

        //check down
        for (int row = r + 1; row < rowBounds; row++) {
            if (board[row][c].equals(" ")) {
                empty = true;
            }
            if (board[r + 1][c].equals(player) && first) {
                legal = false;
                first = false;
            }
            if (!board[row][c].equals(" ") && !board[row][c].equals(player) && !empty && legal) {
                found = true;
            }
            if (board[row][c].equals(player) && found && !empty) {
                return true;
            }
        }
        found = false;
        empty = false;
        legal = true;
        first = true;


        //check up
        for (int row = r - 1; row > -1; row--) {
            if (board[row][c].equals(" ")) {
                empty = true;
            }
            if (board[r - 1][c].equals(player) && first) {
                legal = false;
                first = false;
            }
            if (!board[row][c].equals(" ") && !board[row][c].equals(player) && !empty && legal) {
                found = true;
            }
            if (board[row][c].equals(player) && found && !empty) {
                return true;
            }
        }
        found = false;
        empty = false;
        legal = true;
        first = true;



        //check right
        for (int col = c + 1; col < columnBounds; col++) {
            if (board[r][col].equals(" ")) {
                empty = true;
            }
            if (board[r][c + 1].equals(player) && first) {
                legal = false;
                first = false;
            }
            if (!board[r][col].equals(" ") && !board[r][col].equals(player) && !empty && legal) {
                found = true;
            }
            if (board[r][col].equals(player) && found && !empty) {
                return true;
            }
        }
        found = false;
        empty = false;
        legal = true;
        first = true;


        //check left
        for (int col = c - 1; col > -1; col--) {
            if (board[r][col].equals(" ")) {
                empty = true;
            }
            if (board[r][c - 1].equals(player) && first) {
                legal = false;
                first = false;

            }
            if (!board[r][col].equals(" ") && !board[r][col].equals(player) && !empty && legal) {
                found = true;
            }
            if (board[r][col].equals(player) && found && !empty) {
                return true;
            }
        }
        found = false;
        empty = false;
        legal = true;
        first = true;



        //check up-right
        int row = r - 1;
        int col = c + 1;
        while (row > -1 && col < columnBounds) {
            if (board[row][col].equals(" ")) {
                empty = true;
            }
            if (board[r - 1][c + 1].equals(player) && first) {
                legal = false;
                first = false;
            }
            if (!board[row][col].equals(" ") && !board[row][col].equals(player) && !empty && legal) {
                found = true;
            }
            if (board[row][col].equals(player) && found && !empty) {
                return true;
            }
            row--;
            col++;
        }
        found = false;
        empty = false;
        legal = true;
        first = true;



        //check down-left
        row = r + 1;
        col = c - 1;
        while (row < rowBounds && col > -1) {
            if (board[row][col].equals(" ")) {
                empty = true;
            }
            if (board[r + 1][c - 1].equals(player) && first) {
                legal = false;
                first = false;

            }
            if (!board[row][col].equals(" ") && !board[row][col].equals(player) && !empty && legal) {
                found = true;
            }
            if (board[row][col].equals(player) && found && !empty) {
                return true;
            }
            row++;
            col--;
        }
        found = false;
        empty = false;
        legal = true;
        first = true;



        //check up-left
        row = r - 1;
        col = c - 1;
        while (row > -1 && col > -1) {
            if (board[row][col].equals(" ")) {
                empty = true;
            }
            if (board[r - 1][c - 1].equals(player) && first) {
                legal = false;
                first = false;

            }
            if (!board[row][col].equals(" ") && !board[row][col].equals(player) && !empty && legal) {
                found = true;
            }
            if (board[row][col].equals(player) && found && !empty) {
                return true;
            }
            row--;
            col--;
        }
        found = false;
        empty = false;
        legal = true;
        first = true;



        //check down-right
        row = r + 1;
        col = c + 1;
        while (row < rowBounds && col < columnBounds) {
            if (board[row][col].equals(" ")) {
                empty = true;
            }
            if (board[r + 1][c + 1].equals(player) && first) {
                legal = false;
                first = false;

            }
            if (!board[row][col].equals(" ") && !board[row][col].equals(player) && !empty && legal) {
                found = true;
            }
            if (board[row][col].equals(player) && found && !empty) {
                return true;
            }
            row++;
            col++;
        }


        return false;
    }

    /**
     * Checks all 8 directions looking for another piece of the same color as the
     * one just placed. If one is found then flip is called to change the color
     * of all the sequins between them.
     */
    private void updateBoard(int x, int y, String player) {
        int r = x;
        int c = y;
        String setColor = player;
        String oppColor = player;
        if (player.equals("X")) {
            setColor = "X";
            oppColor = "O";
        } else {
            setColor = "O";
            oppColor = "X";
        }
        this.board[r][c] = setColor;
        int row;
        int col;

        // Check down
        for (row = r + 1; row < 8 && this.board[row][c].equals(oppColor); row++) {
        }
        if (row < 8 && this.board[row][c].equals(setColor)) {
            flip("S", setColor, r, c, row, c);
        }

        // Check up
        for (row = r - 1; row >= 0 && this.board[row][c].equals(oppColor); row--) {
        }
        if (row >= 0 && this.board[row][c].equals(setColor)) {
            flip("N", setColor, r, c, row, c);
        }

        // Check right
        for (col = c + 1; col < 8 && this.board[r][col].equals(oppColor); col++) {
        }
        if (col < 8 && this.board[r][col].equals(setColor)) {
            flip("E", setColor, r, c, r, col);
        }

        // Check left
        for (col = c - 1; col >= 0 && this.board[r][col].equals(oppColor); col--) {
        }
        if (col >= 0 && this.board[r][col].equals(setColor)) {
            flip("W", setColor, r, c, r, col);
        }

        // Check down-right
        for (row = r + 1, col = c + 1; row < 8 && col < 8 && this.board[row][col].equals(oppColor); row++, col++) {
        }
        if (row < 8 && col < 8 && this.board[row][col].equals(setColor)) {
            flip("SE", setColor, r, c, row, col);
        }

        // Check down-left
        for (row = r + 1, col = c - 1; row < 8 && col >= 0 && this.board[row][col].equals(oppColor); row++, col--) {
        }
        if (row < 8 && col >= 0 && this.board[row][col].equals(setColor)) {
            flip("SW", setColor, r, c, row, col);
        }

        // Check up-right
        for (row = r - 1, col = c + 1; row >= 0 && col < 8 && this.board[row][col].equals(oppColor); row--, col++) {
        }
        if (row >= 0 && col < 8 && this.board[row][col].equals(setColor)) {
            flip("NE", setColor, r, c, row, col);
        }

        // Check up-left
        for (row = r - 1, col = c - 1; row >= 0 && col >= 0 && this.board[row][col].equals(oppColor); row--, col--) {
        }
        if (row >= 0 && col >= 0 && this.board[row][col].equals(setColor)) {
            flip("NW", setColor, r, c, row, col);
        }
    }

    /**
     * Flips all the pieces between the given coordinates to the indicated
     */
    private void flip(String direction, String setColor, int x1, int y1, int x2, int y2) {
        switch (direction) {
            case "S":
                for (int row = x1 + 1; row < x2; row++) {
                    this.board[row][y1] = setColor;
                }
                break;
            case "N":
                for (int row = x2 + 1; row < x1; row++) {
                    this.board[row][y1] = setColor;
                }
                break;
            case "E":
                for (int col = y1 + 1; col < y2; col++) {
                    this.board[x1][col] = setColor;
                }
                break;
            case "W":
                for (int col = y2 + 1; col < y1; col++) {
                    this.board[x1][col] = setColor;
                }
                break;
            case "SE":
                for (int row = x1 + 1, col = y1 + 1; row < x2 && col < y2; row++, col++) {
                    this.board[row][col] = setColor;
                }
                break;
            case "SW":
                for (int row = x1 + 1, col = y1 - 1; row < x2 && col > y2; row++, col--) {
                    this.board[row][col] = setColor;
                }
                break;
            case "NE":
                for (int row = x1 - 1, col = y1 + 1; row > x2 && col < y2; row--, col++) {
                    this.board[row][col] = setColor;
                }
                break;
            case "NW":
                for (int row = x2 + 1, col = y2 + 1; row < x1 && col < y1; row++, col++) {
                    this.board[row][col] = setColor;
                }
                break;
        }
    }


    /**
     * Get Move from keyboard input
     */
    public void getMove() {
        int x, y;
        Scanner keyboard = new Scanner(System.in);
        boolean success = false;
        do {
            System.out.println("Next Move ");
            System.out.print("Row: ");
            x = keyboard.nextInt();
            System.out.print("Column: ");
            y = keyboard.nextInt();
            System.out.println("Move: " + x + "," + y);
            if (playerHasMove() && playerHasLegalMove(x, y, currentplayer)) {
                //player has legal move
                updateBoard(x, y, currentplayer);
                success = true;
            }else if(!playerHasMove()){
                //player has not legal move
                System.out.println("Player " + currentplayer + " has not legal move so he passes!");
                success = true;
            }
            evaluate(currentplayer);
        } while (!success);

    }


    /**
     * Print current State of the Board
     */
    public void printBoard() {
        System.out.println("-----------------");
        for (int i = 0; i < rowBounds; i++) {
            System.out.print("|");
            for (int j = 0; j < columnBounds; j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    /**
     * Get currentplayer
     * @return currentplayer
     */
    public String getCurrentplayer() {
        return currentplayer;
    }

    /**
     * Set currentplayer
     */
    public void setCurrentplayer(String currentplayer) {
        this.currentplayer = currentplayer;
    }

    /**
     * Find the winner of the Game
     * @return winner
     */
    public String findWinner(){
        String winner = " ";
        int counterX = 0;
        int counterO = 0;

        for(int i=0; i< rowBounds; i++){
            for(int j=0; j<columnBounds; j++){
                if(board[i][j].equals("X")){
                    counterX++;
                }else if(board[i][j].equals("O")){
                    counterO++;
                }
            }
        }

        if(counterO > counterX){
            winner = "O";
        }else if(counterX > counterO){
            winner = "X";
        }else{
            winner = "None";
        }
        System.out.println("CounterO: "+counterO+" CounterX: "+counterX);
        return winner;
    }

}
