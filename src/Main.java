import Misc.MiscMeth;
import Misc.UserInputMethods;
import transactions.NewCustomer;
import users.Admin;
import users.Customer;
import users.User;

public class Main {
	static User currUser = new User();
	
	public static void main(String args[]) {
		while(true){
			currUser = new User();
			front();
			if (currUser.getAcctType().equals("Admin")){
				currUser = new Admin(currUser);
			}
			else if(currUser.getAcctType().equals("Cust")){
				currUser = new Customer(currUser);
			}
			if(!currUser.getUserName().equals(null)){
				currUser.mainMenu();
				System.out.println("Have a frightful day.");
			}
			
		}
	}
	
	static void front(){
		String userName = null;
		NewCustomer newCust = new NewCustomer();
	
		System.out.println("Welcome to Mike's soul repository.");
		System.out.println("The only place you can you literally share your soul.");
		
		while (true){
			System.out.println("New Account (1) or Login (2), type (escape) at any time to quit this program: ");
			int choice = UserInputMethods.scanInt(userName);
			switch (choice) {
			case 1: 
				newCust.createNewCust(null, currUser.getExit());
			case 2:
				while(!currUser.getAuth() && !currUser.getExit()){
					currUser.authentUser();
				}
				currUser.setExit(false);
				return;
			default:
				MiscMeth.invSelect();
			}
		}
	}
}
