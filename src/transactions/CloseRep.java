package transactions;

import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;
import users.*;

public class CloseRep extends CloseAccts {
	String delRepType = "Null";
	
	public CloseRep(User customer, Customer closingUser){
		super(customer, closingUser, "CloseRep");
	}
	
	public CloseRep(User customer, Customer closingUser, int delRepNum){
		super(customer, closingUser, "CloseRep");
		repNum1 = delRepNum;
	}
	
	public boolean startTrans(){
		requiredReps(1);
		verifyUser();
		chooseRep();
		detIfRepHasSoul();
		doubleCheck();
		doTrans();
		transComplete();
		return transExit;
	}
	
	private void verifyUser(){
		if(!authenticated && !transExit) System.out.println("Before you can do close a repository, we need to verify your identity.  Please input your password.");
		while(!authenticated && !transExit){
			System.out.println("To go back to the Main menu, type in 'back'");
			String inPassword = UserInputMethods.scanPwd(currUser.getUserName(), "password: ");
			transExit = MiscMeth.compareStrings(inPassword, "back");
			if(!transExit){
				authenticated = MiscMeth.authenticateUser(currUser.getUserName(), inPassword, "delRep");
			}
		}
	}
	
	private void chooseRep(){
		if(repNum1 == -1 && !transExit && authenticated) {
			repNum1 = chooseARep(true, -1,-1,-1,"Which repository would you like to close?");
		}
		if(repNum1 == -1) transExit = true;	
	}
	
	private void detIfRepHasSoul(){
		if(!transExit && authenticated){
			repHasSoul(repNum1, true, true, true, true);
		}
	}
	
	
	private void doubleCheck() {
		while(!transExit && authenticated){
			System.out.println("Are you sure you want to close Soul " + transCust.getRepType(repNum1) + " account?");
			check = mainDoubleCheck();	
			if(check) return;
		}
	}
	
	protected void doTrans(){
		if(!transExit && check && authenticated){
			delRepType = transCust.getRepType(repNum1);
			ReadWriteFile.deleteRep(repNum1);
			ReadWriteFile.recordUserRepActiv(currUser.getUserName(), transaction, repNum1, 0);
				
			currUser.refreshRepList();
			transCust.refreshRepList();
		}
	}
	
	protected void transComplete(){
		if(!transExit && authenticated){
			System.out.printf("%s's # %d %s account has been closed\n", transCust.getUserName(),repNum1, delRepType);
		}
	}
}