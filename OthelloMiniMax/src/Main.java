import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        Othello o = new Othello();
        //Player p1 = new HumanPlayer("player 1");
        MiniMaxPlayer p1 = new MiniMaxPlayer("player 1");
        p1.setHeuristic(Heuristic.Corners);
        MiniMaxPlayer p2 = new MiniMaxPlayer("player 2");
        p2.setHeuristic(Heuristic.StaticWeights);

        o.play(p1, p2);

    }


}
