package treeoflife.modele;

import java.io.Serializable;
import java.util.ArrayList;


public class TreeNode{
//    source_node_id : Ancestor or source node identifier
//    target_node_id : Descendant or target node identifier

    private final int id;
    private Node node;

    private boolean highLight;

    private Position position;

    private TreeNode parent;

    private String description;
    private String imageurl;
    private ArrayList<TreeNode> treeNodes;

    private int getId() {
        return id;
    }

    public ArrayList<TreeNode> getLinks() {
        return treeNodes;
    }

    public TreeNode(int id) {
        this.id = id;
        this.treeNodes = new ArrayList<>();
        this.highLight = false;
    }
    public TreeNode(int id,TreeNode parent) {
        this.id = id;
        this.treeNodes = new ArrayList<>();
        this.highLight = false;
        this.parent = parent;
    }

    public TreeNode(int id, Node node) {
        this.id = id;
        this.node = node;
        this.treeNodes = new ArrayList<>();
        this.highLight = false;
    }

//    public void addChildLink(int id, Node node){
//        treeNodes.add(new TreeNode(id, node));
//    }

    public void addChildTreeNode(TreeNode treeNode){
        treeNodes.add(treeNode);
    }

    public void setNode(Node node){
        this.node = node;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }
    public Node getNode(){
        return node;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public TreeNode getTreeNode(int id){
        if(this.id == id)
            return this;
        else {
            for (TreeNode treeNode : treeNodes) {
                TreeNode res = treeNode.getTreeNode(id);
                if(res != null)
                    return res;
            }
        }
        return null;
    }

    public boolean isHighlight() {
        return highLight;
    }

    public void setHighlight(boolean highLight) {
        this.highLight = highLight;
    }


}
