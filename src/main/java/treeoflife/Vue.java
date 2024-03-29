package treeoflife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import treeoflife.modele.ListLinks;
import treeoflife.modele.ListNodes;

import java.io.IOException;

public class Vue extends Application {

    ListLinks listLinks = new ListLinks();
    ListNodes listNodes = new ListNodes();

    @Override
    public void start(Stage stage) throws IOException {
        listLinks.LoadLinks("src/main/resources/data/treeoflife_links.csv");
        System.out.println(listLinks.getLinks().size());

        listNodes.LoadNodes("src/main/resources/data/treeoflife_nodes.csv");
        System.out.println(listNodes.getNodes().size());

        FXMLLoader fxmlLoader = new FXMLLoader(Vue.class.getResource("treeview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Tree of life");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}