package transactions;


public class Donations {
	private int repNum = 0;
	private double donAmt = 0;
	
	public Donations (){
		repNum = 0;
		donAmt = 0; 
	}
	
	public Donations (int RN, double DA){
		repNum = RN;
		donAmt = DA;
	}
	
	public int getRepNum(){
		return repNum;
	}
	
	public double getDonAmt(){
		return donAmt;
	}
}
