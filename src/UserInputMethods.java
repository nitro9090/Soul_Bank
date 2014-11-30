import java.util.Scanner;

public class UserInputMethods {
	static Scanner sc = new Scanner(System.in);

	public static String scanStr(String userName){
		String scanVal = sc.nextLine();
		ExitMethods.exitComm(scanVal, userName);
		clearConsole();
		String Output = scanVal;
		return Output;
	}

	public static int scanInt(String userName){
		int Output = 0;
		String scanVal = sc.nextLine();
		ExitMethods.exitComm(scanVal, userName);
		clearConsole();
		try{
			Output = Integer.parseInt(scanVal);
		}catch(Exception e){
		}
		return Output;
	}
	
	public static Double scanDbl(String userName){
		Double Output = -1.0;
		String scanVal = sc.nextLine();
		ExitMethods.exitComm(scanVal, userName);
		clearConsole();
		try{
			Output = Double.parseDouble(scanVal);
		}catch(Exception e){
		}
		return Output;
	}
	
	public static void scanClear(){
		sc.nextLine();
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
}
