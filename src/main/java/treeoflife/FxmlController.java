package treeoflife;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import treeoflife.modele.Position;
import treeoflife.modele.PositionDraw;
import treeoflife.modele.TreeNode;

import java.io.File;
import java.util.ArrayList;

public class FxmlController {

    private TreeNode currentTreeNode;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane stackPane_OUT;

    private VBox popup;
    private VBox listOfNodes;

    private VBox detailBox;
    private VBox toolsBox;

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
        dropShadow.setColor(Color.color(0,0,0,0.5));
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

        //initialize detail box pour afficher les détails du noeud
        detailBox = new VBox();
        detailBox.setStyle("-fx-background-color: white;-fx-background-radius: 8px;-fx-opacity: 0.9;-fx-border-radius: 8px;-fx-border-color: black;-fx-border-width: 1px;-fx-padding: 10px;");
        detailBox.setMaxSize(220,320);
        detailBox.setTranslateX(Vue.WIDTH/2 - detailBox.getMaxWidth()/2 -10);
        detailBox.setTranslateY(0-Vue.HEIGHT/2 + detailBox.getMaxHeight()/2 +10);
        detailBox.setEffect(dropShadow);
        detailBox.setAlignment(Pos.TOP_CENTER);

        //initialize tools box pour afficher les outils
        toolsBox = new VBox();
        toolsBox.setStyle("-fx-background-color: white;-fx-background-radius: 8px;-fx-opacity: 0.9;-fx-border-radius: 8px;-fx-border-color: black;-fx-border-width: 1px;-fx-padding: 10px;");
        toolsBox.setMaxSize(120,260);
        toolsBox.setTranslateX(0-Vue.WIDTH/2 + toolsBox.getMaxWidth()/2 +10);
        toolsBox.setTranslateY(Vue.HEIGHT/2 - toolsBox.getMaxHeight()/2 -10);

        toolsBox.setEffect(dropShadow);
        toolsBox.setAlignment(Pos.TOP_CENTER);

        //Zoom buttons
        Button zoomInButton = new Button("Zoom In");
        zoomInButton.setOnMouseClicked(event -> {
            ZoomWindowIn();
        });

        Button zoomOutButton = new Button("Zoom Out");
        zoomOutButton.setOnMouseClicked(event -> {
            ZoomWindowOut();
        });

        toolsBox.getChildren().add(zoomInButton);
        toolsBox.getChildren().add(zoomOutButton);

        //clear button
        Button clearButton = new Button("Clear Window");
        clearButton.setOnMouseClicked(event -> {
            stackPane_OUT.getChildren().remove(listOfNodes);
            stackPane_OUT.getChildren().remove(detailBox);
            anchorPane.getChildren().remove(popup);
        });
        toolsBox.getChildren().add(clearButton);
        stackPane_OUT.getChildren().add(toolsBox);

    }

    public void setAnchorPaneSize(double width, double height){
        anchorPane.setPrefSize(width, height);
    }

    public void addLine(double width,String color, Position start, Position end){
        //create a line
        Line line = new Line();
        //set line start position
        line.setStartX(start.getX());
        line.setStartY(start.getY());
        //set line end position
        line.setEndX(end.getX());
        line.setEndY(end.getY());
        //set line color
        line.setStyle("-fx-stroke: " + color + ";");

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


        namelabel.setStyle("-fx-text-fill: white  ; -fx-font-size: " + radius/4);
        childCountLabel.setStyle("-fx-background-radius: 8px;-fx-text-fill: blue ; -fx-font-size: " + radius/5 + "px;-fx-background-color: white; ");
        //set label length
        namelabel.setPrefWidth(2*radius);
        childCountLabel.setPrefWidth(radius);


//        vbox.setPrefSize(2*radius, radius);
        vbox.setSpacing(10);

        //set label alignment
        namelabel.setAlignment(Pos.CENTER);
        childCountLabel.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);

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
                    //if checkbox is checked, highlight the node
                    CheckBox checkBox = new CheckBox();
                    if(childTreeNode.isHighlight()){
                        System.out.println(childTreeNode.isHighlight());
                        checkBox.setSelected(true);}
                    checkBox.setOnAction(event1 -> {
                        childTreeNode.setHighlight(checkBox.isSelected());
                        System.out.println(checkBox.isSelected());
//                        DrawTreeNodeParentAndChild(treeNode);
                    });
                    hbox.getChildren().add(checkBox);
                    hbox.getChildren().add(label);

                    listOfNodesVbox.getChildren().add(hbox);
                }


                listScrollPane.setContent(listOfNodesVbox);
                listOfNodes.getChildren().add(listScrollPane);
                Button button = new Button("Visualize");

                button.setOnMouseClicked(event1 -> {
                    DrawTreeNodeParentAndChild(treeNode);
                    focuseToPosition(treeNode.getPosition());
                    currentTreeNode = treeNode;
                    System.out.println("Visualize");
                });

                listOfNodes.getChildren().add(button);

            }



            stackPane_OUT.getChildren().remove(listOfNodes);
            stackPane_OUT.getChildren().add(listOfNodes);
            stackPane_OUT.getChildren().remove(detailBox);
            stackPane_OUT.getChildren().add(detailBox);
            detailBox.getChildren().clear();
            //add label to detail box
            Label label = new Label(treeNode.getNode().getName());
            //set label position center
            label.setAlignment(Pos.CENTER);
            //set label color
            label.setStyle("-fx-text-fill: black; -fx-font-size: 18px");
            detailBox.getChildren().add(label);

            //add description of the node
            Label descriptionTitle = new Label("Description");
            descriptionTitle.setStyle("-fx-text-fill: black; -fx-font-size: 16px");
            detailBox.getChildren().add(descriptionTitle);

            //addInputField
            TextArea descriptionField = new TextArea();
            descriptionField.setPromptText("Description");
            descriptionField.setText(treeNode.getDescription());
            //set description field height
            //set description field line
            //set description change line
            descriptionField.setWrapText(true);
