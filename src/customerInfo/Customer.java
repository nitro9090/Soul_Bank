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
	
	public Repositories getRep(int repNum){
		int repPos = findRepPos(repNum);
		return repositories.get(repPos);
	}
	
	public String getRepType(int repNum){
		int repPos = findRepPos(repNum);
		return repositories.get(repPos).getRepType();
	}
	
	public int getRepNum(int repPos){
		return repositories.get(repPos).getRepNum();
	}
	
	public double getRepBal(int repNum){
		int repPos = findRepPos(repNum);
		return repositories.get(repPos).getRepBal();
	}
	
	public double setRepBal(int repNum, Double newBal){
		int repPos = findRepPos(repNum);
		return repositories.get(repPos).setRepBal(newBal);
	}
	
	public int findRepPos(int repNum) {
		int repPos = -1;
		int repSize = repositories.size();

		for(int i = 0; i < repSize; i++){
			if(repositories.get(i).getRepNum() == repNum){
				return i;
			}
		}
		return repPos;
	}
}
