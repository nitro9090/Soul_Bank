package transactions;

import Misc.ReadWriteFile;
import users.*;

public class Deposit extends TransExtDep{
	public Deposit(User currUser, Customer DepCust){
		super(currUser, DepCust, "Deposit");
	}
	
	public boolean startTrans(){
		requiredReps(1);
		repNum1 = chooseARep(true,repNum1,-1,-1, "Which repository would you like to deposit souls into?");
		amtToTrans();
		doubleCheck();
		doTransfer();
		transComplete();
		return transExit;
	}
	
	private void amtToTrans(){
		boolean check = false;
		while (!check && !transExit){
			System.out.printf("You will be depositing souls into account # %d %s which currently has %.2f souls \n", repNum1, transCust.getRepType(repNum1), transCust.getRepBal(repNum1));
			System.out.println("How many souls will be deposited? You will have a chance to verify.");
			check = amtToTransMethod(false);
		}
	}
	
	void doubleCheck(){
		boolean check = false;
		while (!transExit && !check){
			System.out.println("After depositing " + transVal + " souls, " + transCust.getUserName() + "'s account total will be");
			System.out.printf( "account %d %s : %.2f souls \n", repNum1, transCust.getRepType(repNum1), transCust.getRepBal(repNum1)+transVal);
			System.out.println("Are you sure you want to do this?");
			check = mainDoubleCheck();
		}
	}
	
	public void doTransfer(){
		if(!transExit){
			ReadWriteFile.addSubtrSouls(repNum1, transVal);
			ReadWriteFile.recordUserRepActiv(currUser.getUserName(), transaction, repNum1, transVal);
			
			currUser.refreshRepList();
			transCust.refreshRepList();
		}
	}
	
	public void transComplete(){
		if(!transExit){
			if(currUser.equals(transCust)){
				System.out.println(transVal + " souls have been deposited into your account.");
			}
			else{
				System.out.println(transVal + " souls have been deposited into " + transCust + "'s account.");
			}	
		}
	}
}
