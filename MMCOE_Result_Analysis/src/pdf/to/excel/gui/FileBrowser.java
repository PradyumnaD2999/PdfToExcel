/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Deshpande
 */
public class FileBrowser {
    protected void fileBrowser(ActionEvent e,JButton browse,JTextField t,JTextField fileName){
         if(e.getSource() == browse){    
                JFileChooser fc = new JFileChooser(); 
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");   //filter to select pdf files only
                fc.setAcceptAllFileFilterUsed(false);                           //select any type of files option disabled
                fc.setFileFilter(filter);
                int i = fc.showOpenDialog(null);    
                if(i == JFileChooser.APPROVE_OPTION){                           //when open button is clicked in file browser   
                   t.setText(fc.getSelectedFile().getAbsolutePath());           //gets path of selected pdf in text field
                   fileName.setText(fc.getSelectedFile().getName());
                }     
            }
    }
}
