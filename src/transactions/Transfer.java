package transactions;

import Misc.ReadWriteFile;
import users.*;

public class Transfer extends TransExtDep {
	//repNum1 is the repNum that souls are transferred tp
	//repNum2 is the repNum that souls are transferred from
	
	public Transfer(User currUser, Customer transCust){
		super(currUser, transCust, "Transfer");
	}
	
	public Transfer(User currUser, Customer transCust, int repNumFrom, int repNumTo, Double transAmt){
		super(currUser, transCust, "Transfer");
		repNum1 = repNumTo;
		repNum2 = repNumFrom;
		transVal = transAmt;
	}
	
	
	public boolean startTrans(){
		requiredReps(2);
		if(repNum2 == -1){
			repNum2 = chooseARep(false, -1,-1,-1,"Which repository would you like to transfer souls from?");
		}
		if(repNum1 == -1){
			repNum1 = chooseARep(true,repNum2,-1,-1, "Which repository would you like to transfer souls to?");
		}
		if(transVal == 0){
			amtToTrans();
		}
		doubleCheck();
		doTrans();
		transComplete();
		return transExit;
	}
	
	private void amtToTrans(){
		boolean check = false;
		while (!check && !transExit){
			System.out.println("Your transaction will be");
			System.out.printf("account # %d %s with %.2f souls ---> account # %d %s with %.2f souls \n", repNum2, transCust.getRepType(repNum2), transCust.getRepBal(repNum2), repNum1, transCust.getRepType(repNum1), transCust.getRepBal(repNum1));
			System.out.println("How much is to be transferred? You will have a chance to verify the transfer.");
			check = amtToTransMethod(true);
		}
	}
	
	private void doubleCheck(){
		boolean check = false;
		while (!transExit && !check){
			System.out.println("After transferring " + transVal + " souls, the account totals will be");
			System.out.printf("account %d %s : %.2f souls     account %d %s : %.2f souls \n", repNum2, transCust.getRepType(repNum2), transCust.getRepBal(repNum2)-transVal, repNum1, transCust.getRepType(repNum1), transCust.getRepBal(repNum1)+transVal);
			System.out.println("Are you sure you want to do this?");
			check = mainDoubleCheck();
		}
	}
	
	protected void doTrans(){
		if(!transExit){
			ReadWriteFile.addSubtrSouls(repNum1, transVal);
			int activNum =ReadWriteFile.recordUserRepActiv(currUser.getUserName(), transaction, repNum1, transVal);
			ReadWriteFile.addSubtrSouls(repNum2, -transVal);
			ReadWriteFile.writeRepActiv(activNum, repNum2, transaction, -transVal);
			currUser.refreshRepList();
			transCust.refreshRepList();
		}
	}
	
	protected void transComplete(){
		if(!transExit){
			if(currUser.equals(transCust)){
				System.out.println(transVal + " souls have been transferred between your accounts.");
			}
			else{
				System.out.println(transVal + " souls have been transferred between " + transCust + "'s accounts.");
			}	
		}
	}
}
