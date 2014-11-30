import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import customerInfo.Customer;

public class WriteToFile{
	public static void appendToFile(String filename, String fileData){
		try {
			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			fw.write("\n"+ fileData); //appends the string to the file
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void replaceFile(String filename, String fileData){
		try {
			FileWriter fw = new FileWriter(filename); //the false will overwrite the file
			fw.write(fileData); //appends the string to the file
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void recordAction(String userName, String action, String Desc){
		String fileName = "TrackEdits.txt";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		dateFormat.format(date);
		
		String record = dateFormat.format(date) + " " + userName + " " + action + " " + Desc;
		
		appendToFile(fileName, record);
	}
	
	public static Customer deleteAcct(Customer customer, int acctNum){
		
		return customer;
	}
}
