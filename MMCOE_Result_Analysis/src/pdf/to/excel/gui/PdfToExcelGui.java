/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 *
 * @author Deshpande
 */
public class PdfToExcelGui{

    PdfToExcelGui() throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnsupportedLookAndFeelException{
        loginPage();
    }
    
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnsupportedLookAndFeelException {
        PdfToExcelGui p = new PdfToExcelGui();
        
    }
    
    private void loginPage() throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnsupportedLookAndFeelException{
        //User Interface Code
        
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame f = new JFrame("MMCOE Result Analysis - Login");                        //Window creation
        f.setSize(450,250);                                                     //set size of window
        f.setLocationRelativeTo(null);                                          //window at center of screen
        f.setVisible(true);                                                     //window visible
        f.setResizable(false);                                                  //window cannot be resized
        f.setLayout(null);                                                      //default arrangement of window elements
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                       //exit on clicking close  
        
        JLabel loginid = new JLabel("Login ID: ");
        f.add(loginid);
        loginid.setBounds(90,50,60,20);
        loginid.setVisible(true);
        
        JTextField id = new JTextField();
        f.add(id);
        id.setBounds(155,50,200,20);
        id.setVisible(true);
        
        JLabel password = new JLabel("Password: ");
        f.add(password);
        password.setBounds(90,80,60,20);
        password.setVisible(true);
        
        JPasswordField pass = new JPasswordField();
        f.add(pass);
        pass.setBounds(155,80,200,20);
        pass.setVisible(true);
        
        JButton login = new JButton("Login");
        f.add(login);
        login.setBounds(210,120,80,20);
        login.setVisible(true);
        
        login.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                boolean isValidId = false;
                BufferedReader br = null;
                try {
                    File file = new File("users.txt");
                    br = new BufferedReader(new FileReader(file));
                    String st;
                    while ((st = br.readLine()) != null){
                        System.out.println(id.getText());
                        System.out.println(st);
                        if(st.equals(id.getText()) && pass.getText().equals("mmcoe@123")){
                            isValidId = true;
                            break;
                        }
                        else{
                            isValidId = false;
                        }
                    }
                    if(isValidId == true){
                        FileSelector fs = new FileSelector();
                        f.setVisible(false);
                        fs.loginEvent(e,login,fs);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Error: Invalid ID or Password","Login Error",ERROR_MESSAGE);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PdfToExcelGui.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PdfToExcelGui.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(PdfToExcelGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });        
    }


};