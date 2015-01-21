package transactions;

import users.Customer;

public class DelUser extends Transactions {
	
	public DelUser(String currUser, Customer closingUser){
		super(currUser, closingUser, "DelUser");
	}
	
}
