package treeoflife.modele;

public class Link {
//    source_node_id : Ancestor or source node identifier
//    target_node_id : Descendant or target node identifier

    private int source_node_id; // Ancestor or source node identifier
    private int target_node_id; // Descendant or target node identifier

    public Link(int source_node_id, int target_node_id) {
        this.source_node_id = source_node_id;
        this.target_node_id = target_node_id;
    }
}
