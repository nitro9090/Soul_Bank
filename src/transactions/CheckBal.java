package transactions;

import java.util.ArrayList;

import repositories.RepActiv;
import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;
import users.*;

public class CheckBal extends Transactions{
	
	public CheckBal(User currUser, Customer checkCust){
		super(currUser, checkCust, "CheckBal");
	}
	
	public void startTrans(){
		checkBalMenu();
	}
	
	private void checkBalMenu() {
		int RepSize = transCust.getRep().size();
		
		if (transCust.getRep().isEmpty()){
			System.out.printf("You do not have any repositories.  You are being returned to the main menu. \n");
			transExit = true;
		}
	
		breakWhile:
		while (!transExit){
			boolean check = false;
			System.out.println("Your accounts:");
			transCust.listReps(true, true, -1, -1, -1);
	
			System.out.printf("(%d) Return to previous menu \n", RepSize+1);
			
			System.out.println("Choose an account to see its recent activity.");
	
			int choice = UserInputMethods.scanInt(currUser.getUserName());
	
			for(int i = 0; i< RepSize; i++){
				if(choice == i+1){
					displayRepActiv(transCust.getRepNum(i));
					check = true;
				}
			}
			if(choice == RepSize+1){
				transExit = true;
				break breakWhile;
			}
			else if(!check) {
				MiscMeth.invSelect();
			}
		}
	}
	
	private void displayRepActiv(int repNum){
		ArrayList<RepActiv> repActiv = ReadWriteFile.loadRepActiv(repNum);
		int repActivSize = repActiv.size();
		int repStep = 10;
		int repStart = repActivSize-1;
		
		boolean listOver = false;
		
		
		while(!listOver && !transExit){
			int repEnd = repStart - repStep+1;
			
			if(repEnd < 0){
				repEnd = 0;	
			}
			
			if(repActivSize == repStart+1){
				System.out.println("The last " + repStep + " transactions for account # : " + repNum + " " + transCust.getRepType(repNum) + " were");	
			}

			System.out.printf("%-12s %-12s %-12s %-12s %-12s \n","Date","Time", "Activity #", "Action", "transaction");
			
			for(int i = repStart; i>=repEnd; i--){
				RepActiv temp =  repActiv.get(i);
				UserActiv temp2 = ReadWriteFile.loadUserActiv(temp.getActivNum());
				if(temp.getTransVal() == 0){
					System.out.printf("%-12s %-12s %09d    %-12s \n", temp2.getDate(),temp2.getTime(), temp.getActivNum(), temp.getAction());
				}
				else{
					System.out.printf("%-12s %-12s %09d    %-12s %-12.2f \n", temp2.getDate(),temp2.getTime(), temp.getActivNum(), temp.getAction(), temp.getTransVal());
				}
			}
			
			if(repEnd > 0){ 
				System.out.println("Input 'Next' to see the next 10 transactions.");
			}
			
			if(repActivSize != repStart+1){
				System.out.println("Input 'Back' to see previous 10 transactions.");
			}
			
			System.out.println("Input 'Other' to look at a different account.");
			System.out.println("Input 'Exit' to go back to the main menu.");
			String input = UserInputMethods.scanStr(currUser.getUserName());
			
			if(input.equals("Next") && repEnd > 0){
				repStart = repStart - repStep;
			}
			else if(input.equals("Back") && repActivSize != repStart+1){
				repStart = repStart + repStep;
			}
			else if(input.equals("Other")){
				listOver = true;
			}
			else if(input.equals("Exit")){
				transExit = true;
			}
			else MiscMeth.invSelect();
		}
		
		String temp = "CheckBal:" + repNum;
		ReadWriteFile.recordActiv(transCust.getUserName(), temp);
	}
}
