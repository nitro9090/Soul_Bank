package transactions;
import java.util.ArrayList;

import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;
import users.*;


public class ChooseCust extends Transactions {
	ArrayList<Customer> customers = ReadWriteFile.loadCustomers();
	Customer chosenCust = new Customer();
	String searchMeth = "null"; 
	
	public ChooseCust(User cU, Customer tC) {
		super(cU, tC, "ChooseCust");
	}

	public boolean startTrans(){
		findUserMethods();
		verifyUser();
		
		return transExit;
	}

	private void findUserMethods(){
		while (!transExit){
			System.out.println("How would you like to look for the user?");
			
			System.out.println("(1) Search for their username");
			System.out.println("(2) Search for their first and last name");
			System.out.println("(3) return to main menu");
			int choice = UserInputMethods.scanInt(currUser.getUserName());
			switch (choice) {
			case 1: 
				userNameSearch();
				searchMeth = "UserName";
				return;
			case 2:
				nameSearch();
				searchMeth = "Name";
				return;
			case 3:
				transExit = true;
				break;
			default:
				MiscMeth.invSelect();
			}
		}
	}

	private void userNameSearch() {
		while (!transExit){
			System.out.println("In order to find this customer, please input the their user name.");
			System.out.println("type '123' to go back to the main menu");
			System.out.print("User name: ");
			String inUserName = UserInputMethods.scanStr(currUser.getUserName());
			transExit = MiscMeth.compareStrings(inUserName, "123");

			if(!transExit){
				for (int i = 0; i<customers.size();i++){
					if(customers.get(i).getUserName().equals(inUserName)){
						chosenCust = customers.get(i);
					}
				}
			}
		}
	}

	private void nameSearch() {
		while (!transExit){
			System.out.println("Please input the user's first and last name.");
			System.out.println("type '123' to go back to the main menu");
			System.out.print("First Name: ");
			String inFirstName = UserInputMethods.scanStr(currUser.getUserName());
			transExit = MiscMeth.compareStrings(inFirstName, "123");
			if(!transExit){
				System.out.println("Last Name: ");
				String inLastName = UserInputMethods.scanStr(currUser.getUserName());
				transExit = MiscMeth.compareStrings(inLastName, "123");
			}

			if(!transExit){
				int custFound = 0;
				for (int i = 0; i<customers.size();i++){
					if(customers.get(i).getFirstName().equals(inFirstName) && customers.get(i).getLastName().equals(inLastName)){
						chosenCust = customers.get(i);
						custFound = custFound++;
						break;
					}
				}
				if(custFound == 0){
					System.out.println("A customer by this name does not exist.");
				}
				if(custFound == 1){
					System.out.println("A customer by")
				}
			}
		}
	}
	
	private void verifyUser() {
		while(!transExit && searchMeth.equals("UserName")){
			System.out.println("You have ")
			
		}
	}
}
