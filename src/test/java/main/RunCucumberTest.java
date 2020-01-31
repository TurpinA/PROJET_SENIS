package main;

import controler.Connexion;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},strict = true,  features = {"src/test/resources/Scenario/"})
public class RunCucumberTest {
    @BeforeClass
    public static void beforeScenario() throws SQLException, FileNotFoundException {
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/?serverTimezone=UTC&useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
        ScriptRunner sr = new ScriptRunner(con);
        sr.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/baseTest.sql"));
        sr.runScript(reader);
        Connexion connexion = new Connexion();
        connexion.setConnexion("127.0.0.1","3306","baseTest", "root", "",connexion);
    }
}
