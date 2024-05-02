package treeoflife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import treeoflife.modele.*;

import java.io.IOException;

public class Vue extends Application {

    public static double WIDTH = 800;
    public static double HEIGHT = 600;

    Tree tree = new Tree();

    @Override
    public void start(Stage stage) throws IOException {
        tree.LoadLinks("src/main/resources/data/treeoflife_links_simplified.csv");
        tree.LoadNodes("src/main/resources/data/treeoflife_nodes_simplified.csv");
//        tree.LoadLinks("src/main/resources/data/treeoflife_links.csv");
//        tree.LoadNodes("src/main/resources/data/treeoflife_nodes.csv");

        FXMLLoader fxmlLoader = new FXMLLoader(Vue.class.getResource("treeview.fxml"));
        Parent root = fxmlLoader.load();
        FxmlController fxmlController =  fxmlLoader.getController();

        TreeNode rootTreeNode = tree.getRoot();

        //generate positions for each node
        tree.GeneratePositions();

        fxmlController.SetRootTreeNode(rootTreeNode);
        fxmlController.DrawTreeNodeParentAndChild(rootTreeNode);


        root.setOnScroll(fxmlController::scroll);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Tree of life");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

//
}