import java.util.ArrayList;

//AI player
public class MiniMaxPlayer extends Player{
    private int depth = 4; //the depth of the minimax search, the actual depth is 1 higher than this value
    private Heuristic heuristic = Heuristic.PieceParity; //piece parity by default

    public MiniMaxPlayer(String name) {
        super(name);
    }

    public MiniMaxPlayer(String name, char symbol) {
        super(name, symbol);
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    //the main method of the class
    //based on the game and the symbol it returns coordinates for their move
    //returns [-1,-1] if there are no moves
    @Override
    public int[] move(Othello game) {
        ArrayList<int[]> availableMoves = game.getValidMoves(symbol);
        int[] move = new int[0];
        if (availableMoves.isEmpty()){
            System.out.println("There are no possible moves to be made! Skipping turn...");
            move = new int[]{-1, -1};
            return move;
        }
        //best is the index of the best move to make from availableMoves
        int best = -1;
        //utility is the value of that best move
        float utility = -1 * Float.MAX_VALUE;
        //beginning of minimax
        //the first step is performed here, hence the fact that depth is 1 higher than the variable
        for (int n=0; n< availableMoves.size(); n++){
            Othello copyGame = new Othello(game);
            copyGame.makeMove(symbol, availableMoves.get(n)[0], availableMoves.get(n)[1]);
            float val = minimax(copyGame, utility, Float.MAX_VALUE, depth, game.otherPlayer(symbol), false);
            //if a better path is found update the current best path
            if (val > utility){
                utility = val;
                best = n;
            }
        }
        return availableMoves.get(best);
    }

    //the method that chooses the best path
    //a and b are used in alpha beta pruning
    //boolean maximizingPlayer represents if we are maximizing or minimizing currently
    public float minimax(Othello game, float a, float b, int depth, char p, boolean maximizingPlayer){
        //System.out.println(game.toString());
        //get valid moves
        ArrayList<int[]> availableMoves = game.getValidMoves(p);
        //exit condition, either we are at the end or we have no moves to make
        //returns the board evaluation
        if (depth == 0 || availableMoves.isEmpty())
            return HeuristicEval.getValue(heuristic, game, symbol);

        //if the current player is max
        if (maximizingPlayer){
            float maxEval = -1 * Float.MAX_VALUE;
            for (int[] move : availableMoves){
                //make a copy to simulate ahead
                Othello copyGame = new Othello(game);
                //make a move
                copyGame.makeMove(p,move[0], move[1]);
                //continue down that path returning the best value for the path
                float eval = minimax(copyGame, a, b, depth-1, game.otherPlayer(p), false);
                //choose the best path
                maxEval = Math.max(eval, maxEval);
                //update alpha
                a = Math.max(a, eval);
                //can we prune?
                if (b <= a)
                    break;
            }
            return maxEval;
        }
        //analogically to max
        //min player
        else {
            float minEval = Float.MAX_VALUE;
            for (int[] move : availableMoves){
                //copy game then make a move
                Othello copyGame = new Othello(game);
                copyGame.makeMove(p,move[0], move[1]);
                //explore the path
                float eval = minimax(copyGame, a, b, depth-1, game.otherPlayer(p), true);
                //decide on the best path
                minEval = Math.min(eval, minEval);
                //update beta
                b = Math.min(b, eval);
                //can we prune?
                if (b <= a)
                    break;
            }
            return minEval;
        }
    }
}
