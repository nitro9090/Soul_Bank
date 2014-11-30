import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import customerInfo.Repositories;
import customerInfo.Customer;


public class CustMethods {
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
			
			/*for(int i=0; i<customer.size(); i++){
			//	if(customer.get(i).getUserName().equals(userName)){
			//		customer.get(i).addAccount(temp);
			//	}
			}*/
		}
	
		sc2.close();
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
	
	public static int findUnusedRepNum(){
		//fix this by adding load customers and then search for duplicates until one isn't found.
		Scanner sc2 = null;
		int i = 1;
		boolean found = false;

		try {
			sc2 = new Scanner(new File("Repositories.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (found == false){
			found = true;
			while(sc2.hasNext()){
				sc2.next();  //skip username
				int repNum = sc2.nextInt();
				sc2.next();  //skip account type
				sc2.next(); //skip account balance

				if(repNum == i){
					i++;
					found = false;
				}
			}
		}
		sc2.close();
		return i;
	}
	
	public static Customer transferSouls(Customer customer, int repAddBal, int repSubtrBal, double transAmt, boolean addToRep, boolean subtrFromRep){
		ArrayList<String> reps = new ArrayList<String>(); 
		Scanner sc2 = null;
		
		try {
			sc2 = new Scanner(new File("Repositories.txt"));
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
		for (int i = 0; i < reps.size(); i++){
			if(i == 0){
				WriteToFile.replaceFile("Repositories.txt", reps.get(i));
			}
			else{
				WriteToFile.appendToFile("Repositories.txt", reps.get(i));
			}
		}
		
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
						WriteToFile.replaceFile(fileName, temp2);
						firstWrite = false;
					}
					else{
						WriteToFile.appendToFile(fileName, temp2);
					}
				}
			}
			
		}
	}
}
