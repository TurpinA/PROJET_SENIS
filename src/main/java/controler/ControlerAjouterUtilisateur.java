package controler;

import model.Role;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControlerAjouterUtilisateur {

    public static final String FX_BORDER_COLOR_RED = "-fx-border-color: red ;";
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
        role.getItems().addAll(Role.MANAGER,Role.VENDEUR);
    }

    public void ajouter() throws SQLException {
        boolean champValide = testRegex();

        if(champValide) {
            ActionBD.createUtilisateur(nom.getText(),prenom.getText(),Integer.valueOf(age.getText()),role.getValue(),mail.getText(),motdepasse.getText());

            Stage stage = (Stage) ajouterButton.getScene().getWindow();
            stage.close();
        }
    }

    public void annuler() {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }

    public boolean testRegex(){
        boolean valide = true;

        if(!testRegexNom(nom.getText()) || nom.getText().isEmpty()) {
            nom.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            nom.setStyle("");

        if(!testRegexPrenom(prenom.getText()) || prenom.getText().isEmpty()) {
            prenom.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            prenom.setStyle("");

        if(!testRegexAge(age.getText()) || age.getText().isEmpty()) {
            age.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            age.setStyle("");

        if(role.getValue() == null) {
            role.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            role.setStyle("");

        if(!testRegexMail(mail.getText()) || mail.getText().isEmpty() || !uniqueArobase(mail.getText())) {
            mail.setStyle(FX_BORDER_COLOR_RED);
            valide = false;
        }
        else
            mail.setStyle("");

        if(!testRegexMotdepasse(motdepasse.getText()) || motdepasse.getText().isEmpty()) {
            motdepasse.setStyle(FX_BORDER_COLOR_RED);
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

    public boolean testRegexNom(String nom){
        return Pattern.matches("[A-Za-z \\-']++$", nom);
    }
    public boolean testRegexPrenom(String prenom){
        return Pattern.matches("[A-Za-z \\-']++$", prenom);
    }
    public boolean testRegexAge(String age){
        if(Pattern.matches("[0-9]++$", age)) {
            return Integer.valueOf(age) >= 0;
        }
        return false;
    }
    public boolean testRegexMail(String mail){
        return Pattern.matches("[A-Za-z0-9 @\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\.\\{\\|\\}\\~]++$", mail);
    }
    public boolean testRegexMotdepasse(String motdepasse){
        return Pattern.matches("[A-Za-z0-9 @\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\.\\{\\|\\}\\~]++$", motdepasse);
    }
}
