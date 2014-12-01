import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import customerInfo.Customer;
import customerInfo.Repositories;

public class ReadWriteFile{
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

	public static ArrayList<Repositories> loadReps() {
		ArrayList<Repositories> repositories = new ArrayList<>();
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File("Repositories.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc1.hasNext()){
			sc1.next();  //skips over usernames in the file
			String repNum = sc1.next();
			String repType = sc1.next();
			String balance = sc1.next();
			int repNumInt = Integer.parseInt(repNum);
			double repBalDbl = Double.parseDouble(balance);
			
			Repositories temp = new Repositories(repType, repNumInt, repBalDbl);
			
			repositories.add(temp);
		}
		
		sc1.close();
		return repositories;
	}

	public static ArrayList<Customer> loadCustomersFull() {
		ArrayList<Customer> customer = new ArrayList<>();
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File("Customers.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc1.hasNext()){
			String userName = sc1.next();
			String password = sc1.next();
			String firstName = sc1.next();
			String lastName = sc1.next();
			
			Customer temp = new Customer(userName, password, firstName, lastName);
			
			customer.add(temp);
		}
		
		sc1.close();
		
		Scanner sc2 = null;
		try {
			sc2 = new Scanner(new File("Repositories.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int custSize = customer.size();
		
		while(sc2.hasNext()){
			String userNameFile = sc2.next(); 
			int repNum = Integer.parseInt(sc2.next());
			String repType = sc2.next();
			double repBal = Double.parseDouble(sc2.next());
			for(int i = 0; i < custSize; i++){
				if(userNameFile.equals(customer.get(i).getUserName())){
					Repositories temp = new Repositories(repType, repNum, repBal);
					customer.get(i).addRep(temp);
				}
			}
		}
		
		sc2.close();
		return customer;
	}

	public static ArrayList<Customer> loadCustomers() {
		ArrayList<Customer> customer = new ArrayList<>();
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File("Customers.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc1.hasNext()){
			String userName = sc1.next();
			String password = sc1.next();
			String firstName = sc1.next();
			String lastName = sc1.next();
			
			Customer temp = new Customer(userName, password, firstName, lastName);
			
			customer.add(temp);
		}
		
		sc1.close();
		return customer;
	}

	public static Customer loadCustomerFull(String userName) {
		Customer customer = new Customer();
		Scanner sc1 = null;
		Scanner sc2 = null;
		String userNameFile;

		try {
			sc1 = new Scanner(new File("Customers.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(sc1.hasNext()){
			userNameFile = sc1.next();
			String password = sc1.next();
			String firstName = sc1.next();
			String lastName = sc1.next();

			if(userName.equals(userNameFile)){
				customer.setUserName(userNameFile);
				customer.setPassword(password);
				customer.setFirstName(firstName);
				customer.setLastName(lastName);
			}
		}

		sc1.close();

		try {
			sc2 = new Scanner(new File("Repositories.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(sc2.hasNext()){
			userNameFile = sc2.next(); 
			int repNum = Integer.parseInt(sc2.next());
			String repType = sc2.next();
			double repBal = Double.parseDouble(sc2.next());

			if(userNameFile.equals(userName)){
				Repositories temp = new Repositories(repType, repNum, repBal);
				customer.addRep(temp);
			}
		}
		
		sc2.close();
		return customer;
	}
	
	public static int findUnusedRepNum(){
		ArrayList<Repositories> Reps = loadReps();
		int repSize = Reps.size();
		boolean found = false;
		int i = 1;

		while(found == false){
			for(int j=0; j<repSize; j++){
				if (i == Reps.get(j).getRepNum()){
					i++;
					break;
				}
				else if(j == repSize-1){
					found = true;
				}
			}
		}
		
		return i;
	}

	public static Customer transferSouls(Customer customer, int repAddBal, int repSubtrBal, double transAmt, boolean addToRep, boolean subtrFromRep){
		String fileName = "Repositories.txt";
		ArrayList<String> reps = new ArrayList<String>(); 
		Scanner sc2 = null;
		
		try {
			sc2 = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//reads all account data into the program, while adding or subtracting to balances depending on the transaction
		while(sc2.hasNext()){
			String userName = sc2.next(); 
			int repNum = sc2.nextInt();
			String repType = sc2.next();
			double repBal = Double.parseDouble(sc2.next());
	
			if (customer.getUserName().equals(userName)){
				if(repAddBal == repNum && addToRep == true){
					repBal = repBal + transAmt;
				}
				else if ( repSubtrBal == repNum && subtrFromRep == true){
					repBal = repBal - transAmt;
				}
			}
	
			StringBuilder temp = new StringBuilder(); 
			
			temp.append(userName).append(" ");
			temp.append(repNum).append(" ");
			temp.append(repType).append(" ");
			temp.append(repBal);
			
			String temp2 = temp.toString();
			
			reps.add(temp2);
		}
		
		sc2.close();
		
		//replaces file with the newly editted values
		rewriteFile(fileName, reps);
		
		customer = loadCustomerFull(customer.getUserName());
		
		return customer;
	}

	public static void deleteRep(int repNum){
		ArrayList<Customer> customer = loadCustomersFull();
		boolean firstWrite = true;
		String fileName = "Repositories.txt";
		int custSize = customer.size();
		int RepSize = 0;
		
		for(int i = 0; i < custSize; i++){
			RepSize = customer.get(i).getRep().size();
			for(int j = 0; j < RepSize; j++){
				int repNum2 = customer.get(i).getRepNum(j);
				if(repNum != repNum2){	
					StringBuilder temp = new StringBuilder(); 
					
					temp.append(customer.get(i).getUserName()).append(" ");
					temp.append(repNum2).append(" ");
					temp.append(customer.get(i).getRepType(repNum2)).append(" ");
					temp.append(customer.get(i).getRepBal(repNum2));
					
					String temp2 = temp.toString();
					
					if(firstWrite == true){
						ReadWriteFile.replaceFile(fileName, temp2);
						firstWrite = false;
					}
					else{
						ReadWriteFile.appendToFile(fileName, temp2);
					}
				}
			}
		}
	}

	public static void donateToDarkOnes(Customer customer, double transVal){
		String fileName = "DarkOnes.txt";
		ArrayList<String> darkDons = new ArrayList<String>();
		boolean isThere = false;
		Scanner sc2 = null;
		double darkOneBal = 0;
		
		try {
			sc2 = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//reads all account data into the program, while adding or subtracting to balances depending on the transaction
		while(sc2.hasNext()){
			String userName = sc2.next(); 
			darkOneBal = Double.parseDouble(sc2.next());

			if (customer.getUserName().equals(userName)){
				darkOneBal = darkOneBal + transVal;
				isThere = true;
			}

			StringBuilder temp = new StringBuilder(); 

			temp.append(userName).append(" ");
			temp.append(darkOneBal);

			String temp2 = temp.toString();
			
			darkDons.add(temp2);
		}
		
		if(isThere == false){
			darkOneBal = transVal;
			StringBuilder temp = new StringBuilder(); 
			
			temp.append(customer.getUserName()).append(" ");
			temp.append(darkOneBal);
			
			String temp2 = temp.toString();
			
			darkDons.add(temp2);
		}
		
		sc2.close();
		
		//replaces file with the newly editted values
		rewriteFile(fileName, darkDons);
	}

	private static void rewriteFile(String fileName, ArrayList<String> newData) {
		for (int i = 0; i < newData.size(); i++){
			if(i == 0){
				ReadWriteFile.replaceFile(fileName, newData.get(i));
			}
			else{
				ReadWriteFile.appendToFile(fileName, newData.get(i));
			}
		}
	}
}
