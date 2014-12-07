package customerInfo;

import java.util.ArrayList;

public class Repositories extends Customer {
	String repType = null;
	int repNumber = 0;
	double repBal = 0;
	ArrayList<String> repActivity;
	
	public Repositories (){
		repType = "test";
		repNumber = 0;
		repBal = 0; 
		repActivity = new ArrayList<>();
	}
	
	public Repositories (String AT, int AN, double BL){
		repType = AT;
		repNumber = AN;
		repBal = BL;
		repActivity = new ArrayList<>();
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

	public ArrayList<String> getRepActiv(){
		return repActivity;
	}

	public double setRepBal(Double newBal) {
		repBal = newBal;
		return repBal;
	}
	
	public void addRepActiv(String newActiv){
		repActivity.add(newActiv);
	}
}
