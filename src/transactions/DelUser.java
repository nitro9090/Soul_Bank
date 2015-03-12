package transactions;

import users.*;

public class DelUser extends CloseAccts {
	
	public DelUser(User currUser, Customer closingUser){
		super(currUser, closingUser, "DelUser");
	}
	
}
