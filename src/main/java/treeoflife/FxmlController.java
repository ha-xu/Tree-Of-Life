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

    public void addCircle(String name){
        int radius = 45;
        Circle newCircle = new Circle(radius);
        Label label = new Label(name);
        //set label color
        label.setStyle("-fx-text-fill: white");
        //set label length
        label.setPrefWidth(2*radius);
        //set label alignment
        label.setAlignment(javafx.geometry.Pos.CENTER);

        //set circle color
        newCircle.setStyle("-fx-fill: gray");
        StackPane stackPane = new StackPane();
        stackPane.setLayoutX((double) Vue.WIDTH /2 - radius);
        stackPane.setLayoutY((double) Vue.HEIGHT /2 - radius);
        stackPane.getChildren().add(newCircle);
        stackPane.getChildren().add(label);
        anchorPane.getChildren().add(stackPane);
    }
}