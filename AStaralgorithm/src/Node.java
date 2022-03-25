import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Node {
    public int index;
    public double weight; // weight of this node
    public double g; //cost from the start to the node
    public double h; //cost to the end from the node, estimated with a heuristic
    public double F = g + h; //function that A* uses to select a path
    public ArrayList<Node> parents;
    public ArrayList<Node> children;
    public Node parent;

    public Node(int index, double weight) {
        this.index = index;
        this.weight = weight;
        parents = new ArrayList<Node>();
        children = new ArrayList<Node>();
    }

    public void addChild(Node child){
        children.add(child);
        child.parents.add(this);
    }

    @Override
    public String toString() {
        String string = String.format("Node %d : weight= %f g= %f, h= %f, F=%f, parents[", index, weight, g, h, F);
        for (Node parent : parents)
            string += parent.index + " ";
        string += "],children[";
        for (Node child : children)
            string += child.index + " ";
        string += "]";
        return string;
    }

    public boolean hasNext(){
        return children.size() != 0;
    }

    public Node getBestChild(){
        if (children.isEmpty())
            return null;
        Node best = this.children.get(0);
        for (Node node : children){
            if (node.F < best.F)
                continue;
            best = node;
        }
        return best;
    }

    public void estimate(Heuristic heuristic) {
        switch (heuristic){
            case BruteForce:
                calculateBruteForce();
                break;
            case Random:
                calculateRandom();
                break;
            case FarthestNeighbor:
                calculateFarthestNeighbor();
                break;
            case MostNodes:
            default:
                calculateMostNodes();
        }
    }

    private void calculateBruteForce(){
        if (children.size() !=0)
            return;
        bruteForceHeuristic(0);
    }
    private void bruteForceHeuristic(double currentWeight){
        if (h == 0)
            h = weight + currentWeight;
        else if (h-weight < currentWeight)
            h = weight + currentWeight;
        else
            return;
        for (Node parent : parents)
            parent.bruteForceHeuristic(h);
    }

    private void calculateMostNodes(){
        h = mostNodesHeuristic();
    }
    private int mostNodesHeuristic(){
        if (children.isEmpty())
            return 0;
        ArrayList<Integer> NONodes = new ArrayList<>();
        for (Node child : children){
            int number = child.mostNodesHeuristic();
            NONodes.add(number);
        }
        h = Collections.max(NONodes)+1;
        return Collections.max(NONodes)+1;
    }

    private static final Random rand = new Random(5);

    private void calculateRandom(){
        h = rand.nextDouble();
    }

    private void calculateFarthestNeighbor(){
        h = weight;
    }
}
