import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player{
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

    private static void printAvailableMoves(Othello game, ArrayList<int[]> availableMoves, char marker){
        Othello copy = new Othello(game);
        for (int[] space : availableMoves){
            copy.getBoard()[space[0]][space[1]] = marker;
        }
        System.out.println(copy.toString());
    }
}
