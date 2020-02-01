package main;

import controler.ActionBD;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import model.Role;
import model.Utilisateur;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

public class GestionUtilisateurTest {

    private List<Utilisateur>  ArrayListUtilisateur = new ArrayList<>();
    private List<Utilisateur> ExtractUtilisateur = new ArrayList<>();

    //|nom      |prenom |age |role    |email                    |mot de passe | id (generated) |
    //|tem      |bob    |18  |MANAGER |bob@outlook.fr           |password     | 1              |
    //|pivot    |bernard|54  |VENDEUR |bernard.pivot@outlook.fr |password     | 2              |
    //|sinclard |paul   |68  |VENDEUR |paul.sinclard@outlook.fr |password     | 3              |


    @Before
    public void before(Scenario scenario) throws SQLException, FileNotFoundException {
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/?serverTimezone=UTC&useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
        ScriptRunner sr = new ScriptRunner(con);
        sr.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/viderLaBase.sql"));
        sr.runScript(reader);
    }
    @Given("un utilisateur :")
    public void un_utilisateur(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> map :
                list) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(map.get("nom"));
            utilisateur.setPrenom(map.get("prenom"));
            utilisateur.setAge(Integer.valueOf(map.get("age")));
            utilisateur.setRole(Role.valueOf(map.get("role")));
            utilisateur.setMail(map.get("email"));
            utilisateur.setMotDePasse(map.get("mot de passe"));
            ArrayListUtilisateur.add(utilisateur);
        }
    }

    @When("je clique sur le bouton ajouter un utilisateur")
    public void je_clique_sur_le_bouton_ajouter_un_utilisateur() throws SQLException {
        for (Utilisateur utilisateur : ArrayListUtilisateur) {
            ExtractUtilisateur.add(ActionBD.createUtilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAge(), utilisateur.getRole(), utilisateur.getMail(), utilisateur.getMotDePasse()));
        }
    }

    @Then("Je vérifie les entrées et j'ajoute l'utilisateur à la base de donnée")
    public void je_vérifie_les_entrées_et_j_ajoute_l_utilisateur_à_la_base_de_donnée() throws SQLException {
        for (Utilisateur utilisateur : ExtractUtilisateur) {
            assertNotNull(model.ExtractionData.rechercheUtilisateurParID(utilisateur.getId()));
            assertNotNull(model.ExtractionData.rechercheUtilisateurParMail(utilisateur.getMail()));
        }
    }

}