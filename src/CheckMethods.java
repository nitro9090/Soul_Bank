import java.util.regex.Pattern;

import customerInfo.Customer;

public class CheckMethods extends MainMethods {
	
	// checks if a string is made of only letters and numbers
	public static boolean LNCheck(String value, int minLength, int maxLength){
		boolean test = false;
		
		if (Pattern.matches("[a-zA-Z0-9]+",value) == true && value.length() >= minLength && value.length() <= maxLength){
			test = true;
		}

		return test;
	}
	
	//checks if a string is either a Y or an N
	/*public static boolean checkYN(String value){
		boolean test = false;
		
		if (value.equals("Y") || value.equals("N")){
			test = true;
		}

		return test;
	}*/
	
	public static boolean decCheck(Double input, int numDec){
	    boolean test;
	    String dblToString = String.valueOf(input);
		String[] result = dblToString.split("\\.");
		if (result[1].length() < numDec){
			test = true;
		}
		else test = false;
		
	    return test;
	}
	
	public static String doubleCheck(Customer customer){
		if(exit == false){
			String check = UserInputMethods.scanStr(customer.getUserName());

			if(check.equals("Y")){	
				return check;
			}
			else if(check.equals("N")){
				//System.out.println("The transaction has been cancelled.  You will be returned to the main menu.");
				return check;
			}
			else invSelect();
		}
		return "null";
	}
}
