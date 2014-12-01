import java.util.ArrayList;

import customerInfo.Customer;

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
    			System.out.println("please choose again, your options are");
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
			System.out.println("Which account balance would you like to check");
			listReps(customer, -1, -1, -1);

			System.out.printf("(%d) Return to previous menu \n", RepSize+1);

			int choice = UserInputMethods.scanInt(customer.getUserName());

			for(int i = 0; i< RepSize; i++){
				if(choice == i+1){
					MainMethods.checkBalPrompt(customer.getRepNum(i), customer);
					break breakWhile;
				}
			}
			if(choice == RepSize+1){
				exit = true;
				break breakWhile;
			}
			else{
				System.out.println("Invalid Choice, please choose again.");
			}
		}
		if (finalTrans == true) exit = false;
	}

	public static void checkBalPrompt(int repNum, Customer customer) {
		System.out.printf("The current balance of your soul %s repository # %d is %.2f souls \n", customer.getRepType(repNum), repNum,  customer.getRepBal(repNum));
		ReadWriteFile.recordAction(customer.getUserName(), "checkBal", repNum + " " + customer.getRepType(repNum));
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
				System.out.println("please choose again.");
			}
		}
		if(finalTrans == true) exit = false;
		return customer;
	}

	public static Customer soulTransPrompt(Customer customer, boolean finalTrans) {		
		int repSize = customer.getRep().size();
		String action = "Transfer";
	
		if (repSize < 2){
			System.out.printf("You need to have at least 2 repositories to transfer.  You currently have %d repository \n",repSize);
			exit = true;
		}
	
		int repNumTo = chooseRepPrompt(customer, false, "Which repository would you like to transfer souls to?");
		int repNumFrom = choose2ndRepPrompt(customer, false, repNumTo, "Which repository would you like to transfer souls from?");
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
		
		int repNumTo = chooseRepPrompt(customer, false, "Which repository would you like to deposit your souls?" );
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
		
		int repNumFrom = chooseRepPrompt(customer, false, "Which repository would you like to extract souls from?" );
		double transVal = amtToTransPrompt(customer, action, -1, null, repNumFrom,  customer);
		customer = actionDoubleCheck(customer, action, finalTrans, transVal, -1, null, repNumFrom, customer, null);
		
		return customer;
	}

	private static double amtToTransPrompt(Customer customer, String transType, int repNumTo, Customer customerTo, int repNumFrom, Customer customerFrom){
		double transVal = 0;
		
		breakWhile:
		while (exit == false){
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
	
			transVal = UserInputMethods.scanDbl(customer.getUserName());
	
			boolean checkDec = CheckMethods.decCheck(transVal, 2);
	
			if (transType.equals("Deposit") || (transVal <= customerFrom.getRepBal(repNumFrom) && transVal > 0 && checkDec == true)) break breakWhile;
			else if (transVal > customerFrom.getRepBal(repNumFrom)){
				System.out.println("You do not have enough souls in your account, please give a different amount.");
			}
			else System.out.println("That is an invalid amount, we are not pleased. Try again.");
		}	
		return transVal;
	}

	static Customer closeRepMenu(Customer customer, boolean finalTrans) {
		breakWhile:
		while (exit == false){
			System.out.println("What would you like to do?");
			System.out.println("(1) close a soul repository");
			System.out.println("(2) close your customer account (including all soul repositories)");
			System.out.println("(3) Return to Main Menu");
			int choice = UserInputMethods.scanInt(customer.getUserName());
			switch (choice) {
			case 1: 
				if (customer.getRep().isEmpty()){
					System.out.println("You do not have any repositories to close.");
				}
				else customer = closeRepPrompt(customer, finalTrans);
				break breakWhile;
			case 2:
				customer = deleteCust(customer, finalTrans);
				break breakWhile;
			case 3:
				exit = true;
				break breakWhile;
			default:
				System.out.println("please choose again.");
			}
		}
		if(finalTrans == true) exit = false;
		return customer;
	}

	public static Customer closeRepPrompt(Customer customer, boolean finalTrans) {
		int repSize = customer.getRep().size();
		String action = "CloseRep";
		
		if (repSize == 0){
			System.out.println("You do not have any acccounts to close, returning to the previous menu.");
			exit = true;
		}
		
		int delRepNum = chooseRepPrompt(customer, false, "Which repository would you like to close?");
		customer = repHasSoul(customer, delRepNum);
		customer = actionDoubleCheck(customer, action, finalTrans, 0, -1, null, delRepNum, customer, null);
		
		return customer;
	}

	private static Customer repHasSoul(Customer customer, int closingRepNum){
		boolean doIt = false;
		double transVal = 0;
		int repPos = customer.findRepPos(closingRepNum); 
		int newRepNum = -1;
		int repSize = customer.getRep().size();
		
		//When attempting to close an account, this method checks to see if the account is empty 
		//and gives the user options on how to empty the account.
		if (exit == false){
			while (customer.getRepBal(closingRepNum) != 0  && exit == false) {
				boolean check = false;
				//menu on how to empty the account with souls in it.
				System.out.println("Your account still has souls in it, you must empty the account in order to close it.");
				if (repSize == 0) System.out.println("Note, you are attempting to close your final account.");
				System.out.println("You may either: ");
				if (repSize != 0) System.out.println("Transfer all of the souls to the below accounts.");
				listReps(customer, closingRepNum, 0, 0);
				System.out.printf("(%d) Extract all of the souls\n", repSize+1);
				System.out.printf("(%d) Create a new repository and move all of the souls into it\n", repSize+2);
				System.out.printf("(%d) Donate all of the souls to the Dark Ones\n", repSize+3);
				System.out.printf("(%d) Return to previous menu\n", repSize+4);
	
				int choice = UserInputMethods.scanInt(customer.getUserName());
	
				//transfers all of the souls into another account
				for(int i = 0; i< repSize; i++){
					if (choice == i+1 ){
						int repNumTo = customer.getRepNum(i); 
						customer = actionDoubleCheck(customer, "Transfer", false, customer.getRepBal(closingRepNum), repNumTo, customer, closingRepNum, customer, null);
						check = true;
					}
				}
				//extracts all of the souls from the account
				if(choice == repSize+1){
					customer = actionDoubleCheck(customer, "Extract", false, customer.getRepBal(closingRepNum), -1, null, closingRepNum, customer, null);
				}
	
				//creates a new repository, then transfers all of the souls into that new account.
				else if(choice == repSize+2){
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
				else if(choice == repSize+3){
					transVal = customer.getRepBal(repPos);
					//doIt = doubleCheck(customer, null, customer, exit, repSize, "Extract", customer.getRepBal(repPos), -1, repPos);
	
					if (doIt == true){
						customer = extractSouls(customer, closingRepNum, transVal);
						System.out.println("Your transfer is complete.  The dark ones appreciate your business.");
						System.out.println("You will be returned to the previous menu.");
					}
				}
				//Exits the menu
				// needs help
				else if(choice == repSize+4){
					exit = true;
					break;
				}
				else if(check == false){
					System.out.println("Invalid Choice, please choose again.");
				}
			}
			if (customer.getRepBal(closingRepNum) == 0) exit = false;
		}
		return customer;
	}

	static Customer devilDealMenu(Customer customer, boolean finalTrans) {
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
					customer = moneyDealPrompt(customer,finalTrans);
					break breakWhile;
				case 2:
					customer = powerDealPrompt(customer, finalTrans);
					break breakWhile;
				case 3:
					customer = loveDealPrompt(customer, finalTrans);
					break breakWhile;
				case 4:
					exit = true;
					break breakWhile;
				default:
					System.out.println("please choose again.");
				}
			}
	if(finalTrans == true) exit = false;
	return customer;
	}

	private static Customer moneyDealPrompt(Customer customer, boolean finalTrans) {
		ArrayList<Donations> donations = new ArrayList<>();
		
		System.out.println("You would like money, the root of all evil...");
		System.out.println("This can be granted, but at a high price and we are fickle.");
		System.out.println("How much will you give up for it?");
		
		donations = amtToDonate(customer);
		
		return customer;
	}
	
	private static ArrayList<Donations> amtToDonate(Customer customer){
		ArrayList<Donations> donations = new ArrayList<>();
		boolean moreToAdd = true;
		
		while(moreToAdd == true && exit == false){
			int repNumDonate = chooseRepPrompt(customer, false, "Which repository would you like to donate from?  You may take from more than one.");
			double newDonAmt = amtToTransPrompt(customer, "Donate", -1, null, repNumDonate, customer);
			
			if(exit == false){
				Donations temp = new Donations(repNumDonate,newDonAmt);
				donations.add(temp);
			}
			
			while (exit == false){
				System.out.println("Would you like to donate more?");
				String isYN = CheckMethods.doubleCheck(customer);
				if (isYN.equals("Y")){
					break;
				}
				else if (isYN.equals("N")){
					moreToAdd = false;
					break;
				}
				else{
					System.out.println("invalid choice");
				}
			}
		}
		return donations;
	}
	
	static Customer dealDoubleCheck(Customer customer, String action, boolean finalTrans, ArrayList<Donations> donations){
		double transVal = 0;
		
		for(int i=0; i < donations.size();i++){
			transVal = transVal + donations.get(i).getDonAmt();
		}
			
		
		breakWhile:
		while (exit == false){
			if(action.equals("Donate")){
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
				System.out.println("You will now be closing " + customerFrom.getUserName() + "'s account # : " + repNumFrom + " soul " + customerFrom.getRepType(repNumFrom));
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else{
				System.out.println("Error: action double check wasn't accessed properly");
				exit = true;
				break breakWhile;
			}
			
			String isYN = CheckMethods.doubleCheck(customer);
			
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
					customer = newRepos(customer, customerTo.getUserName(), repType, 0);
				}
				else if(action.equals("CloseRep")){
					customer = deleteRepos(customer, customerFrom, repNumFrom);
				}
				else if(action.equals("DonateMoney"))
				break breakWhile;
			}
			else if(isYN.equals("N")){
				exit = true;
			}	
		}
		return customer;
	}
	

	private static Customer powerDealPrompt(Customer customer,
			boolean finalTrans) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Customer loveDealPrompt(Customer customer, boolean finalTrans) {
		// TODO Auto-generated method stub
		return null;
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
				System.out.println("You will now be closing " + customerFrom.getUserName() + "'s account # : " + repNumFrom + " soul " + customerFrom.getRepType(repNumFrom));
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else{
				System.out.println("Error: action double check wasn't accessed properly");
				exit = true;
				break breakWhile;
			}
			
			String isYN = CheckMethods.doubleCheck(customer);
			
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
					customer = newRepos(customer, customerTo.getUserName(), repType, 0);
				}
				else if(action.equals("CloseRep")){
					customer = deleteRepos(customer, customerFrom, repNumFrom);
				}
				else if(action.equals("DonateMoney"))
				break breakWhile;
			}
			else if(isYN.equals("N")){
				exit = true;
			}	
		}
		return customer;
	}

	
	
	/*public static void transCompl(String transType){
		if (transType.equals("Transfer")){
			System.out.println("Your transfer is complete.");
		}
		else if (transType.equals("Deposit")){
			System.out.println("Your deposit is complete.");
		}
		else if (transType.equals("Extract") ){
			System.out.println("Your extraction is complete.");
		}
		else if(transType.equals("NewRep")){
			// output is within new repository action
		}
		else if (transType.equals("CloseRep")){
			System.out.println("The dark ones appreciate your business.");
		}
		System.out.println("You will be returned to the main menu.");
	}*/

	private static Customer depSouls(Customer customer, int repNum,  double transVal){
		customer = ReadWriteFile.transferSouls(customer, repNum, -1, transVal, true, false);
		ReadWriteFile.recordAction(customer.getUserName(), "Deposit", transVal + "-->" + " " + repNum + " " + customer.getRepType(repNum));
		System.out.println(transVal + " souls have been deposited into " + customer.getUserName() + "'s account.");
		
		return customer;
	}

	private static Customer extractSouls(Customer customer, int repNum, double transVal){
		customer = ReadWriteFile.transferSouls(customer, -1, repNum, transVal, false, true);
		ReadWriteFile.recordAction(customer.getUserName(), "Extract", repNum + " " + customer.getRepType(repNum) + "-->" + transVal);
		System.out.println(transVal + " souls have been extracted from " + customer.getUserName() + "'s account.");
				
		return customer;
	}

	private static Customer transSouls(Customer customer, Customer customerTo, Customer customerFrom, int repNumTo, int repNumFrom, double transVal){
		customer = ReadWriteFile.transferSouls(customer, repNumTo, repNumFrom, transVal, true, true);
		ReadWriteFile.recordAction(customer.getUserName(), "Transfer", repNumFrom + " " + customerFrom.getRepType(repNumFrom) + "-->" + transVal + "-->" + repNumTo + " " + customerTo.getRepType(repNumTo));
		System.out.println(transVal + " souls have been transfered between " + customer.getUserName() + "'s accounts.");
		
		return customer;
	}

	private static Customer newRepos(Customer customer, String repUserName, String repType, double inRepBal){
		String filename = "Repositories.txt";
		int repNum = ReadWriteFile.findUnusedRepNum();
	
		String FileData = repUserName + " " + repNum + " " + repType + " " + inRepBal;
		ReadWriteFile.appendToFile(filename, FileData);
	
		customer = ReadWriteFile.loadCustomerFull(customer.getUserName());
		ReadWriteFile.recordAction(customer.getUserName(), "NewRep", repUserName + " " + repNum + " " + repType);
		
		System.out.println("A new " + repType + " account for " + repUserName + " has been created. The account # is " + repNum + " and the initial balance is " + inRepBal + " souls");
		
		return customer;
	}
	
	private static Customer deleteRepos(Customer customer, Customer delCustomer, int delRepNum){
		String delRepType = delCustomer.getRepType(delRepNum);
	
		ReadWriteFile.deleteRep(delRepNum);
	
		customer = ReadWriteFile.loadCustomerFull(customer.getUserName());
		ReadWriteFile.recordAction(customer.getUserName(), "DelRep", delCustomer.getUserName() + " " + delRepNum + " " + delRepType);
		
		System.out.printf("%s's # %d %s account has been closed\n", delCustomer.getUserName(),delRepNum, delRepType);
		
		return customer;
	}


	private static int chooseRepPrompt(Customer customer, boolean finalTrans, String question){
		int repSize = customer.getRep().size();
		int repNum = -1;
		boolean check = false;
		
		breakWhile:
		while (exit == false){
			System.out.println(question);
			listReps(customer, -1, -1, -1);
			System.out.printf("(%d) Cancel the transaction \n", repSize+1);
	
			int choice = UserInputMethods.scanInt(customer.getUserName());
	
			for(int j = 0; j < repSize; j++){
				if(choice == j+1){
					repNum = customer.getRepNum(j);
					break breakWhile;
				}
			}
			if(choice == repSize+1){
				System.out.println("You are being returned to the main menu.");
				exit = true;
			}
			else if(check == false){
				System.out.println("Invalid selection!");
			}
		}
		return repNum;
	}

	private static int choose2ndRepPrompt(Customer customer, boolean finalTrans, int firstRepNum, String question) {
		int repNum = -1;
		int repSize = customer.getRep().size();
	
		while (exit == false){
			System.out.println(question);
			listReps(customer, firstRepNum, -1, -1);
	
			System.out.printf("(%d) Cancel the transfer \n", repSize+1);
	
			int choice = UserInputMethods.scanInt(customer.getUserName());
	
			for(int j = 0; j< repSize; j++){ 
				int repNumList = customer.getRepNum(j);
				if(choice == j+1 && repNumList != firstRepNum && customer.getRepBal(repNumList) != 0){
					return repNum = customer.getRepNum(j);
				}
			}
			if(choice == repSize+1){
				System.out.println("You are being returned to the previous menu.");
				exit = true;
			}
			else {
				System.out.println("Invalid selection");
			}
		}
		return repNum;
	}
	
	public static void listRepsTrans(Customer customer, int skipRepNum1, int skipRepNum2, int skipRepNum3){
		int repSize = customer.getRep().size();
		for (int i = 0; i < repSize; i++){
			int repNumList = customer.getRepNum(i);
			if(repNumList != skipRepNum1 && repNumList != skipRepNum2 && repNumList != skipRepNum3){
				System.out.printf("(%d) Transfer your balance to Account # %d %s : %.2f souls\n", i+1,repNumList, customer.getRepType(repNumList), customer.getRepBal(repNumList));
			}
		}
	}
	
	public static void listReps(Customer customer, int skipRepNum1, int skipRepNum2, int skipRepNum3){
		int repSize = customer.getRep().size();
		for (int i = 0; i < repSize; i++){
			int repNumList = customer.getRepNum(i);
			if(repNumList != skipRepNum1 && repNumList != skipRepNum2 && repNumList != skipRepNum3){
				System.out.printf("(%d) Account #: %d  Soul %s : %.2f \n", i+1, repNumList, customer.getRepType(repNumList), customer.getRepBal(repNumList));
			}
		}
	}

	public static Customer deleteCust(Customer customer, boolean finalTrans) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
