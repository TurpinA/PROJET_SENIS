package main;

import controler.ActionBD;
import controler.ControlerConnexion;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConnexionTest {

    private List<Utilisateur>  ArrayListUtilisateur = new ArrayList<>();
    private ControlerConnexion controlerConnexion = new ControlerConnexion();

    @Before
    public void before(Scenario scenario) throws SQLException, FileNotFoundException {
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/?serverTimezone=UTC&useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
        ScriptRunner sr = new ScriptRunner(con);
        sr.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/viderLaBase.sql"));
        sr.runScript(reader);
    }
    @Given("des identifiants :")
    public void des_identifiants(io.cucumber.datatable.DataTable dataTable) throws SQLException {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> map :
                list) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom("");
            utilisateur.setAge(0);
            utilisateur.setPrenom("");
            utilisateur.setRole(Role.MANAGER);
            utilisateur.setRayon(null);
            utilisateur.setMail(map.get("mail"));
            utilisateur.setMotDePasse(map.get("motdepasse"));

            utilisateur = ActionBD.createUtilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAge(), utilisateur.getRole(), utilisateur.getMail(), utilisateur.getMotDePasse());

            ArrayListUtilisateur.add(utilisateur);
        }
    }

    @When("je clique sur le bouton connexion")
    public void je_clique_sur_le_bouton_connexion() throws SQLException {

    }
    @Then("Je me connecte à l'application")
    public void je_me_connecte_à_l_application() throws SQLException {
        assertNotNull(controlerConnexion.checkConnexion(ArrayListUtilisateur.get(0).getMail(),ArrayListUtilisateur.get(0).getMotDePasse()));
    }
}
