package CONTROLLER;

import MODEL.Article;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import static java.lang.String.valueOf;

public class ControllerModifierArticle {

    public static Article articleSelectione;

    @FXML   private TextField nom;
    @FXML   private TextField reference;
    @FXML   private TextField prix;
    @FXML   private TextField quantite;
    @FXML   private TextField description;

    @FXML   private Button modifierButton;
    @FXML   private Button annulerButton;

    @FXML
    public void initialize() throws SQLException {
        nom.setText(articleSelectione.getNom());
        reference.setText(articleSelectione.getReference());
        prix.setText(valueOf(articleSelectione.getPrix()));
        quantite.setText(valueOf(articleSelectione.getQuantite()));
        description.setText(articleSelectione.getDescription());

    }

    public void modifier(ActionEvent actionEvent) {
    }

    public void annuler(ActionEvent actionEvent) {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }
}
