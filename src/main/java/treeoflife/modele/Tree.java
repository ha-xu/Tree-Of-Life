package treeoflife.modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tree {

    private final TreeNode root = new TreeNode(1);

    public TreeNode getRoot() {
        return root;
    }

    public void LoadLinks(String filePath) throws IOException {
        //open file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            //skip first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // 以逗号分隔每行的数据
                String[] values = line.split(",");
                int source_node_id = Integer.parseInt(values[0]);
                int target_node_id = Integer.parseInt(values[1]);

                TreeNode sourceNode = root.getTreeNode(source_node_id);
                sourceNode.addChildTreeNode(new TreeNode(target_node_id));
            }
        }
    }

    public void LoadNodes(String filePath) throws IOException {
        //open file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            //skip first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // 以逗号分隔每行的数据
                String[] values = line.split(",");
                if(values.length == 8) {
                    Node currentNode = new Node(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7]));
                    TreeNode sourceNode = root.getTreeNode(currentNode.getId());
                    if(sourceNode != null){
                        sourceNode.setNode(currentNode);
                    }
                }
            }
        }
    }

}