package controler;

import model.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControlerAjouterUtilisateur {

    @FXML   private TextField nom;
    @FXML   private TextField prenom;
    @FXML   private TextField age;
    @FXML   private ComboBox<Role> role;
    @FXML   private TextField mail;
    @FXML   private PasswordField motdepasse;

    @FXML   private Button ajouterButton;
    @FXML   private Button annulerButton;

    @FXML
    public void initialize() {
        role.getItems().addAll(Role.Manager,Role.Vendeur);
    }

    public void ajouter(ActionEvent actionEvent) throws SQLException {
        boolean champValide = testRegex();

        if(champValide) {
            ActionBD.CreateUtilisateur(nom.getText(),prenom.getText(),Integer.valueOf(age.getText()),role.getValue(),mail.getText(),motdepasse.getText());

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

        if(!Pattern.matches("[A-Za-z \\-]++$", nom.getText()) || nom.getText().isEmpty()) {
            nom.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            nom.setStyle("");

        if(!Pattern.matches("[A-Za-z \\-]++$", prenom.getText()) || prenom.getText().isEmpty()) {
            prenom.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            prenom.setStyle("");

        if(!Pattern.matches("[0-9]++$", age.getText()) || age.getText().isEmpty() || Integer.valueOf(age.getText())<0) {
            age.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            age.setStyle("");

        if(role.getValue() == null) {
            role.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            role.setStyle("");

        if(!Pattern.matches("[A-Za-z0-9 @\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\.\\{\\|\\}\\~]++$", mail.getText()) || mail.getText().isEmpty() || !uniqueArobase(mail.getText())) {
            mail.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            mail.setStyle("");

        if(!Pattern.matches("[A-Za-z0-9 @\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\.\\{\\|\\}\\~]++$", motdepasse.getText()) || motdepasse.getText().isEmpty()) {
            motdepasse.setStyle("-fx-border-color: red ;");
            valide = false;
        }
        else
            motdepasse.setStyle("");

        return valide;
    }

    public boolean uniqueArobase(String mail) {
        boolean vu = false;
        for(int i = 0; i <= mail.length()-1; i++) {
            if(mail.charAt(i) == '@')
            {
                if(vu)
                    return false;
                else
                    vu = true;
            }
        }

        return vu;
    }
}
