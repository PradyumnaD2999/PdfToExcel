/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Deshpande
 */
public class FileSelector {
    protected void loginEvent(ActionEvent e,JButton login,FileSelector fs){
        JFrame frame = new JFrame("MMCOE Result Analysis - File Selection");
        frame.setSize(450,250);                                                     //set size of window
        frame.setLocationRelativeTo(null);                                          //window at center of screen
        frame.setVisible(true);                                                     //window visible
        frame.setResizable(false);                                                  //window cannot be resized
        frame.setLayout(null);                                                      //default arrangement of window elements
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                       //exit on clicking close  
        
        JButton save = new JButton("Save As Excel");                            //save as excel button creation
        frame.add(save);                                                            //adding it to the window
        save.setBounds(145,95,120,20);                                         //position of button inside window
        save.setVisible(true);
      
        JTextField t = new JTextField();                                        //textfield to display file path button creation 
        frame.add(t);
        t.setBounds(100,60,210,20); 
        t.setVisible(true);
        t.setEditable(false);                                                   //textfield non-editable
        t.setBackground(Color.WHITE);
        
        JTextField fileName = new JTextField();
      
        JButton browse = new JButton("Browse");                                 //browse pdf file button creation
        frame.add(browse);
        browse.setBounds(330,60,80,20);
        browse.setVisible(true);
        
        //Action Listener executed when browse button is clicked. Selects the pdf file.
        browse.addActionListener(new ActionListener(){  
        @Override
        public void actionPerformed(ActionEvent e){   
           FileBrowser fb = new FileBrowser();
           fb.fileBrowser(e,browse,t,fileName);
        }
        });
        
        //Action Listener executed when save as excel button is clicked. Converts pdf file to excel.
        save.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            ExcelConversion ec = new ExcelConversion(); 
            ec.saveAsExcel(t,frame,fs,fileName);
        }
        });
    }
    
    protected void resultButton(JFrame frame,StringBuffer csvData,JTextField fileName){
        JButton publish = new JButton("Publish Result");                            //save as excel button creation
        frame.add(publish);                                                            //adding it to the window
        publish.setBounds(150,125,110,20);                                         //position of button inside window
        publish.setVisible(true);
        
        publish.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                DatabaseConnection dbc = new DatabaseConnection();
                if(csvData != null){
                    dbc.connectDB(csvData,fileName);
                }
            }
        });
    }
}
