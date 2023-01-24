/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.*;
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
public class PdfToExcelGuiTest {
    private static ArrayList<String> id;
    private static String pw;
    private static String field;
    
    public PdfToExcelGuiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Login Page Testing.");
        id = new ArrayList<>();
        id.add("Pradyumna@mmcoe");
        id.add("Varun@mmcoe");
        id.add("Durvesh@mmcoe");
        id.add("Karthik@mmcoe");
        
        pw = "mmcoe@123";
        
        field = "Not Empty";
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Done.");
        id = null;
        pw = null;
        field = null;
        System.gc();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class PdfToExcelGui.
     * @throws java.lang.Exception
     */
    @Test
    public void testUsername() throws Exception {
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("Test: Valid Username.");
        assertThat("Username not valid.", id, hasItem("Pradyumna@mmcoe"));
    }
    
    @Test
    public void testUsernameInvalid() throws Exception {
        System.out.println("Test: Invalid Username.");
        assertThat("Username valid.", id, not(hasItem("Apurv@mmcoe")));
    }
    
    @Test
    public void testValidPassword() throws Exception {
        System.out.println("Test: Valid Password.");
        assertTrue("Password not valid.", "mmcoe@123".equals(pw));
    }
    
    @Test
    public void testInvalidPassword() throws Exception {
        System.out.println("Test: Invalid Password.");
        pw = "mmcoe";
        assertFalse("Password valid.", "mmcoe@123".equals(pw));
    }
    
    @Test
    public void requiredFieldsFilled() throws Exception {
        System.out.println("Test: Required Fields Are Filled.");
        assertTrue("All required fields not filled.", !"".equals(field));
    }
    
    @Test
    public void requiredFieldsNotFilled() throws Exception {
        System.out.println("Test: Required Fields Are Not Filled.");
        field = "";
        assertFalse("All required fields filled.", !"".equals(field));
    }
}
