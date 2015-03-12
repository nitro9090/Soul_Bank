import Misc.ExitMethods;
import Misc.MiscMeth;
import Misc.UserInputMethods;
import transactions.NewCustomer;
import transactions.Transactions;
import users.Admin;
import users.Customer;
import users.User;

public class Main {
	static User currUser = new User();
	static boolean userAuthenticated = false;
	static String inUserName = "null";
	static String inPassword = "null";
	static boolean exit = false;
	
	
	public static void main(String args[]) {
		while(true){
			currUser = new User();
			userAuthenticated = false;
			inUserName = "null";
			inPassword = "null";
			exit = false;
			
			front();
			setUserType();
			enterMainMenu();
		}
	}
	
	static private void front(){
		System.out.println("Welcome to Mike's soul repository.");
		System.out.println("The only place you can you literally share your soul.");
		
		while (true){
			exit = false;
			System.out.println("New Account (1) or Login (2), type (escape) at any time to quit this program: ");
			int choice = UserInputMethods.scanInt(currUser.getUserName());
			switch (choice) {
			case 1: 
				NewCustomer newCust = new NewCustomer();
				newCust.createNewCust(null);
				System.out.println("try logging in with your new account.");
			case 2:
				while(!userAuthenticated && !exit){
					System.out.println("Type 'back' to go back to go back to the login menu.");
					inputUsername();
					inputPassword();
					if(!exit){
						userAuthenticated = MiscMeth.authenticateUser(inUserName, inPassword, "Login");
					}
				}
				if(userAuthenticated){
					currUser = new User(inUserName);
					return;
				}
				break;
			default:
				MiscMeth.invSelect();
			}
		}
	}
	
	private static void setUserType(){
		if(userAuthenticated){
			if (currUser.getAcctType().equals("Admin")){
				currUser = new Admin(inUserName);
			}
			else if(currUser.getAcctType().equals("Cust")){
				currUser = new Customer(inUserName);
			}
			else{
				System.out.println("Your account type isn't setup properly");
			}
		}
	}
	
	private static void enterMainMenu(){
		if(userAuthenticated){
			currUser.mainMenu();
			System.out.println("Have a frightful day.");
		}
	}

	public static void inputUsername(){
		if(!exit){
			System.out.print("Username: ");
			inUserName = UserInputMethods.scanStr(currUser.getUserName());
			exit = MiscMeth.compareStrings(inUserName, "back");
		}
	}

	private static void inputPassword(){
		if(!exit){
			inPassword = UserInputMethods.scanPwd(currUser.getUserName(), "Password: ");
			exit = MiscMeth.compareStrings(inPassword, "back");
		}
	}
}
