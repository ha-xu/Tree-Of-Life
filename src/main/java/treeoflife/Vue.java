package treeoflife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import treeoflife.modele.Tree;
import treeoflife.modele.ListNodes;

import java.io.IOException;

public class Vue extends Application {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    Tree tree = new Tree();
    ListNodes listNodes = new ListNodes();

    @Override
    public void start(Stage stage) throws IOException {
        tree.LoadLinks("src/main/resources/data/treeoflife_links_simplified.csv");
        tree.LoadNodes("src/main/resources/data/treeoflife_nodes_simplified.csv");


        FXMLLoader fxmlLoader = new FXMLLoader(Vue.class.getResource("treeview.fxml"));
        Parent root = fxmlLoader.load();

        FxmlController fxmlController =  fxmlLoader.getController();
        fxmlController.addCircle(tree.getRoot().getNode().getName(),20,20);
        fxmlController.addCircle(tree.getRoot().getLinks().getFirst().getNode().getName(),100,100);
        fxmlController.addCircle(tree.getRoot().getLinks().getFirst().getLinks().get(1).getNode().getName(),200,200);


        //scroll event
        root.setOnScroll(event -> {
            if(event.getDeltaY() > 0)
                fxmlController.zoomIn();
            else
                fxmlController.zoomOut();
        });

        root.setOnMousePressed(event -> {
            System.out.println("Pressed");
        });

        root.setOnMouseReleased(event -> {
            System.out.println("Released");
        });

        //set drag event
        root.setOnMouseDragged(event -> {
            System.out.println("Dragged");
        });

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Tree of life");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}