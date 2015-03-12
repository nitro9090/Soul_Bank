package users;


public class UserActiv {
	String userName;
	int activNum;
	String date;
	String time;
	String action;
	
	public UserActiv (){
		userName = "null";
		activNum = -1;
		date = "null";
		time = "null";
		action = "null";
	}
	
	public UserActiv (String uN, int aN, String d, String t, String a){
		userName = uN;
		activNum = aN;
		date = d;
		time = t;
		action = a;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public int getActivNum(){
		return activNum;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getTime(){
		return time;
	}
	
	public String getAction(){
		return action;
	}
}