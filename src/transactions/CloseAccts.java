package transactions;

import java.util.ArrayList;

import Misc.MiscMeth;
import Misc.UserInputMethods;
import users.Customer;
import users.User;

public class CloseAccts extends Transactions {
	boolean authenticated = false;
	boolean check = false;

	public CloseAccts(User currUser, Customer transUser){
		super(currUser, transUser, "closeAccts");
	}
	
	public CloseAccts(User currUser, Customer transUser, String transType){
		super(currUser, transUser, transType);
	}
	
	public void setAuthentication(boolean authent){
		authenticated = authent;
	}
	
	public void setCheck(boolean checkVal){
		check = checkVal;
	}
	
	protected boolean repHasSoul(int repNumClosing, boolean allowTrans, boolean allowExtr, boolean allowNewRep, boolean allowDon){
		int repSize = transCust.getRep().size();
		boolean didRepHaveSouls = false;

		//When attempting to close an account, this method checks to see if the account is empty 
		//and gives the user options on how to empty the account.
		
		while (transCust.getRepBal(repNumClosing) != 0 && !transExit) {
			didRepHaveSouls = true;
			Transactions temp = new Transactions();
			boolean check = false;
			//menu on how to empty the account with souls in it.
			System.out.println("account # : " +  repNumClosing + " has " + transCust.getRepBal(repNumClosing) + " souls in it, you must empty the account in order to close it.");
			if(allowTrans == true){
				if (repSize == 1) {
					System.out.println("Note, you are attempting to close your final account.");
				}
				System.out.println("You may either: ");
				if (repSize != 1) System.out.println("Transfer all of the souls into the below accounts.");
				transCust.listReps(true, true, repNumClosing, 0, 0);
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

			int choice = UserInputMethods.scanInt(currUser.getUserName());

			//transfers all of the souls into another account
			if(allowTrans){
				for(int i = 0; i< repSize; i++){
					if (choice == i+1){
						int repNumTo = transCust.getRepNum(i); 
						if(repNumTo != repNumClosing){
							temp = new Transfer(currUser, transCust, repNumClosing, repNumTo, transCust.getRepBal(repNumClosing));
							currUser.setSecTrans(temp);
							check = true;
						}
					}
				}
			}
			
			//extracts all of the souls from the account
			if(choice == repSize+1 && allowExtr){
				temp = new Extract(currUser, transCust, repNumClosing, transCust.getRepBal(repNumClosing));
				currUser.setSecTrans(temp);
				check = true;
			}
			//creates a new repository, then transfers all of the souls into that new account.
			else if(choice == repSize+2 && allowNewRep){
				temp = new NewRep(currUser, transCust);
				currUser.setSecTrans(temp);
				currUser.getSecTrans().startTrans();
				if(!currUser.getSecTrans().getTransExit()){
					temp = new Transfer(currUser, transCust, repNumClosing, currUser.getSecTrans().getRepNum1(), transCust.getRepBal(repNumClosing));
					currUser.setSecTrans(temp);
					check = true;
				}
			}

			// Donate your souls to the dark ones (aka empties the account to nowhere)
			// needs help
			else if(choice == repSize+3 && allowDon){
				/*ArrayList<Donations> donations = new ArrayList<>();
				Donations temp = new Donations(repNumClosing, customer.getRepBal(repNumClosing));
				donations.add(temp);
				customer = CustomerMenus.devilDealMenu(customer, false, donations);
				check = true;*/
			}
			//Cancels the transaction
			else if(choice == repSize+4){
				transExit = true;
			}
			else if(!check) MiscMeth.invSelect();
			
			if(check && !transExit){
				currUser.getSecTrans().startTrans();
			}
		}
		return didRepHaveSouls;
	}
}