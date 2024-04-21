package treeoflife;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import treeoflife.modele.Position;
import treeoflife.modele.PositionDraw;
import treeoflife.modele.TreeNode;

import java.util.ArrayList;
import java.util.Timer;

public class FxmlController {

    public double currentZoom = 1;

    private TreeNode currentTreeNode;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane stackPane_OUT;

    private VBox popup;
    private VBox listOfNodes;

    private boolean canZoom = true;

    public void initialize() {
        System.out.println("FxmlController initialized");
        //Je met la taille de l'anchorPane à la taille de la fenêtre
        setAnchorPaneSize(Vue.WIDTH, Vue.HEIGHT);

        anchorPane.setStyle("-fx-background-color: none");

        //draw a point in the center of the anchorPane
        Circle circle = new Circle(5);
        circle.setStyle("-fx-fill: red");
        circle.setCenterX(anchorPane.getLayoutX());
        circle.setCenterY(anchorPane.getLayoutY());
        anchorPane.getChildren().add(circle);
        anchorPane.setOnMousePressed(this::pressed);
        anchorPane.setOnMouseDragged(this::dragged);
        //create a popup of the node
        popup = new VBox();
        popup.setStyle("-fx-background-color: white;-fx-background-radius: 8px;-fx-opacity: 0.9;-fx-border-radius: 8px;-fx-border-color: black;-fx-border-width: 1px;-fx-padding: 10px;");
        popup.setLayoutX(100);
        popup.setLayoutY(100);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(javafx.scene.paint.Color.color(0,0,0,0.5));
        popup.setEffect(dropShadow);

        //create a list of nodes
        listOfNodes = new VBox();
        listOfNodes.setStyle("-fx-background-color: white;-fx-background-radius: 8px;-fx-opacity: 0.9;-fx-border-radius: 8px;-fx-border-color: black;-fx-border-width: 1px;-fx-padding: 10px;");
        listOfNodes.setMaxSize(220,250);
        listOfNodes.setTranslateX(Vue.WIDTH/2 - listOfNodes.getMaxWidth()/2 -10);
        listOfNodes.setTranslateY(Vue.HEIGHT/2 - listOfNodes.getMaxHeight()/2 -10);

        listOfNodes.setEffect(dropShadow);

        listOfNodes.setOnMouseEntered(event -> {
            canZoom = false;
        });

        listOfNodes.setOnMouseExited(event -> {
            canZoom = true;
        });

    }

    public void setAnchorPaneSize(double width, double height){
        anchorPane.setPrefSize(width, height);
    }

    public void addLine(double width,Position start, Position end){
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

        //set line width
        line.setStrokeWidth(width);
        //set z-index

        //add line to anchorPane
        anchorPane.getChildren().add(line);

//        line.setOpacity(0);
//
//        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), line);
//        fadeTransition.setFromValue(0);
//        fadeTransition.setToValue(1);
//        fadeTransition.play();
    }

