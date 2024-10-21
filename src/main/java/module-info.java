module edu.badpals.magictg {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;
    requires java.xml;
    exports edu.badpals.magictg.model;

    opens edu.badpals.magictg.model to com.fasterxml.jackson.databind;
    opens edu.badpals.magictg to javafx.fxml;
    exports edu.badpals.magictg;
    exports edu.badpals.magictg.controller;
    opens edu.badpals.magictg.controller to javafx.fxml;
}