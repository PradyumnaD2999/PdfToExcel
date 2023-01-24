/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Deshpande
 */
public class DatabaseConnectionTest {
    private static String DB_URL;
    private static String USER;
    private static String PASS;
    private static Connection conn;
    
    public DatabaseConnectionTest() {
        DB_URL = "jdbc:mysql://localhost/abcd";
        USER = "root";
        PASS = null;
        conn = null;
    }
    
    @BeforeClass
    public static void setUpClass() throws ClassNotFoundException {
        System.out.println("JDBC Testing.");
        Class.forName("com.mysql.cj.jdbc.Driver");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Done.");
        DB_URL = null;
        USER = null;
        PASS = null;
        conn = null;
        System.gc();
    }
    
    @Before
    public void setUp() {
        conn = null;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of connectDB method, of class DatabaseConnection.
     * @throws java.sql.SQLException
     */
    @Test
    public void testPassword() throws SQLException {
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("Test: Valid Password. Connected.");
        PASS = "CaputDraconis@99";
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        assertTrue("Invalid Password.", conn != null);
    }
    
    @Test
    public void testPasswordInvalid() {
        try {
            System.out.println("Test: Invalid Password. Not connected.");
            PASS = "";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            assertFalse("Valid Password.", conn != null);
        }
    }
}
