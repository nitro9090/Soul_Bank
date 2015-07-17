package transactions;

import users.Customer;
import users.User;

public class TransTemplate extends Transactions {

	public TransTemplate(User cU, Customer tC) {
		super(cU, tC, "ChooseCust");
	}

	public boolean startTrans(){

		return transExit;
	}
}
