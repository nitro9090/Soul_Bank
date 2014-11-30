
public class ExitMethods {
	public static void exit(){
		System.out.println("Goodbye, for now...");
		System.exit(0);
	}
	
	public static void exitComm(String value, String userName){
		if(value.equals("escape")){
			if (!userName.equals(null)){
				WriteToFile.recordAction(userName, "NormalLogout", "");
			}
			exit();
		}
	}
	
	public static void exitCommBad(){
		System.out.println("You have been bad, now begone.");
		WriteToFile.recordAction("null", "BadLogin", "Reached mainmenu without a username");
		System.exit(0);
	}
}
