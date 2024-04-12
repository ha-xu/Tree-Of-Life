package treeoflife;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import treeoflife.modele.Position;

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

    public void addCircle(String name,int x, int y){
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
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);
        stackPane.getChildren().add(newCircle);
        stackPane.getChildren().add(label);
        anchorPane.getChildren().add(stackPane);
    }

    public void zoomIn(){
        anchorPane.setScaleX(anchorPane.getScaleX() * 1.1);
        anchorPane.setScaleY(anchorPane.getScaleY() * 1.1);
    }

    public void zoomOut(){
        anchorPane.setScaleX(anchorPane.getScaleX() / 1.1);
        anchorPane.setScaleY(anchorPane.getScaleY() / 1.1);
    }

    boolean isPressed = false;
    Position startPostion;
    Position currentPosition;

    public void pressed(MouseEvent e){
        isPressed = true;
        startPostion = new Position(e.getX(), e.getY());
    }

    public void released(MouseEvent e){
        isPressed = false;
        System.out.println("Released");
    }

    public void dragged(MouseEvent e){
        currentPosition = new Position(currentPosition.getX(), currentPosition.getY());
        System.out.println("Dragged");
    }
}