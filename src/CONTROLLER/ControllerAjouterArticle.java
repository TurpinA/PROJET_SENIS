package CONTROLLER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControllerAjouterArticle {

    @FXML   private TextField nom;
    @FXML   private TextField reference;
    @FXML   private TextField prix;
    @FXML   private TextField quantite;
    @FXML   private TextArea description;

    @FXML   private Button ajouterButton;
    @FXML   private Button annulerButton;

    public void ajouter(ActionEvent actionEvent) throws SQLException {

        boolean champValide = testRegex();

        if(champValide) {
            ActionBD.CreateArticle(nom.getText(), reference.getText(), Double.valueOf(prix.getText()), Integer.valueOf(quantite.getText()), description.getText(), null, Main.rayonAffiche);

            Stage stage = (Stage) ajouterButton.getScene().getWindow();
            stage.close();
        }
    }

    public void annuler(ActionEvent actionEvent) {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }

    public boolean testRegex(){
        boolean valide = true;

        if(!Pattern.matches("[A-Za-z 0-9]++$", nom.getText()) || nom.getText().isEmpty()) {
            nom.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            nom.setStyle("");

        if(!Pattern.matches("[A-Za-z0-9]++$", reference.getText()) || reference.getText().isEmpty()) {
            reference.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            reference.setStyle("");

        if(!Pattern.matches("[0-9\\.]++$", prix.getText()) || prix.getText().isEmpty()) {
            prix.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else {
            try {
                Double.parseDouble(prix.getText());
                prix.setStyle("");
            }
            catch(NumberFormatException e)
            {
                prix.setStyle("-fx-border-color: red ;");
                valide = false;
            }
        }
        if(!Pattern.matches("[0-9]++$", quantite.getText()) || quantite.getText().isEmpty() || Integer.valueOf(quantite.getText())<0) {
            quantite.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            quantite.setStyle("");

        if(description.getText().isEmpty()) {
            description.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            description.setStyle("");

        return valide;
    }
}
