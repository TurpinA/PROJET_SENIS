package controler;

import model.Role;
import model.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControlerModifierUtilisateur {

    public static final String FX_BORDER_COLOR_RED = "-fx-border-color: red ;";
    public static Utilisateur utilisateurSelectione;

    @FXML   private TextField nom;
    @FXML   private TextField prenom;
    @FXML   private TextField age;
    @FXML   private ComboBox<Role> role;
    @FXML   private TextField mail;
    @FXML   private PasswordField motdepasse;

    @FXML   private Button modifierButton;
    @FXML   private Button annulerButton;

    @FXML
    public void initialize() {
        role.getItems().addAll(Role.MANAGER,Role.VENDEUR);
        nom.setText(utilisateurSelectione.getNom());
        prenom.setText(utilisateurSelectione.getPrenom());
        age.setText(String.valueOf(utilisateurSelectione.getAge()));
        role.setValue(utilisateurSelectione.getRole());
        mail.setText(utilisateurSelectione.getMail());
        motdepasse.setText(utilisateurSelectione.getMotDePasse());
    }

    public void modifier() throws SQLException {
        boolean champValide = testRegex();

        if(champValide) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(utilisateurSelectione.getId());
            utilisateur.setNom(nom.getText());
            utilisateur.setMotDePasse(motdepasse.getText());
            utilisateur.setMail(mail.getText());
            utilisateur.setRole(role.getValue());
            utilisateur.setAge(Integer.valueOf(age.getText()));
            utilisateur.setPrenom(prenom.getText());
            ActionBD.modifierUtilisateur(utilisateur);

            Stage stage = (Stage) modifierButton.getScene().getWindow();
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
        return Pattern.matches("[A-Za-z \\-]++$", nom);
    }
    public boolean testRegexPrenom(String prenom){
        return Pattern.matches("[A-Za-z \\-]++$", prenom);
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
