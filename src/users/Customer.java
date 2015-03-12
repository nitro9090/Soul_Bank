package users;
import java.util.ArrayList;

import Misc.ExitMethods;
import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;
import repositories.*;
import transactions.*;

public class Customer extends User{
	ArrayList<Repositories> repositories; 
	
	public Customer(){
		super();
		repositories = new ArrayList<>(); 
	}
	
	public Customer(String userName){
		super(userName);
		repositories = ReadWriteFile.loadRep(userName);
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
	
	private int findRepPos(int repNum) {
		int repPos = -1;
		int repSize = repositories.size();

		for(int i = 0; i < repSize; i++){
			if(repositories.get(i).getRepNum() == repNum){
				return i;
			}
		}
		return repPos;
	}
	
	public void refreshRepList(){
		repositories = ReadWriteFile.loadRep(userName);
	}
	
	public void mainMenu(){
		//The main menu for a customer, it lists all of the customer's account options.
	
		System.out.println("Welcome to the main menu, what would you like to do?");
		
		while (!mainExit){
			//nulls out any previous transactions
			currTrans = new Transactions(this,this, "null");
			secTrans = new Transactions(this,this, "null");
			
			//A basic customers main menu
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
				currTrans = new NewRep(this, this);
				break;
			case 2:
				currTrans = new CheckBal(this, this);
				break;
			case 3:
				transExtDepMenu();
				break;
			case 4:
				closeAcctsMenu();
				break;
			case 5:
				//customer = devilDealMenu(customer, true, null);
				break;
			case 6:
				mainExit = true;
				break;
			default:
				MiscMeth.invSelect();
				currTrans.setTransExit(true);
			}
			
			if(!currTrans.getTransExit() && !mainExit){
				currTrans.startTrans();
				returnToMainDial();
				UserInputMethods.scanStr(userName);
				backMain();
			}
			else if(currTrans.getTransExit()){
				returnToMainDial();
				UserInputMethods.scanStr(userName);
				backMain();
			}
		}
	}
	
	private void transExtDepMenu() {
		currTrans = new TransExtDep(this,this, "TransExtDep");
		currTrans.haveReps();
		
		while (!currTrans.getTransExit()){
			System.out.println("What would you like to do?");
			System.out.println("(1) Soul transfer (transfer souls between your repositories)");
			System.out.println("(2) Soul deposit (add more souls to a repository)");
			System.out.println("(3) Soul extraction (remove souls from an repository)");
			System.out.println("(4) Return to Main Menu");
			int choice = UserInputMethods.scanInt(userName);
			switch (choice) {
			case 1: 
				currTrans = new Transfer(this, this);
				return;
			case 2:
				currTrans = new Deposit(this, this);
				return;
			case 3:
				currTrans = new Extract(this, this);
				return;
			case 4:
				currTrans.setTransExit(true);
				return;
			default:
				MiscMeth.invSelect();
			}
		}
	}
	
	private void closeAcctsMenu() {
		currTrans = new CloseAccts(this,this);
		
		while (!currTrans.getTransExit()){
			System.out.println("What would you like to do?");
			if (!currTrans.getTransUser().getRep().isEmpty()){
				System.out.println("(1) close a soul repository");
			}
			System.out.println("(2) close your account (including all soul repositories)");
			System.out.println("(3) Return to Main Menu");
			int choice = UserInputMethods.scanInt(getUserName());
			switch (choice) {
			case 1: 
				if (!currTrans.getTransUser().getRep().isEmpty()){
					currTrans = new CloseRep(this, this);
				}
				else MiscMeth.invSelect();
				return;
			case 2:
				currTrans = new DelUser(this, this);
				return;
			case 3:
				currTrans.setTransExit(true);
				return;
			default:
				MiscMeth.invSelect();
			}
		}
	}
	
	public String newRepMenu(){	
		while (!currTrans.getTransExit()){
			System.out.println("What kind of repository would you like to open?");
			System.out.println("(1) Soul checking (easily access your souls through dark rituals)");
			System.out.println("(2) Soul growth (through close proximity your souls will grow off one another)");
			System.out.println("(3) Soul investment (We will pool your souls and trade them in the free market");
			System.out.println("(4) Return to previous menu");

			int choice = UserInputMethods.scanInt(userName);
			switch (choice) {
			case 1: 
				return "checking";
			case 2:
				return "growth";
			case 3:
				return "invest";
			case 4:
				currTrans.setTransExit(true);
				return "null";
			default:
				MiscMeth.invSelect();
			}
		}
		return "null";
	}
	
	public void newRepMenu(Transactions transOpenRep){	
		while (!currTrans.getTransExit()){
			System.out.println("What kind of repository would you like to open?");
			System.out.println("(1) Soul checking (easily access your souls through dark rituals)");
			System.out.println("(2) Soul growth (through close proximity your souls will grow off one another)");
			System.out.println("(3) Soul investment (We will pool your souls and trade them in the free market");
			System.out.println("(4) Return to previous menu");

			int choice = UserInputMethods.scanInt(userName);
			switch (choice) {
			case 1: 
				transOpenRep.setRepType("checking");
				return;
			case 2:
				transOpenRep.setRepType("growth");
				return;
			case 3:
				transOpenRep.setRepType("invest");
				return;
			case 4:
				currTrans.setTransExit(true);
				return;
			default:
				MiscMeth.invSelect();
			}
		}
	}

	public int listReps(boolean printList, boolean inclEmptReps, int skipRepNum1, int skipRepNum2, int skipRepNum3){
		int countReps = 0;
		int repSize = getRep().size();

		for (int i = 0; i < repSize; i++){
			int repNumList = getRepNum(i);
			if((repNumList != skipRepNum1 && repNumList != skipRepNum2 && repNumList != skipRepNum3)){
				if(inclEmptReps == true || (inclEmptReps == false && getRepBal(repNumList) != 0)){
					if(printList == true){
						System.out.printf("(%d) Account #: %d  Soul %s : %.2f \n", i+1, repNumList, getRepType(repNumList), getRepBal(repNumList));
					}
					countReps++;
				}
			}
		}
		return countReps;
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
	
	public void donateSouls(String donUserName, int repNum, double transVal, String transType){
		String action = "Donate";
		
		ReadWriteFile.transferSouls(false, true, -1, repNum, transVal);
		ReadWriteFile.donateToDarkOnes(donUserName, transVal);
		int activNum = ReadWriteFile.recordActiv(donUserName, action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, -transVal);
		System.out.println(transVal + " souls have been donated from " + donUserName + "'s " + repNum + " " + getRepType(repNum) +  " account.");
	}

	
}
