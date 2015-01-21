package users;
import java.util.ArrayList;

import Misc.ExitMethods;
import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;
import repositories.Repositories;
import transactions.Deposit;
import transactions.Donations;
import transactions.Transfer;

public class Customer extends User{
	ArrayList<Repositories> repositories; 
	
	public Customer(){
		repositories = new ArrayList<>(); 
	}
	
	public Customer(User customer){
		super(customer.getUserName(), customer.getPassword(),customer.getAcctType(), customer.getFirstName(), customer.getLastName());
		repositories = ReadWriteFile.loadRep(customer.getUserName());
	}
	
	public Customer (String UN, String PW, String UT, String FN, String LN){
		super(UN, PW, UT, FN, LN);
		repositories = ReadWriteFile.loadRep(UN);
	}
    
	public ArrayList<Repositories> getRep(){
		return repositories;
	}
	
	public void addRep(int newRepNum, String repType, double inRepBal){
		Repositories temp = new Repositories(newRepNum, repType, inRepBal);
	    repositories.add(temp);
	}
	
	public void addCust(int newRepNum, String repType, double inRepBal){
		Repositories temp = new Repositories(newRepNum, repType, inRepBal);
	    repositories.add(temp);
	}
	
	public Repositories getRep(int repNum){
		int repPos = findRepPos(repNum);
		return repositories.get(repPos);
	}
	
	public String getRepType(int repNum){
		int repPos = findRepPos(repNum);
		return repositories.get(repPos).getRepType();
	}
	
	public int getRepNum(int repPos){
		return repositories.get(repPos).getRepNum();
	}
	
	public double getRepBal(int repNum){
		int repPos = findRepPos(repNum);
		return repositories.get(repPos).getRepBal();
	}
	
	public void setRepBal(int repNum, Double newBal){
		int repPos = findRepPos(repNum);
		repositories.get(repPos).setRepBal(newBal);
	}
	
	public int findRepPos(int repNum) {
		int repPos = -1;
		int repSize = repositories.size();

		for(int i = 0; i < repSize; i++){
			if(repositories.get(i).getRepNum() == repNum){
				return i;
			}
		}
		return repPos;
	}
	
	public void mainMenu(){
	
		//in case someone gets into the MainMenu without a user name
		if(userName == null){
			ExitMethods.exitCommBad();
		}
	
		Customer customer = ReadWriteFile.loadCustomer(userName);
	
		System.out.println("Welcome to the main menu, what would you like to do?");
	
		while (exit == false){
			System.out.println("(1) Open New Repository");
			System.out.println("(2) Check your balance");
			System.out.println("(3) Transfer/extract/deposit souls");
			System.out.println("(4) Close repository/account");
			System.out.println("(5) Make a deal with the devil");
			System.out.println("(6) Logout");
			System.out.println("(escape) at any time to exit the program");
			int choice = UserInputMethods.scanInt(userName);
			switch (choice) {
			case 1: 
				customer = newRepMenu(customer, true);
				backMain();
				break;
			case 2:
				checkBalMenu(customer, true);
				backMain();
				break;
			case 3:
				customer = transExtDepMenu(customer, true);
				backMain();
				break;
			case 4:
				customer = closeAcctsMenu(customer, true);
				backMain();
				break;
			case 5:
				customer = devilDealMenu(customer, true, null);
				backMain();
				break;
			case 6:
				exit = true;
				break;
			default:
				MiscMeth.invSelect();
			}
			if(customer.getUserName() == null){
				exit = true;
			}
		}
		exit = false;
	}

	private Customer newRepMenu(Customer customer, boolean finalTrans) {
		String action = "NewRep";
	
		breakWhile:
			while (exit == false){
				System.out.println("What kind of repository would you like to open?");
				System.out.println("(1) Soul checking (easily access your souls through dark rituals)");
				System.out.println("(2) Soul growth (through close proximity your souls will grow off one another)");
				System.out.println("(3) Soul investment (We will pool your souls and trade them in the free market");
				System.out.println("(4) Return to previous menu");
				//System.out.println("Enter (escape) at any time to exit");
				int choice = UserInputMethods.scanInt(customer.getUserName());
				switch (choice) {
			case 1: 
				actionDoubleCheck(action, finalTrans, 0, 0, 0, "checking");
				break breakWhile;
			case 2:
				actionDoubleCheck(action, finalTrans, 0, 0,  0, "growth");
				break breakWhile;
			case 3:
				actionDoubleCheck(action, finalTrans, 0, 0, 0, "invest");
				break breakWhile;
			case 4:
				exit = true;
				break breakWhile;
			default:
				MiscMeth.invSelect();
			}
		}
		if(finalTrans == true) exit = false;
		return customer;
	}
	
	public void soulTransPrompt(Customer customer, boolean finalTrans) {		
		Transfer transfer = new Transfer(userName, customer);
	
		int repSize = customer.getRep().size();
		if (repSize < 2){
			System.out.printf("You need to have at least 2 repositories to transfer.  You currently have %d repository \n",repSize);
			exit = true;
		}
	
		Transfer.chooseRepPrompt(customer, false, true, "Which repository would you like to transfer souls to?");
		Transfer.choose2ndRepPrompt(customer, false, false, repNumTo, "Which repository would you like to transfer souls from?");
		Transfer.amtToTransPrompt(customer, action, repNumTo, customer, repNumFrom, customer);
		Transfer.doubleCheck
		
		actionDoubleCheck(customer, action, finalTrans, transVal, repNumTo, customer, repNumFrom, customer, null);
	}

