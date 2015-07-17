package Misc;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInputMethods {
	static Scanner sc = new Scanner(System.in);

	public static String scanStr(String currUserName){
		String scanVal = sc.nextLine();
		ExitMethods.exitNormal(scanVal, currUserName);
		clearConsole();
		return scanVal;
	}

	public static int scanInt(String currUserName){
		int Output = -1;
		String scanVal = sc.nextLine();
		ExitMethods.exitNormal(scanVal, currUserName);
		clearConsole();
		try{
			Output = Integer.parseInt(scanVal);
		}catch(Exception e){
		}
		return Output;
	}
	
	public static Double scanDbl(String currUserName, int numDec){
		String scanVal = sc.nextLine();
		ExitMethods.exitNormal(scanVal, currUserName);
		boolean check = decCheck(scanVal, numDec);
		clearConsole();
		if(check == true){
			try{
				return Double.parseDouble(scanVal);
			}catch(Exception e){
			}
		}
		return -1.0;
	}
	
	public final static void clearConsole()  // unclear if this works, but it is supposed to clear the console each time it is run.
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    }
	}
	
	public static boolean checkLetNum(String value, int minLength, int maxLength){
		boolean test = false;
		
		if (Pattern.matches("[a-zA-Z0-9]+",value) == true && value.length() >= minLength && value.length() <= maxLength){
			test = true;
		}

		return test;
	}
	
	public static boolean decCheck(String input, int numDec){
	    if(input.contains(".")){
	    	String[] splitDouble = input.split("\\.");
	    	if (splitDouble[1].length() <= numDec){
				return true;
			}
	    	else return false;
	    }
	    return true;
	}
	
	public static String scanPwd(String currUserName, String dialogue) {
		System.out.print("Password: ");
		String pwdStr = scanStr(currUserName);
		
		/*String pwdStr = "boogie";
		
		try{
			// creates a console object
			Console cnsl = System.console();

			// if console is not null
			if (cnsl != null) {
				// read password into the char array
				char[] pwdChar = cnsl.readPassword(dialogue);
				pwdStr = new String(pwdChar);
			}      
		}catch(Exception ex){

			// if any error occurs
			ex.printStackTrace();      
		}
		ExitMethods.exitNormal(pwdStr, userName);
		clearConsole();*/
		return pwdStr;
    }

	public static String inputYN(String currUserName){
		String isYN = UserInputMethods.scanStr(currUserName);

		if(isYN.equals("Y") || isYN.equals("N")){	
			return isYN;
		}
		else {
			MiscMeth.invSelect();
			return null;
		}
	}
}
