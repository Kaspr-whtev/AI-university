import java.util.ArrayList;

public class Main {
    private static String[] paths = {
            "data/default.txt",
            "data/test_small_sparse.dag",
            "data/test_small.dag",
            "data/test_medium_sparse.dag",
            "data/test_medium.dag",
            "data/test_large_sparse.dag",
            "data/test_large.dag",
            "data/test_xlarge_sparse.dag",
            "data/test_xlarge.dag",
    };

    private static ArrayList<Node> setup(String filePath){
        ArrayList<Node> nodes = ReadFile.read(filePath);
        nodes = Algorithm.addStartEnd(nodes);
        return nodes;
    }

    public static void main(String[] args){
        //basic setup
        for (String filePath : paths){
            long startTime;
            long finishTime;
            long time;
            ArrayList<Node> nodes = setup(filePath);
            Node start = ReadFile.getByIndex(-1, nodes);
            Node goal = nodes.get(nodes.size()-1);
            System.out.println(filePath);

            //calculate heuristic: farthest neighbor
            startTime = System.nanoTime();
            for (Node node : nodes)
                node.estimate(Heuristic.FarthestNeighbor);
            finishTime  = System.nanoTime();
            time = finishTime - startTime;
            System.out.println("To calculate the farthest neighbor heuristics it took " + time);
            ArrayList<Node> path = Algorithm.AStar(start, goal);
            double totalWeight = 0;
            for (Node node : path) {
                totalWeight += node.weight;
            }
            System.out.println("The total weight of the farthest neighbor path is: " + totalWeight);
            System.out.println();



        }
    }
}
