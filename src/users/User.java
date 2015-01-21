package users;
import repositories.RepActiv;
import repositories.Repositories;
import transactions.Donations;
import transactions.Transactions;
import transactions.Transfer;

import java.util.ArrayList;

import Misc.ExitMethods;
import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;

public class User {
	public static boolean exit = false;
	private boolean authenticated = false;
	protected String userName;
	private String password;
	private String acctType;
	private String firstName;
	private String lastName;
	
	public User(){
		userName = "null";
		password = null;
		acctType = null;
		firstName = null;
		lastName = null;
	}
	
	public User(String uN, String pW, String uT, String fN, String lN){
		userName = uN;
		password = pW;
		acctType = uT;
		firstName = fN;
		lastName = lN;
	}
	
	public User(String uN){
		User temp = ReadWriteFile.loadUser(uN);
		userName = uN;
		password = temp.getPassword();
		acctType = temp.getAcctType();
		firstName = temp.getFirstName();
		lastName = temp.getLastName();
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getAcctType(){
		return acctType;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public boolean getAuth(){
		return authenticated;
	}
	
	public boolean getExit(){
		return exit;
	}
	
	public void setUser(String uN){
		User temp = ReadWriteFile.loadUser(uN);
		userName = uN;
		password = temp.getPassword();
		acctType = temp.getAcctType();
		firstName = temp.getFirstName();
		lastName = temp.getLastName();
	}
	
	public void setUserName(String uN){
		 userName = uN;
		 // return userName
	}
	
	public void setPassword(String pW){
		 password = pW;
		 //return password;
	}
	
	public void setFirstName(String fN){
		 firstName = fN;
		 //return firstName;
	}
	
	public void setLastName(String lN){
		 lastName = lN;
		 //return lastName;
	}
	
	public void setAcctType(String uT){
		acctType = uT;
	}
	
	public void setExit(boolean inExit){
		exit = inExit;
	}



	/*public String Login(){	
		ArrayList<Customer> customers = ReadWriteFile.loadCustomers();
		
		while (exit == false){
			String inPassword = null;
			System.out.println("Enter 'back' to go back to the login screen");
			System.out.print("Username: ");
			
			String inUserName = UserInputMethods.scanStr("null");
			exit = ExitMethods.exitCompare(inUserName, "back");
			
			if(exit == false){
				//System.out.print("Password: ");
				inPassword = UserInputMethods.scanPwd(inUserName, "Password: ");
				ExitMethods.exitCompare(inUserName, "back");
			}
			
			if(exit == false){
				for(int i = 0; i<customers.size(); i++){
					if (inUserName.equals(customers.get(i).getUserName())){
						if (inPassword.equals(customers.get(i).getPassword())){
							ReadWriteFile.recordActiv(inUserName, "Login");
							return inUserName;
						}
						else{
							System.out.println("Username and/or password do not match or exist, try again.");
							ReadWriteFile.recordActiv(inUserName, "FailedLogin");
						}
					}
					else if(i == customers.size()-1){
						System.out.println("Username and/or password do not match or exist, try again.");
						ReadWriteFile.recordActiv(inUserName, "FailedLogin");
					}
				}
			}
		}
		return null;
	}*/

	
	
	public void authentUser(){
		ArrayList<Customer> customers = ReadWriteFile.loadCustomers();
		String inPassword = null;
		
		System.out.println("Enter 'back' to go back to the login screen");
		System.out.print("Username: ");

		String inUserName = UserInputMethods.scanStr(null);
		exit = ExitMethods.exitCompare(inUserName, "back");

		if(exit == false){
			//System.out.print("Password: ");
			inPassword = UserInputMethods.scanPwd(inUserName, "Password: ");
			exit = ExitMethods.exitCompare(inUserName, "back");
		}

		if(exit == false){
			for(int i = 0; i<customers.size(); i++){
				if (inUserName.equals(customers.get(i).getUserName())){
					if (inPassword.equals(customers.get(i).getPassword())){
						setUser(inUserName);
						ReadWriteFile.recordActiv(inUserName, "Login");
						authenticated = true;
						break;
					}
					
				}
				else if(i == customers.size()-1){
					System.out.println("Username and/or password do not match or exist, try again.");
					authenticated = false;
					ReadWriteFile.recordActiv(userName, "FailedLogin:" + inUserName);
				}
			}
		}
	}

	protected void depSouls(int repNum,  double transVal){
		String action = "Deposit";
		
		ReadWriteFile.transferSouls(true, false, repNum, -1, transVal);
		int activNum = ReadWriteFile.recordActiv(userName, "Deposit");
		ReadWriteFile.recordRepActiv(activNum, repNum, action, transVal);
	}

	protected void extractSouls(int repNum, double transVal){
		String action = "Extract";
				
		ReadWriteFile.transferSouls(false, true, -1, repNum, transVal);
		int activNum = ReadWriteFile.recordActiv(userName, action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, -transVal);
	}

	public void newRep(String repUserName, String repType, double inRepBal){
		if (exit == false){
			String action = "NewRep";
			String filename = "Repositories.txt";
			
			int newRepNum = ReadWriteFile.findPersValInt("NewRepNum");
			ReadWriteFile.updPersValInt("NewRepNum", newRepNum+1);
			
			String FileData = repUserName + " " + newRepNum + " " + repType + " " + inRepBal;
			ReadWriteFile.appendToFile(filename, FileData);
			
			int activNum = ReadWriteFile.recordActiv(getUserName(), action);
			ReadWriteFile.recordRepActiv(activNum, newRepNum, action, inRepBal);
		}
	}

	protected void deleteUser(String customerToDel){
		Customer delCustomer = ReadWriteFile.loadCustomer(customerToDel);
		String action = "DelCust";
		
		for (int i = 0; i < delCustomer.getRep().size(); i++){
			int delRepNum = delCustomer.getRepNum(i);
			deleteRep(delRepNum);
		}
		
		ReadWriteFile.deleteCust(delCustomer.getUserName());
		ReadWriteFile.recordActiv(userName, action);
		
		System.out.println(delCustomer.getUserName() + "'s customer account has been closed");
		
		if(userName.equals(delCustomer.getUserName()) ){
			authenticated = false;
			userName = null;
			exit = true;
		}
	}

	protected void deleteRep(int delRepNum){
		String action = "DelRep";
	
		ReadWriteFile.deleteRep(delRepNum);
		int activNum = ReadWriteFile.recordActiv(userName, action);
		ReadWriteFile.recordRepActiv(activNum, delRepNum, action, 0);
		}

	public void donateSouls(String donUserName, int repNum, double transVal, String transType){
		String action = "Donate";
		
		ReadWriteFile.transferSouls(false, true, -1, repNum, transVal);
		ReadWriteFile.donateToDarkOnes(donUserName, transVal);
		int activNum = ReadWriteFile.recordActiv(donUserName, action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, -transVal);
		//System.out.println(transVal + " souls have been donated from " + donUserName + "'s " + repNum + " " + customer.getRepType(repNum) +  " account.");
	}

	protected static void backMain() {
		System.out.println("Welcome back to the main menu, your options are:");
	}

	public String inputYN(){
		if(exit == false){
			String isYN = UserInputMethods.scanStr(userName);
	
			if(isYN.equals("Y")){	
				return isYN;
			}
			else if(isYN.equals("N")){
				//System.out.println("The transaction has been cancelled.  You will be returned to the main menu.");
				return isYN;
			}
			else MiscMeth.invSelect();
		}
		return "null";
	}

	void actionDoubleCheck(String action, boolean finalTrans, double transVal, Customer customer, int repNumTo, int repNumFrom, String repType){
		while (exit == false){
			Transactions currTrans = new Transactions(userName, customer, null);
			
			if(action.equals("Transfer")){
				currTrans = new Transfer(userName, customer, transVal, repNumTo, repNumFrom);
			}
			
			if(action.equals("Transfer")){
				System.out.println("After transferring " + transVal + " souls, your account totals will be");
				System.out.printf("account %d %s : %.2f souls     account %d %s : %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom)-transVal, repNumTo, customerTo.getRepType(repNumTo), customerTo.getRepBal(repNumTo)+transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("Deposit")){
				System.out.println("After depositing " + transVal + " souls, your account total will be");
				System.out.printf("account %d %s : %.2f souls \n", repNumTo, customerTo.getRepType(repNumTo), customerTo.getRepBal(repNumTo)+transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("Extract")){
				System.out.println("After extracting " + transVal + " souls, your account total will be");
				System.out.printf("account %d %s : %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom)-transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("NewRep")){
				System.out.println("Are you sure you want to open a new Soul " + repType + " account? type (Y) for yes or (N) for no.");
			}
			else if (action.equals("CloseRep")){
				System.out.println("You will now be closing " + customerTo.getUserName() + "'s account # : " + repNumTo + " soul " + customerTo.getRepType(repNumTo));
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("1stCloseCustCheck")){
				System.out.println("You are about to close your customer account, aka delete it forever.");
				System.out.println("Are you sure you wish to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("2ndCloseCustCheck")){
				System.out.println("Once again, you are about to close your customer account, aka delete it forever.");
				System.out.println("type (Y) to delete your account or (N) for to cancel the transaction.");
			}
			else{
				System.out.println("Error: action double check wasn't accessed properly");
				exit = true;
				break breakWhile;
			}
			
			currTrans.transDesc();
			String isYN = inputYN();
			
			if(isYN.equals("Y")){
				currTrans.doTrans();
				currTrans.transComplete();
				
				if(action.equals("Transfer")){
					transSouls(repNumTo, repNumFrom, transVal);
					System.out.println(transVal + " souls have been transferred between " + customer + "'s accounts.");
				}
				else if(action.equals("Deposit")){
					depSouls(repNumTo, transVal);
				}
				else if(action.equals("Extract")){
					extractSouls(repNumFrom, transVal);
				}
				else if (action.equals("NewRep")){
					newRep(customerTo.getUserName(), repType, 0);
				}
				else if(action.equals("CloseRep")){
					deleteRep(repNumTo);
				}
				else if(action.equals("1stCloseCustCheck")){
				}
				else if(action.equals("2ndCloseCustCheck")){
					deleteUser(customerTo);
				}
				break;
			}
			else if(isYN.equals("N")){
				exit = true;
			}	
		}
	}

	private Customer processTheDeal(Customer customer, ArrayList<Donations> donations, String transType) {
		for(int i = 0; i < donations.size(); i++){
			donateSouls(customer.getUserName(), donations.get(i).getRepNum(), donations.get(i).getDonAmt(), transType);
		}	
		return customer;
	}

	static Customer dealDoubleCheck(Customer customer, boolean finalTrans, String action,  ArrayList<Donations> donations){
		double transVal = 0;
		
		for(int i=0; i < donations.size();i++){
			transVal = transVal + donations.get(i).getDonAmt();
		}
		
		breakWhile:
		while (exit == false){
			if(action.equals("Money") || action.equals("Power") || action.equals("Love")){
				for(int i = 0; i < donations.size(); i++){
					System.out.println("You are donating " + donations.get(i).getDonAmt() + " souls from account # " + donations.get(i).getRepNum() + " " + customer.getRepType(donations.get(i).getRepNum()));
				}
				System.out.println("For a total donation of " + transVal + " souls in order to gain " + action + "." );
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
				System.out.println("Be careful what you wish for.");
			}
			else{
				System.out.println("Error: deal double check wasn't accessed properly");
				exit = true;
				break breakWhile;
			}
			
			String isYN = inputYN(customer);
			
			if(isYN.equals("Y")){
				customer = processTheDeal(customer, donations, action);
				if(action.equals("Money")){	
					//moneyDealResults(transVal);
				}
				else if(action.equals("Power")){
					//powerDealResults(transVal);
				}
				else if(action.equals("Love")){
					//loveDealResults(transVal);
				}
				break breakWhile;
			}
			else if(isYN.equals("N")){
				System.out.println("Your donation has been cancelled.");
				exit = true;
			}	
		}
		if(finalTrans == true) exit = false;
		return customer;
	}

	private static ArrayList<Donations> amtToDonate(Customer customer, boolean finalTrans){
		ArrayList<Donations> donations = new ArrayList<>();
		boolean moreToAdd = true;
		
		while(moreToAdd == true && exit == false){
			int repNumDonate = chooseRepPrompt(customer, false, false, "Which repository would you like to donate from?  You may take from more than one.");
			double newDonAmt = amtToTransPrompt(customer, "Donate", -1, null, repNumDonate, customer);
			
			if(exit == false){
				double origBal = customer.getRepBal(repNumDonate);
				customer.setRepBal(repNumDonate, origBal - newDonAmt);
				
				Donations temp = new Donations(repNumDonate, newDonAmt);
				donations.add(temp);
			}
			
			while (exit == false){
				System.out.println("Would you like to donate more?");
				String isYN = inputYN(customer);
				if (isYN.equals("Y")){
					break;
				}
				else if (isYN.equals("N")){
					moreToAdd = false;
					break;
				}
			}
		}
		if(finalTrans == true) exit = false;
		return donations;
	}

	protected static Customer dealPrompt(Customer customer, boolean finalTrans, String dealType, ArrayList<Donations> donations) {
		if(dealType == "Money"){
			System.out.println("You would like money, the root of all evil...");
			System.out.println("This can be granted, but at a high price and we are fickle.");
			//System.out.println("How much are you willing to donate to the dark ones for it?");
		}
		else if(dealType == "Power"){
			System.out.println("You would like power, that which requires great responsibility");
			System.out.println("To gain or bestow such is difficult, you best be prepared donate");
			//System.out.println("How much are you willing to donate to the dark ones for it?");
		}
		else if(dealType == "Love"){
			System.out.println("You wish to find love, a noble pursuit for such a lowly creature.");
			System.out.println("This is the hardest task known, the dark ones will expect much.");
			//System.out.println("How much are you willing to donate to the dark ones for it?");
		}
	
		if (donations == null){
			donations = amtToDonate(customer, false);
		}
		
		customer = dealDoubleCheck(customer, true, dealType, donations);
		
		return customer;
	}

	protected Customer repHasSoul(String userName, int closingRepNum, boolean allowTrans, boolean allowExtr, boolean allowNewRep, boolean allowDon){
		double transVal = 0;
		int newRepNum = -1;
		int repSize = customer.getRep().size();
	
		//When attempting to close an account, this method checks to see if the account is empty 
		//and gives the user options on how to empty the account.
		if(exit == false){
			while (customer.getRepBal(closingRepNum) != 0) {
				boolean check = false;
				//menu on how to empty the account with souls in it.
				System.out.println("account # : " +  closingRepNum + " has " + customer.getRepBal(closingRepNum) + " souls in it, you must empty the account in order to close it.");
				if(allowTrans == true){
					if (repSize == 1) System.out.println("Note, you are attempting to close your final account.");
					System.out.println("You may either: ");
					if (repSize != 1) System.out.println("Transfer all of the souls into the below accounts.");
					listReps(customer, closingRepNum, 0, 0, true);
				}
				if(allowExtr == true){
					System.out.printf("(%d) Extract all of the souls\n", repSize+1);
				}
				if(allowNewRep == true){
					System.out.printf("(%d) Create a new repository and move all of the souls into it\n", repSize+2);
				}
				if(allowDon == true){
					System.out.printf("(%d) Donate all of the souls to the Dark Ones\n", repSize+3);
				}
				System.out.printf("(%d) Return to previous menu\n", repSize+4);
	
				int choice = UserInputMethods.scanInt(customer.getUserName());
	
				//transfers all of the souls into another account
				for(int i = 0; i< repSize; i++){
					if (choice == i+1 && allowTrans){
						int repNumTo = customer.getRepNum(i); 
						customer = actionDoubleCheck(customer, "Transfer", false, customer.getRepBal(closingRepNum), repNumTo, customer, closingRepNum, customer, null);
						check = true;
					}
				}
				//extracts all of the souls from the account
				if(choice == repSize+1 && allowExtr){
					customer = actionDoubleCheck(customer, "Extract", false, customer.getRepBal(closingRepNum), -1, null, closingRepNum, customer, null);
				}
				//creates a new repository, then transfers all of the souls into that new account.
				else if(choice == repSize+2 && allowNewRep){
					Customer customer1 = CustomerMenus.newRepMenu(customer, false);
	
					int cust1size = customer1.getRep().size();
	
					if(exit == false){
						for(int j = 0; j < cust1size ; j++){
							for(int k = 0; k < repSize; k++){
								if(customer1.getRepNum(j) == customer.getRepNum(k)){
									break;
								}
								else if(k == repSize - 1){
									newRepNum = customer1.getRepNum(j);
								}
							}
						}
						customer = customer1;
						transVal = customer.getRepBal(closingRepNum);
					}
					customer = actionDoubleCheck(customer, "Transfer", false, transVal, newRepNum, customer, closingRepNum, customer, null);
				}
	
				// Donate your souls to the dark ones (aka empties the account to nowhere)
				// needs help
				else if(choice == repSize+3 && allowDon){
					ArrayList<Donations> donations = new ArrayList<>();
					Donations temp = new Donations(closingRepNum, customer.getRepBal(closingRepNum));
					donations.add(temp);
					customer = CustomerMenus.devilDealMenu(customer, false, donations);
				}
				//Exits the menu
				else if(choice == repSize+4){
					exit = true;
					break;
				}
				else if(check == false) MiscMeth.invSelect();
			}
		}
		return customer;
	}

	protected static void checkRepActiv(int repNum, Customer customer) {
		String action = "CheckRep";
		
		System.out.printf("The current balance of your soul %s repository # %d is %.2f souls \n", customer.getRepType(repNum), repNum,  customer.getRepBal(repNum));
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, 0);
	}

	protected static void displayRepActiv(Customer customer, int repNum){
		ArrayList<RepActiv> repActiv = ReadWriteFile.loadRepActiv(repNum);
		int repActivSize = repActiv.size();
		int repStart = repActiv.size() - 10;
		
		if(repActivSize < 10){
			repStart = 0;
		}
		
		System.out.println("The last 10 transactions for account # : " + repNum + " " + customer.getRepType(repNum) + " were");
		System.out.printf("%-12s %-12s %-12s \n", "Activity #", "Action", "transaction");
		
		for(int i = repStart; i<repActiv.size(); i++){
			System.out.printf("%09d    %-12s %-12.2f \n", repActiv.get(i).getActivNum(), repActiv.get(i).getAction(), repActiv.get(i).getTransVal());
		}
		
		String temp = "CheckBal:" + repNum;
		ReadWriteFile.recordActiv(customer.getUserName(), temp);
	}

	public void mainMenu() {
		// TODO Auto-generated method stub
		
	}

	protected void repHasSoul(Customer customer, Repositories rep, boolean allowTrans, boolean allowExtr, boolean allowNewRep, boolean allowDon){
		double transVal = 0;
		int newRepNum = -1;
		int repSize = customer.getRep().size();
	
		//When attempting to close an account, this method checks to see if the account is empty 
		//and gives the user options on how to empty the account.
		if(exit == false){
			while (closingRep.getRepBal() != 0) {
				boolean check = false;
				//menu on how to empty the account with souls in it.
				System.out.println("account # : " +  closingRepNum + " has " + customer.getRepBal(closingRepNum) + " souls in it, you must empty the account in order to close it.");
				if(allowTrans == true){
					if (repSize == 1) System.out.println("Note, you are attempting to close your final account.");
					System.out.println("You may either: ");
					if (repSize != 1) System.out.println("Transfer all of the souls into the below accounts.");
					listReps(customer, closingRepNum, 0, 0, true);
				}
				if(allowExtr == true){
					System.out.printf("(%d) Extract all of the souls\n", repSize+1);
				}
				if(allowNewRep == true){
					System.out.printf("(%d) Create a new repository and move all of the souls into it\n", repSize+2);
				}
				if(allowDon == true){
					System.out.printf("(%d) Donate all of the souls to the Dark Ones\n", repSize+3);
				}
				System.out.printf("(%d) Return to previous menu\n", repSize+4);
	
				int choice = UserInputMethods.scanInt(customer.getUserName());
	
				//transfers all of the souls into another account
				for(int i = 0; i< repSize; i++){
					if (choice == i+1 && allowTrans){
						int repNumTo = customer.getRepNum(i); 
						customer = actionDoubleCheck(customer, "Transfer", false, customer.getRepBal(closingRepNum), repNumTo, customer, closingRepNum, customer, null);
						check = true;
					}
				}
				//extracts all of the souls from the account
				if(choice == repSize+1 && allowExtr){
					actionDoubleCheck("Extract", false, customer.getRepBal(closingRepNum), -1, null, closingRepNum, customer, null);
				}
				//creates a new repository, then transfers all of the souls into that new account.
				else if(choice == repSize+2 && allowNewRep){
					Customer customer1 = CustomerMenus.newRepMenu(customer, false);
	
					int cust1size = customer1.getRep().size();
	
					if(exit == false){
						for(int j = 0; j < cust1size ; j++){
							for(int k = 0; k < repSize; k++){
								if(customer1.getRepNum(j) == customer.getRepNum(k)){
									break;
								}
								else if(k == repSize - 1){
									newRepNum = customer1.getRepNum(j);
								}
							}
						}
						customer = customer1;
						transVal = customer.getRepBal(closingRepNum);
					}
					customer = actionDoubleCheck(customer, "Transfer", false, transVal, newRepNum, customer, closingRepNum, customer, null);
				}
	
				// Donate your souls to the dark ones (aka empties the account to nowhere)
				// needs help
				else if(choice == repSize+3 && allowDon){
					ArrayList<Donations> donations = new ArrayList<>();
					Donations temp = new Donations(closingRepNum, customer.getRepBal(closingRepNum));
					donations.add(temp);
					customer = CustomerMenus.devilDealMenu(customer, false, donations);
				}
				//Exits the menu
				else if(choice == repSize+4){
					exit = true;
					break;
				}
				else if(check == false) MiscMeth.invSelect();
			}
		}
		return customer;
	}

	private void actionDoubleCheck(String action, boolean finalTrans, double transVal, Repositories repsTo, Repositories repsFrom, String repType){
		breakWhile:
		while (exit == false){
			if(action.equals("Transfer")){
				System.out.println("After transferring " + transVal + " souls, your account totals will be");
				System.out.printf("account %d %s : %.2f souls     account %d %s : %.2f souls \n", repsFrom.getRepNum(), repsFrom.getRepType(), repsFrom.getRepBal()-transVal, repsTo.getRepNum(), repsTo.getRepType(), repsTo.getRepBal()+transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("Deposit")){
				System.out.println("After depositing " + transVal + " souls, your account total will be");
				System.out.printf("account %d %s : %.2f souls \n", repsTo.getRepNum(), repsTo.getRepType(), repsTo.getRepBal()+transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("Extract")){
				System.out.println("After extracting " + transVal + " souls, your account total will be");
				System.out.printf("account %d %s : %.2f souls \n", repsFrom.getRepNum(), repsFrom.getRepType(), repsFrom.getRepBal()-transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("NewRep")){
				System.out.println("Are you sure you want to open a new Soul " + repType + " account? type (Y) for yes or (N) for no.");
			}
			else if (action.equals("CloseRep")){
				System.out.println("You will now be closing " + repsTo.getUserName() + "'s account # : " + repsTo.getRepNum() + " soul " + repsTo.getRepType());
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("1stCloseCustCheck")){
				System.out.println("You are about to close your customer account, aka delete it forever.");
				System.out.println("Are you sure you wish to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("2ndCloseCustCheck")){
				System.out.println("Once again, you are about to close your customer account, aka delete it forever.");
				System.out.println("type (Y) to delete your account or (N) for to cancel the transaction.");
			}
			else{
				System.out.println("Error: action double check wasn't accessed properly");
				exit = true;
				break breakWhile;
			}
			
			String isYN = inputYN();
			
			if(isYN.equals("Y")){
				if(action.equals("Transfer")){
					transSouls(repsTo.getRepNum(), repsFrom.getRepNum(), transVal);
					System.out.println(transVal + " souls have been transferred between your accounts.");
				}
				else if(action.equals("Deposit")){
					depSouls(repsTo.getRepNum(), transVal);
					System.out.println(transVal + " souls have been deposited into your account.");
				}
				else if(action.equals("Extract")){
					extractSouls(repsFrom.getRepNum(), transVal);
					System.out.println(transVal + " souls have been extracted from your account.");
				}
				else if (action.equals("NewRep")){
					newRep(userName, repType, 0);
					System.out.println("A new " + repType + " account has been created with an initial balance of 0 souls");
				}
				else if(action.equals("CloseRep")){
					deleteRep(repsTo.getRepNum());
					System.out.printf("Repository # %d has been closed\n", repsTo.getRepNum());
	
				}
				else if(action.equals("1stCloseCustCheck")){
				}
				else if(action.equals("2ndCloseCustCheck")){
					deleteUser(userName);
				}
				break breakWhile;
			}
			else if(isYN.equals("N")){
				exit = true;
			}	
		}
	}
}