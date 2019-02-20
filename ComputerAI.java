import java.util.ArrayList;

/**
 * Reversi (a.k.a. Othello) game written in java for AI project
 * Created By Symeon-Ioannis Arnokouros A.M.: 3130015
 * Created At 2018-11-19
 */

public class ComputerAI {
    private int depth;
    private Board aiboard;
    private int min, max;
    private String player;
    private String noplayer;
    private int alpha = Integer.MIN_VALUE;
    private int beta = Integer.MAX_VALUE;


    /**
     * Default Constructor
     * @param board Board of AI
     */
    public ComputerAI(Board board){
        aiboard = new Board(board);
    }

    /**
     * Minimax Algorithm
     * @param board current gameboard
     * @param player Y if player is X and N if player is O
     * @return gameboard with the best move
     */
    public Board minimax(Board board, String player){
        /*
         * Find which player is
         */
        this.player = player;
        if(player.equals("Y")){
            //player is human
            //noplayer is computer
            this.player = "X";
            noplayer = "O";
        }else{
            //player is computer
            //noplayer is human
            this.player = "O";
            noplayer = "X";
        }
        board.createChildren(board.getCurrentplayer());
        ArrayList<Board> children = board.getChildren();

        /*
         * Take as value the max score of this board
         */
        int value = maxValue(board, 0);

        /*
         * return the child who has the same score as value
         */
        for(Board child: children){
            if(child.getScore() == value){
                return child;
            }
        }
        return board;
    }

    /**
     * Min value of minimax algorithm
     * @param board this board
     * @param depth of the tree
     * @return minimun score of a specific level of the tree
     */
    private int minValue(Board board,int depth){

        //if the child is terminal or we have reached the maximum depth
        if(board.playerhasWon()||depth>=this.depth){
            board.evaluate(noplayer);
            return board.getScore();
        }else{
            //create next child
            board.createChildren(noplayer);
            ArrayList<Board> children = board.getChildren();

            /*
             * When min player has not legal move pass the minimum value of int
             * so minimax will not keep in mind
             */
            if(children.size() == 0){
                return Integer.MIN_VALUE;
            }

            /*
             * Child's score will be the min score of his children
             */
            for(Board child:children){
                child.setScore(maxValue(child,depth+1));
                //update beta
                if(child.getScore() < beta) beta = child.getScore();
                if(beta <= alpha) break; //Alpha Cutoff
            }

            /*
             * Get min as the value of the first board
             * Find min score among the children
             */
            min=children.get(0).getScore();
            for(Board child:children){
                if(min>child.getScore()){
                    min=child.getScore();
                }
            }
            return min;
        }
    }

    /**
     * Max value of minimax algorithm
     * @param board this board
     * @param depth of the tree
     * @return maximum score of a specific level of the tree
     */
    private int maxValue(Board board,int depth){

        //if the child is terminal or we have reached the maximum depth
        if(board.playerhasWon()||depth>=this.depth){
            board.evaluate(player);
            return board.getScore();
        }else{
            //create next child
            board.createChildren(player);
            ArrayList<Board> children = board.getChildren();

            /*
             * When max has not legal move pass the maximum value of int
             * so minimax will not keep in mind
             */
            if(children.size() == 0){
                return Integer.MAX_VALUE;
            }

            /*
             * Child's score will be the min score of his children
             */
            for(Board child:children){
                child.setScore(minValue(child,depth+1));
                //update alpha
                if(child.getScore() > alpha) alpha = child.getScore();
                if(beta <= alpha) break; //Beta Cutoff
            }

            /*
             * Get max as the value of the first board
             * Find max score among the children
             */
            max=children.get(0).getScore();
            for(Board child:children){
                if(max<child.getScore()){
                    max=child.getScore();
                }
            }
            return max;
        }

    }

    /**
     * Sets the depth of the minimax tree
     * @param depth of the tree
     */
    public void setDepth(int depth){
        this.depth = depth;
    }
}
