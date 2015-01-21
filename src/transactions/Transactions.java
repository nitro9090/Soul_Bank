package transactions;

import Misc.MiscMeth;
import Misc.UserInputMethods;
import users.Customer;

public class Transactions {
	protected String currUser;  //the userName of the user doing the transaction
	protected Customer transCust;  //the customer the transaction is applied to
	protected String transaction = null;  //the transaction being done
	protected int repNum1 = -1;  // if a repository number is needed for the transaction, it can be loaded here
	protected int repNum2 = -1;  // if a 2nd repository number is needed for the transaction, it can be loaded here
	protected double transVal = -1; // if an amount is being transferred, this value will be used
	protected boolean exit = false; // the exit token
	
	public Transactions(String user, Customer transactionUser, String currTrans){
		currUser = user;
		transCust = transactionUser;
		transaction = currTrans; 
	}
	
	public String getCurrUserName(){
		return currUser;
	}
	
	public Customer getTransUser(){
		return transCust;
	}
	
	protected int chooseRep(boolean exit, Customer customer, boolean inclEmptReps){
		int repSize = customer.getRep().size();
		int repNum = -1;
		boolean check = false;
		
		breakWhile:
		while (exit == false){
			
			int countReps = customer.listReps(-1, -1, -1, inclEmptReps);
			
			if (countReps == 0){
				System.out.println("This transaction is not possible with these accounts.");
				exit = true;
				break;
			}
			
			System.out.printf("(%d) Cancel the transaction \n", repSize+1);
	
			int choice = UserInputMethods.scanInt(customer.getUserName());
	
			for(int j = 0; j < repSize; j++){
				int repNumList = customer.getRepNum(j);
				if(inclEmptReps == false && choice == j+1 && customer.getRepBal(repNumList) == 0){
					MiscMeth.invSelect();
					check = true;
				}
				else if(choice == j+1){
					repNum = repNumList;
					break breakWhile;
				}
			}
			if(choice == repSize+1){
				System.out.println("You are being returned to the main menu.");
				exit = true;
			}
			else if(check == false) MiscMeth.invSelect();
		}
		return repNum;
	}

	public void transDesc(){
		System.out.println("Error: not reaching the appropriate Transaction.");
	}
	
	public void doTrans(){
		
	}
	
	public void transComplete(){
		System.out.println("Error: not reaching the appropriate Transaction.");
	}
	
	protected void transValPrompt(int outRepNum){
		double inTransVal = UserInputMethods.scanDbl(transCust.getUserName(), 2);

		if(transVal > 0){
			if (transVal > transCust.getRepBal(outRepNum)){
				System.out.println("You do not have enough souls in your account, please give a different amount.");
			}
			else{
				transVal = inTransVal;
			}
		}
		else MiscMeth.invAmt();	
	}

	private double amtToTransPrompt(boolean exit, int repNumTo, Customer customerTo, int repNumFrom, Customer customerFrom){
		double transVal = 0;
		
		breakWhile:
		while (exit == false && customer.getRep().size() > 0){
			if(transType.equals("Transfer")){
				System.out.println("Your transaction will be");
				System.out.printf("account # %d %s with %.2f souls ---> account # %d %s with %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom), repNumTo, customerTo.getRepType(repNumTo), customerTo.getRepBal(repNumTo));
				System.out.println("How much would you like to transfer? You will have a chance to verify the transfer.");
			}
			else if(transType.equals("Deposit")){
				
			}
			else if(transType.equals("Extract")){
				System.out.printf("You will be extracting from account # %d %s which currently has %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom));
				System.out.println("How much would you like to extract? You will have a chance to verify the transfer.");
			}
			else if(transType.equals("Donate")){
				System.out.printf("You will be donating from account # %d %s which currently has %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom));
				System.out.println("How much would you like to donate? You will have a chance to verify the transfer.");
			}
	
			transVal = UserInputMethods.scanDbl(customer.getUserName(), 2);
	
			if(transVal > 0){
				if (transType.equals("Deposit") || transVal <= customerFrom.getRepBal(repNumFrom)) break breakWhile;
				else if (transVal > customerFrom.getRepBal(repNumFrom)){
					System.out.println("You do not have enough souls in your account, please give a different amount.");
				}
			}
			else MiscMeth.invAmt();
		}	
		return transVal;
	}

	protected static int choose2ndRepPrompt(Customer customer, boolean finalTrans, boolean inclEmptReps, int firstRepNum, String question) {
		int repNum = -1;
		int repSize = customer.getRep().size();
		boolean check = false;
	
		while (exit == false){
			System.out.println(question);
			int countReps = listReps(customer, firstRepNum, -1, -1, inclEmptReps);
	
			if (countReps == 0){
				System.out.println("This transaction is not possible with these accounts.");
				exit = true;
				break;
			}
			
			System.out.printf("(%d) Cancel the transfer \n", repSize+1);
	
			int choice = UserInputMethods.scanInt(customer.getUserName());
	
			for(int j = 0; j< repSize; j++){ 
				int repNumList = customer.getRepNum(j);
				if(inclEmptReps == false && choice == j+1 && customer.getRepBal(repNumList) == 0){
					MiscMeth.invSelect();
					check = true;
				}
				if(choice == j+1 && repNumList != firstRepNum && customer.getRepBal(repNumList) != 0){
					return repNum = customer.getRepNum(j);
				}
			}
			if(choice == repSize+1){
				System.out.println("You are being returned to the previous menu.");
				exit = true;
			}
			else if(check == false) MiscMeth.invSelect();
		}
		if(finalTrans == true) exit = false;
		return repNum;
	}
	
	public void doubleCheckPrompt(){
		System.out.println("Are you sure you want to proceed with this transaction?")
		isYN
	}
}
