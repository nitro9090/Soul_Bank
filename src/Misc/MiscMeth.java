package Misc;

import java.util.ArrayList;

import users.User;

public class MiscMeth {
	public static void invSelect(){
		System.out.println("Invalid Selection!");
	}

	public static void invAmt(){
		System.out.println("Invalid Amount!");
	}
	
	public static boolean authenticateUser(String inUserName, String inPassword, String transaction){
		ArrayList<User> users = ReadWriteFile.loadUsers();
		for(int i = 0; i<users.size(); i++){
			if (inUserName.equals(users.get(i).getUserName())){
				if (inPassword.equals(users.get(i).getPassword())){
					ReadWriteFile.recordActiv(inUserName, "PassedAuth-"+transaction+"-" + inUserName);
					return true;
				}
			}
			else if(i == users.size()-1){
				System.out.println("Username and/or password do not match or exist, try again.");
				ReadWriteFile.recordActiv(inUserName, "FailedAuth:"+transaction+"-" + inUserName);
				return false;
			}
		}
		return false;
	}

	public static boolean compareStrings(String inputString, String compareString){
		if(inputString.equals(compareString)){
			return true;
		}
		return false;
	}
}
