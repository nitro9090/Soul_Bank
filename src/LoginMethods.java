import java.util.ArrayList;

import customerInfo.Customer;

public class LoginMethods extends Main{
	/**
	 * This method prompts 
	 */
	public static void newCust(){
		
		System.out.println("To create a new account, we need a username, password and your name.");
		System.out.println("Enter 'back' to go back to the login screen.");

		String inUserName = newUserName(5, 10);
		String inPassword = newPassword(5, 10);
		String inFirstName = inputName("your first name", 50);
		String inLastName = inputName("your last name", 50);
		
		if(exit == false){
			ReadWriteFile.newCust(inUserName, inUserName, inPassword, inFirstName, inLastName);
			System.out.println("Congratulations on opening a new Account");
		}
	}
	
	public static String Login(){	
		ArrayList<customerInfo.Customer> customers = ReadWriteFile.loadCustomers();
		
		while (exit == false){
			String inPassword = null;
			System.out.println("Enter 'back' to go back to the login screen");
			System.out.print("Username: ");
			
			
			String inUserName = UserInputMethods.scanStr("null");
			exitCompare(inUserName, "back");
			
			
			
			if(exit == false){
				//System.out.print("Password: ");
				inPassword = UserInputMethods.scanPwd(inUserName, "Password: ");
				exitCompare(inUserName, "back");
			}
			
			if(exit == false){
				for(int i = 0; i<customers.size(); i++){
					if (inUserName.equals(customers.get(i).getUserName())){
						if (inPassword.equals(customers.get(i).getPassword())){
							ReadWriteFile.recordActiv(inUserName, "Login");
							return inUserName;
							//System.out.println("Welcome to super awesome fun time.");
						}
						else if(i == customers.size()){
							System.out.println("Username and/or password do not match or exist, try again.");
							ReadWriteFile.recordActiv(inUserName, "FailedLogin");
						}
					}
				}
			}
		}
		return null;
	}
	
	private static void exitCompare(String value, String check){
		if(value.equals(check)){
			exit = true;
		}
		else exit = false;
	}

	public static String newUserName(int minLen, int maxLen){
		ArrayList<Customer> customers = ReadWriteFile.loadCustomers();
		boolean valid = false; 
		
		while (valid == false && exit == false){
			System.out.print("New username (" + minLen + "-" + maxLen + " characters, may only contain letters and numbers): ");
			String inUserName = UserInputMethods.scanStr(null);

			valid = UserInputMethods.checkLetNum(inUserName,minLen,maxLen);
			exitCompare(inUserName, "back");

			if (valid == false && exit == false){
				System.out.println("Invalid username.");
			}
			else if(valid == true && exit == false){
				for(int i = 0; i<customers.size(); i++){
					if (inUserName.equals(customers.get(i).getUserName())){
						valid = false;
						System.out.println("This username is already in use, please choose another.");
					}
					else if(i == customers.size()) return inUserName;
				} 
			}
		}
		return null;
	}

	public static String newPassword(int minLen, int maxLen){
		boolean valid = false;

		while (valid == false  && exit == false){
			System.out.print("New Password (" + minLen + "-" + maxLen + " characters, may only contain letters and numbers): ");
			String inPassword = UserInputMethods.scanStr(null);
			
			valid = UserInputMethods.checkLetNum(inPassword,minLen,maxLen);
			exitCompare(inPassword, "back");

			if (valid == true && exit == false){
				System.out.println("Your password is valid.");
				return inPassword;
			}
			else if(exit == false)
				System.out.println("Your password is not valid, try again.");
		}
		return null;
	}
	

	public static String inputName(String label, int maxLen){
		boolean valid = false;
		
		while (valid == false && exit == false){
			System.out.println("Input " + label + " (only letters, max length of " + maxLen + "): ");
			String inName = UserInputMethods.scanStr(null);

			valid = UserInputMethods.checkLetNum(inName,1,maxLen);
			exitCompare(inName, "back");

			if(valid == false && exit == false){
				System.out.println("Please only use letters.");
			}
		}
		return null;
	}
}