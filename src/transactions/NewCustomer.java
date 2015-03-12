package transactions;
import java.util.ArrayList;

import Misc.*;
import users.Customer;


public class NewCustomer extends Transactions {
	
	public NewCustomer(){
		super();
		transaction = "newCustomer";
	}
	
	public void createNewCust(String currUser){
		System.out.println("To create a new account, we need a username, password, first and last name.");
		System.out.println("Enter 'back' to go back to the login screen.");
	
		String inUserName = newUserName(5, 10); //min userName length should more than 5 or more since "null" is used as a filler
		String inPassword = newPassword(5, 10); //min password length should more than 5 or more since "null" is used as a filler
		String inFirstName = inputName("Your first name", 50);
		String inLastName = inputName("your last name", 50);
		
		if(transExit == false){
			ReadWriteFile.addUser(inUserName, inUserName, inPassword, "Cust", inFirstName, inLastName);
			ReadWriteFile.recordActiv(currUser, "NewCust:" + inUserName);
			System.out.println("Congratulations on opening a new Account");
		}
	}

	private String newUserName(int minLen, int maxLen){
		boolean valid = false; 
		
		while (valid == false && transExit == false){
			System.out.print("New username (" + minLen + "-" + maxLen + " characters, may only contain letters and numbers): ");
			String inUserName = UserInputMethods.scanStr(null);
	
			valid = UserInputMethods.checkLetNum(inUserName,minLen,maxLen);
			transExit = MiscMeth.compareStrings(inUserName, "back");
	
			if (valid == false && transExit == false){
				System.out.println("Invalid username.");
			}
			else if(valid == true && transExit == false){
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
		
		while (valid == false  && transExit == false){
			System.out.print("New Password (" + minLen + "-" + maxLen + " characters, may only contain letters and numbers): ");
			String inPassword = UserInputMethods.scanStr(null);

			valid = UserInputMethods.checkLetNum(inPassword,minLen,maxLen);
			transExit = MiscMeth.compareStrings(inPassword, "back");
			
			if (valid == true && transExit == false){
				System.out.print("Retype your new password: ");
				String inPassword2 = UserInputMethods.scanStr(null);

				if(inPassword.equals(inPassword2)) valid2 = true;
				transExit = MiscMeth.compareStrings(inPassword2, "back");
			}
			
			if (valid == true && valid2 == true && transExit == false){
				System.out.println("Your new password is valid.");
				return inPassword;
			}
			else if (valid == true && transExit == false){
				System.out.println("Your passwords do not match.");
				valid = false;
			}
			else if(transExit == false){
				System.out.println("Your password is not valid, try again.");
			}
		}
		return null;
	}

	private String inputName(String label, int maxLen){
		boolean valid = false;
		
		while (valid == false && transExit == false){
			System.out.println("Input " + label + " (only letters, max length of " + maxLen + "): ");
			String inName = UserInputMethods.scanStr(null);
	
			valid = UserInputMethods.checkLetNum(inName,1,maxLen);
			transExit = MiscMeth.compareStrings(inName, "back");
	
			if(valid == true && transExit == false){
				return inName;
			}
			if(valid == false && transExit == false){
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
