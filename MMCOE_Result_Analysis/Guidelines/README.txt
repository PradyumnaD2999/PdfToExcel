General:
1) Open the project with netbeans IDE.
2) JDK 8 is used.
3) JAR files used are located in "dist" folder. Can be seen in IDE as well. Right click on project -> Properties -> Libraries.
4) Run on windows only.
5) If code is changed, run from netbeans IDE, NOT from JAR file. Build the project after changes if you want to run from JAR file.

Login Credentials:
1) Login IDs are kept in "users.txt"
2) Password is mmcoe@123
3) Add or replace username(s) in users.txt to add/change login IDs if required.

Subject Codes:
1) Subject Codes are placed in file "subject code.txt".
2) Almost all the subject codes exist in the file. If any new codes are to be added, PLEASE FOLLOW THE FORMAT:
	<subject code> <space> <space> <name/subject code (Can replace with name later in generated excel)>

Potential Values needed to be changed according to PDF:-
1) Only values in ExcelConversion.java need to be changed. No other class will require changes.
2) Changes to be made on following lines. To determine what and whether to make the change or not please refer to the comment in code for that line.
Line:
55
63
166
253
432

3) If in any case the code is changed or line nos. are changed, please update above line nos. accordingly.