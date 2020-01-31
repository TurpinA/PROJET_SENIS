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
        Main.main(args);
    }
}
