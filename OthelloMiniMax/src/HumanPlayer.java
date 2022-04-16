import java.util.ArrayList;
import java.util.Scanner;

//used when a person wishes to play
public class HumanPlayer extends Player{
    //marker represents the spaces where a player can place their piece
    private char marker = 'x';

    public HumanPlayer(String name){
        super(name);
    }

    public HumanPlayer(String name, char symbol) {
        super(name, symbol);
    }

    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    public void setMarker(char marker) {
        this.marker = marker;
    }

    //main method
    //reads input from the terminal, checks if it is valid and if it is a legal move, rules-wise
    //if a player input -1 for the first coordinate, then they concede
    //if there are no moves to be made the player is informed and the turn is skipped
    //the method visualises the moves the player can make with the marker
    @Override
    public int[] move(Othello game) {
        int[] move = new int[0];
        Scanner scanner = new Scanner(System.in);
        ArrayList<int[]> availableMoves = game.getValidMoves(symbol);
        if (availableMoves.isEmpty()){
            System.out.println("There are no possible moves to be made! Skipping turn...");
            move = new int[]{-1, -1};
            return move;
        }
        boolean validInput = false;
        int row;
        int col;
        System.out.println("Your available moves, marked as: " + marker);
        //visualize potential moves
        printAvailableMoves(game, availableMoves, marker);
        while (!validInput){
            System.out.println("Enter your move, row col (-1 to concede)");
            try {
                row = scanner.nextInt();
                if (row == -1) {
                    System.out.println("Game conceded");
                    move = new int[]{game.getBoard().length, game.getBoard().length};
                    return move;
                } else {
                    col = scanner.nextInt();
                    move = new int[]{row, col};
                    System.out.println(""+move[0] + move[1]);
                    if (findArray(availableMoves, move)) {
                        validInput = true;
                    } else
                        System.out.println("Invalid move!");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input");
            }
        }
        return move;
    }

    //creates a copy of the game and marks the spaces indicated in availableMoves with marker
    // as moves the player can make
    //then it prints if out
    private static void printAvailableMoves(Othello game, ArrayList<int[]> availableMoves, char marker){
        Othello copy = new Othello(game);
        for (int[] space : availableMoves){
            copy.getBoard()[space[0]][space[1]] = marker;
        }
        System.out.println(copy.toString());
    }
}
