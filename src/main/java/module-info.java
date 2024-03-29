module org.example.treeoflife {
    requires javafx.controls;
    requires javafx.fxml;


    opens treeoflife to javafx.fxml;
    exports treeoflife;
}