package transactions;

import Misc.ReadWriteFile;
import users.*;

public class Extract extends TransExtDep {

	public Extract(User currUser, Customer extrUser){
		super(currUser, extrUser, "Extract");
	}
	
	public Extract(User currUser, Customer transCust, int repNumFrom, Double transAmt){
		super(currUser, transCust, "Transfer");
		repNum2 = repNumFrom;
		transVal = transAmt;
	}
	
	public void startTrans(){
		requiredReps(2);
		if(repNum2 == -1){
			repNum2 = chooseARep(true, -1,-1,-1,"Which repository would you like to extract souls from?");
		}
		if(transVal == 0){
			amtToTrans();
		}
		doubleCheck();
		doTransfer();
		transComplete();
	}
	
	private void amtToTrans(){
		boolean check = false;
		while (!check && !transExit){
			System.out.printf("You will be extracting souls from account # %d %s which currently has %.2f souls \n", repNum2, transCust.getRepType(repNum2), transCust.getRepBal(repNum2));
			System.out.println("How many souls will be extracted? You will have a chance to verify.");
			check = amtToTransMethod(true);
		}
	}
	
	void doubleCheck(){
		boolean check = false;
		while (!transExit && !check){
			System.out.println("After extracting " + transVal + " souls, " + transCust.getUserName() + "'s account total will be");
			System.out.printf( "account %d %s : %.2f souls \n", repNum2, transCust.getRepType(repNum2), transCust.getRepBal(repNum2)-transVal);
			System.out.println("Are you sure you want to do this?");
			check = mainDoubleCheck();
		}
	}
	
	public void doTransfer(){
		if(!transExit){
			int activNum = ReadWriteFile.recordActiv(currUser.getUserName(), transaction);
			ReadWriteFile.addSubtrSouls(repNum2, -transVal);
			ReadWriteFile.recordRepActiv(activNum, repNum2, transaction, -transVal);
			currUser.refreshRepList();
			transCust.refreshRepList();
		}
	}
	
	public void transComplete(){
		if(!transExit){
			if(currUser.equals(transCust)){
				System.out.println(transVal + " souls have been extracted from your account.");
			}
			else{
				System.out.println(transVal + " souls have been extracted from " + transCust + "'s account.");
			}
		}
	}
}
