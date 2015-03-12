package Misc;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import repositories.RepActiv;
import repositories.Repositories;
import users.Admin;
import users.Customer;
import users.User;
import users.UserActiv;

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
	
	public static void addRep(String repUserName, int repNum, String repType, Double repBal){
		String filename = "Repositories.txt";
		String FileData = repUserName + " " + repNum + " " + repType + " " + repBal;
		
		appendToFile(filename, FileData);
	}
	
	public static void addUser(String customer, String userName, String pwd, String acctType, String firstName, String lastName){
		String filename= "Users.txt";
		String FileData = userName + " " + pwd + " " + acctType + " " + firstName + " " + lastName;
		
		appendToFile(filename, FileData);
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

	public static int recordActiv(String currUser, String action){
		String fileName = "UserActivity.txt";
		String persLabel = "ActivityNum";
		int activNum = findPersValInt(persLabel);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		dateFormat.format(date);
		
		String record = currUser + " " + activNum + " " + dateFormat.format(date) + " " + action;
		
		appendToFile(fileName, record);
		
		updPersValInt(persLabel, activNum+1);
		
		return activNum;
	}
	
	public static void recordRepActiv(int activNum, int repNum, String action, double transVal){
		String fileName = "RepActivity.txt";
		
		String record = activNum + " " + repNum + " " + action + " " + transVal;
		
		appendToFile(fileName, record);
	}
	
	/*public static void transferSouls(boolean addToRep, boolean subtrFromRep, int repNumAdd, int repNumSubtr, double transAmt){
		String fileName = "Repositories.txt";
		ArrayList<String> reps = new ArrayList<String>(); 
		Scanner sc2 = null;
		
		// start file scanner
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
	
			if(repNumAdd == repNum && addToRep == true){
				repBal = repBal + transAmt;
			}
			else if (repNumSubtr == repNum && subtrFromRep == true){
				repBal = repBal - transAmt;
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
	}*/
	
	public static void addSubtrSouls(int transRepNum, double transAmt){
		String fileName = "Repositories.txt";
		ArrayList<String> reps = new ArrayList<String>(); 
		Scanner sc2 = null;
		
		// start file scanner
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
	
			if(transRepNum == repNum){
				repBal = repBal + transAmt;
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
	}
	
	public static ArrayList<User> loadUsers() {
		ArrayList<User> users = new ArrayList<>();
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File("Users.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc1.hasNext()){
			String userName = sc1.next();
			String password = sc1.next();
			String userType = sc1.next();
			String firstName = sc1.next();
			String lastName = sc1.next();
			
			User temp = new User(userName, password, userType, firstName, lastName);
			
			users.add(temp);
		}
		
		sc1.close();
		return users;
	}

	
	public static User loadUser(String userName){
		ArrayList<User> users = loadUsers();
		
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUserName().equals(userName)){
				return users.get(i);
			}
		}
		return new User();
	}
	
	public static ArrayList<Admin> loadAdmins() {
		ArrayList<User> users = loadUsers();
		ArrayList<Admin> admins = new ArrayList<>();
		
		for(int i = 0; i<users.size(); i++){
			if(users.get(i).getAcctType().equals("Admin")){
				Admin temp = new Admin(users.get(i));
				admins.add(temp);
			}
		}
		return admins;
	}

	
	public static User loadAdmin(String userName){
		ArrayList<User> users = loadUsers();
		
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getUserName().equals(userName)){
				return users.get(i);
			}
		}
		return new User();
	}
	
	public static ArrayList<Customer> loadCustomers(){
		ArrayList<User> users = loadUsers();
		ArrayList<Customer> customers = new ArrayList<>();
		
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getAcctType().equals("Cust")){
				Customer temp = new Customer(users.get(i));
				customers.add(temp);
			}	
		}
		return customers;
	}

	public static Customer loadCustomer(String userName) {
		ArrayList<Customer> customers = loadCustomers();
		
		for(int i = 0; i<customers.size(); i++){
			if(userName.equals(customers.get(i).getUserName())){
				return customers.get(i);
			}
		}
		return new Customer();
	}
	
	public static ArrayList<Repositories> loadRep(String loadUserName) {
		String fileName = "Repositories.txt";
		ArrayList<Repositories> repositories = new ArrayList<>();
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc1.hasNext()){
			String inUserName = sc1.next();  //skips over usernames in the file
			String repNum = sc1.next();
			String repType = sc1.next();
			String balance = sc1.next();
			int repNumInt = Integer.parseInt(repNum);
			double repBalDbl = Double.parseDouble(balance);
			
			if(inUserName.equals(loadUserName)){
				Repositories temp = new Repositories(repNumInt, repType, repBalDbl);
				repositories.add(temp);
			}
		}
		
		sc1.close();
		return repositories;
	}

	public static ArrayList<Repositories> loadReps() {
		String fileName = "Repositories.txt";
		ArrayList<Repositories> repositories = new ArrayList<>();
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File(fileName));
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
			
			Repositories temp = new Repositories(repNumInt, repType, repBalDbl);
			
			repositories.add(temp);
		}
		
		sc1.close();
		return repositories;
	}

	public static ArrayList<PersVals> loadPersVals(){
		ArrayList<PersVals> persVals = new ArrayList<>();
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File("PersistentVals.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc1.hasNext()){
			String valType = sc1.next();
			String value = sc1.next();

			PersVals temp = new PersVals(valType, value);
			
			persVals.add(temp);
		}
		
		sc1.close();
		
		return persVals;
	}
	
	public static ArrayList<RepActiv> loadRepActiv(int loadRepNum){
		ArrayList<RepActiv> repActiv = new ArrayList<>();
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File("RepActivity.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc1.hasNext()){
			int activNum = Integer.parseInt(sc1.next());
			int repNum = Integer.parseInt(sc1.next());
			String action = sc1.next();
			double transVal = Double.parseDouble(sc1.next());
			
			if(loadRepNum == repNum){
				RepActiv temp = new RepActiv(activNum, repNum, action, transVal);	
				repActiv.add(temp);
			}
		}
		
		sc1.close();

		return repActiv;
	}
	
	public static UserActiv loadUserActiv(int currActivNum){
		Scanner sc1 = null;
		
		try {
			sc1 = new Scanner(new File("UserActivity.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc1.hasNext()){
			String userName = sc1.next();
			int activNum = Integer.parseInt(sc1.next());
			String date = sc1.next();
			String time = sc1.next();
			String action = sc1.next();
			
			if(currActivNum == activNum){
				UserActiv temp = new UserActiv(userName, activNum, date, time, action);
				sc1.close();
				return temp;
			}
		}
		
		sc1.close();
		
		System.out.println("ReadWriteFile.loadUserActiv couldn't find the User Activity.");
		return null;
	}
	
	public static int findPersValInt(String label){
		ArrayList<PersVals> persVals = loadPersVals();
		int persSize = persVals.size();

		for(int i=0; i<persSize; i++){
			if ((persVals.get(i).getLabel()).equals(label)){
				return persVals.get(i).getValueInt();
			}
		}
		return -1;
	}
	
	public static void updPersValInt(String persLabel, int newVal){
		updPersValStr(persLabel, Integer.toString(newVal));
	}
	
	public static void updPersValStr(String persLabel, String newVal){
		ArrayList<PersVals> persVals = loadPersVals();
		ArrayList<String> newPersVals = new ArrayList<>();
		int persSize = persVals.size();

		for(int i=0; i<persSize; i++){
			StringBuilder temp = new StringBuilder();
			String valLabel = persVals.get(i).getLabel();
			if (!valLabel.equals(persLabel)){
				temp.append(valLabel).append(" ");
				temp.append(persVals.get(i).getValueStr());
			}
			else{
				temp.append(valLabel).append(" ");
				temp.append(newVal);
			}	
			
			String temp2 = temp.toString();
			newPersVals.add(temp2);
		}
		rewriteFile("PersistentVals.txt", newPersVals);
	}

	public static void deleteRep(int repNum){
		ArrayList<Customer> customers = loadCustomers(); 
		boolean firstWrite = true;
		String fileName = "Repositories.txt";
		int custSize = customers.size();
		int RepSize = 0;
		
		for(int i = 0; i < custSize; i++){
			RepSize = customers.get(i).getRep().size();
			for(int j = 0; j < RepSize; j++){
				int repNum2 = customers.get(i).getRepNum(j);
				if(repNum != repNum2){	
					StringBuilder temp = new StringBuilder(); 
					
					temp.append(customers.get(i).getUserName()).append(" ");
					temp.append(repNum2).append(" ");
					temp.append(customers.get(i).getRepType(repNum2)).append(" ");
					temp.append(customers.get(i).getRepBal(repNum2));
					
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

	public static void deleteCust(String customer){
		ArrayList<Customer> allCustomers = loadCustomers();
		boolean firstWrite = true;
		String fileName = "Customers.txt";
		int custSize = allCustomers.size();

		for(int i = 0; i < custSize; i++){
			if(!customer.equals(allCustomers.get(i).getUserName())){	
				StringBuilder temp = new StringBuilder(); 
				
				temp.append(allCustomers.get(i).getUserName()).append(" ");
				temp.append(allCustomers.get(i).getPassword()).append(" ");
				temp.append(allCustomers.get(i).getFirstName()).append(" ");
				temp.append(allCustomers.get(i).getLastName());

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

	public static void donateToDarkOnes(String custUserName, double transVal){
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

			if (custUserName.equals(userName)){
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
			
			temp.append(custUserName).append(" ");
			temp.append(darkOneBal);
			
			String temp2 = temp.toString();
			
			darkDons.add(temp2);
		}
		
		sc2.close();
		
		//replaces file with the newly editted values
		rewriteFile(fileName, darkDons);
	}
}