    public void addCircle(double radius, String color, String name, Position position, int childCount, TreeNode treeNode){
        Circle newCircle = new Circle(radius);
        VBox vbox = new VBox();

        Label namelabel = new Label(name);
        Label childCountLabel = new Label(String.valueOf(childCount));
        //set label color

        //        childCountLabel.setStyle("-fx-font-size: " + radius/4);

        namelabel.setStyle("-fx-text-fill: white  ; -fx-font-size: " + radius/4);
        childCountLabel.setStyle("-fx-background-radius: 8px;-fx-text-fill: blue ; -fx-font-size: " + radius/5 + "px;-fx-background-color: white; ");
        //set label length
        namelabel.setPrefWidth(2*radius);
        childCountLabel.setPrefWidth(radius);


//        vbox.setPrefSize(2*radius, radius);
        vbox.setSpacing(10);

        //set label alignment
        namelabel.setAlignment(javafx.geometry.Pos.CENTER);
        childCountLabel.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        //set label position


        //set circle color
        newCircle.setStyle("-fx-fill:" + color + ";");
        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(position.getX() - radius);
        stackPane.setLayoutY(position.getY() - radius);
        stackPane.getChildren().add(newCircle);

        vbox.getChildren().add(namelabel);
        vbox.getChildren().add(childCountLabel);
        stackPane.getChildren().add(vbox);

        anchorPane.getChildren().add(stackPane);

        stackPane.setOpacity(1);


//        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), stackPane);
//        fadeTransition.setFromValue(0);
//        fadeTransition.setToValue(1);
//        fadeTransition.play();


        //add click event to circle
        stackPane.setOnMouseClicked(event -> {
            System.out.println("Clicked position" + event.getX() + " " + event.getY());
            anchorPane.getChildren().remove(popup);
            popup.setLayoutX(treeNode.getPosition().getX() - 50);
            popup.setLayoutY(treeNode.getPosition().getY() - 150);

            Label labelName = new Label("Name: " + name);
            Label labelChildCount = new Label("Child Count: " + childCount);
            Label labelLeaf = new Label("Leaf: " + treeNode.getNode().getLeafNodeString());
            Label labelTolorgLink = new Label("Total Org Link: " + treeNode.getNode().getTolorgLinkString());
            Label labelExtinct = new Label("Extinct: " + treeNode.getNode().getExtinctString());
            Label labelConfidence = new Label("Confidence: " + treeNode.getNode().getConfidenceString());
            Label labelPhylesis = new Label("Phylesis: " + treeNode.getNode().getPhylesisString());

            popup.getChildren().clear();
            popup.getChildren().add(labelName);
            popup.getChildren().add(labelChildCount);
            popup.getChildren().add(labelLeaf);
            popup.getChildren().add(labelTolorgLink);
            popup.getChildren().add(labelExtinct);
            popup.getChildren().add(labelConfidence);
            popup.getChildren().add(labelPhylesis);

            anchorPane.getChildren().add(popup);

//            VBox listOfNodesVbox = new VBox();
            listOfNodes.getChildren().clear();

            listOfNodes.getChildren().add(new Label("Child nodes of " + treeNode.getNode().getName()));

            if(treeNode.getLinks().isEmpty()){
                Label label = new Label("No child nodes");
                listOfNodes.getChildren().add(label);
            }else{
                ScrollPane listScrollPane = new ScrollPane();
                VBox listOfNodesVbox = new VBox();
                ArrayList<Position> positions;
                if(treeNode.getParent() == null) {
                    positions = PositionDraw.GetAroundPositions(treeNode.getPosition(), 200, treeNode.getLinks().size());
                }else{
                    positions = PositionDraw.GetAroundPositions(treeNode.getPosition(), treeNode.getParent().getPosition(), 200, treeNode.getLinks().size());
                }
                for (int i = 0; i < treeNode.getLinks().size(); i++) {
                    TreeNode childTreeNode = treeNode.getLinks().get(i);
                    childTreeNode.setPosition(positions.get(i));
                    HBox hbox = new HBox();
                    hbox.setSpacing(5);
                    hbox.setStyle("-fx-padding: 5px 3px;-fx-background-color: #a1a1a1;-fx-background-radius: 4px;-fx-border-style: solid;-fx-border-width: 1px;-fx-border-color: black;-fx-border-radius: 4");
                    Label label = new Label(childTreeNode.getNode().getName());
                    label.setOnMouseClicked(event1 -> {
                        currentTreeNode = childTreeNode;
                        DrawTreeNodeParentAndChild(childTreeNode);
                        focuseToPosition(childTreeNode.getPosition());
                    });
                    CheckBox checkBox = new CheckBox();
                    hbox.getChildren().add(checkBox);
                    hbox.getChildren().add(label);

                    listOfNodesVbox.getChildren().add(hbox);
                }


                listScrollPane.setContent(listOfNodesVbox);
                listOfNodes.getChildren().add(listScrollPane);
                Button button = new Button("Visualize");
                listOfNodes.getChildren().add(button);
            }



            stackPane_OUT.getChildren().remove(listOfNodes);
            stackPane_OUT.getChildren().add(listOfNodes);

        });

        stackPane.setOnScroll(event -> {
            if(event.getDeltaY() > 0){
                currentTreeNode = treeNode;
            }
        });


    }

