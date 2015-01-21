package repositories;


public class RepActiv {
	int activNum;
	int repNum;
	String action;
	double transVal;
	
	public RepActiv (){
		activNum = 0;
		repNum = 0;
		action = null; 
		transVal = 0.0;
	}
	
	public RepActiv (int inActivNum, int inRepNum, String inAction, double inTransVal){
		activNum = inActivNum;
		repNum = inRepNum;
		action = inAction; 
		transVal = inTransVal;
	}
	
	public int getActivNum(){
		return activNum;
	}
	
	public int getRepNum(){
		return repNum;
	}
	
	public String getAction(){
		return action;
	}
	
	public double getTransVal(){
		return transVal;
	}
}