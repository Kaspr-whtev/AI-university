import java.util.ArrayList;

public class Algorithm {
    public static void calculateHeuristics(ArrayList<Node> nodes, Heuristic heuristic){
        long start = System.currentTimeMillis();

        for (Node node : nodes)
            node.estimate(heuristic);

        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println(String.format("To calculate the %s heuristic it took %d", heuristic, time));
    }

    public static ArrayList<Node> path(Node currentNode){
        ArrayList<Node> path = new ArrayList<>();
        long start = System.currentTimeMillis();

        while (currentNode.hasNext()){
            path.add(currentNode);
            currentNode = currentNode.traverse();
        }

        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println(String.format("To find the path it took %d", time));
        return path;
    }

    public static ArrayList<Node> addStartEnd(ArrayList<Node> nodes){
        ArrayList<Node> newNodes = addMainParent(nodes);
        newNodes = addMainChild(newNodes);
        return newNodes;
    }
    private static ArrayList<Node> addMainParent(ArrayList<Node> nodes){
        Node parent = new Node(0,0);
        for (Node node : nodes){
            if (node.parents.isEmpty()){
                parent.addChild(node);
            }
        }
        nodes.add(parent);
        return nodes;
    }
    private static ArrayList<Node> addMainChild(ArrayList<Node> nodes){
        Node child = new Node(nodes.size(), 0);
        for (Node node : nodes){
            if (node.children.isEmpty()){
                node.addChild(child);
            }
        }
        nodes.add(child);
        return nodes;
    }

}
