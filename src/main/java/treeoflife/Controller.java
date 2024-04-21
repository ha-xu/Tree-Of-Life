package treeoflife;

import javafx.scene.input.MouseEvent;
import treeoflife.modele.TreeNode;

public class Controller {
    FxmlController fxmlController;
    TreeNode rootTreeNode;

    public Controller(FxmlController fxmlController, TreeNode rootTreeNode){
        this.fxmlController = fxmlController;
        this.rootTreeNode = rootTreeNode;
    }


}
