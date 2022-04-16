import java.util.ArrayList;

public class HeuristicEval {

    //the function governs which heuristic is to be used
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

    //returns a value between -100 and +100
    //adds together owned pieces and stores it as max
    //adds together the opponents pieces and stores it as min
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
        //i dont need to check if min+max=0, as pieces cannot be removed from the board, so the sum is always >0
        //the calculation is set up in this way to conform the score to the predetermined scale
        return 100*(max-min)/(max+min);
    }

    //each position on the board is assigned a weight (stored in the weights array)
    //the function loops through every position on the board and if it is owned by player, it adds the value to max
    //and if it is owned by the opponent of the player then the value is added to min
    private static float evalStaticWeights(Othello game, char player){
        //I tested two sets of weights, the top 1 performed better (5 won and 1 tie), than the bottom (4 won and 2 lost)
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
//        int[][] weights = {
//                {120, -20, 20, 5, 5, 20, -20, 120},
//                {-20, -40, -5, -5, -5, -5, -40, -20},
//                {20, -5, 15, 3, 3, 15, -5, 20},
//                {5, -5, 3, 3, 3, 3, -5, 5},
//                {5, -5, 3, 3, 3, 3, -5, 5},
//                {20, -5, 15, 3, 3, 15, -5, 20},
//                {-20, -40, -5, -5, -5, -5, -40, -20},
//                {120, -20, 20, 5, 5, 20, -20, 120},
//        };
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

    //checks the number of moves the player can make, stored as maxMoves
    //checks the number of moves the player's opponent can make, stored as minMoves
    //counts the number of empty spaces adjacent to the player's opponent's pieces, stored as maxPotential
    //counts the number of empty spaces adjacent to the player's pieces, stored as minPotential
    //based on these values it returns a value
    private static float evalMobility(Othello game, char player){
        //System.out.println(game.toString());
        ArrayList<int[]> maxMoves = new ArrayList<>();
        ArrayList<int[]> minMoves = new ArrayList<>();

        float maxPotential = 0;
        float minPotential = 0;
        for (int r=0; r<game.getBoard().length; r++){
            for (int c=0; c<game.getBoard().length; c++){
                //to fit everything in 1 loop and not count empty spaces multiple times if they border multiple pieces,
                //these boolean values were created
                boolean notAddedMax = true;
                boolean notAddedMin = true;
                if (game.getBoard()[r][c] == Othello.getEmpty()){
                    for (int[] dir : Othello.getDirections()){
                        if (game.onBoard(r+dir[0], c+dir[1])){
                            if (game.getBoard()[r+dir[0]][c+dir[1]] == game.otherPlayer(player) && notAddedMax) {
                                maxPotential++;
                                notAddedMax = false;
                            }
                            else if (game.getBoard()[r+dir[0]][c+dir[1]] == player && notAddedMin) {
                                minPotential++;
                                notAddedMin = false;
                            }
                            //if a piece from both players has been found escape from the loop
                            if (!notAddedMax && !notAddedMin)
                                break;
                        }
                    }
                    //calculates the available moves for both players
                    //to reduce the number of loops, getValidMoves(char p) was not used
                    if (game.validMove(player, r, c)){
                        int[] coordinates = {r,c};
                        maxMoves.add(coordinates);
                    }
                    else if (game.validMove(game.otherPlayer(player), r, c)){
                        int[] coordinates = {r,c};
                        minMoves.add(coordinates);
                    }
                }
            }
        }

        float maxActual = maxMoves.size();
        float minActual = minMoves.size();
        float actual = 0;
        float potential = 0;

        //calculates the value for actual and potential moves for both players
        //since max+min can be 0, ex. when all the spaces on the board are taken
        //i need to check for it to avoid getting a NaN
        if (maxActual + minActual != 0)
            actual = 100*(maxActual-minActual)/(maxActual+minActual);
        else actual = 0;

        if (maxPotential + minPotential != 0)
            potential = 100*(maxPotential-minPotential)/(maxPotential+minPotential);
        else
            potential = 0;
        //returns a value based on the actual and potential scores
        return actual + potential;
    }

    //returns a value between -100 and +100
    //checks for corners owned and corners to be captured in the next move
    //values for the player are stored as max
    //values for the opponent are stored as min
    private static float evalCorners(Othello game, char player){
        //collection with the coordinates of the board corners
        ArrayList<int[]> corners = new ArrayList<>();
        corners.add(new int[]{0, 0});
        corners.add(new int[]{0, game.getBoard().length-1});
        corners.add(new int[]{game.getBoard().length-1, 0});
        corners.add(new int[]{game.getBoard().length-1, game.getBoard().length-1});

        float max = 0;
        float min = 0;

        //next moves
        ArrayList<int[]> maxMoves = game.getValidMoves(player);
        ArrayList<int[]> minMoves = game.getValidMoves(game.otherPlayer(player));

        for (int[] corner : corners){
            //owned corners
            if (game.getBoard()[corner[0]][corner[1]] == player)
                max += 4;
            else if (game.getBoard()[corner[0]][corner[1]] == game.otherPlayer(player))
                min += 4;
            //potential corners
            for (int[] maxMove : maxMoves){
                if (maxMove[0] == corner[0] && maxMove[1] == corner[1])
                    max += 4;
            }
            for (int[] minMove : minMoves){
                if (minMove[0] == corner[0] && minMove[1] == corner[1])
                    min += 4;
            }
        }
        //no corners can be owned, so the 0 check is warranted
        //the calculation to conform to the scale
        if (max+min != 0)
            return 100*(max-min)/(max+min);
        else return 0;
    }
}
