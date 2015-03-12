package users;

import transactions.CheckBal;
import transactions.CloseAccts;
import transactions.NewRep;
import transactions.TransExtDep;
import Misc.ExitMethods;
import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;

public class Admin extends User{


	public Admin(String uN){
		super(uN);
	}

	public Admin (String uN, String pW, String uT, String fN, String lN){
		super(uN, pW, uT, fN, lN);
	}

	public Admin(User user){
		super(user.getUserName(), user.getPassword(),user.getAcctType(), user.getFirstName(), user.getLastName());
	}
	public void mainMenu(){
		Customer testCustomer = ReadWriteFile.loadCustomer("nitro90902");
		//in case someone gets into the MainMenu without a user name
		if(userName == null){
			ExitMethods.exitCommBad();
		}

		System.out.println("Welcome Admin to the main menu, what would you like to do?");

		while (mainExit == false){
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
				currTrans = new NewRep(this, testCustomer);
				break;
			case 2:
				currTrans = new CheckBal(this, testCustomer);
				break;
			case 3:
				currTrans = new TransExtDep(this, testCustomer);
				break;
			case 4:
				currTrans = new CloseAccts(this, testCustomer);
				break;
			case 5:
				//customer = devilDealMenu(customer, true, null);
				break;
			case 6:
				mainExit = true;
				break;
			default:
				MiscMeth.invSelect();
			}

			if(mainExit == false){
				currTrans.startTrans();
				backMain();
			}
		}
		mainExit = false;
	}

	public void newRepMenu(){		
		breakWhile:
			while (mainExit == false){
				System.out.println("What kind of repository would you like to open?");
				System.out.println("(1) Soul checking (easily access your souls through dark rituals)");
				System.out.println("(2) Soul growth (through close proximity your souls will grow off one another)");
				System.out.println("(3) Soul investment (We will pool your souls and trade them in the free market");
				System.out.println("(4) Return to previous menu");

				int choice = UserInputMethods.scanInt(userName);
				switch (choice) {
				case 1: 
					currTrans.setNewRepType("checking");
					break breakWhile;
				case 2:
					currTrans.setNewRepType("growth");
					break breakWhile;
				case 3:
					currTrans.setNewRepType("invest");
					break breakWhile;
				case 4:
					mainExit = true;
					break breakWhile;
				default:
					MiscMeth.invSelect();
				}
			}

	while (mainExit == false){
		System.out.println("What is the initial balance of the new " + currTrans.getNewRepType() + "account?");

		double inBal = UserInputMethods.scanDbl(userName, 2);

		if(inBal < 0){
			MiscMeth.invAmt();
		}
		else{
			currTrans.setInBal(inBal);
			break;
		}
	}
	}
}
