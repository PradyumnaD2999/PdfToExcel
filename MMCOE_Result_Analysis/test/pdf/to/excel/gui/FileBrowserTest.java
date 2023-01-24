/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import static org.hamcrest.CoreMatchers.endsWith;
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
public class FileBrowserTest {
    private static JFileChooser fc;
    private static Path path;
    private static String ext;
    private static String[] ext1;
    private static String fileType;
    private static int i;
    
    public FileBrowserTest() {
        ext1 = null;
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println("File Format Selection Testing.");
        fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");   //filter to select pdf files only
        fc.setAcceptAllFileFilterUsed(false);                                               //select any type of files option disabled
        fc.setFileFilter(filter);
        i = fc.showOpenDialog(null);    
        if(i == JFileChooser.APPROVE_OPTION){                                           //when open button is clicked in file browser   
            ext = fc.getSelectedFile().getAbsolutePath();
            path = FileSystems.getDefault().getPath(ext);
            fileType = Files.probeContentType(path);
        }  
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("Done.");
        fc = null;
        path = null;
        ext = null;
        ext1 = null;
        fileType = null;
        i = 0;
        System.gc();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of fileBrowser method, of class FileBrowser.
     */
    @Test
    public void testFileExtension() {
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("Test: File Extension.");
        if(i == JFileChooser.APPROVE_OPTION){                                           //when open button is clicked in file browser   
            ext1 = ext.split("\\.");
            assertThat("Selected File is not a PDF.", ext1[ext1.length - 1], endsWith("pdf"));
        }  
    }
    
    @Test
    public void testFileType() {
        System.out.println("Test: File Content Type Valid.");
        assertTrue("File Type is not PDF.", fileType.equals("application/pdf"));
    }
    
    @Test
    public void testFileTypeInvalid() {
        System.out.println("Test: File Content Type Invalid.");
        fileType = "application/msword";
        assertFalse("File Type is PDF.", fileType.equals("application/pdf"));
    }
}
