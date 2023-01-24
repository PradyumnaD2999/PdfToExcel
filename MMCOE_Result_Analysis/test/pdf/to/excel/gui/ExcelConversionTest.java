/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
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
public class ExcelConversionTest {
    private static File subCode;
    private static File pdf;
    private static PdfDocument doc;
    
    public ExcelConversionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException, PdfException {
        System.out.println("Excel conversion testing.");
        subCode = new File("subject codes.txt");
        pdf = new File("E:\\Pradyumna\\Backup\\MMCOE Result PDFs\\2020\\BE_Comp 2015.pdf");
        doc = new PdfDocument();                            //PDFOne API's class's object
        doc.load("E:\\Pradyumna\\Backup\\MMCOE Result PDFs\\2020\\BE_Comp 2015.pdf");
    }
    
    @AfterClass
    public static void tearDownClass() throws IOException, PdfException {
        System.out.println("Done.");
        subCode = null;
        pdf = null;
        doc.close();
        System.gc();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of saveAsExcel method, of class ExcelConversion.
     */
    @Test
    public void checkFileExists() {
        System.out.println("Test: Subject Code file exists.");
        assertTrue("Text file doesn't exist.", subCode.exists());
    }
    
    @Test
    public void checkFileNotEmpty() {
        System.out.println("Test: Subject Code file not empty.");
        assertTrue("No codes in text file.", subCode.length() != 0);
    }
    
    @Test
    public void checkPDFLoaded() {
        System.out.println("Test: PDF loaded.");
        assertTrue("PDF is not loaded.", doc.isLoaded());
    }
    
    @Test
    public void checkPDFNotEmpty() {
        System.out.println("Test: PDF not empty.");
        assertTrue("PDF is empty.", pdf.length() != 0);
    }
}
