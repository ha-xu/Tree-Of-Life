package treeoflife.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListNodes {

    //arrayList of nodes
    private final ArrayList<Node> nodes;

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ListNodes() {
        this.nodes = new ArrayList<>();
    }

    public void LoadNodes(String filePath) {
        //open file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            //skip first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // 以逗号分隔每行的数据
                String[] values = line.split(",");
//                System.out.println(values.length);
                if(values.length == 8) {
                    nodes.add(new Node(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7])));
                }
//                nodes.add(new Node(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
