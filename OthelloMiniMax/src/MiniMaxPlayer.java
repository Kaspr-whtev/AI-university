import java.util.ArrayList;

public class MiniMaxPlayer extends Player{
    private int depth = 4; //the depth of the minimax search
    private Heuristic heuristic = Heuristic.PieceParity;

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

    @Override
    public int[] move(Othello game) {
        ArrayList<int[]> availableMoves = game.getValidMoves(symbol);
        int[] move = new int[0];
        if (availableMoves.isEmpty()){
            System.out.println("There are no possible moves to be made! Skipping turn...");
            move = new int[]{-1, -1};
            return move;
        }
        int best = -1;
        float utility = -1 * Float.MAX_VALUE;
        for (int n=0; n< availableMoves.size(); n++){
            Othello copyGame = new Othello(game);
            copyGame.makeMove(symbol, availableMoves.get(n)[0], availableMoves.get(n)[1]);
            float val = minimax(copyGame, utility, Float.MAX_VALUE, depth, symbol, false);
            if (val > utility){
                utility = val;
                best = n;
            }
        }
        return availableMoves.get(best);
    }

    public float minimax(Othello game, float a, float b, int depth, char p, boolean maximizingPlayer){
        if (depth == 0 || game.anyMove(p))
            return HeuristicEval.getValue(heuristic, game, p);
        ArrayList<int[]> availableMoves = game.getValidMoves(p);
        if (maximizingPlayer){
            float maxEval = -1 * Float.MAX_VALUE;
            for (int[] move : availableMoves){
                Othello copyGame = new Othello(game);
                copyGame.makeMove(p,move[0], move[1]);
                float eval = minimax(copyGame, a, b, depth-1, game.otherPlayer(p), false);
                maxEval = Math.max(eval, maxEval);
                a = Math.max(a, eval);
                if (b <= a)
                    break;
                return maxEval;
            }
        }
        else {
            float minEval = Float.MAX_VALUE;
            for (int[] move : availableMoves){
                Othello copyGame = new Othello(game);
                copyGame.makeMove(p,move[0], move[1]);
                float eval = minimax(copyGame, a, b, depth-1, game.otherPlayer(p), true);
                minEval = Math.min(eval, minEval);
                b = Math.min(b, eval);
                if (b <= a)
                    break;
                return minEval;
            }
        }
        return 0;
    }
}
