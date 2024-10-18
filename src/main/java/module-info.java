module edu.badpals.starwarsapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens edu.badpals.starwarsapi to javafx.fxml;
    exports edu.badpals.starwarsapi;
    exports edu.badpals.starwarsapi.controller;
    opens edu.badpals.starwarsapi.controller to javafx.fxml;
}