	private void closeRepPrompt(Customer customer, boolean finalTrans) {
		int repSize = customer.getRep().size();
		String action = "CloseRep";
		
		if (repSize == 0){
			System.out.println("You do not have any acccounts to close, returning to the previous menu.");
			exit = true;
		}
		
		int delRepNum = chooseRepPrompt(customer, false, true, "Which repository would you like to close?");
		repHasSoul(customer, delRepNum, true, true, true, true);
		actionDoubleCheck(action, finalTrans, 0, delRepNum, -1, null);
	}

	private void closeCustPrompt(String customerToDel, boolean finalTrans) {
		// make sure the customer wants to close their account	
		actionDoubleCheck("1stCloseCustCheck", false, -1, -1, -1, null);
		boolean allEmptReps = true;
	
		//check to see if the account has any repositories with souls in it, if there are they are asked what to do with the souls in each account.
		if (exit == false){
			Customer delCustomer = ReadWriteFile.loadCustomer(customerToDel);
			for (int i = 0; i < delCustomer.getRep().size(); i++){
				int repNumList = delCustomer.getRepNum(i);
				if (delCustomer.getRepBal(repNumList) > 0 && allEmptReps == false){
					System.out.println("You still have souls in your repositories, you will need to empty each one before closing your account.");
					allEmptReps = false;
				}
				if(delCustomer.getRepBal(repNumList) > 0){
					ArrayList<Donations> donations = new ArrayList<>();
					Donations temp = new Donations(repNumList, customer.getRepBal(repNumList));
					donations.add(temp);
					repHasSoul(repNumList, false, true, false, true);
				}
			}
		}
	
		// Make sure the customer really want to close the account, if they do then the customer account is closed.
		actionDoubleCheck("2ndCloseCustCheck", true, -1, -1, -1, null);
		if(finalTrans == true) exit = false;
	}

	private void checkBalMenu(Customer customer, boolean finalTrans) {
		int RepSize = customer.getRep().size();
	
		if (customer.getRep().isEmpty()){
			System.out.printf("You do not have any repositories.  You are being returned to the main menu. \n");
			exit = true;
		}
	
		breakWhile:
		while (exit == false){
			System.out.println("Your account balances are:");
			listReps(customer, -1, -1, -1, true);
	
			System.out.printf("(%d) Return to previous menu \n", RepSize+1);
			
			System.out.println("Choose an account to see its recent activity.");
	
			int choice = UserInputMethods.scanInt(customer.getUserName());
	
			for(int i = 0; i< RepSize; i++){
				if(choice == i+1){
					displayRepActiv(customer, customer.getRepNum(i));
					break breakWhile;
				}
			}
			if(choice == RepSize+1){
				exit = true;
				break breakWhile;
			}
			else MiscMeth.invSelect();
		}
		if (finalTrans == true) exit = false;
	}

	static Customer transExtDepMenu(Customer customer, boolean finalTrans) {
		if (customer.getRep().isEmpty()){
			System.out.printf("You do not have any repositories.  You are being returned to the main menu. \n");
			exit = true;
		}
	
		breakWhile:
		while (exit == false){
			System.out.println("What would you like to do?");
			System.out.println("(1) Soul transfer (transfer souls between your repositories)");
			System.out.println("(2) Soul deposit (add more souls to a repository)");
			System.out.println("(3) Soul extraction (remove souls from an repository)");
			System.out.println("(4) Return to Main Menu");
			int choice = UserInputMethods.scanInt(customer.getUserName());
			switch (choice) {
			case 1: 
				customer = soulTransPrompt(customer, finalTrans);
				break breakWhile;
			case 2:
				customer = soulDepPrompt(customer, finalTrans);
				break breakWhile;
			case 3:
				customer = soulExtrPrompt(customer, finalTrans);
				break breakWhile;
			case 4:
				exit = true;
				break breakWhile;
			default:
				MiscMeth.invSelect();
			}
		}
		if(finalTrans == true) exit = false;
		return customer;
	}

	Customer closeAcctsMenu(Customer customer, boolean finalTrans) {
		breakWhile:
		while (exit == false){
			System.out.println("What would you like to do?");
			if (!customer.getRep().isEmpty()){
				System.out.println("(1) close a soul repository");
			}
			System.out.println("(2) close your customer account (including all soul repositories)");
			System.out.println("(3) Return to Main Menu");
			int choice = UserInputMethods.scanInt(customer.getUserName());
			switch (choice) {
			case 1: 
				if (!customer.getRep().isEmpty()){
					closeRepPrompt(customer, finalTrans);
				}
				else MiscMeth.invSelect();
				break breakWhile;
			case 2:
				closeCustPrompt(customer, customer, finalTrans);
				break breakWhile;
			case 3:
				exit = true;
				break breakWhile;
			default:
				MiscMeth.invSelect();
			}
		}
		if(finalTrans == true) exit = false;
		return customer;
	}

