package transactions;

import users.Customer;

public class Extract extends Transactions {

	public Extract(String currUser, Customer transferUser){
		super(currUser, transferUser, "Extract");
	}
}
