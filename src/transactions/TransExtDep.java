package transactions;

import Misc.MiscMeth;
import Misc.UserInputMethods;
import users.*;



public class TransExtDep extends Transactions {
	public TransExtDep(User currUser, Customer transUser, String transType){
		super(currUser, transUser, transType);
	}

	protected boolean amtToTransMethod(boolean isExtr){
		transVal = UserInputMethods.scanDbl(transCust.getUserName(), 2);

		if(transVal > 0){
			if(isExtr && transVal > transCust.getRepBal(repNum2)){
				System.out.println("You do not have enough souls in your account, please give a different amount.");
				return false;
			}
			else{
				return true;
			}
		}
		else{
			MiscMeth.invAmt();
			return false;
		}
	}
}