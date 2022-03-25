import java.util.ArrayList;
import java.util.Collections;

public class Algorithm {

    //main algorithm, it uses the calculated heuristic to traverse the graph via the best path
    //written based on pseudocode from https://en.wikipedia.org/wiki/A*_search_algorithm
    public static ArrayList<Node> AStar(Node currentNode, Node goal){
        ArrayList<Node> open = new ArrayList<>();
        currentNode.g = 0;
        open.add(currentNode);
        currentNode.F = currentNode.h;
        long start = System.nanoTime();

        while (!open.isEmpty()){

            Node node = open.get(0);
            for (Node n : open){
                if (n.F < node.F)
                    continue;
                node = n;
            }

            if (node == goal){
                long finish = System.nanoTime();
                long time = finish - start;
                System.out.println(String.format("To find the path it took %d", time));
                return getPath(node);
            }
            open.remove(node);
            for (Node child : node.children){
                double gScore = node.g + child.weight;
                if (gScore >= child.g){
                    child.parent = node;
                    child.g = gScore;
                    child.F = gScore + child.h;
                    if (!open.contains(child))
                        open.add(child);
                }
            }
        }
        long finish = System.nanoTime();
        long time = finish - start;
        System.out.println(String.format("Failed to find a path, time: %d", time));
        return null;
    }
    private static ArrayList<Node> getPath(Node pathTo){
        ArrayList<Node> path = new ArrayList<>();
        Node current = pathTo;
        while (current.parent != null){
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    //this method adds the synthetic parent and child to the graph so there is a consistent start and end point
    public static ArrayList<Node> addStartEnd(ArrayList<Node> nodes){
        ArrayList<Node> newNodes = addMainParent(nodes);
        newNodes = addMainChild(newNodes);
        return newNodes;
    }
    private static ArrayList<Node> addMainParent(ArrayList<Node> nodes){
        Node parent = new Node(-1,0);
        for (Node node : nodes){
            if (node.parents.isEmpty()){
                parent.addChild(node);
            }
        }
        nodes.add(parent);
        return nodes;
    }
    private static ArrayList<Node> addMainChild(ArrayList<Node> nodes){
        Node child = new Node(nodes.size()-1, 0);
        for (Node node : nodes){
            if (node.children.isEmpty()){
                node.addChild(child);
            }
        }
        nodes.add(child);
        return nodes;
    }

}