    public void zoomIn(ScrollEvent e){
        if(canZoom) {
            if (currentTreeNode == null)
                return;
            DrawTreeNodeParentAndChild(currentTreeNode);
            focuseToPosition(currentTreeNode.getPosition());
        }

    }

    public void zoomOut(ScrollEvent e){
        if(canZoom) {
            if (currentTreeNode == null)
                return;
//
            if (currentTreeNode.getParent() != null) {
                DrawTreeNodeParentAndChild(currentTreeNode.getParent());
                focuseToPosition(currentTreeNode.getParent().getPosition());
                currentTreeNode = currentTreeNode.getParent();
            }
            focuseToPosition(currentTreeNode.getPosition());

        }
    }

    Position startPostion;
    Position currentPosition;

    public void clear(){

        anchorPane.getChildren().clear();
    }

    private void focuseToPosition(Position position){

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), anchorPane);
//        translateTransition.setFromX(anchorPane.getTranslateX());
//        translateTransition.setFromY(anchorPane.getTranslateY());
        translateTransition.setToX(Vue.WIDTH/2 - position.getX());
        translateTransition.setToY(Vue.HEIGHT/2 - position.getY());
        translateTransition.play();

    }

//    private void setTreeNodeChildPositions

    public void pressed(MouseEvent e){
        startPostion = new Position(e.getX(), e.getY());
    }

    public void dragged(MouseEvent e){
        currentPosition = new Position(e.getX(), e.getY());
        double deltaX = currentPosition.getX() - startPostion.getX();
        double deltaY = currentPosition.getY() - startPostion.getY();
        anchorPane.setTranslateX(anchorPane.getTranslateX() + deltaX);
        anchorPane.setTranslateY(anchorPane.getTranslateY() + deltaY);

        System.out.println("mouse position: " + e.getX() + " " + e.getY());
    }


    public void DrawTreeNodeParentAndChild(TreeNode treeNode){
        if(treeNode == null)
            return;
        clear();
        if(!treeNode.getLinks().isEmpty()){
            ArrayList<Position> positions;
            if(treeNode.getParent() == null){
                positions = PositionDraw.GetAroundPositions(treeNode.getPosition(), 200, treeNode.getLinks().size());
            }else{
                positions = PositionDraw.GetAroundPositions(treeNode.getPosition(), treeNode.getParent().getPosition(), 200, treeNode.getLinks().size());
            }

            for(int i = 0; i < treeNode.getLinks().size(); i++){
                treeNode.getLinks().get(i).setPosition(positions.get(i));
            }
        }

        if(treeNode.getParent() != null){
            addLine(1, treeNode.getPosition(), treeNode.getParent().getPosition());
            addCircle(80,"gray" , treeNode.getParent().getNode().getName(), treeNode.getParent().getPosition(), treeNode.getParent().getLinks().size(), treeNode.getParent());
        }

        int size = treeNode.getLinks().size();
        double radius = 40;
        if(size < 10){
            radius = 40;
        }else if(size < 20){
            radius = 30;
        }else if(size < 30){
            radius = 20;
        }else if(size < 40){
            radius = 15;
        }else{
            radius = 10;
        }

        for (TreeNode childTreeNode : treeNode.getLinks()) {
            addLine(1, treeNode.getPosition(), childTreeNode.getPosition());
            addCircle(radius,"gray" , childTreeNode.getNode().getName(), childTreeNode.getPosition(), childTreeNode.getLinks().size(), childTreeNode);
        }

        addCircle(50, "blue" , treeNode.getNode().getName(), treeNode.getPosition(), treeNode.getLinks().size(), treeNode);


    }

    public void scroll(ScrollEvent scrollEvent) {
        if(scrollEvent.getDeltaY() > 0)
            zoomIn(scrollEvent);
        else
            zoomOut(scrollEvent);
    }
}