module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires java.desktop;
//    requires org.xerial.sqlitejdbc;
    opens gui to javafx.fxml;
    opens main to javafx.fxml;
    exports gui;
    exports main;
}