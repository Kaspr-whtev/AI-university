import java.util.ArrayList;

public class Othello {
    //the 8 directions, i.e. spaces that border a given space,
    //the first direction is directly above, the next ones go clockwise
    private static final int[][] directions = {
            {-1, 0},
            {-1, 1},
            {0, 1},
            {1, 1},
            {1, 0},
            {1, -1},
            {0, -1},
            {-1, -1}
    };

    //size of the board in each dimension
    private static final int size = 8;
    //character representing each player on the board, as well as an empty space
    private static final char white = 'w';
    private static final char black = 'b';
    private static final char empty = '.';
    //represents the game board: first array represents the rows, second- columns
    private final char[][] board;

    public Othello(){
        board = new char[size][size];
        newGame();
    }
    //copy constructor, used for simulating future game states
    public Othello(Othello o){
        board = new char[size][size];
        for (int r=0; r< size; r++){
            System.arraycopy(o.board[r], 0, board[r], 0, size);
        }
    }

    //check whether the given coordinates, are on the board
    protected boolean onBoard(int r, int c){
        return (r >= 0 && r < size) && (c >= 0 && c < size);
    }

    //returns the char representing the opponent of the inputted player
    protected char otherPlayer(char player){
        if (player == black)
            return white;
        else return black;
    }

    //sets the board for a new game: all spaces empty, and the middle square is occupied by the initial pieces
    private void newGame(){
        for (int n=0; n<size; n++){
            for (int m=0; m<size; m++)
                board[n][m] = empty;
        }
        int n = size/2;
        board[n][n] = white;
        board[n-1][n-1] = white;
        board[n-1][n] = black;
        board[n][n-1] = black;
    }

    //checks if a game piece of player p can be placed on coordinates r, c as per the rules
    //i.e. would it capture any of the opponents pieces
    protected boolean validMove(char p, int r, int c){
        if (this.onBoard(r,c) && board[r][c] == empty) {
            boolean res;
            for (int d = 0; d < directions.length; d++) {
                res = validInDirection(p, r, c, d);
                if (res) return true;
            }
        }
        return false;
    }
    //checks in 1 direction if a move is legal
    private boolean validInDirection(char p, int r, int c, int d){
        int row = r;
        int col = c;
        boolean enemyPiece = false;
        for (int n=0; n<size; n++){
            row += directions[d][0];
            col += directions[d][1];
            if (!onBoard(row,col))
                return false;
            if (board[row][col] == otherPlayer(p)){
                enemyPiece = true;
                continue;
            }
            else if (board[row][col] == p){
                return enemyPiece;
            }
            return false;
        }
        return false;
    }

    //returns an arraylist containing every move player p can make
    //a move is represented as a 2-element array with coordinates where a piece can be placed
    protected ArrayList<int[]> getValidMoves(char p){
        ArrayList<int[]> moves = new ArrayList();
        for (int r=0; r<size; r++){
            for (int c=0; c<size; c++){
                if (validMove(p, r, c)){
                    int[] coordinates = {r,c};
                    moves.add(coordinates);
                }
            }
        }
        return moves;
    }

    //makes a player p place their piece on the coordinates r and c,
    // capturing opponent's pieces (where applicable)
    protected void makeMove(char p, int r, int c){
        if (validMove(p, r, c)){
            board[r][c] = p;
            for (int d=0; d< directions.length; d++){
                int row = r;
                int col = c;
                if (validInDirection(p, r, c, d)){
                    row += directions[d][0];
                    col += directions[d][1];
                    while (board[row][col] != p){
                        board[row][col] = p;
                        row += directions[d][0];
                        col += directions[d][1];
                    }
                }
            }
        }
    }

    //checks if there is at least 1 move a player p can make (true if yes, false if no)
    private boolean anyMove(char p){
        boolean check;
        for (int r=0; r<size; r++){
            for (int c=0; c<size; c++){
                check = validMove(p, r, c);
                if (check)
                    return true;
            }
        }
        return false;
    }


    //calculates the number of occurrences of a given symbol on the game board
    //used to calculate points at the end
    private int countPoints(char color){
        int count = 0;
        for (int r=0; r<size; r++){
            for (int c=0; c<size; c++){
                if (board[r][c] == color)
                    count++;
            }
        }
        return count;
    }

    //prints the game board with row and column labeled with numbers
    public String toString(){
        StringBuilder s = new StringBuilder("  ");
        for (int n=0; n<size; n++)
            s.append(n).append(" ");
        s.append("\n");
        for (int i=0; i<size; i++){
            s.append(i).append(" ");
            for (int j=0; j<size; j++)
                s.append(board[i][j]).append(" ");
            s.append("\n");
        }
        return s.toString();
    }

    public char[][] getBoard() {
        return board;
    }

    public static char getEmpty() {
        return empty;
    }

    public static int[][] getDirections() {
        return directions;
    }

    //plays a single game between players p1 and p2
    //p1 is the player controlling the black pieces (goes first)
    //allows a player to surrender (only applicable to human players)
    public void play(Player p1, Player p2){
        p1.setSymbol(black);
        p2.setSymbol(white);
        char currentTurn = black;
        while(true){
            //player (black)'s turn
            if (currentTurn == black){
                System.out.println(this.toString());
                System.out.println("Black's turn");
                //get the coordinates of a move
                int[] move = p1.move(this);
                //if the coordinates are equal to the size of the board, so out of bounds, the player (black) concedes
                // (the number is 8 by default)
                if (move[0] == board.length && move[1] == board.length){
                    System.out.println("Player 1 concedes!");
                    break;
                }
                //if a [-1,-1] is returned as the move coordinates,
                //that means that the player (black) has no possible moves
                //so the current player's turn is skipped
                if (move[0] == -1 && move[1] == -1){
                    //if the opponent has a move, then it's their turn
                    if (anyMove(white)){
                        System.out.println("White's turn");
                        currentTurn = white;
                    }
                    //if both players don't have a move, the game ends
                    else {
                        System.out.println("Game Over!");
                        System.out.println("Black Player "+ countPoints(black) + " : " + countPoints(white) + " White Player");
                        break;
                    }
                }
                //if the player (black) has a normal move, they make it
                else {
                    makeMove(black, move[0], move[1]);
//                    System.out.println(this.toString());
                    currentTurn = white;
                    continue;
                }
            }
            //player (white)'s turn
            //this follows the same logic as the turn of the other player
            else {
                System.out.println(this.toString());
                System.out.println("White's turn");
                //player (white) tries to make a move
                int[] move = p2.move(this);
                //surrender case
                if (move[0] == board.length && move[1] == board.length){
                    System.out.println("Player 2 concedes!");
                    break;
                }
                //no available moves for player (white)
                if (move[0] == -1 && move[1] == -1){
                    //pass the turn to the opponent
                    if (anyMove(black)){
                        System.out.println("Black's turn");
                        currentTurn = black;
                    }
                    //end the game if there are no moves to be made
                    else {
                        System.out.println("Game Over!");
                        System.out.println("Black Player "+ countPoints(black) + " : " + countPoints(white) + " White Player");
                        break;
                    }
                }
                //make a move if available
                else {
                    makeMove(white, move[0], move[1]);
//                    System.out.println(this.toString());
                    currentTurn = black;
                }
            }
        }
    }
}
