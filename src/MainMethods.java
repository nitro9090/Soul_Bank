import java.util.ArrayList;

import customerInfo.Customer;
import customerInfo.Donations;
import customerInfo.RepActiv;

public class MainMethods extends Main {
	
	static Customer newRepMenu(Customer customer, boolean finalTrans) {
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
    			customer = MainMethods.actionDoubleCheck(customer, action, finalTrans, 0, 0, customer, 0, null, "checking");
    			break breakWhile;
    		case 2:
    			customer = MainMethods.actionDoubleCheck(customer, action, finalTrans, 0, 0, customer, 0, null, "growth");
    			break breakWhile;
    		case 3:
    			customer = MainMethods.actionDoubleCheck(customer, action, finalTrans, 0, 0, customer, 0, null, "invest");
    			break breakWhile;
    		case 4:
    			exit = true;
    			break breakWhile;
    		default:
    			invSelect();
    		}
    	}
    	if(finalTrans == true) exit = false;
    	return customer;
	}

	static void checkBalMenu(Customer customer, boolean finalTrans) {
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
			else invSelect();
		}
		if (finalTrans == true) exit = false;
	}

	private static void displayRepActiv(Customer customer, int repNum){
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
				customer = MainMethods.soulTransPrompt(customer, finalTrans);
				break breakWhile;
			case 2:
				customer = MainMethods.soulDepPrompt(customer, finalTrans);
				break breakWhile;
			case 3:
				customer = MainMethods.soulExtrPrompt(customer, finalTrans);
				break breakWhile;
			case 4:
				exit = true;
				break breakWhile;
			default:
				invSelect();
			}
		}
		if(finalTrans == true) exit = false;
		return customer;
	}

	static Customer closeAcctsMenu(Customer customer, boolean finalTrans) {
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
					customer = closeRepPrompt(customer, finalTrans);
				}
				else invSelect();
				break breakWhile;
			case 2:
				customer = closeCustPrompt(customer, customer, finalTrans);
				break breakWhile;
			case 3:
				exit = true;
				break breakWhile;
			default:
				invSelect();
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
					invSelect();
				}
			}
	if(finalTrans == true) exit = false;
	return customer;
	}

	public static void checkRepActiv(int repNum, Customer customer) {
		String action = "CheckRep";
		
		System.out.printf("The current balance of your soul %s repository # %d is %.2f souls \n", customer.getRepType(repNum), repNum,  customer.getRepBal(repNum));
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, 0);
	}

	public static Customer soulTransPrompt(Customer customer, boolean finalTrans) {		
		int repSize = customer.getRep().size();
		String action = "Transfer";
	
		if (repSize < 2){
			System.out.printf("You need to have at least 2 repositories to transfer.  You currently have %d repository \n",repSize);
			exit = true;
		}
	
		int repNumTo = chooseRepPrompt(customer, false, true, "Which repository would you like to transfer souls to?");
		int repNumFrom = choose2ndRepPrompt(customer, false, false, repNumTo, "Which repository would you like to transfer souls from?");
		double transVal = amtToTransPrompt(customer, action, repNumTo, customer, repNumFrom, customer);
		customer = actionDoubleCheck(customer, action, finalTrans, transVal, repNumTo, customer, repNumFrom, customer, null);
		
		return customer;
	}

	public static Customer soulDepPrompt(Customer customer, boolean finalTrans) {
		int repSize = customer.getRep().size();
		String action = "Deposit";
		
		if (repSize < 1){
			System.out.printf("You do not have any repositories, you will be returned to the previous menu. \n");
			exit = true;
		}
		
		int repNumTo = chooseRepPrompt(customer, false, true, "Which repository would you like to deposit your souls?" );
		double transVal = amtToTransPrompt(customer, action, repNumTo, customer, -1,  null);
		customer = actionDoubleCheck(customer, action, finalTrans, transVal, repNumTo, customer, -1, null, null);
		
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

	private static double amtToTransPrompt(Customer customer, String transType, int repNumTo, Customer customerTo, int repNumFrom, Customer customerFrom){
		double transVal = 0;
		
		breakWhile:
		while (exit == false && customer.getRep().size() > 0){
			if(transType.equals("Transfer")){
				System.out.println("Your transaction will be");
				System.out.printf("account # %d %s with %.2f souls ---> account # %d %s with %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom), repNumTo, customerTo.getRepType(repNumTo), customerTo.getRepBal(repNumTo));
				System.out.println("How much would you like to transfer? You will have a chance to verify the transfer.");
			}
			else if(transType.equals("Deposit")){
				System.out.printf("You will be deposting into account # %d %s which currently has %.2f souls \n", repNumTo, customerTo.getRepType(repNumTo), customerTo.getRepBal(repNumTo));
				System.out.println("How much would you like to deposit? You will have a chance to verify the transfer.");
			}
			else if(transType.equals("Extract")){
				System.out.printf("You will be extracting from account # %d %s which currently has %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom));
				System.out.println("How much would you like to extract? You will have a chance to verify the transfer.");
			}
			else if(transType.equals("Donate")){
				System.out.printf("You will be donating from account # %d %s which currently has %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom));
				System.out.println("How much would you like to donate? You will have a chance to verify the transfer.");
			}
	
			transVal = UserInputMethods.scanDbl(customer.getUserName(), 2);
	
			if(transVal > 0){
				if (transType.equals("Deposit") || transVal <= customerFrom.getRepBal(repNumFrom)) break breakWhile;
				else if (transVal > customerFrom.getRepBal(repNumFrom)){
					System.out.println("You do not have enough souls in your account, please give a different amount.");
				}
			}
			else invAmt();
		}	
		return transVal;
	}

	public static Customer closeRepPrompt(Customer customer, boolean finalTrans) {
		int repSize = customer.getRep().size();
		String action = "CloseRep";
		
		if (repSize == 0){
			System.out.println("You do not have any acccounts to close, returning to the previous menu.");
			exit = true;
		}
		
		int delRepNum = chooseRepPrompt(customer, false, true, "Which repository would you like to close?");
		customer = repHasSoul(customer, delRepNum, true, true, true, true);
		customer = actionDoubleCheck(customer, action, finalTrans, 0, delRepNum, customer, -1, null, null);
		
		return customer;
	}

	public static Customer closeCustPrompt(Customer customer, Customer delCustomer, boolean finalTrans) {
		// make sure the customer wants to close their account	
		customer = actionDoubleCheck(customer, "1stCloseCustCheck", false, -1, -1, null, -1, null, " ");
		boolean allEmptReps = true;

		//check to see if the account has any repositories with souls in it, if there are it asks the user what to do with the souls in each account.
		if (exit == false){
			for (int i = 0; i < customer.getRep().size(); i++){
				int repNumList = customer.getRepNum(i);
				if (customer.getRepBal(repNumList) > 0 && allEmptReps == false){
					System.out.println("You still have souls in your repositories, you will need to empty each one before closing your account.");
					allEmptReps = false;
				}
				if(customer.getRepBal(repNumList) > 0){
					ArrayList<Donations> donations = new ArrayList<>();
					Donations temp = new Donations(repNumList, customer.getRepBal(repNumList));
					donations.add(temp);
					customer = repHasSoul(customer, repNumList, false, true, false, true);
				}
			}
		}

		// Make sure the customer really want to close the account, if they do then the customer account is closed.
		customer = actionDoubleCheck(customer, "2ndCloseCustCheck", true, -1, -1, delCustomer, -1, null, null);
		if(finalTrans == true) exit = false;
		return customer;
	}

	private static Customer repHasSoul(Customer customer, int closingRepNum, boolean allowTrans, boolean allowExtr, boolean allowNewRep, boolean allowDon){
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
					Customer customer1 = newRepMenu(customer, false);

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
					customer = devilDealMenu(customer, false, donations);
				}
				//Exits the menu
				else if(choice == repSize+4){
					exit = true;
					break;
				}
				else if(check == false) invSelect();
			}
		}
		return customer;
	}

	private static Customer dealPrompt(Customer customer, boolean finalTrans, String dealType, ArrayList<Donations> donations) {
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

	private static Customer processTheDeal(Customer customer, ArrayList<Donations> donations, String transType) {
		for(int i = 0; i < donations.size(); i++){
			customer = donateSouls(customer, donations.get(i).getRepNum(), donations.get(i).getDonAmt(), transType);
		}	
		return customer;
	}

	static Customer actionDoubleCheck(Customer customer, String action, boolean finalTrans, double transVal, int repNumTo, Customer customerTo, int repNumFrom, Customer customerFrom, String repType){
		breakWhile:
		while (exit == false){
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
			
			String isYN = inputYN(customer);
			
			if(isYN.equals("Y")){
				if(action.equals("Transfer")){
					customer = transSouls(customer, customerTo, customerFrom, repNumTo, repNumFrom, transVal);
				}
				else if(action.equals("Deposit")){
					customer = depSouls(customerTo, repNumTo, transVal);
				}
				else if(action.equals("Extract")){
					customer = extractSouls(customerFrom, repNumFrom, transVal);
				}
				else if (action.equals("NewRep")){
					customer = newRep(customer, customerTo.getUserName(), repType, 0);
				}
				else if(action.equals("CloseRep")){
					customer = deleteRep(customer, customerTo, repNumTo);
				}
				else if(action.equals("1stCloseCustCheck")){
				}
				else if(action.equals("2ndCloseCustCheck")){
					customer = deleteCust(customer, customerTo);
				}
				break breakWhile;
			}
			else if(isYN.equals("N")){
				exit = true;
			}	
		}
		return customer;
	}

	private static Customer depSouls(Customer customer, int repNum,  double transVal){
		String action = "Deposit";
		
		customer = ReadWriteFile.transferSouls(customer, repNum, -1, transVal, true, false);
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), "Deposit");
		ReadWriteFile.recordRepActiv(activNum, repNum, action, transVal);
		System.out.println(transVal + " souls have been deposited into " + customer.getUserName() + "'s account.");
		
		return customer;
	}

	private static Customer extractSouls(Customer customer, int repNum, double transVal){
		String action = "Extract";
				
		customer = ReadWriteFile.transferSouls(customer, -1, repNum, transVal, false, true);
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, -transVal);
		System.out.println(transVal + " souls have been extracted from " + customer.getUserName() + "'s account.");
				
		return customer;
	}

	private static Customer transSouls(Customer customer, Customer customerTo, Customer customerFrom, int repNumTo, int repNumFrom, double transVal){
		String action = "Transfer";
		
		customer = ReadWriteFile.transferSouls(customer, repNumTo, repNumFrom, transVal, true, true);
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), action);
		ReadWriteFile.recordRepActiv(activNum, repNumTo, action, transVal);
		ReadWriteFile.recordRepActiv(activNum, repNumFrom, action, -transVal);
		System.out.println(transVal + " souls have been transferred between " + customer.getUserName() + "'s accounts.");
		
		return customer;
	}
	
	private static Customer donateSouls(Customer customer, int repNum, double transVal, String transType){
		String action = "Donate";
		
		customer = ReadWriteFile.transferSouls(customer, -1, repNum, transVal, false, true);
		ReadWriteFile.donateToDarkOnes(customer.getUserName(), transVal);
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, -transVal);
		System.out.println(transVal + " souls have been donated from " + customer.getUserName() + "'s " + repNum + " " + customer.getRepType(repNum) +  " account.");
		
		return customer;
	}

	private static Customer newRep(Customer customer, String repUserName, String repType, double inRepBal){
		String action = "NewRep";
		String filename = "Repositories.txt";
		int newRepNum = ReadWriteFile.findPersValInt("NewRepNum");
	
		String FileData = repUserName + " " + newRepNum + " " + repType + " " + inRepBal;
		ReadWriteFile.appendToFile(filename, FileData);
		ReadWriteFile.updPersValInt("NewRepNum", newRepNum+1);
	
		customer = ReadWriteFile.loadCustomerFull(customer.getUserName());
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), action);
		ReadWriteFile.recordRepActiv(activNum, newRepNum, action, inRepBal);
		
		System.out.println("A new " + repType + " account for " + repUserName + " has been created. The account # is " + newRepNum + " and the initial balance is " + inRepBal + " souls");
		
		return customer;
	}
	
	private static Customer deleteRep(Customer customer, Customer delCustomer, int delRepNum){
		String action = "DelRep";
		String delRepType = delCustomer.getRepType(delRepNum);
	
		ReadWriteFile.deleteRep(delRepNum);
		int activNum = ReadWriteFile.recordActiv(customer.getUserName(), action);
		ReadWriteFile.recordRepActiv(activNum, delRepNum, action, 0);
		
		System.out.printf("%s's # %d %s account has been closed\n", delCustomer.getUserName(),delRepNum, delRepType);
		
		if(customer.getUserName().equals(delCustomer.getUserName())){
			return ReadWriteFile.loadCustomerFull(customer.getUserName());
		}
		
		return customer;
	}
	
	private static Customer deleteCust(Customer customer, Customer delCustomer){
		String action = "DelCust";
		
		for (int i = 0; i < delCustomer.getRep().size(); i++){
			int delRepNum = delCustomer.getRepNum(i);
			customer = deleteRep(customer, delCustomer, delRepNum);
		}
		
		ReadWriteFile.deleteCust(delCustomer.getUserName());
		ReadWriteFile.recordActiv(customer.getUserName(), action);
		
		System.out.println(delCustomer.getUserName() + "'s customer account has been closed");
		
		if(customer.getUserName().equals(delCustomer.getUserName()) ){
			customer.setUserName(null);
		}
		return customer;
	}


	private static int chooseRepPrompt(Customer customer, boolean finalTrans, boolean inclEmptReps, String question){
		int repSize = customer.getRep().size();
		int repNum = -1;
		boolean check = false;
		
		breakWhile:
		while (exit == false){
			System.out.println(question);
			int countReps = listReps(customer, -1, -1, -1, inclEmptReps);
			
			if (countReps == 0){
				System.out.println("This transaction is not possible with these accounts.");
				exit = true;
				break;
			}
			
			System.out.printf("(%d) Cancel the transaction \n", repSize+1);
	
			int choice = UserInputMethods.scanInt(customer.getUserName());
	
			for(int j = 0; j < repSize; j++){
				int repNumList = customer.getRepNum(j);
				if(inclEmptReps == false && choice == j+1 && customer.getRepBal(repNumList) == 0){
					invSelect();
					check = true;
				}
				else if(choice == j+1){
					repNum = repNumList;
					break breakWhile;
				}
			}
			if(choice == repSize+1){
				System.out.println("You are being returned to the main menu.");
				exit = true;
			}
			else if(check == false) invSelect();
		}
		if(finalTrans == true) exit = false;
		return repNum;
	}

	private static int choose2ndRepPrompt(Customer customer, boolean finalTrans, boolean inclEmptReps, int firstRepNum, String question) {
		int repNum = -1;
		int repSize = customer.getRep().size();
		boolean check = false;
	
		while (exit == false){
			System.out.println(question);
			int countReps = listReps(customer, firstRepNum, -1, -1, inclEmptReps);
	
			if (countReps == 0){
				System.out.println("This transaction is not possible with these accounts.");
				exit = true;
				break;
			}
			
			System.out.printf("(%d) Cancel the transfer \n", repSize+1);
	
			int choice = UserInputMethods.scanInt(customer.getUserName());
	
			for(int j = 0; j< repSize; j++){ 
				int repNumList = customer.getRepNum(j);
				if(inclEmptReps == false && choice == j+1 && customer.getRepBal(repNumList) == 0){
					invSelect();
					check = true;
				}
				if(choice == j+1 && repNumList != firstRepNum && customer.getRepBal(repNumList) != 0){
					return repNum = customer.getRepNum(j);
				}
			}
			if(choice == repSize+1){
				System.out.println("You are being returned to the previous menu.");
				exit = true;
			}
			else if(check == false) invSelect();
		}
		if(finalTrans == true) exit = false;
		return repNum;
	}
	
	public static int listReps(Customer customer, int skipRepNum1, int skipRepNum2, int skipRepNum3, boolean inclEmptReps){
		int countReps = 0;
		int repSize = customer.getRep().size();
		
		for (int i = 0; i < repSize; i++){
			int repNumList = customer.getRepNum(i);
			if(repNumList != skipRepNum1 && repNumList != skipRepNum2 && repNumList != skipRepNum3){
				if(inclEmptReps == true || (inclEmptReps == false && customer.getRepBal(repNumList) != 0)){
					System.out.printf("(%d) Account #: %d  Soul %s : %.2f \n", i+1, repNumList, customer.getRepType(repNumList), customer.getRepBal(repNumList));
					countReps++;
				}
			}
		}
		return countReps;
	}
	
	public static String inputYN(Customer customer){
		if(exit == false){
			String isYN = UserInputMethods.scanStr(customer.getUserName());

			if(isYN.equals("Y")){	
				return isYN;
			}
			else if(isYN.equals("N")){
				//System.out.println("The transaction has been cancelled.  You will be returned to the main menu.");
				return isYN;
			}
			else invSelect();
		}
		return "null";
	}
}
