import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public static ArrayList<Node> read(String path){
        try{
            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            int count = Integer.parseInt(lines.get(0));
            ArrayList<Node> nodes = new ArrayList<>();
            for (int n=1; n<count+1; n++){
                Node node = new Node(n-1, Double.parseDouble(lines.get(n)));
                nodes.add(node);
            }
            for (int n=0; n<count;n++){
                if (lines.get(n+count+1) != "-1" && !nodes.isEmpty()){
                    String[] split = lines.get(n+count+1).split("\\s+");
                    for (String connection: split){
                        int index = Integer.parseInt(connection);
                        Node child = getByIndex(index, nodes);
                        if (child != null){
                            nodes.get(n).addChild(child);
                        }
                    }
                }
            }
            return nodes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Node getByIndex(int index, ArrayList<Node> nodes){
        for (int n=0; n<nodes.size(); n++){
            if (nodes.get(n).index == index)
                return nodes.get(n);
        }
        return null;
    }
}
