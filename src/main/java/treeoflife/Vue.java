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


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Tree of life");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


























/*
package treeoflife;


        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import treeoflife.modele.Tree;
        import treeoflife.modele.ListNodes;
        import treeoflife.modele.TreeNode;

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

        FxmlController fxmlController = fxmlLoader.getController();

        // 初始化视图中的树
        displayNode(tree.getRoot(), WIDTH / 2, HEIGHT / 2, 0, 100, fxmlController, 0);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Tree of Life");
        stage.setScene(scene);
        stage.show();
    }

    // 递归函数显示所有节点
    private void displayNode(TreeNode node, double x, double y, double angle, double distance, FxmlController controller, int depth) {
        // 显示当前节点
        controller.addCircle(node.getNode().getName(), (int) x, (int) y);

        int numChildren = node.getLinks().size();
        double newAngleStep = 360.0 / numChildren;

        for (int i = 0; i < numChildren; i++) {
            // 计算子节点的位置
            double newAngle = Math.toRadians(angle + i * newAngleStep);
            double newX = x + distance * Math.cos(newAngle);
            double newY = y + distance * Math.sin(newAngle);

            // 递归显示子节点
            displayNode(node.getLinks().get(i), newX, newY, newAngle, distance * 1, controller, depth + 5);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/
