import java.util.ArrayList;

public class HeuristicEval {

    public static float getValue(Heuristic heuristic, Othello game, char player){
        switch (heuristic){
            case StaticWeights:
                return evalStaticWeights(game, player);
            case Mobility:
                return evalMobility(game, player);
            case Corners:
                return evalCorners(game, player);
            case PieceParity:
            default:
                return evalPieceParity(game, player);
        }
    }

    private static float evalPieceParity(Othello game, char player){
        float max = 0;
        float min = 0;
        for (int r=0; r<game.getBoard().length; r++){
            for (int c=0; c<game.getBoard().length; c++){
                if (game.getBoard()[r][c] == player)
                    max++;
                else if (game.getBoard()[r][c] == game.otherPlayer(player))
                    min++;
            }
        }
        return 100*(max-min)/(max+min);
    }

    private static float evalStaticWeights(Othello game, char player){
        int[][] weights = {
                {4, -3, 2, 2, 2, 2, -3, 4},
                {-3, -4, -1, -1, -1, -1, -4, -3},
                {2, -1, 1, 0, 0, 1, -1, 2},
                {2, -1, 0, 1, 1, 0, -1, 2},
                {2, -1, 0, 1, 1, 0, -1, 2},
                {2, -1, 1, 0, 0, 1, -1, 2},
                {-3, -4, -1, -1, -1, -1, -4, -3},
                {4, -3, 2, 2, 2, 2, -3, 4},
        };
        float max = 0;
        float min = 0;
        for (int r=0; r<game.getBoard().length; r++){
            for (int c=0; c<game.getBoard().length; c++){
                if (game.getBoard()[r][c] == player)
                    max += weights[r][c];
                else if (game.getBoard()[r][c] == game.otherPlayer(player))
                    min += weights[r][c];
            }
        }
        return max - min;
    }

    private static float evalMobility(Othello game, char player){
        float max = game.getValidMoves(player).size();
        float min = game.getValidMoves(game.otherPlayer(player)).size();
        if (max + min != 0)
            return 100*(max-min)/(max+min);
        else return 0;
    }

    private static float evalCorners(Othello game, char player){
        ArrayList<int[]> corners = new ArrayList<>();
        corners.add(new int[]{0, 0});
        corners.add(new int[]{0, game.getBoard().length});
        corners.add(new int[]{game.getBoard().length, 0});
        corners.add(new int[]{game.getBoard().length, game.getBoard().length});

        float max = 0;
        float min = 0;

//        ArrayList<int[]> maxMoves = game.getValidMoves(player);
//        ArrayList<int[]> minMoves = game.getValidMoves(game.otherPlayer(player));
        //for now only evaluates corners owned,
        //not potential corners (those that could be captured in the next move)

        for (int[] corner : corners){
            if (game.getBoard()[corner[0]][corner[1]] == player)
                max += 4;
            else if (game.getBoard()[corner[0]][corner[1]] == game.otherPlayer(player))
                min += 4;
        }
        if (max+min != 0)
            return 100*(max-min)/(max+min);
        else return 0;
    }
}