	static Customer devilDealMenu(Customer customer, boolean finalTrans, ArrayList<Donations> donations) {
		breakWhile:
			while(exit == false){
				System.out.println("So... you wish to make a deal?");
				System.out.println("What is it that you wish for?");
				System.out.println("(1) Money");
				System.out.println("(2) Power");
				System.out.println("(3) Love");
				System.out.println("(4) Return to Main Menu");
				int choice = UserInputMethods.scanInt(customer.getUserName());
				switch (choice) {
				case 1: 
					customer = dealPrompt(customer, finalTrans, "Money", donations);
					break breakWhile;
				case 2:
					customer = dealPrompt(customer, finalTrans, "Power", donations);
					break breakWhile;
				case 3:
					customer = dealPrompt(customer, finalTrans, "Love", donations);
					break breakWhile;
				case 4:
					exit = true;
					break breakWhile;
				default:
					MiscMeth.invSelect();
				}
			}
	if(finalTrans == true) exit = false;
	return customer;
	}

	public static Customer soulExtrPrompt(Customer customer, boolean finalTrans) {
		int repSize = customer.getRep().size();
		String action = "Extract";
		
		if (repSize < 1){
			System.out.printf("You do not have any repositories, you will be returned to the previous menu. \n");
			exit = true;
		}
		
		int repNumFrom = chooseRepPrompt(customer, false, false, "Which repository would you like to extract souls from?" );
		double transVal = amtToTransPrompt(customer, action, -1, null, repNumFrom,  customer);
		customer = actionDoubleCheck(customer, action, finalTrans, transVal, -1, null, repNumFrom, customer, null);
		
		return customer;
	}

	public Customer soulDepPrompt(Customer customer, boolean finalTrans) {
		
		Deposit deposit = new Deposit(userName, customer);
		
		int repSize = customer.getRep().size();
		if (repSize < 1){
			System.out.printf("You do not have any repositories, you will be returned to the previous menu. \n");
			exit = true;
		}
		
		
		deposit.chooseRepPrompt(customer, false, true, "Which repository would you like to deposit your souls?" );
		
		
		double transVal = amtToTransPrompt(customer, action, repNumTo, customer, -1,  null);
		customer = actionDoubleCheck(customer, action, finalTrans, transVal, repNumTo, customer, -1, null, null);
		
		return customer;
	}
	
	public void depSouls(String depUserName, int repNum,  double transVal){
		String action = "Deposit";
		
		ReadWriteFile.transferSouls(true, false, repNum, -1, transVal);
		int activNum = ReadWriteFile.recordActiv(depUserName, "Deposit");
		ReadWriteFile.recordRepActiv(activNum, repNum, action, transVal);
		System.out.println(transVal + " souls have been deposited into " + depUserName + "'s account.");
		
		if(depUserName.equals(getUserName())){
			setRepBal(repNum, getRepBal(repNum) + transVal);
		}
	}
	
	private void deleteRep(Customer customer, Customer delCustomer, int delRepNum){
		String action = "DelRep";
		String delRepType = delCustomer.getRepType(delRepNum);
	
		ReadWriteFile.deleteRep(delRepNum);
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), action);
		ReadWriteFile.recordRepActiv(activNum, delRepNum, action, 0);
		
		System.out.printf("%s's # %d %s account has been closed\n", delCustomer.getUserName(),delRepNum, delRepType);
		
		if(customer.getUserName().equals(delCustomer.getUserName())){
			repositories.remove(findRepPos(delRepNum));
		}
	}
	
	public void donateSouls(String donUserName, int repNum, double transVal, String transType){
		String action = "Donate";
		
		ReadWriteFile.transferSouls(false, true, -1, repNum, transVal);
		ReadWriteFile.donateToDarkOnes(donUserName, transVal);
		int activNum = ReadWriteFile.recordActiv(donUserName, action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, -transVal);
		System.out.println(transVal + " souls have been donated from " + donUserName + "'s " + repNum + " " + getRepType(repNum) +  " account.");
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
			
			if(userName == repUserName){
				addRep(newRepNum, repType, inRepBal);
			}
			
			System.out.println("A new " + repType + " account for " + repUserName + " has been created. The account # is " + newRepNum + " and the initial balance is " + inRepBal + " souls");
		}
	}

	public int listReps(int skipRepNum1, int skipRepNum2, int skipRepNum3, boolean inclEmptReps){
		int countReps = 0;
		int repSize = repositories.size();
		
		for (int i = 0; i < repSize; i++){
			int repNumList = getRepNum(i);
			if(repNumList != skipRepNum1 && repNumList != skipRepNum2 && repNumList != skipRepNum3){
				if(inclEmptReps == true || (inclEmptReps == false && getRepBal(repNumList) != 0)){
					System.out.printf("(%d) Account #: %d  Soul %s : %.2f \n", i+1, repNumList, getRepType(repNumList), getRepBal(repNumList));
					countReps++;
				}
			}
		}
		return countReps;
	}
}
