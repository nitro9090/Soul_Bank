package customerInfo;

public class Repositories extends Customer {
	String repType = null;
	int repNumber = 0;
	double repBal = 0;
	
	public Repositories (){
		repType = "test";
		repNumber = 0;
		repBal = 0; 
	}
	
	public Repositories (String AT, int AN, double BL){
		repType = AT;
		repNumber = AN;
		repBal = BL;
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

	public double setRepBal(Double newBal) {
		repBal = newBal;
		return repBal;
	}
}
