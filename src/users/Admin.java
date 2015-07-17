package users;

import transactions.*;
import Misc.*;

public class Admin extends User{
	Customer transCust;
	
	/*admin methods
	 * access different accounts
	 * check balances and activity
	 * transfer balances
	 * change user info
	 * Wire Transfer
	 * undo activities
	 *  */

	public Admin(String uN){
		super(uN);
		transCust = null;
	}

	public Admin (String uN, String pW, String uT, String fN, String lN){
		super(uN, pW, uT, fN, lN);
		transCust = null;
	}

	public Admin(User user){
		super(user.getUserName(), user.getPassword(),user.getAcctType(), user.getFirstName(), user.getLastName());
	}
	
	public void mainMenu(){
		transCust = new Customer();

		System.out.println("Welcome Admin to the main menu, what would you like to do?");

		while (mainExit == false){
			if(transCust.getUserName().equals("null")){
				custLessMenu();
			}
			else{
				custMenu();
			}
			
			if(!mainExit  && !currTrans.getTransExit()){
				currTrans.startTrans();
				backMain();
			}
		}
		mainExit = false;
	}

	private void custLessMenu() {
		System.out.println("(1) Choose a customer account to access");
		System.out.println("(2) Change your account info");
		System.out.println("(3) Logout");
		System.out.println("(escape) at any time to exit the program");
		int choice = UserInputMethods.scanInt(userName);
		switch (choice) {
		case 1: 
			currTrans = new ChooseCust(this, null);
			break;
		case 2:
			currTrans = new UpdateAcct(this, null);
			break;
		case 3:
			mainExit = true;
			break;
		default:
			MiscMeth.invSelect();
		}
	}

	private void custMenu() {
		System.out.println("You are currently working on " + transCust.getFirstName()+ " " + transCust.getLastName() + "'s account, username:" + transCust.getUserName());

		while (!mainExit){
			//nulls out any previous transactions
			currTrans = new Transactions(this,transCust, "null");
			secTrans = new Transactions(this,transCust, "null");

			//A basic customers main menu
			System.out.println("(1) Open a New Repository for him/her");
			System.out.println("(2) Check his/her balance");
			System.out.println("(3) Transfer/extract/deposit souls");
			System.out.println("(4) Close repository/account");
			System.out.println("(5) Make a deal with the devil");
			System.out.println("(6) Change account information");
			System.out.println("(7) Wire transfer souls to another user");
			System.out.println("(8) Choose a different user/logout of current user");
			System.out.println("(9) Logout");
			int choice = UserInputMethods.scanInt(userName);
			switch (choice) {
			case 1: 
				currTrans = new NewRep(this, transCust);
				break;
			case 2:
				currTrans = new CheckBal(this, transCust);
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
				updateAcctsMenu();
				break;
			case 7:
				updateAcctsMenu();
				break;
			case 8:
				updateAcctsMenu();
				break;
			case 9:
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
			else if(currTrans.getTransExit() && !mainExit){
				returnToMainDial();
				UserInputMethods.scanStr(userName);
				backMain();
			}
		}
	}

	private void updateAcctsMenu() {
		// TODO Auto-generated method stub
		
	}

	private void transExtDepMenu() {
		currTrans = new TransExtDep(this,transCust, "TransExtDep");
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
				currTrans = new Transfer(this, transCust);
				return;
			case 2:
				currTrans = new Deposit(this, transCust);
				return;
			case 3:
				currTrans = new Extract(this, transCust);
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
		currTrans = new CloseAccts(this,transCust);
		
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
					currTrans = new CloseRep(this, transCust);
				}
				else MiscMeth.invSelect();
				return;
			case 2:
				currTrans = new DelUser(this, transCust);
				return;
			case 3:
				currTrans.setTransExit(true);
				return;
			default:
				MiscMeth.invSelect();
			}
		}
	}
	
	private void changeAcctsMenu() {
		// TODO Auto-generated method stub
		
	}

	public void newRepMenuDial(){	
		while (!currTrans.getTransExit()){
			System.out.println("What kind of repository would you like to open for "+ transCust.getFirstName() +"?");
			System.out.println("(1) Soul checking (easily access your souls through dark rituals)");
			System.out.println("(2) Soul growth (through close proximity your souls will grow off one another)");
			System.out.println("(3) Soul investment (We will pool your souls and trade them in the free market");
			System.out.println("(4) Return to previous menu");
		}
	}
}
