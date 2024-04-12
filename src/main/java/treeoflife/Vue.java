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

        FxmlController fxmlController =  fxmlLoader.getController();
        fxmlController.addCircle(tree.getRoot().getNode().getName(),400,300);


        /*fxmlController.addCircle(tree.getRoot().getLinks().get(0).getNode().getName(),100,100);
        fxmlController.addCircle(tree.getRoot().getLinks().get(1).getNode().getName(),200,200);*/


        /*fxmlController.addCircle(tree.getRoot().getLinks().getFirst().getLinks().get(1).getNode().getName(),300,300);*/

        TreeNode rootNode = tree.getRoot();
        int size = rootNode.getLinks().size();
        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;
        double radius = 200; // 设置圆的半径

        for (int i = 0; i < size; i++) {
            // 计算每个节点的角度和坐标
            double angle = 2 * Math.PI * i / size; // 2π * 当前索引 / 总节点数
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            fxmlController.addCircle(rootNode.getLinks().get(i).getNode().getName(), x, y);

            int size1 = rootNode.getLinks().get(i).getLinks().size();

            double spanAngle = Math.PI * 2 / 3;  // 120度扇形
            double startAngle = angle - spanAngle / 2;  // 让扇形从父节点的左侧开始
            double subRadius = radius * 2.8;  // 增加父节点与子节点之间的距离

            for(int a = 0;a < size1;a++){
                double angle2 = startAngle + (double) a*2 / (size1 - 1); // 分布在半圆上，注意分母是 size1-1 以确保覆盖完整的半圆
                double x2 =  x + subRadius * 0.5 * Math.cos(angle2); // 子节点的半径为父节点的一半
                double y2 = y + subRadius * 0.5 * Math.sin(angle2);
                fxmlController.addCircle(rootNode.getLinks().get(i).getLinks().get(a).getNode().getName(), x2, y2);

                int size2 = rootNode.getLinks().get(i).getLinks().get(a).getLinks().size();
                double subRadius1 = radius * 5.8;
                for(int b=0;b<size2;b++){
                    double angle3 = startAngle + (double) b*2 / (size2 - 1); // 分布在半圆上，注意分母是 size1-1 以确保覆盖完整的半圆
                    double x3 =  x2 + subRadius1 * 0.5 * Math.cos(angle3); // 子节点的半径为父节点的一半
                    double y3 = y2 + subRadius1 * 0.5 * Math.sin(angle3);
                    fxmlController.addCircle(rootNode.getLinks().get(i).getLinks().get(a).getLinks().get(b).getNode().getName(), x3, y3);

                }
            }
        }





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
