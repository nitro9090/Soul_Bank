import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import customerInfo.Customer;
import customerInfo.PersVals;
import customerInfo.RepActiv;
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

	public static int recordActiv(String userName, String action){
		String fileName = "CustomerActivity.txt";
		String persLabel = "ActivityNum";
		int activNum = findPersValInt(persLabel);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		dateFormat.format(date);
		
		String record = userName + " " + activNum + " " + dateFormat.format(date) + " " + action;
		
		appendToFile(fileName, record);
		
		updPersValInt(persLabel, activNum+1);
		
		return activNum;
	}
	
	public static void recordRepActiv(int activNum, int repNum, String action, double transVal){
		String fileName = "RepActivity.txt";
		
		String record = activNum + " " + repNum + " " + action + " " + transVal;
		
		appendToFile(fileName, record);
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
	
	public static void updPersValStr(String persLabel, String newVal){
		updPersVal(persLabel, newVal);
	}
	
	public static void updPersValInt(String persLabel, int newVal){
		updPersVal(persLabel, Integer.toString(newVal));
	}
	
	public static void updPersVal(String persLabel, String newVal){
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
		ArrayList<Customer> customers = loadCustomersFull();
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
	
	public static void newCust(String customer, String userName, String pwd, String firstName, String lastName){
		String filename= "Customers.txt";
		String FileData = userName + " " + pwd + " " + firstName + " " + lastName;
		
		appendToFile(filename, FileData);
		recordActiv(customer, "NewCust");
	}
}
