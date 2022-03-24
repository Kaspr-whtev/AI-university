import java.util.ArrayList;

public class Main {
    private static String path = "data/test_small.dag";

    public static void main(String[] args){
        ArrayList<Node> nodes = ReadFile.read(path);
        nodes = Algorithm.addStartEnd(nodes);
        Node start = ReadFile.getByIndex(-1, nodes);
        nodes.get(nodes.size()-1).estimate(Heuristic.BruteForce);
        //start.estimate(Heuristic.MostNodes);
//        for (Node node : nodes)
//            System.out.println(node.toString());
        ArrayList<Node> path = Algorithm.AStar(start);
        String finalPath = "";
        double totalWeight = 0;
        for (Node node : path) {
            finalPath += node.index;
            finalPath += " -> ";
            totalWeight += node.weight;
        }
        finalPath = finalPath.substring(0, finalPath.length()-4);
        System.out.println(finalPath);
        System.out.println(totalWeight);
    }
}
