package edu.badpals.starwarsapi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StarWarsController {

    @FXML
    private Button btnBuscar;

    @FXML
    void btnClicked(ActionEvent event) {
            System.out.println("Clicked");
    }

}
