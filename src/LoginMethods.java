import java.util.ArrayList;

public class LoginMethods {
	public static void newCust(){
		Boolean test = false;
		ArrayList<customerInfo.Customer> customers = CustMethods.loadCustomers();
		String inUserName = null;
		String inPassword = null;
		String inFirstName = null;
		String inLastName = null;
		UserInputMethods.scanClear();

		while (test == false){
			System.out.print("New username (letters and numbers only, between 5-10 characters): ");
			inUserName = UserInputMethods.scanStr(null);

			test = CheckMethods.LNCheck(inUserName,5,10);

			if (test == false){
				System.out.println("Invalid username.");
			}

			for(int i = 0; i<customers.size(); i++){
				if (inUserName.equals(customers.get(i).getUserName())){
					test = false;
					System.out.println("This username is already in use, please choose another.");
				}
			}
		}

		test = false;

		while (test == false){
			System.out.print("New Password (may only contain letters and numbers, between 5-10 characters): ");
			inPassword = UserInputMethods.scanStr(null);

			test = CheckMethods.LNCheck(inPassword,5,10); 
			if (test == true){
				System.out.println("Your password is valid.");
			}
			else{
				System.out.println("Your password is not valid, try again.");
			}
		}

		test = false;

		while (test == false){
			System.out.print("Input your First Name (only letters, max length of 50): ");
			inFirstName = UserInputMethods.scanStr(null);

			test = CheckMethods.LNCheck(inFirstName,1,50);

			if(test == false){
				System.out.println("Please only use letters.");
			}
		}

		test = false;

		while (test == false){
			test = true;
			System.out.print("Input your Last Name (no spaces, max length of 50): ");
			inLastName = UserInputMethods.scanStr(null);

			test = CheckMethods.LNCheck(inLastName, 1, 50);

			if(test == false){
				System.out.print("Please only use letters.");
			}
		}
		
		String filename= "Customers.txt";
		String FileData = inUserName + " " + inPassword + " " + inFirstName + " " + inLastName;
		
		WriteToFile.appendToFile(filename, FileData);
		WriteToFile.recordAction(inUserName, "NewAccount", "");

		System.out.println("Thank you for your soul, please press enter to login with your new account");
	}
	
	public static String Login(){
		
		boolean passwordCorrect = false;
		ArrayList<customerInfo.Customer> customers = CustMethods.loadCustomers();
		UserInputMethods.scanClear();
		String inUserName = null;
		String inPassword;
		
		while (passwordCorrect == false){
			System.out.print("Username: ");
			inUserName = UserInputMethods.scanStr(inUserName);

			System.out.print("Password: ");
			inPassword = UserInputMethods.scanStr(inUserName);
			
			for(int i = 0; i<customers.size(); i++){
				if (inUserName.equals(customers.get(i).getUserName())){
					if (inPassword.equals(customers.get(i).getPassword())){
						passwordCorrect = true;
						WriteToFile.recordAction(inUserName, "Login", "");
						System.out.println("Welcome to super awesome fun time.");
					}
				}
			}
			if(passwordCorrect == false){
				System.out.println("Username and/or password do not match or exist, try again.");
				WriteToFile.recordAction(inUserName, "FailedLogin", "");
			}
		}
		return inUserName;
	}
}
