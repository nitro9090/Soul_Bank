package transactions;

import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;
import users.*;

public class NewRep extends Transactions {
	private String newRepType = null;
	
	public NewRep(User currentUser, Customer transactionCust){
		super(currentUser, transactionCust, "NewRep");
	}
	
	public void setRepType(String repType){
		newRepType = repType;
	}
	
	public boolean startTrans(){
		newRepType = newRepMenu();
		doubleCheckTrans();
		doTrans();
		transComplete();
		return transExit;
	}
	
	private String newRepMenu(){
		while(!transExit){
			currUser.newRepMenuDial();
			int choice = UserInputMethods.scanInt(currUser.getUserName());
			switch (choice) {
			case 1: 
				return "checking";
			case 2:
				return "growth";
			case 3:
				return "invest";
			case 4:
				transExit = true;
				return "null";
			default:
				MiscMeth.invSelect();
			}
		}
		return "null";
	}
	

	private void doubleCheckTrans(){
		while (!transExit){
			if(transVal > 0){
				System.out.println("Are you sure you want to open a new Soul " + newRepType + " account with an initial balance of " + transVal + " souls?");
			}
			else{
				System.out.println("Are you sure you want to open a new Soul " + newRepType + " account?");
			}
			
			boolean check = mainDoubleCheck();	
			if(check) return;
		}
	}
	
	protected void doTrans(){
		if (!transExit){
			repNum1 = ReadWriteFile.findPersValInt("NewRepNum");
			ReadWriteFile.updPersValInt("NewRepNum", repNum1+1);
			
			ReadWriteFile.addRep(transCust.getUserName(), repNum1, newRepType, transVal);
			
			ReadWriteFile.recordUserRepActiv(currUser.getUserName(), transaction, repNum1, transVal);
			
			currUser.refreshRepList();
			transCust.refreshRepList();	
		}
	}
	
	protected void transComplete(){
		if (!transExit){
			System.out.println("A new " + newRepType + " account for " + transCust.getUserName() + " has been created.");
			System.out.println("The account # is " + repNum1 + " and it has an initial balance of " + transVal + " souls");
		}
	}
}
