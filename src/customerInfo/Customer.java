package customerInfo;

import java.util.ArrayList;

public class Customer{
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	ArrayList<customerInfo.Repositories> repositories; 
	
	public Customer (){
		userName = "test";
		password = "test";
		firstName = "test";
		lastName = "test";
		repositories = new ArrayList<>(); 
	}
	
	public Customer (String UN, String PW, String FN, String LN){
		userName = UN;
		password = PW;
		firstName = FN;
		lastName = LN;
		repositories = new ArrayList<>(); 
	}
    
	public String getUserName(){
		return userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String setUserName(String UN){
		 userName = UN;
		 return userName;
	}
	
	public String setPassword(String PW){
		 password = PW;
		 return password;
	}
	
	public String setFirstName(String FN){
		 firstName = FN;
		 return firstName;
	}
	
	public String setLastName(String LN){
		 lastName = LN;
		 return lastName;
	}
	
	public ArrayList<customerInfo.Repositories> getRep(){
		return repositories;
	}
	
	public ArrayList<Repositories> addRep(Repositories temp){
	    repositories.add(temp);
		return repositories;
	}
	
	public Repositories getRep(int acctNum){
		int acctPos = findRepPos(acctNum);
		return repositories.get(acctPos);
	}
	
	public String getRepType(int acctNum){
		int acctPos = findRepPos(acctNum);
		return repositories.get(acctPos).getRepType();
	}
	
	public int getRepNum(int acctPos){
		return repositories.get(acctPos).getRepNum();
	}
	
	public double getRepBal(int acctNum){
		int acctPos = findRepPos(acctNum);
		return repositories.get(acctPos).getRepBal();
	}
	
	public double setRepBal(int acctNum, Double newBal){
		int acctPos = findRepPos(acctNum);
		return repositories.get(acctPos).setRepBal(newBal);
	}
	
	public int findRepPos(int acctNum) {
		int acctPos = -1;
		int acctSize = repositories.size();

		for(int i = 0; i < acctSize; i++){
			if(repositories.get(i).getRepNum() == acctNum){
				return i;
			}
		}
		return acctPos;
	}
}
