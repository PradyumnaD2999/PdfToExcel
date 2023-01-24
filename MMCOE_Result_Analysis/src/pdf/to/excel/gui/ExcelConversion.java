/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf.to.excel.gui;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfSearchElement;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JTextField;

/**
 *
 * @author Deshpande
 */
public class ExcelConversion {
    protected void saveAsExcel(JTextField t,JFrame frame,FileSelector fs,JTextField fileName){
        try {
                String path = t.getText();

                int i,j,k,sgpaSize,studIdSize,subMarksSize,cnt;
                boolean isPresent;
                PdfSearchElement sgpaData,sgpaData1,sgpaData2,studId,subMarks,subMarks2;                          //PDFOne API's class's object
        
                // Load a PDF document 
                PdfDocument doc = new PdfDocument();                            //PDFOne API's class's object
                doc.load(path);                                                 //selected pdf file opened for extraction

                // Obtain all instances of the word "SGPA" in all pages 
                ArrayList sgpaList =                                   //List to store line containing sgpa
                (ArrayList) doc.search(1,                                       //searches the line containing sgpa
                                       "SGPA",
                                       PdfSearchMode.LITERAL,
                                       PdfSearchOptions.NONE);
                // Close the document                
                ArrayList studIdList =                                   
                (ArrayList) doc.search(1,                                      //searches the line containing seat no.
                                       //if MMCOE not present in line of seat no., replace string with seat no. pattern
                                       "MMCOE",
                                       PdfSearchMode.LITERAL,
                                       PdfSearchOptions.NONE);
                
                ArrayList subjectList =                                   
                (ArrayList) doc.search("/",                               //searches the line containing seat no.
                                       //Change this value to page having max no. of students
                                       //Choose page with max no. students with max no. of subjects
                                       doc.getPageCount() - 1,
                                       PdfSearchMode.LITERAL,
                                       PdfSearchOptions.NONE);
                
                ArrayList subjectMarks =                                   //List to store line containing sgpa
                (ArrayList) doc.search(1,                                       //searches the line containing seat no.
                                       "/",
                                       PdfSearchMode.LITERAL,
                                       PdfSearchOptions.NONE);
                // Close the document
                doc.close();
                
                

                
                //System.out.println(subjectMarks.get(0));
                
                PrintWriter pw = new PrintWriter(new File(fileName.getText() + ".csv"));         //for writing data to excel
                StringBuffer csvData = new StringBuffer("");                    //to store required data in string format
                // Iterate through all search results
                sgpaSize = sgpaList.size();
                studIdSize = studIdList.size();
                subMarksSize = subjectMarks.size();
                
                //System.out.println(n);
                for(i = 0; i < sgpaSize; i++){
                    //System.out.println(i);
                    sgpaData2 = (PdfSearchElement) sgpaList.get(i);
                    String sgpa2 = sgpaData2.getLineContainingMatchString();
                    if(sgpa2.contains("FE SGPA") || sgpa2.contains("SE SGPA") || sgpa2.contains("TE SGPA")){
                        sgpaList.remove(i);
                        System.out.println(i + " Removed\n" + sgpa2);
                        i--;
                        sgpaSize--;
                    }
                }
                
                for(i = 0; i < subMarksSize; i++){
                    subMarks2 = (PdfSearchElement) subjectMarks.get(i);
                    String sub2 = subMarks2.getLineContainingMatchString();
                    if(sub2.contains("310261")){
                        subjectMarks.remove(i);
                        System.out.println(i + " Removed\n" + sub2);
                        i--;
                        subMarksSize--;
                    }
                }
                
                System.out.println("No. of students : " + studIdSize);
                System.out.println("No. of sgpa lines : " + sgpaSize);
                
                //creates columns as follows. ',' used to separate two columns in different cells.
                csvData.append("SeatNo");
                csvData.append(",");
                csvData.append("Name");
                csvData.append(",");
                
                int[] count2 = addSubjectColumn(csvData,subjectList);            //integer array to add subject columns
                
                csvData.append("SGPA");
                csvData.append(",");
                csvData.append("CreditsEarned");
                csvData.append(",");
                csvData.append("Status");
                csvData.append("\n");
                csvData.append("");
                csvData.append(",");
                csvData.append("");
                
                System.out.println("Total subject columns created : " + count2[1]);
                for(int y = 0; y < count2[1]; y++){
                    csvData.append(",");
                    csvData.append("IN");
                    csvData.append(",");
                    csvData.append("TH");
                    csvData.append(",");
                    csvData.append("Total");
                    csvData.append(",");
                    csvData.append("Grade");
                }
                csvData.append("\n");
                
                j=0;
                System.out.println("Total subjects for 1 student : " + count2[0]);
                System.out.println("Total subject columns for sem 1 (only for full year result) : " + Math.ceil((double)(count2[1])/2));
                for (i = 0; i < studIdSize; i++) {                                         //i stores seat no
                    System.out.println("Student no. : " + i);
                    studId = (PdfSearchElement) studIdList.get(i);    //stores sgpa line in pseResult
                    String[] sid = studId.getLineContainingMatchString().split("\\s+");        //separates words in line in string array indexes
                    String[] parsedStudId = parseStudId(sid);
                    csvData.append(parsedStudId[0]);                                                        //seat no.
                    csvData.append(",");
                    
                    final String s1 = String.valueOf(parsedStudId[1] + " " + parsedStudId[2] + " " + parsedStudId[3]);            //name
                    csvData.append(s1);
                    csvData.append(",");
                    
                    for(; j < subMarksSize;){                                                         //j stores marks
                        sgpaData1 = (PdfSearchElement) sgpaList.get(i);
                        String sgpa1 = sgpaData1.getLineContainingMatchString();
                        if(sgpa1.contains("SGPA1")){
                            System.out.println("Total subjects for sem 1 : " + count2[0]/2);
                            //if count of sem1 subs > count of sem2 subs use Math.ceil((double)(count2[1])/2)in loop
                            for(int z = 0; z < (count2[1])/2; z++){
                            //System.out.println("z = " + z);
                            //System.out.println("j:"+ j);
                            subMarks = (PdfSearchElement) subjectMarks.get(j);
                           
                            String smkss = subMarks.getLineContainingMatchString();
                            while(true){
                            if(smkss.contains("EXAMINATION")){
                                j++;
                                subMarks = (PdfSearchElement) subjectMarks.get(j);
                                break;
                            }
                            else if(smkss.contains("CGPA")){
                                j++;
                                subMarks = (PdfSearchElement) subjectMarks.get(j);
                                smkss = subMarks.getLineContainingMatchString();
                            }
                            else{
                                break;
                            }
                            }
                            String[] smks = subMarks.getLineContainingMatchString().split("\\s+");
                            String[] parsedMarks = parseMarks(smks);
                            String[] smks1;
                            String[] sCode;
                            String subCode;
                            int a = 0;
                            cnt = 0;
                            for(smks1 = subMarks.getLineContainingMatchString().split("\\s+"); a < smks1.length; ++a){
                                if(smks1[a].contains("/")){
                                    cnt++;
                                }
                                else{}
                            }

                            File file = new File("subject codes.txt");
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String st;
                            String[] st1;
                            isPresent = false;
                            while ((st = br.readLine()) != null){
                                st1 = st.split("\\s+");
                                if(parsedMarks[0].contains("B")){
                                    sCode = parsedMarks[0].split("B");
                                    subCode = sCode[0];
                                }
                                else if(parsedMarks[0].contains("D")){
                                    sCode = parsedMarks[0].split("D");
                                    subCode = sCode[0];
                                }
                                else if(parsedMarks[0].contains("C")){
                                    sCode = parsedMarks[0].split("C");
                                    subCode = sCode[0];
                                }
                                else if(parsedMarks[0].contains("A")){
                                    sCode = parsedMarks[0].split("A");
                                    subCode = sCode[0];
                                }
                                else if(parsedMarks[0].contains("E")){
                                    sCode = parsedMarks[0].split("E");
                                    subCode = sCode[0];
                                }
                                else{
                                    subCode = parsedMarks[0];
                                }
                                if(subCode.equals(st1[0])){
                                    isPresent = true;
                                    break;
                                }
                                else{
                                    isPresent = false;
                                }
                            }
                            if(isPresent == true){
                                csvData.append(parsedMarks[1].split("\\/+")[0]);
                                csvData.append(",");
                                csvData.append(parsedMarks[2].split("\\/+")[0]);
                                csvData.append(",");
                                csvData.append(parsedMarks[3].split("\\/+")[0]);
                                csvData.append(",");
                                csvData.append(parsedMarks[4]);
                                csvData.append(",");
                            }
                            j = j + cnt;
                            }
                            //if pdf has only sem1 students comment this
                            //if count of sem1 subs > count of sem2 subs use (count2[1])/2 in loop
                            for(int z = 0; z < Math.ceil((double)(count2[1])/2); ++z){
                                csvData.append("-");
                                csvData.append(",");
                                csvData.append("-");
                                csvData.append(",");
                                csvData.append("-");
                                csvData.append(",");
                                csvData.append("-");
                                csvData.append(",");
                            }   
                        }
                        else{
                            for(int z = 0; z < count2[1]; ++z){
                            //System.out.println("Slash iteration : " + j);
                            subMarks = (PdfSearchElement) subjectMarks.get(j);
                            
                            String smkss = subMarks.getLineContainingMatchString();
                            while(true){
                            if(smkss.contains("EXAMINATION")){
                                j++;
                                subMarks = (PdfSearchElement) subjectMarks.get(j);
                                break;
                            }
                            else if(smkss.contains("CGPA")){
                                j++;
                                subMarks = (PdfSearchElement) subjectMarks.get(j);
                                smkss = subMarks.getLineContainingMatchString();
                            }
                            else{
                                break;
                            }
                            }
                            String[] smks = subMarks.getLineContainingMatchString().split("\\s+");
                            String[] parsedMarks = parseMarks(smks);
                            String[] smks1;
                            String[] sCode;
                            String subCode;
                            int a = 0;
                            cnt = 0;
                            for(smks1 = subMarks.getLineContainingMatchString().split("\\s+"); a < smks1.length; ++a){
                                if(smks1[a].contains("/")){
                                    cnt++;
                                }
                                else{}
                            }
                           
                            File file = new File("subject codes.txt");
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String st;
                            String[] st1;
                            isPresent = false;
                            while ((st = br.readLine()) != null){
                                st1 = st.split("\\s+");
                                if(parsedMarks[0].contains("B")){
                                    sCode = parsedMarks[0].split("B");
                                    subCode = sCode[0];
                                }
                                else if(parsedMarks[0].contains("D")){
                                    sCode = parsedMarks[0].split("D");
                                    subCode = sCode[0];
                                }
                                else if(parsedMarks[0].contains("C")){
                                    sCode = parsedMarks[0].split("C");
                                    subCode = sCode[0];
                                }
                                else if(parsedMarks[0].contains("A")){
                                    sCode = parsedMarks[0].split("A");
                                    subCode = sCode[0];
                                }
                                else if(parsedMarks[0].contains("E")){
                                    sCode = parsedMarks[0].split("E");
                                    subCode = sCode[0];
                                }
                                else{
                                    subCode = parsedMarks[0];
                                }
                                if(subCode.equals(st1[0])){
                                    isPresent = true;
                                    break;
                                }
                                else{
                                    isPresent = false;
                                }
                            }
                            if(isPresent == true){
                                csvData.append(parsedMarks[1].split("\\/+")[0]);
                                csvData.append(",");
                                csvData.append(parsedMarks[2].split("\\/+")[0]);
                                csvData.append(",");
                                csvData.append(parsedMarks[3].split("\\/+")[0]);
                                csvData.append(",");
                                csvData.append(parsedMarks[4]);
                                csvData.append(",");
                            }
                            j = j + cnt;
                            }
                        }
                        
                        for(k = i; k < sgpaSize;){
                            String gpa = "";
                            //System.out.println("k:"+ k);
                            sgpaData = (PdfSearchElement) sgpaList.get(k);
                            String[] sgpa = sgpaData.getLineContainingMatchString().split("\\s+");
                            if(sgpa[0].equals("SGPA1") || sgpa[0].equals("SGPA")){
                                csvData.append(sgpa[2]);                                                  //sgpa
                                gpa = sgpa[2];
                            }
                            else if(sgpa[2].equals("SGPA")){
                                csvData.append(sgpa[4]);                                                  //sgpa
                                gpa = sgpa[4];
                            }

                            if(sgpa[4].equals("CREDITS")){
                                String[] creds;
                                String credits;
                                if(sgpa[7].contains("[")){
                                    creds = sgpa[7].split("\\[+");
                                    credits = creds[0];
                                }
                                else{
                                    credits = sgpa[7];
                                }
                                csvData.append(credits);                                                  //credits
                                csvData.append(",");
                                if(gpa.contains("-")){
                                    
                                    csvData.append("Fail");                                              //status
                                }
                                else{
                                    csvData.append("Pass");                                              //status
                                }
                            }
                            else if(sgpa[6].equals("CREDITS")){
                                String[] creds;
                                String credits;
                                if(sgpa[9].contains("[")){
                                    creds = sgpa[9].split("\\[+");
                                    credits = creds[0];
                                }
                                else{
                                    credits = sgpa[9];
                                }
                                csvData.append(credits);                                                  //credits
                                csvData.append(",");
                                if(gpa.contains("-")){
                                    csvData.append("Fail");                                              //status
                                }
                                else{
                                    csvData.append("Pass");                                              //status
                                }
                            }
                            csvData.append("\n");
                            break;
                        }
                        break;
                    }
                }
                pw.write(csvData.toString());                                          //prints all the above data in respective columns in excel sheet       
                pw.close();
                ExcelStyling es = new ExcelStyling();
                es.styleSpreadsheet();
                
                JOptionPane.showMessageDialog(null,"Saved as Excel File","Message",INFORMATION_MESSAGE);
                
                fs.resultButton(frame,csvData,fileName);
            } catch (IOException | PdfException ex) {
                Logger.getLogger(PdfToExcelGui.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private int[] addSubjectColumn(StringBuffer csvData,ArrayList subjectList) throws FileNotFoundException, IOException{
        PdfSearchElement sub;
        int i,colcnt=0, cnt, totcnt = 0, count=0, slashCount = subjectList.size();
        int[] count2 = {0,0};
        System.out.println("No. of slashes on entire last page : " + slashCount);
        System.out.println("No of slashes for one student : " + Math.ceil(((double) slashCount)/2));
        
        //Remove '/2' from condition if last page has only one students
        //Similarly divide by '/n' in loop for n students on page
        for(i = 0;i < Math.ceil(((double) slashCount)/2);i++){
            sub = (PdfSearchElement)subjectList.get(i);

            
            String[] subName = sub.getLineContainingMatchString().split("\\s+");
            String[] slashIterate;
            String[] sCode;
            String subCode;
            int a = 0;
            cnt = 0;
            for(slashIterate = sub.getLineContainingMatchString().split("\\s+"); a < slashIterate.length; ++a){
                if(slashIterate[a].contains("/")){
                    cnt++;
                    colcnt = colcnt + 1;
                }
                else{}
            }
            totcnt = totcnt + cnt;
            colcnt = colcnt - 1;
            count2[0] = totcnt - colcnt;
            
            File file = new File("subject codes.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String[] st1 = null;
            boolean isPresent = false;
            while ((st = br.readLine()) != null){
                st1 = st.split("\\s+");
//                System.out.println(s[1]);
//                System.out.println(st1[0]);
//                System.out.println(st1[1]);
                if(subName[1].contains("B")){
                    sCode = subName[1].split("B");
                    subCode = sCode[0];
                }
                else if(subName[1].contains("D")){
                    sCode = subName[1].split("D");
                    subCode = sCode[0];
                }
                else if(subName[1].contains("C")){
                    sCode = subName[1].split("C");
                    subCode = sCode[0];
                }
                else if(subName[1].contains("A")){
                    sCode = subName[1].split("A");
                    subCode = sCode[0];
                }
                else if(subName[1].contains("E")){
                    sCode = subName[1].split("E");
                    subCode = sCode[0];
                }
                else{
                    subCode = subName[1];
                }
                if(subCode.equals(st1[0])){
                    isPresent = true;
                    break;
                }
                else{
                    isPresent = false;
                }
            }
            if(isPresent == true){
                    csvData.append("");
                    csvData.append(",");
                    csvData.append(st1[1]);
                    csvData.append(",");
                    csvData.append("");
                    csvData.append(",");
                    csvData.append("");
                    csvData.append(",");
                    count++;
            }
            else{
                    
            }
            i = i + cnt - 1;
        }
        count2[1] = count;
        return count2;
    }
    
    private String[] parseMarks(String[] smks) {
        String[] parsedMarks = new String[5];
        parsedMarks[0] = smks[1];
        
        for(int i = 2; i < smks.length; i++) {
            if(smks[i].contains("/")) {
                if(smks[i+7].matches("([a-zA-Z]{1}|[a-zA-Z][+])")) {
                    parsedMarks[1] = smks[i-1];
                    parsedMarks[2] = smks[i];
                    parsedMarks[3] = smks[i+1];
                    parsedMarks[4] = smks[i+7];
                    break;
                }
                else if(smks[i+6].matches("([a-zA-Z]{1}|[a-zA-Z][+])")) {
                    parsedMarks[1] = smks[i-2];
                    parsedMarks[2] = smks[i-1];
                    parsedMarks[3] = smks[i];
                    parsedMarks[4] = smks[i+6];
                    break;
                }
                else if(smks[i+5].matches("([a-zA-Z]{1}|[a-zA-Z][+])")) {
                    parsedMarks[1] = smks[i];
                    parsedMarks[2] = smks[i+1];
                    parsedMarks[3] = smks[i+2];
                    parsedMarks[4] = smks[i+5];
                    break;
                }
                else if(smks[i+4].matches("([a-zA-Z]{1}|[a-zA-Z][+])")) {
                    parsedMarks[1] = smks[i-1];
                    parsedMarks[2] = smks[i];
                    parsedMarks[3] = smks[i+1];
                    parsedMarks[4] = smks[i+4];
                    break;
                }
                else if(smks[i+3].matches("([a-zA-Z]{1}|[a-zA-Z][+])")) {
                    parsedMarks[1] = smks[i-2];
                    parsedMarks[2] = smks[i-1];
                    parsedMarks[3] = smks[i];
                    parsedMarks[4] = smks[i+3];
                    break;
                }
                else if(smks[i+8].matches("([a-zA-Z]|[a-zA-Z][+])")) {
                    parsedMarks[1] = smks[i];
                    parsedMarks[2] = smks[i+1];
                    parsedMarks[3] = smks[i+2];
                    parsedMarks[4] = smks[i+8];
                    break;
                }
            }
        }
        System.out.println("marks : " + Arrays.toString(parsedMarks));
        return parsedMarks;
    }
    
    private String[] parseStudId(String[] sid) {
        String[] parsedStudId = new String[4];
        
        for(int i = 0, j = 0; j < 4; i++) {
            if(sid[i].toLowerCase().contains("seat") || sid[i].toLowerCase().contains("no.:") || sid[i].toLowerCase().contains("name") || sid[i].toLowerCase().contains(":")) {
                continue;
            }
            parsedStudId[j] = sid[i];
            j++;
        }
        
        System.out.println("student id : " + Arrays.toString(parsedStudId));
        return parsedStudId;
    }
}
