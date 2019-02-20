import java.util.Scanner;

/**
 * Reversi (a.k.a. Othello) game written in java for AI project
 * Created By Symeon-Ioannis Arnokouros A.M.: 3130015
 * Created At 2018-11-19
 */

public class ReversiAI {
    public static void main (String[] args){

        /*
         * Ask player about the depth of the minimax algorithm
         */
        System.out.print("What depth do you want: ");
        Scanner keyboard = new Scanner(System.in);
        int depth = keyboard.nextInt();

        /*
         * Ask human if he want to start first
         */
        System.out.print("Human play first[Y/N]: ");
        String player = keyboard.next();
        Board board = new Board();

        /*
         * Create AI instance
         */
        ComputerAI computerAI = new ComputerAI(board);
        computerAI.setDepth(depth);

        /*
         * Begin the game
         */
        while(true){
            board.printBoard();

            /*
             * Checks if a player has won and breaks
             */
            if(board.playerhasWon()){
                System.out.println("Winner is "+board.findWinner());
                break;
            }

            /*
             * Show current player
             */
            System.out.println("Player: "+board.getCurrentplayer());

            /*
             * Checks if human plays first
             */
            if(player.equals("Y")){
                //Human play
                System.out.println("Human turn");
                board.getMove();
                player = "N";   //change player for every turn, now new player will be computer
            }else if(player.equals("N")){
                //Computer play
                System.out.println("Computer turn");
                board = new Board(computerAI.minimax(board, player));
                player = "Y";   //change player for every turn, now new player will be human
            }
            /*
             * Change player every turn
             */
            if(board.getCurrentplayer().equals("X")){
                board.setCurrentplayer("O");
            }else{
                board.setCurrentplayer("X");
            }
        }
    }
}
