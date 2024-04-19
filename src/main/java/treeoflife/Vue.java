package treeoflife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import treeoflife.modele.*;

import java.io.IOException;
import java.util.ArrayList;

public class Vue extends Application {

    public static double WIDTH = 800;
    public static double HEIGHT = 600;

    Tree tree = new Tree();
    ListNodes listNodes = new ListNodes();

    @Override
    public void start(Stage stage) throws IOException {
        tree.LoadLinks("src/main/resources/data/treeoflife_links.csv");
        tree.LoadNodes("src/main/resources/data/treeoflife_nodes.csv");


        FXMLLoader fxmlLoader = new FXMLLoader(Vue.class.getResource("treeview.fxml"));
        Parent root = fxmlLoader.load();

        FxmlController fxmlController =  fxmlLoader.getController();
        TreeNode rootTreeNode = tree.getRoot();
        rootTreeNode.setPosition(new Position((double) WIDTH /2, HEIGHT/2));
        ArrayList<Position> positions = PositionDraw.GetAroundPositions(rootTreeNode.getPosition(), 200, rootTreeNode.getLinks().size());


        DrawCircles(rootTreeNode, fxmlController,1);
        fxmlController.addCircle(rootTreeNode.getNode().getName(), rootTreeNode.getPosition());
        //scroll event
        root.setOnScroll(event -> {
            if(event.getDeltaY() > 0)
                fxmlController.zoomIn(event);
            else
                fxmlController.zoomOut(event);
        });

        root.setOnMousePressed(fxmlController::pressed);
        root.setOnMouseDragged(fxmlController::dragged);

            Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Tree of life");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void DrawCircles(TreeNode root, FxmlController fxmlController,int level){
        ArrayList<TreeNode> treeNodes = root.getLinks();
        if(root.getLinks().isEmpty())
            return;
        ArrayList<Position> positions = PositionDraw.GetAroundPositions(root.getPosition(), (300/(level*Math.sqrt(level))), root.getLinks().size());

        for(int i = 0; i < root.getLinks().size(); i++){
            root.getLinks().get(i).setPosition(positions.get(i));
        }

        for(TreeNode treeNode : treeNodes){
            if(treeNode.getNode() == null)
                continue;
            fxmlController.addLine(root.getPosition(), treeNode.getPosition());
            fxmlController.addCircle(treeNode.getNode().getName(), treeNode.getPosition());
            DrawCircles(treeNode, fxmlController,level+1);
        }

    }
}