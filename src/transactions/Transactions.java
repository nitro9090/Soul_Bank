package transactions;

import Misc.MiscMeth;
import Misc.UserInputMethods;
import users.Customer;
import users.User;

public class Transactions {
	protected User currUser;  //the userName of the user doing the transaction
	protected Customer transCust;  //the customer the transaction is applied to
	protected String transaction = "null";  //the transaction being done
	protected int repNum1 = -1;  // if a repository number is needed for the transaction, it can be loaded here
	protected int repNum2 = -1;  // if a 2nd repository number is needed for the transaction, it can be loaded here
	protected double transVal = 0; // if an amount is being transferred, this value will be used
	protected boolean transExit = false; // the exit token
	
	public Transactions(){
		currUser = null;
		transCust = null;
	}
	
	public Transactions(User currentUser){
		currUser = currentUser;
		transCust = null;
	}
	
	public Transactions(User CurrentUser, Customer transactionUser, String currTrans){
		currUser = CurrentUser;
		transCust = transactionUser;
		transaction = currTrans; 
	}

	public void setTransExit(boolean exit) {
		transExit = exit;
	}
	
	public void setRepType(String repType){
		System.out.println("Error: setRepType in Transactions is being called.");
		transExit = true;
	}
	
	public void setRepNum1(int repNum){
		repNum1 = repNum;
	}

	public String getCurrUserName(){
		return currUser.getUserName();
	}
	
	public Customer getTransUser(){
		return transCust;
	}
	
	public String getNewRepType(){
		System.out.println("getNewRepType in Transactions is being called wrong");
		return null;
	}
	
	public int getRepNum1(){
		return repNum1;
	}

	public int getRepNum2(){
		return repNum2;
	}
	
	public boolean getTransExit() {
		return transExit;
	}
	
	protected boolean mainDoubleCheck(){
		System.out.println("type (Y) for yes or (N) for no.");
		String isYN = UserInputMethods.inputYN(currUser.getUserName());
		
		if(isYN.equals("Y")){
			return true;
		}
		else if(isYN.equals("N")){
			transExit = true;
			return false;
		}
		else{
			return false;
		}
	}
	
	public void haveReps(){
		if (transCust.getRep().isEmpty()){
			System.out.printf("This account does not have any repositories.  You are being returned to the main menu. \n");
			setTransExit(true);
		}
	}
	
	public int chooseARep(boolean inclEmptReps, int skipRep1, int skipRep2, int skipRep3, String question){
		int repSize = transCust.getRep().size();
		int repCount = transCust.listReps(false, inclEmptReps, skipRep1, skipRep2, skipRep3);
		
		if (repSize == 0  && !transExit){
			System.out.println("You do not have any repositories.  Please choose another option.");
			transExit = true;
		}
		else if(repCount == 0 && !transExit){
			System.out.println("All of your repositories are empty. Please choose another option.");
			transExit = true;
		}
	
		while (!transExit){
			System.out.println(question);
			transCust.listReps(true, inclEmptReps, skipRep1, skipRep2, skipRep3);
			
			System.out.printf("(%d) Cancel the transaction \n", repSize+1);
	
			int choice = UserInputMethods.scanInt(transCust.getUserName());
	
			for(int j = 0; j < repSize; j++){
				int repNumList = transCust.getRepNum(j);
				if(inclEmptReps == false && choice == j+1 && transCust.getRepBal(repNumList) == 0){
				}
				else if((choice == j+1 && skipRep1 == repNumList)||(choice == j+1 && skipRep2 == repNumList)||(choice == j+1 && skipRep3 == repNumList)){
				}
				else if(choice == j+1){
					return repNumList;
				}
			}
			if(choice == repSize+1){
				transExit = true;
			}
			else MiscMeth.invSelect();
		}
		return -1;
	}

	public boolean startTrans(){
		System.out.println("This transaction is not setup.");
		transExit = true;
		return transExit;
	}
	
	public void transDesc(){
		System.out.println("Error: not reaching the appropriate Transaction.");
	}
	
	protected void doTrans(){
		System.out.println("Error: This transaction cannot be finalized, because it is not setup.");
	}
	
	protected void transComplete(){
		System.out.println("Error: not reaching the appropriate Transaction.");
	}
	
	protected void requiredReps(int requiredNum){
		int repSize = transCust.getRep().size();
		if (repSize < requiredNum){
			System.out.printf("You need to have at least %d repositories to take this action.  You currently have %d repository \n",requiredNum, repSize);
			transExit = true;
		}
	}

	public void setAuthentication(boolean authenticated) {
		System.out.println("Error: set Authentication isn't setup for this Transaction");
	}
}
