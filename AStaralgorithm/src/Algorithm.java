import java.util.ArrayList;
import java.util.Collections;

public class Algorithm {
    public static void calculateHeuristics(ArrayList<Node> nodes, Heuristic heuristic){
        long start = System.currentTimeMillis();

        for (Node node : nodes)
            node.estimate(heuristic);

        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println(String.format("To calculate the %s heuristic it took %d", heuristic, time));
    }

    public static ArrayList<Node> AStar(Node currentNode){
        ArrayList<Node> open = new ArrayList<>();
        ArrayList<Node> closed = new ArrayList<>();
        currentNode.g = 0;
        open.add(currentNode);
        Node node = open.get(0);
        long start = System.nanoTime();

        while (currentNode.hasNext()){
            node = open.get(0);
            for (Node n : open){
                if (n.F < node.F)
                    continue;
                node = n;
            }
            if (node.children.isEmpty()){
                long finish = System.nanoTime();
                long time = finish - start;
                System.out.println(String.format("To find the path it took %d", time));
                return getPath(node);
            }
            open.remove(node);
            closed.add(node);
            for (Node child : node.children){
                if (closed.contains(child))
                    continue;
                double gScore = node.g + child.weight;
                boolean betterGScore = false;
                if (!open.contains(child)){
                    open.add(child);
                    betterGScore = true;
                } else if (gScore > child.g)
                    betterGScore = true;
                if (betterGScore){
                    child.parent = node;
                    child.g = gScore;
                    child.F = child.g + child.h;
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
