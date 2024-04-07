package treeoflife;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class FxmlController {
    @FXML
    private AnchorPane anchorPane;

    public void initialize() {
        System.out.println("FxmlController initialized");
        //Je met la taille de l'anchorPane à la taille de la fenêtre
        setAnchorPaneSize(Vue.WIDTH, Vue.HEIGHT);
    }

    public void setAnchorPaneSize(double width, double height){
        anchorPane.setPrefSize(width, height);
    }

    public void addCircle(){
        Circle newCircle = new Circle(30);
        Label label = new Label("Hello");
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(newCircle);
        stackPane.getChildren().add(label);
        anchorPane.getChildren().add(stackPane);
    }
}