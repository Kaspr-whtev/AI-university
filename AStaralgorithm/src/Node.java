import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Node {
    public int index;
    public double weight; // weight of this node
    private double g; //cost from the start to the node
    private double h; //cost to the end from the node, estimated with a heuristic
    private double F = g + h; //function that A* uses to select a path
    public ArrayList<Node> parents;
    public ArrayList<Node> children;

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

    public Node traverse(){
        if (!hasNext())
            throw new IndexOutOfBoundsException();
        Node nextNode = children.get(0);
        for (Node child: children) {
            if (child.g < g)
                child.g = g + child.weight;
            if (child.F < nextNode.F)
                continue;
            nextNode = child;
        }
        return nextNode;
    }

    public void estimate(Heuristic heuristic) {
        switch (heuristic){
            case BruteForce:
                calculateBruteForce();
                break;
//            case LeastNodes:
//                calculateLeastNodes();
//                break;
            case Random:
                calculateRandom();
                break;
            case LeastNodes:
            default:
                calculateLeastNodes();
        }
    }

    private void calculateBruteForce(){
        if (parents.size() !=0)
            return;
        h = bruteForceHeuristic();
    }
    private double bruteForceHeuristic(){
        if (h == 0)
            h=weight;
        ArrayList<Double> childrenWeights = new ArrayList<>();
        for (Node child : children){
            double childWeight = child.bruteForceHeuristic();
            childrenWeights.add(childWeight);
        }
        if (childrenWeights.size() != 0){
            h = Collections.max(childrenWeights);
        }
        return h;
    }

    private void calculateLeastNodes(){
        h = leastNodesHeuristic();
    }
    private int leastNodesHeuristic(){
        if (children.size() == 0)
            return 0;
        return children.get(0).leastNodesHeuristic();
    }

    private void calculateRandom(){
        Random rand = new Random(children.size());
        h = rand.nextDouble();
    }
}
