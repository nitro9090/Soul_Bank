package transactions;

import Misc.ReadWriteFile;
import users.Customer;

public class Transfer extends Transactions {
	private int outRepNum = 0;
	private int inRepNum = 0;
	
	public Transfer(String currUser, Customer transferUser){
		super(currUser, transferUser, "Transfer");
	}
	
	public Transfer(String currUser, Customer transferUser, double transferVal, int outAcctNumber, int inAcctNumber) {
		super(currUser, transferUser, "Transfer");
		transVal = transferVal;
		outRepNum = outAcctNumber;
		inRepNum = inAcctNumber;
		// TODO Auto-generated constructor stub
	}
	
	public void amtToTransPrompt(boolean exit, int repNumTo, Customer customerTo, int repNumFrom, Customer customerFrom){
		
		while (exit == false && transCust.getRep().size() > 0){
			System.out.println("Your transaction will be");
			System.out.printf(customerFrom.getUserName() + "'s account # %d %s with %.2f souls ---> " + customerTo.getUserName() +"'s account # %d %s with %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom), repNumTo, customerTo.getRepType(repNumTo), customerTo.getRepBal(repNumTo));
			System.out.println("How much would you like to transfer? You will have a chance to verify the transfer.");
	
			transValPrompt(repNumFrom);
		}	
	}

	public void transDoubleCheck(){
		System.out.println("After transferring " + transVal + " souls, your account totals will be");
		System.out.printf("account %d %s : %.2f souls     account %d %s : %.2f souls \n", outRepNum, transCust.getRepType(outRepNum), transCust.getRepBal(outRepNum)-transVal, inRepNum, transCust.getRepType(inRepNum), transCust.getRepBal(inRepNum)+transVal);
		System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
	}
	
	public void doTransfer(){
		int activNum = ReadWriteFile.recordActiv(currUser, transaction);
		ReadWriteFile.addSubtrSouls(inRepNum, transVal);
		ReadWriteFile.recordRepActiv(activNum, inRepNum, transaction, transVal);
		ReadWriteFile.addSubtrSouls(inRepNum, -transVal);
		ReadWriteFile.recordRepActiv(activNum, outRepNum, transaction, -transVal);
	}
	
	public void transComplete(){
		if(currUser.equals(transCust.getUserName())){
			System.out.println(transVal + " souls have been transferred between your accounts.");
		}
		else{
			System.out.println(transVal + " souls have been transferred between " + transCust + "'s accounts.");
		}	
	}
}
