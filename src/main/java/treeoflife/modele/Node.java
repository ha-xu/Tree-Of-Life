package treeoflife.modele;

public class Node {
//    node_id : Numeric identifier for the species in the tree
//    node_name : Name of the species or none if unknown
//    child_nodes : Number of child nodes
//    leaf_node : Whether or not the node is a leaf
//    tolorg_link : Whether or not there is a page on the website tolweb.org describing this species
//    http://tolweb.org/${node_name}/${node_id}
//    extinct : Whether the species is living or extinct
//              0 - living , 1 - extinct species
//    confidence : Confidence of placement in the tree structure.
//              0 - confident position , 1 - problematic position , 2 - unspecified position
//    phylesis : 0, 1 or 2 :
//              0 - monophyletic , 1 - uncertain monophyly , 2 - not monophyletic
    private int node_id; // Numeric identifier for the species in the tree
    private String node_name; // Name of the species or none if unknown
    private int child_nodes; // Number of child nodes
    private int leaf_node; // 0 or 1
    private int tolorg_link; // 0 or 1
    private int extinct; // 0 or 1
    private int confidence; // 0, 1 or 2
    private int phylesis; // 0, 1 or 2

    public Node(int node_id, String node_name, int child_nodes, int leaf_node, int tolorg_link, int extinct, int confidence, int phylesis) {
        this.node_id = node_id;
        this.node_name = node_name;
        this.child_nodes = child_nodes;
        this.leaf_node = leaf_node;
        this.tolorg_link = tolorg_link;
        this.extinct = extinct;
        this.confidence = confidence;
        this.phylesis = phylesis;
    }
}
