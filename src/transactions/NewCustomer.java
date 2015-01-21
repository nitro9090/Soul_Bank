package transactions;
import java.util.ArrayList;

import Misc.ExitMethods;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;
import users.Customer;


public class NewCustomer {
	private boolean exit = false;
	
	public void createNewCust(String currUser, boolean exit){
		System.out.println("To create a new account, we need a username, password and your name.");
		System.out.println("Enter 'back' to go back to the login screen.");
	
		String inUserName = newUserName(5, 10);
		String inPassword = newPassword(5, 10);
		String inFirstName = inputName("Your first name", 50);
		String inLastName = inputName("your last name", 50);
		
		if(exit == false){
			ReadWriteFile.newUser(inUserName, inUserName, inPassword, "Cust", inFirstName, inLastName);
			ReadWriteFile.recordActiv(currUser, "NewCust");
			System.out.println("Congratulations on opening a new Account");
		}
		
		exit = false;
	}

	private String newUserName(int minLen, int maxLen){
		boolean valid = false; 
		
		while (valid == false && exit == false){
			System.out.print("New username (" + minLen + "-" + maxLen + " characters, may only contain letters and numbers): ");
			String inUserName = UserInputMethods.scanStr(null);
	
			valid = UserInputMethods.checkLetNum(inUserName,minLen,maxLen);
			exit = ExitMethods.exitCompare(inUserName, "back");
	
			if (valid == false && exit == false){
				System.out.println("Invalid username.");
			}
			else if(valid == true && exit == false){
				valid = isUserNameInUse(inUserName);
				
				if(valid == false) return inUserName;
				valid = false;
			}
		}
		return null;
	}

	private String newPassword(int minLen, int maxLen){
		boolean valid = false;
		boolean valid2 = false;
		
		while (valid == false  && exit == false){
			System.out.print("New Password (" + minLen + "-" + maxLen + " characters, may only contain letters and numbers): ");
			String inPassword = UserInputMethods.scanStr(null);

			valid = UserInputMethods.checkLetNum(inPassword,minLen,maxLen);
			exit = ExitMethods.exitCompare(inPassword, "back");
			
			if (valid == true && exit == false){
				System.out.print("Retype your new password: ");
				String inPassword2 = UserInputMethods.scanStr(null);

				if(inPassword.equals(inPassword2)) valid2 = true;
				exit = ExitMethods.exitCompare(inPassword2, "back");
			}
			
			if (valid == true && valid2 == true && exit == false){
				System.out.println("Your new password is valid.");
				return inPassword;
			}
			else if (valid == true && exit == false){
				System.out.println("Your passwords do not match.");
				valid = false;
			}
			else if(exit == false)
				System.out.println("Your password is not valid, try again.");
		}
		return null;
	}

	private String inputName(String label, int maxLen){
		boolean valid = false;
		
		while (valid == false && exit == false){
			System.out.println("Input " + label + " (only letters, max length of " + maxLen + "): ");
			String inName = UserInputMethods.scanStr(null);
	
			valid = UserInputMethods.checkLetNum(inName,1,maxLen);
			exit = ExitMethods.exitCompare(inName, "back");
	
			if(valid == false && exit == false){
				System.out.println("Please only use letters.");
			}
		}
		return null;
	}
	
	private boolean isUserNameInUse (String inUserName){
		ArrayList<Customer> customers = ReadWriteFile.loadCustomers();
		
		for(int i = 0; i<customers.size(); i++){
			if(inUserName.equals(customers.get(i).getUserName())){
				System.out.println("This username is already in use, please choose another.");
				return true;
			}
		} 
		return false;
	}
}
