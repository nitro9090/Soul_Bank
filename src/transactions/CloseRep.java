package transactions;

import Misc.ReadWriteFile;
import users.*;

public class CloseRep extends CloseAccts {
	String delRepType = "Null";
	
	public CloseRep(User customer, Customer closingUser){
		super(customer, closingUser, "CloseRep");
	}
	
	public void startTrans(){
		requiredReps(1);
		repNum1 = chooseARep(true, -1,-1,-1,"Which repository would you like to close?");
		repHasSoul(repNum1, true, true, true, true);
		doubleCheck();
		doTrans();
		transComplete();
	}
	
	private void doubleCheck() {
		while(!transExit){
			System.out.println("Are you sure you want to close Soul " + transCust.getRepType(repNum1) + " account?");
			boolean check = mainDoubleCheck();	
			if(check) return;
		}
	}
	
	protected void doTrans(){
		if(!transExit){
			delRepType = transCust.getRepType(repNum1);
			ReadWriteFile.deleteRep(repNum1);
			int activNum = ReadWriteFile.recordActiv(currUser.getUserName(), transaction);
			ReadWriteFile.recordRepActiv(activNum, repNum1, transaction, 0);

			currUser.refreshRepList();
			transCust.refreshRepList();
		}
	}
	
	protected void transComplete(){
		if(!transExit){
			System.out.printf("%s's # %d %s account has been closed\n", transCust.getUserName(),repNum1, delRepType);
		}
	}
}