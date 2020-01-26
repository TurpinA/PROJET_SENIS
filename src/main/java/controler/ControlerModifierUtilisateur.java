package controler;

import model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Utilisateur;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ControlerModifierUtilisateur {

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
        role.getItems().addAll(Role.Manager,Role.Vendeur);
        nom.setText(utilisateurSelectione.getNom());
        prenom.setText(utilisateurSelectione.getPrenom());
        age.setText(String.valueOf(utilisateurSelectione.getAge()));
        role.setValue(utilisateurSelectione.getRole());
        mail.setText(utilisateurSelectione.getMail());
        motdepasse.setText(utilisateurSelectione.getMotDePasse());
    }

    public void modifier(ActionEvent actionEvent) throws SQLException {
        boolean champValide = testRegex();

        if(champValide) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setID(utilisateurSelectione.getID());
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
