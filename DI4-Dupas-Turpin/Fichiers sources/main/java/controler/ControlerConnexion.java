package controler;

import model.Role;
import model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlerConnexion {

    public static final String LOGGER = "logger";
    @FXML
    private Button buttonConnexion;
    @FXML
    private TextField mail;
    @FXML
    private PasswordField motDePasse;
    @FXML
    private Label errorLabel;

    public static Connexion connexion = null;

    public void buttonClick(ActionEvent event) throws IOException, SQLException {
        Utilisateur utilisateur = checkConnexion(mail.getText(),motDePasse.getText());

        if(utilisateur != null) {
            main.Main.utilisateurConnecte = utilisateur;

            if(main.Main.utilisateurConnecte.getRole().equals(Role.VENDEUR)) {
                main.Main.rayonAffiche = model.ExtractionData.rechercheRayonParResponsable(utilisateur);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/Gestion d'un rayon.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
            else
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/Affichage des rayons.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }

        else
            errorLabel.setVisible(true);
    }

    public Utilisateur checkConnexion(String email, String motDePasse) throws SQLException {

        Utilisateur utilisateur = null;
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM utilisateur WHERE email = '" + email + "' AND motdepasse = '" + motDePasse + "'")) {
            if (result.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(result.getInt(1));
                utilisateur.setNom(result.getString(2));
                utilisateur.setPrenom(result.getString(3));
                utilisateur.setAge(result.getInt(4));
                utilisateur.setRole(Role.valueOf(result.getString(5)));
                utilisateur.setMail(result.getString(6));
                utilisateur.setMotDePasse(result.getString(7));
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }

        connexion.stopConnexion();


        return utilisateur;
    }
}
