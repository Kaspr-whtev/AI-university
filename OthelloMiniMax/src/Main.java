import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        Othello o = new Othello();
        //Player p1 = new HumanPlayer("player 1");
        MiniMaxPlayer p1 = new MiniMaxPlayer("player 1");
        p1.setHeuristic(Heuristic.Mobility);
        MiniMaxPlayer p2 = new MiniMaxPlayer("player 2");
        p2.setHeuristic(Heuristic.StaticWeights);

        o.play(p1, p2);

//        char[][] test = {
//                {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'},
//                {'b', 'b', 'b', 'w', 'b', 'w', 'w', 'w'},
//                {'b', 'b', 'b', 'b', 'w', 'b', 'w', 'w'},
//                {'b', 'b', 'w', 'b', 'b', 'b', 'w', 'w'},
//                {'b', 'w', 'b', 'b', 'b', 'b', 'w', 'w'},
//                {'b', 'b', 'b', 'b', 'w', 'b', 'w', 'w'},
//                {'b', 'b', 'b', 'b', 'b', 'w', 'w', 'b'},
//                {'.', '.', 'b', '.', '.', 'b', 'w', 'b'} };
//        Othello o = new Othello(test);
//        p2.symbol = 'w';
//        p2.move(o);

    }


}
