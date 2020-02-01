package controler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControlerAfficherUtilisateur {

    @FXML   private Label nomPrenom;
    @FXML   private Label id;
    @FXML   private Label age;
    @FXML   private Label role;
    @FXML   private Label mail;

    @FXML
    public void initialize(){
        nomPrenom.setText(main.Main.utilisateurConnecte.getPrenom() + " " + main.Main.utilisateurConnecte.getNom());
        id.setText(String.valueOf(main.Main.utilisateurConnecte.getId()));
        age.setText(String.valueOf(main.Main.utilisateurConnecte.getAge()));
        role.setText(String.valueOf(main.Main.utilisateurConnecte.getRole()));
        mail.setText(main.Main.utilisateurConnecte.getMail());

    }
}
