
public class ExitMethods {
	public static void exitNormal(String value, String userName){
		if(value.equals("escape")){
			if (!userName.equals(null)){
				ReadWriteFile.recordAction(userName, "NormalLogout", "");
			}
			System.out.println("Goodbye, for now...");
			System.exit(0);
		}
	}
	
	public static void exitCommBad(){
		System.out.println("You have been bad, now begone.");
		ReadWriteFile.recordAction("null", "BadLogin", "Reached mainmenu without a username");
		System.exit(0);
	}
}
