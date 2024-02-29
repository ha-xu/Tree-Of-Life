module org.example.treeoflife {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.treeoflife to javafx.fxml;
    exports org.example.treeoflife;
}