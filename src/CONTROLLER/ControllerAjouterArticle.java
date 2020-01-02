package CONTROLLER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerAjouterArticle {

    @FXML   private TextField nom;
    @FXML   private TextField reference;
    @FXML   private TextField prix;
    @FXML   private TextField quantite;
    @FXML   private TextField description;

    @FXML   private Button ajouterButton;
    @FXML   private Button annulerButton;
    public void ajouter(ActionEvent actionEvent) {
    }

    public void annuler(ActionEvent actionEvent) {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }
}
