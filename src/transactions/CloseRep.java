package transactions;

import users.Customer;

public class CloseRep extends Transactions {
	
	public CloseRep(String currUser, Customer closingUser){
		super(currUser, closingUser, "CloseRep");
	}
	
}