//            descriptionField.setPrefColumnCount(4);
            descriptionField.setPrefRowCount(4);
            descriptionField.setPrefHeight(100);
            //when finish editing, save the description
            descriptionField.setOnKeyReleased(event1 -> {
                treeNode.setDescription(descriptionField.getText());
            });


            detailBox.getChildren().add(descriptionField);

            //add image object
            Label imageTitle = new Label("Image");
            imageTitle.setStyle("-fx-text-fill: black; -fx-font-size: 16px");
            detailBox.getChildren().add(imageTitle);

            ImageView imageView = new ImageView();
            if(treeNode.getImageurl() != null){
                Image image = new Image("file:" + treeNode.getImageurl());
                imageView.setImage(image);
            }
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            detailBox.getChildren().add(imageView);

            //add upload image button
            Button uploadButton = new Button("Upload Image");
            ImageView finalImageView = imageView;
            uploadButton.setOnMouseClicked(event1 -> {
                System.out.println("Upload Image");
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

                File selectedFile = fileChooser.showOpenDialog(null);


                // 处理用户选择的文件
                if (selectedFile != null) {
                    System.out.println("已选择文件：" + selectedFile.getAbsolutePath());
                    // 在这里可以进一步处理所选文件，比如读取内容或进行其他操作
                    Image newImage = new Image("file:" + selectedFile.getAbsolutePath());
                    treeNode.setImageurl(selectedFile.getAbsolutePath());
                    imageView.setImage(newImage);

                } else {
                    System.out.println("未选择文件");
                }
            });

            detailBox.getChildren().add(uploadButton);

//            //add button to save description
//            Button saveButton = new Button("Save ");
//            saveButton.setOnMouseClicked(event1 -> {
//                treeNode.setDescription(descriptionField.getText());
//            });
//
//            detailBox.getChildren().add(saveButton);
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

    public void ZoomWindowIn(){
        //Zoom with transition
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), anchorPane);
        scaleTransition.setToX(anchorPane.getScaleX() * 1.3);
        scaleTransition.setToY(anchorPane.getScaleY() * 1.3);
        scaleTransition.play();
    }

    public void ZoomWindowOut(){
        //Zoom with transition
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), anchorPane);
        scaleTransition.setToX(anchorPane.getScaleX() * 0.7);
        scaleTransition.setToY(anchorPane.getScaleY() * 0.7);
        scaleTransition.play();
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
        translateTransition.setToX(anchorPane.getWidth()/2 - position.getX());
        translateTransition.setToY(anchorPane.getHeight()/2 - position.getY());
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

        //draw parent node
        TreeNode parentTreeNode = treeNode.getParent();
        if(parentTreeNode != null)
            addLine(1,"black", treeNode.getPosition(), parentTreeNode.getPosition());
        int level = 1;
        while(parentTreeNode != null){
            if(parentTreeNode.getParent()!= null){
                addLine(1,"black", parentTreeNode.getPosition(), parentTreeNode.getParent().getPosition());
            }
            addCircle(50 + 5*level,"black" , parentTreeNode.getNode().getName(), parentTreeNode.getPosition(), parentTreeNode.getLinks().size(), parentTreeNode);
            parentTreeNode = parentTreeNode.getParent();
            level++;
        }


        //choose the radius of the circle
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

        //draw child nodes
        for (TreeNode childTreeNode : treeNode.getLinks()) {
            if(childTreeNode.isHighlight()) {
                addLine(1,"yellow", treeNode.getPosition(), childTreeNode.getPosition());
                addCircle(radius,"orange" , childTreeNode.getNode().getName(), childTreeNode.getPosition(), childTreeNode.getLinks().size(), childTreeNode);
            }else {
                addLine(1,"black", treeNode.getPosition(), childTreeNode.getPosition());

                if(childTreeNode.getLinks().size() > 0)
                    addCircle(radius,"blue" , childTreeNode.getNode().getName(), childTreeNode.getPosition(), childTreeNode.getLinks().size(), childTreeNode);
                else
                    addCircle(radius,"gray" , childTreeNode.getNode().getName(), childTreeNode.getPosition(), childTreeNode.getLinks().size(), childTreeNode);
            }
        }

        //draw current node
        addCircle(50, "green" , treeNode.getNode().getName(), treeNode.getPosition(), treeNode.getLinks().size(), treeNode);




    }

    public void scroll(ScrollEvent scrollEvent) {
        if(scrollEvent.getDeltaY() > 0)
            zoomIn(scrollEvent);
        else
            zoomOut(scrollEvent);
    }


}