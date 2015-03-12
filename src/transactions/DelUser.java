package transactions;

import Misc.ExitMethods;
import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;
import users.*;

public class DelUser extends CloseAccts {
	boolean doubleCheck = false;
	
	public DelUser(User currUser, Customer closingUser){
		super(currUser, closingUser, "DelUser");
	}
	
	public void startTrans(){
		verifyUser();
		doubleCheck();
		emptyRepsWSouls();
		doubledoubleCheck();
		deleteUser();
	}
	
	private void verifyUser(){
		System.out.println("Before you can do close an account, we need to verify your identity.  Please input your password.");
		while(!authenticated && !transExit){
			System.out.println("To go back to the Main menu, type in 'back'");
			String inPassword = UserInputMethods.scanPwd(currUser.getUserName(), "password: ");
			transExit = MiscMeth.compareStrings(inPassword, "back");
			if(!transExit){
				authenticated = MiscMeth.authenticateUser(currUser.getUserName(), inPassword, "delRep");
			}
		}
	}

	private void doubleCheck(){
		while (!transExit && !check && authenticated){
			System.out.println("Are you sure you want to close your Account?");
			check = mainDoubleCheck();
		}
	}

	
	private void emptyRepsWSouls(){
		boolean acctHasSouls = true;
		boolean firstTime = true;
		while(acctHasSouls){
			acctHasSouls = false;
			for(int i = 0; i < transCust.getRep().size(); i++){
				int repNumList = transCust.getRep().get(i).getRepNum();
				if(firstTime && transCust.getRep().get(i).getRepBal() > 0){
					System.out.println("At least 1 of your Repositories still has souls.");
					System.out.println("You will need to empty each before the account can be closed.");
					firstTime = false;
				}
				acctHasSouls = repHasSoul(repNumList, false, true, false, true);
				if(acctHasSouls) break;
			}
		}
		if(firstTime){
			System.out.println("All of your accounts are already empty.");
		}
	}
	
	private void doubledoubleCheck(){
		while (!transExit && !doubleCheck && check && authenticated){
			System.out.println("Are you truly sure you want to close your Account? (this can't be undone)");
			doubleCheck = mainDoubleCheck();
		}
	}
	
	protected void deleteUser(){
		if(!transExit && check && doubleCheck && authenticated){
			int numReps = transCust.getRep().size();
			while (numReps> 0){
				int delRepNum = transCust.getRepNum(0);
				CloseRep closingReps = new CloseRep(currUser, transCust, delRepNum);
				closingReps.setAuthentication(authenticated);
				closingReps.setCheck(doubleCheck);
				closingReps.doTrans();
				numReps = transCust.getRep().size();
				System.out.println("acct #:" + delRepNum + " has been deleted.");
			}
			
			ReadWriteFile.deleteUser(transCust.getUserName());
			ReadWriteFile.recordActiv(transCust.getUserName(), transaction);
			
			System.out.println(transCust.getUserName() + "'s customer account has been closed");
			
			if(currUser.getUserName().equals(transCust.getUserName())){
				transExit = true;
				currUser.setExit(true);
			}
		}
	}
}
