import java.util.ArrayList;

public class Main {
    private static String path = "data/test_small_sparse.dag";

    public static void main(String[] args){
        ArrayList<Node> nodes = ReadFile.read(path);
        nodes = Algorithm.addStartEnd(nodes);
        for (Node node : nodes)
            System.out.println(node.toString());
    }
}
