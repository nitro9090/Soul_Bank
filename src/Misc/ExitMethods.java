package Misc;



public class ExitMethods {
	/**
	 * This method quits the program if 'escape' is input as value. If quitting,
	 * it checks to see if the user is logged in. If they are logged in it
	 * records that the user is logging out normally. After which it quits with
	 * a goodbye message.
	 * 
	 * @param value
	 *            the string value tested whether it equals "escape"
	 * @param userName
	 *            the username of the person logging out.
	 */
	public static void exitNormal(String value, String userName){
		if(value.equals("escape")){
			if (userName != null){
				ReadWriteFile.recordActiv(userName, "NormalLogout");
			}
			System.out.println("Goodbye, for now...");
			System.exit(0);
		}
	}
	
	/**
	 * This command is evoked when someone logs in incorrectly and proceeds to
	 * warn the offender, records the action and exits the program.
	 */
	public static void exitCommBad(){
		System.out.println("You have been bad, now begone.");
		ReadWriteFile.recordActiv("null", "BadLogin");
		System.exit(0);
	}
	
	public static boolean exitCompare(String value, String check){
		if(value.equals(check)){
			return true;
		}
		return false;
	}
}
