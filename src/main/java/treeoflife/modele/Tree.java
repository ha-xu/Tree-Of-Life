package treeoflife.modele;

import treeoflife.Vue;

import java.io.*;
import java.util.ArrayList;


public class Tree  {

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
                sourceNode.addChildTreeNode(new TreeNode(target_node_id, sourceNode));
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

    //generate positions for each node use recursive
    public void GeneratePositions() {
        root.setPosition(new Position((double) Vue.WIDTH /2, Vue.HEIGHT/2));
        GenratePosRec(root);
    }

    public void GenratePosRec(TreeNode treeNode){
        if(!treeNode.getLinks().isEmpty()){
            ArrayList<Position> positions;
            if(treeNode.getParent() == null){
                positions = PositionDraw.GetAroundPositions(treeNode.getPosition(), 200, treeNode.getLinks().size());
            }else{
                positions = PositionDraw.GetAroundPositions(treeNode.getPosition(), treeNode.getParent().getPosition(), 200, treeNode.getLinks().size());
            }

            for(int i = 0; i < treeNode.getLinks().size(); i++){
                treeNode.getLinks().get(i).setPosition(positions.get(i));
                GenratePosRec(treeNode.getLinks().get(i));
            }
        }
    }


    @Override
    public String toString() {
        return "root { " + root + " }";
    }

    //save to file
    public void SaveTree(String filePath) {
        //open file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            //write root
            bw.write(root.toString());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Save tree failed: " + e.getMessage());
        }
    }
}