package pdf.to.excel.gui;



import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
/**
 *
 * @author paulpogba
 */
public class ResultScreen {
      protected void resultAnalysis(int appeared,int pass,int fail,int atkt,int distinction,int firstClass,int HSecondClass,int secondClass,int passClass,String[] name,String[] sgpa) throws ClassNotFoundException, IllegalAccessException, InstantiationException, UnsupportedLookAndFeelException{
        //User Interface Code
        
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame f = new JFrame("MMCOE Result Analysis - Result Statistics");                        //Window creation
        f.setSize(600,600);                                                     //set size of window
        f.setLocationRelativeTo(null);                                          //window at center of screen
        f.setVisible(true);                                                     //window visible
        f.setResizable(false);                                                  //window cannot be resized
        f.setLayout(null);                                                 //default arrangement of window elements
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                       //exit on clicking close  
        
        JLabel l1 = new JLabel("OVERALL");
        l1.setFont(new Font("",Font.BOLD,15));
        l1.setBounds(30,35,150,30);
        f.add(l1);
        
        JPanel overall = new JPanel();
        overall.setLayout(null);
        overall.setBorder(BorderFactory.createLineBorder(Color.black));
        overall.setBounds(35,70,195,130);
        f.add(overall);
        
        JLabel StAppeared = new JLabel("Regular Students : " + appeared);
        overall.add(StAppeared);
        StAppeared.setFont(new Font("",Font.BOLD,13));
        StAppeared.setBounds(10,10,170,20);
        StAppeared.setVisible(true);
       
        JLabel StPass = new JLabel("All Clear : " + pass);
        overall.add(StPass);
        StPass.setFont(new Font("",Font.BOLD,13));
        StPass.setBounds(10,40,170,20);
        StPass.setVisible(true);
        
        JLabel STatkt  = new JLabel("Backlog Students : " + atkt);
        overall.add(STatkt);
        STatkt.setFont(new Font("",Font.BOLD,13));
        STatkt.setBounds(10,100,170,20);
        STatkt.setVisible(true);
        
        JLabel StFail  = new JLabel("ATKT : " + fail);
        overall.add(StFail);
        StFail.setFont(new Font("",Font.BOLD,13));
        StFail.setBounds(10,70,170,20);
        StFail.setVisible(true);
        
        JLabel l2 = new JLabel("CLASS AWARDED");
        l2.setFont(new Font("",Font.BOLD,15));
        l2.setBounds(30,220,150,30);
        f.add(l2);
        
        JPanel Aclass = new JPanel();
        Aclass.setLayout(null);
        Aclass.setBorder(BorderFactory.createLineBorder(Color.black));
        Aclass.setBounds(35,255,195,160);
        f.add(Aclass);
        
        JLabel STDistinction = new JLabel("Distinction : " + distinction);
        Aclass.add(STDistinction);
        STDistinction.setFont(new Font("",Font.BOLD,13));
        STDistinction.setBounds(10,10,170,20);
        STDistinction.setVisible(true);
        
        JLabel StFClass  = new JLabel("First Class : " + firstClass);
        Aclass.add(StFClass);
        StFClass.setFont(new Font("",Font.BOLD,13));
        StFClass.setBounds(10,40,170,20);
        StFClass.setVisible(true);
        
        JLabel StHSClass  = new JLabel("Higher Second Class : " + HSecondClass);
        Aclass.add(StHSClass);
        StHSClass.setFont(new Font("",Font.BOLD,13));
        StHSClass.setBounds(10,70,170,20);
        StHSClass.setVisible(true);
        
        JLabel StSClass  = new JLabel("Second Class : " + secondClass);
        Aclass.add(StSClass);
        StSClass.setFont(new Font("",Font.BOLD,13));
        StSClass.setBounds(10,100,170,20);
        StSClass.setVisible(true);
        
        JLabel StPClass  = new JLabel("Pass Class : " + passClass);
        Aclass.add(StPClass);
        StPClass.setFont(new Font("",Font.BOLD,13));
        StPClass.setBounds(10,130,170,20);
        StPClass.setVisible(true);
        
        JLabel l3 = new JLabel("TOPPERS (TOP 10)");
        l3.setFont(new Font("",Font.BOLD,15));
        l3.setBounds(250,47,150,30);
        f.add(l3);
        
        JPanel toppers = new JPanel();
        toppers.setLayout(null);
        toppers.setBorder(BorderFactory.createLineBorder(Color.black));
        toppers.setBounds(250,82,330,315);
        f.add(toppers);
        
        int y = 10;
        for(int i = 0; i < name.length && i < sgpa.length; i++, y += 30){
            JLabel top  = new JLabel(i+1 + ") " + name[i] + " : ");
            toppers.add(top);
            top.setFont(new Font("",Font.PLAIN,13));
            top.setBounds(10,y,255,20);
            top.setVisible(true);
            
            JLabel top1  = new JLabel(sgpa[i]);
            toppers.add(top1);
            top1.setFont(new Font("",Font.BOLD,13));
            top1.setBounds(270,y,60,20);
            top1.setVisible(true);
        }
        
        JLabel footHeading = new JLabel("RESULT STATISTICS");
        footHeading.setFont(new Font("",Font.BOLD,30));
        footHeading.setBounds(130,470,370,60);
        footHeading.setForeground(Color.red);
        f.add(footHeading);
    }

}