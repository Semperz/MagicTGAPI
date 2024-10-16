module edu.badpals.starwarsapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;


    opens edu.badpals.starwarsapi to javafx.fxml;
    exports edu.badpals.starwarsapi;
}