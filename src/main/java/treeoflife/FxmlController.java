package treeoflife;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import treeoflife.modele.Position;
import treeoflife.modele.PositionDraw;

public class FxmlController {

    double currentZoom = 1;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    public void initialize() {
        System.out.println("FxmlController initialized");
        //Je met la taille de l'anchorPane à la taille de la fenêtre
        setAnchorPaneSize(Vue.WIDTH, Vue.HEIGHT);
//        anchorPane.setPrefSize(2000, 2000);
        //color
        anchorPane.setStyle("-fx-background-color: none");

    //        scrollPane.setOnScroll(event -> {
//            double zoomFactor = event.getTextDeltaY() > 0 ? 1.1 : 0.9;
//            scrollPane.setViewportBounds(new );
//        });
    }

    public void setAnchorPaneSize(double width, double height){
        anchorPane.setPrefSize(width, height);
    }

    public void addLine(Position start, Position end){
        //create a line
        javafx.scene.shape.Line line = new javafx.scene.shape.Line();
        //set line start position
        line.setStartX(start.getX());
        line.setStartY(start.getY());
        //set line end position
        line.setEndX(end.getX());
        line.setEndY(end.getY());
        //set line color
        line.setStyle("-fx-stroke: black");
        //set z-index

        //add line to anchorPane
        anchorPane.getChildren().add(line);
    }

    public void addCircle(String name, Position position){
        double radius = 3;
        Circle newCircle = new Circle(radius);
        Label label = new Label(name);
        //set label color
        label.setStyle("-fx-text-fill: white");
        label.setStyle("-fx-background-color: black");
        //fontsize
        label.setStyle("-fx-font-size: 1");
        //set label length
        label.setPrefWidth(2*radius);
        //set label alignment
        label.setAlignment(javafx.geometry.Pos.CENTER);

        //set circle color
        newCircle.setStyle("-fx-fill: gray");
        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(position.getX() - radius);
        stackPane.setLayoutY(position.getY() - radius);
        stackPane.getChildren().add(newCircle);
        stackPane.getChildren().add(label);

        anchorPane.getChildren().add(stackPane);
    }
    private double initialScaleX;
    private double initialScaleY;
    public void zoomIn(ScrollEvent e){
        Scale newScale = new Scale();
        newScale.setX(anchorPane.getScaleX() * 1.1);
        newScale.setY(anchorPane.getScaleY() * 1.1);
        currentZoom = currentZoom * 1.1;
        newScale.setPivotX(e.getX());
        newScale.setPivotY(e.getY());
        anchorPane.getTransforms().add(newScale);
//        initialScaleX = anchorPane.getScaleX(); // 记录初始缩放比例
//        initialScaleY = anchorPane.getScaleY();
//        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), anchorPane);
//
//        currentZoom = currentZoom * 1.3;
//        scaleTransition.setToX(currentZoom);
//        scaleTransition.setToY(currentZoom);
//
//
//        scaleTransition.play(); // 在点击时触发缩放动画

    }

    public void zoomOut(ScrollEvent e){
        System.out.println(currentZoom);
        System.out.println(anchorPane.getLayoutX());

        Scale newScale = new Scale();
        newScale.setX(anchorPane.getScaleX() * 0.9);
        newScale.setY(anchorPane.getScaleY() * 0.9);
        currentZoom = currentZoom * 0.9;
        newScale.setPivotX(e.getX());
        newScale.setPivotY(e.getY());
        anchorPane.getTransforms().add(newScale);
//        if(currentZoom * 0.7 >= 1){
//            currentZoom = currentZoom * 0.7;
//        }else {
//            currentZoom = 1;
//            anchorPane.setLayoutX(0);
//            anchorPane.setLayoutY(0);
//        }
//
//
//        scaleTransition.setToX(currentZoom);
//        scaleTransition.setToY(currentZoom);
//
//        scaleTransition.play(); // 在点击时触发缩放动画
    }

    Position startPostion;
    Position currentPosition;

    public void pressed(MouseEvent e){
        startPostion = new Position(e.getX(), e.getY());
    }

    public void dragged(MouseEvent e){
        currentPosition = new Position(e.getX(), e.getY());
        double deltaX = currentPosition.getX() - startPostion.getX();
        double deltaY = currentPosition.getY() - startPostion.getY();
        anchorPane.setLayoutX(anchorPane.getLayoutX() + deltaX);
        anchorPane.setLayoutY(anchorPane.getLayoutY() + deltaY);



    }

}