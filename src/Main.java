import customerInfo.Customer;

public class Main {
	static boolean exit = false;
	
	public static void main(String args[]) {
		String userName = "JoeBob";
		
		while(true){
			//String userName = null;
			//userName = front();  removed for testing purposes
			mainMenu(userName);
			System.out.println("Have a frightful day.");
		}
	}
	
    public static String front(){
    	String userName = null;
    	boolean option = false;

    	System.out.println("Welcome to Mike's soul repository.");
    	System.out.println("The only place you can you literally share your soul.");
    	
    	while (option == false){
    		System.out.println("New Account (1) or Login (2), type (escape) at any time to quit this program: ");
    		int choice = UserInputMethods.scanInt(userName);
    		switch (choice) {
    		case 1: 
    			LoginMethods.newCust();
    		case 2:
    			userName = LoginMethods.Login();
    			return userName;
    		default:
    			invSelect();
    		}
    	}
    	return userName;
    }

    public static void mainMenu(String userName){
    	
    	//in case someone gets into the MainMenu without a user name
    	if(userName == null){
    		ExitMethods.exitCommBad();
    	}
    	
    	Customer customer = ReadWriteFile.loadCustomerFull(userName);
    	
    	System.out.println("Welcome to the main menu, what would you like to do?");
    	
    	while (exit == false){
    		System.out.println("(1) Open New Repository");
	    	System.out.println("(2) Check your balance");
	    	System.out.println("(3) Transfer/extract/deposit souls");
	    	System.out.println("(4) Close repository/account");
	    	System.out.println("(5) Make a deal with the devil");
	    	System.out.println("(6) Logout");
	    	System.out.println("(escape) at any time to exit the program");
    		int choice = UserInputMethods.scanInt(userName);
    		switch (choice) {
    		case 1: 
    			customer = MainMethods.newRepMenu(customer, true);
    			backMain();
    			exit = false;
    			break;
    		case 2:
    			MainMethods.checkBalMenu(customer, true);
    			backMain();
    			exit = false;
    			break;
    		case 3:
    			customer = MainMethods.transExtDepMenu(customer, true);
    			backMain();
    			exit = false;
    			break;
    		case 4:
    			customer = MainMethods.closeRepMenu(customer, true);
    			backMain();
    			exit = false;
    			break;
    		case 5:
    			customer = MainMethods.devilDealMenu(customer, true);
    			backMain();
    			exit = false;
    			break;
    		case 6:
    			exit = true;
    		default:
    			invSelect();
    		}
    	}
    	exit = false;
    }

	private static void backMain() {
		System.out.println("Welcome back to the main menu, your options are:");
	}
	
	public static void invSelect(){
		System.out.println("Invalid Selection!");
	}
	
	public static void invAmt(){
		System.out.println("Invalid Amount!");
	}
}
