package transactions;

import users.Customer;

public class Deposit extends Transactions{
	private int depRepNum = -1;
	
	public Deposit(String currUser, Customer transferUser){
		super(currUser, transferUser, "Deposit");
	}
	
	public void transDoubleCheck(){
		System.out.println("After depositing " + transVal + " souls, " + transCust.getUserName() + "'s account total will be");
		System.out.printf( "account %d %s : %.2f souls \n", repNum1, transCust.getRepType(repNum1), transCust.getRepBal(repNum1)+transVal);
		System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
	}
	
	
}
