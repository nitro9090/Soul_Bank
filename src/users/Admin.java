package users;

public class Admin extends User{
	
	
	public Admin(){
		
	}
	
	public Admin (String uN, String pW, String uT, String fN, String lN){
		super(uN, pW, uT, fN, lN);
	}
	
	public Admin(User user){
		super(user.getUserName(), user.getPassword(),user.getAcctType(), user.getFirstName(), user.getLastName());
	}
}
