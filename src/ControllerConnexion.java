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

public class ControllerConnexion {

    @FXML
    private Button buttonConnexion;
    @FXML
    private TextField nomUtilisateur;
    @FXML
    private PasswordField motDePasse;


    public void buttonClick(ActionEvent event) throws IOException
    {
        buttonConnexion.setText("TESTTESTTEST");
        FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/Gestion d'un rayon.fxml").toURI().toURL());
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
