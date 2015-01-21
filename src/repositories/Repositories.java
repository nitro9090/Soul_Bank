package repositories;


import java.util.ArrayList;

import Misc.ReadWriteFile;

public class Repositories {
	String repType = null;
	int repNumber = 0;
	double repBal = 0;
	ArrayList<RepActiv> repActivity;
	
	public Repositories (){
		repType = "test";
		repNumber = 0;
		repBal = 0; 
		repActivity = new ArrayList<>();
	}
	
	public Repositories (int RN, String RT,  double BL){
		repType = RT;
		repNumber = RN;
		repBal = BL;
		repActivity = ReadWriteFile.loadRepActiv(RN);
		
	}
	
	public String getRepType(){
		return repType;
	}
	
	public int getRepNum(){
		return repNumber;
	}
	
	public double getRepBal(){
		return repBal;
	}

	public ArrayList<RepActiv> getRepActiv(){
		return repActivity;
	}

	public void setRepBal(Double newBal) {
		repBal = newBal;
	}
	
	public void setRepType(String newRepType) {
		repType = newRepType;
	}
	
	/*public void addRepActiv(String newActiv){
		repActivity.add(newActiv);
	}*/
}
