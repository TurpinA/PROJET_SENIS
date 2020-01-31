package main;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EmbeddedMain {
    public static void main(String[] args) throws FileNotFoundException, SQLException {

        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/?serverTimezone=UTC";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/baseTest.sql"));
        sr.runScript(reader);

        Main.main(args);
    }
}
