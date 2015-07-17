package users;
import repositories.RepActiv;
import repositories.Repositories;
import transactions.Donations;
import transactions.Transactions;
import transactions.Transfer;

import java.util.ArrayList;

import Misc.ExitMethods;
import Misc.MiscMeth;
import Misc.ReadWriteFile;
import Misc.UserInputMethods;

public class User {
	protected static boolean mainExit = false;
	private boolean authenticated = false;
	protected String userName;
	private String password;
	private String acctType;
	private String firstName;
	private String lastName;
	protected Transactions currTrans = new Transactions();  // the primary transaction
	protected Transactions secTrans = new Transactions(); // a secondary transaction, one that takes place due to the primary transaction.
	
	public User(){
		userName = "null";
		password = "null";
		acctType = "null";
		firstName = "null";
		lastName = "null";
	}
	
	public User(String uN){
		User temp = ReadWriteFile.loadUser(uN);
		userName = uN;
		password = temp.getPassword();
		acctType = temp.getAcctType();
		firstName = temp.getFirstName();
		lastName = temp.getLastName();
	}
	
	public User(String uN, String pW, String uT, String fN, String lN){
		userName = uN;
		password = pW;
		acctType = uT;
		firstName = fN;
		lastName = lN;
	}
	
	public static User createUser(){
		User temp = new User();
		return temp;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getAcctType(){
		return acctType;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public boolean getAuth(){
		return authenticated;
	}
	
	public Transactions getCurrTrans(){
		return currTrans;
	}
	
	public Transactions getSecTrans(){
		return secTrans;
	}
	
	public boolean getExit(){
		return mainExit;
	}
	
	public void setUserName(String uN){
		 userName = uN;
		 // return userName
	}
	
	public void setPassword(String pW){
		 password = pW;
		 //return password;
	}
	
	public void setFirstName(String fN){
		 firstName = fN;
		 //return firstName;
	}
	
	public void setLastName(String lN){
		 lastName = lN;
		 //return lastName;
	}
	
	public void setAcctType(String uT){
		acctType = uT;
	}
	
	public void setCurrTrans(Transactions CT){
		secTrans = CT;
	}
	
	public void setSecTrans(Transactions ST){
		secTrans = ST;
	}
	
	public void setExit(boolean inExit){
		mainExit = inExit;
	}

	public void refreshRepList(){
		//if the user is a customer then this will refresh their repository list, otherwise it does nothing.
	}

	public void mainMenu() {
		System.out.println("The main menu for this account type has not been setup.");
	}

	
	protected static void backMain() {
		if(!mainExit)System.out.println("Welcome back to the main menu, your options are:");
	}
	
	public static void returnToMainDial(){
		if(!mainExit) System.out.println("You are being returned to the main menu.");
	}
	
	public String newRepMenu(){	
		System.out.println("Creating new Repositories has not been setup for this account type.");
		currTrans.setTransExit(true);
		return "null";
	}

	public void checkBalMenu() {
		System.out.println("Checking balances has not been setup for this account type.");
		mainExit = true;
	}

	public void newRepMenuDial(){	
		System.out.println("Error:NewRepMenuDial not implemented for this user type.");
	}
	
	public void amtToTransDial() {
		System.out.println("Error: The dialogue for transfer amounts has been setup");
	}

	public void donateSouls(String donUserName, int repNum, double transVal, String transType){
		String action = "Donate";
		
		ReadWriteFile.transferSouls(false, true, -1, repNum, transVal);
		ReadWriteFile.donateToDarkOnes(donUserName, transVal);
		int activNum = ReadWriteFile.recordActiv(donUserName, action);
		ReadWriteFile.recordRepActiv(activNum, repNum, action, -transVal);
		//System.out.println(transVal + " souls have been donated from " + donUserName + "'s " + repNum + " " + customer.getRepType(repNum) +  " account.");
	}

	void actionDoubleCheck(String action, boolean finalTrans, double transVal, Customer customer, int repNumTo, int repNumFrom, String repType){
		while (mainExit == false){
			Transactions currTrans = new Transactions(userName, customer, null);
			
			if(action.equals("Transfer")){
				currTrans = new Transfer(userName, customer, transVal, repNumTo, repNumFrom);
			}
			
			if(action.equals("Transfer")){
				System.out.println("After transferring " + transVal + " souls, your account totals will be");
				System.out.printf("account %d %s : %.2f souls     account %d %s : %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom)-transVal, repNumTo, customerTo.getRepType(repNumTo), customerTo.getRepBal(repNumTo)+transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("Deposit")){
				System.out.println("After depositing " + transVal + " souls, your account total will be");
				System.out.printf("account %d %s : %.2f souls \n", repNumTo, customerTo.getRepType(repNumTo), customerTo.getRepBal(repNumTo)+transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("Extract")){
				System.out.println("After extracting " + transVal + " souls, your account total will be");
				System.out.printf("account %d %s : %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom)-transVal);
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("NewRep")){
				System.out.println("Are you sure you want to open a new Soul " + repType + " account? type (Y) for yes or (N) for no.");
			}
			else if (action.equals("CloseRep")){
				System.out.println("You will now be closing " + customerTo.getUserName() + "'s account # : " + repNumTo + " soul " + customerTo.getRepType(repNumTo));
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("1stCloseCustCheck")){
				System.out.println("You are about to close your customer account, aka delete it forever.");
				System.out.println("Are you sure you wish to do this? type (Y) for yes or (N) for no.");
			}
			else if(action.equals("2ndCloseCustCheck")){
				System.out.println("Once again, you are about to close your customer account, aka delete it forever.");
				System.out.println("type (Y) to delete your account or (N) for to cancel the transaction.");
			}
			else{
				System.out.println("Error: action double check wasn't accessed properly");
				mainExit = true;
				break breakWhile;
			}
			
			currTrans.transDesc();
			String isYN = inputYN();
			
			if(isYN.equals("Y")){
				currTrans.doTrans();
				currTrans.transComplete();
				
				if(action.equals("Transfer")){
					transSouls(repNumTo, repNumFrom, transVal);
					System.out.println(transVal + " souls have been transferred between " + customer + "'s accounts.");
				}
				else if(action.equals("Deposit")){
					depSouls(repNumTo, transVal);
				}
				else if(action.equals("Extract")){
					extractSouls(repNumFrom, transVal);
				}
				else if (action.equals("NewRep")){
					newRep(customerTo.getUserName(), repType, 0);
				}
				else if(action.equals("CloseRep")){
					deleteRep(repNumTo);
				}
				else if(action.equals("1stCloseCustCheck")){
				}
				else if(action.equals("2ndCloseCustCheck")){
					deleteUser(customerTo);
				}
				break;
			}
			else if(isYN.equals("N")){
				mainExit = true;
			}	
		}
	}

	private Customer processTheDeal(Customer customer, ArrayList<Donations> donations, String transType) {
		for(int i = 0; i < donations.size(); i++){
			donateSouls(customer.getUserName(), donations.get(i).getRepNum(), donations.get(i).getDonAmt(), transType);
		}	
		return customer;
	}

	static Customer dealDoubleCheck(Customer customer, boolean finalTrans, String action,  ArrayList<Donations> donations){
		double transVal = 0;
		
		for(int i=0; i < donations.size();i++){
			transVal = transVal + donations.get(i).getDonAmt();
		}
		
		breakWhile:
		while (mainExit == false){
			if(action.equals("Money") || action.equals("Power") || action.equals("Love")){
				for(int i = 0; i < donations.size(); i++){
					System.out.println("You are donating " + donations.get(i).getDonAmt() + " souls from account # " + donations.get(i).getRepNum() + " " + customer.getRepType(donations.get(i).getRepNum()));
				}
				System.out.println("For a total donation of " + transVal + " souls in order to gain " + action + "." );
				System.out.println("Are you sure you want to do this? type (Y) for yes or (N) for no.");
				System.out.println("Be careful what you wish for.");
			}
			else{
				System.out.println("Error: deal double check wasn't accessed properly");
				mainExit = true;
				break breakWhile;
			}
			
			String isYN = inputYN(customer);
			
			if(isYN.equals("Y")){
				customer = processTheDeal(customer, donations, action);
				if(action.equals("Money")){	
					//moneyDealResults(transVal);
				}
				else if(action.equals("Power")){
					//powerDealResults(transVal);
				}
				else if(action.equals("Love")){
					//loveDealResults(transVal);
				}
				break breakWhile;
			}
			else if(isYN.equals("N")){
				System.out.println("Your donation has been cancelled.");
				mainExit = true;
			}	
		}
		if(finalTrans == true) mainExit = false;
		return customer;
	}

	private static ArrayList<Donations> amtToDonate(Customer customer, boolean finalTrans){
		ArrayList<Donations> donations = new ArrayList<>();
		boolean moreToAdd = true;
		
		while(moreToAdd == true && mainExit == false){
			int repNumDonate = chooseRepPrompt(customer, false, false, "Which repository would you like to donate from?  You may take from more than one.");
			double newDonAmt = amtToTransPrompt(customer, "Donate", -1, null, repNumDonate, customer);
			
			if(mainExit == false){
				double origBal = customer.getRepBal(repNumDonate);
				customer.setRepBal(repNumDonate, origBal - newDonAmt);
				
				Donations temp = new Donations(repNumDonate, newDonAmt);
				donations.add(temp);
			}
			
			while (mainExit == false){
				System.out.println("Would you like to donate more?");
				String isYN = inputYN(customer);
				if (isYN.equals("Y")){
					break;
				}
				else if (isYN.equals("N")){
					moreToAdd = false;
					break;
				}
			}
		}
		if(finalTrans == true) mainExit = false;
		return donations;
	}

	protected static Customer dealPrompt(Customer customer, boolean finalTrans, String dealType, ArrayList<Donations> donations) {
		if(dealType == "Money"){
			System.out.println("You would like money, the root of all evil...");
			System.out.println("This can be granted, but at a high price and we are fickle.");
			//System.out.println("How much are you willing to donate to the dark ones for it?");
		}
		else if(dealType == "Power"){
			System.out.println("You would like power, that which requires great responsibility");
			System.out.println("To gain or bestow such is difficult, you best be prepared donate");
			//System.out.println("How much are you willing to donate to the dark ones for it?");
		}
		else if(dealType == "Love"){
			System.out.println("You wish to find love, a noble pursuit for such a lowly creature.");
			System.out.println("This is the hardest task known, the dark ones will expect much.");
			//System.out.println("How much are you willing to donate to the dark ones for it?");
		}
	
		if (donations == null){
			donations = amtToDonate(customer, false);
		}
		
		customer = dealDoubleCheck(customer, true, dealType, donations);
		
		return customer;
	}
}