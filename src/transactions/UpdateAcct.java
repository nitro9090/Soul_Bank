package transactions;

import users.Customer;
import users.User;

public class UpdateAcct extends Transactions {

	public UpdateAcct(User cU, Customer tC) {
		super(cU, tC, "AcctChng");
	}
}
