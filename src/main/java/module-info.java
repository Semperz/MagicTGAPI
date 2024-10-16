module edu.badpals.starwarsapi {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.badpals.starwarsapi to javafx.fxml;
    exports edu.badpals.starwarsapi;
}