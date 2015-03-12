package transactions;

import Misc.ReadWriteFile;
import users.*;

public class NewRep extends Transactions {
	private String newRepType = null;
	
	public NewRep(User currentUser, Customer transactionCust){
		super(currentUser, transactionCust, "NewRep");
	}
	
	public void setRepType(String repType){
		newRepType = repType;
	}
	
	public void startTrans(){
		newRepType = currUser.newRepMenu();
		doubleCheckTrans();
		doTrans();
		transComplete();
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
			
			int activNum = ReadWriteFile.recordActiv(currUser.getUserName(), transaction);
			ReadWriteFile.recordRepActiv(activNum, repNum1, transaction, transVal);
			
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
