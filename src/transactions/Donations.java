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
	
	else if(transType.equals("Donate")){
		System.out.printf("You will be donating from account # %d %s which currently has %.2f souls \n", repNumFrom, customerFrom.getRepType(repNumFrom), customerFrom.getRepBal(repNumFrom));
		System.out.println("How much would you like to donate? You will have a chance to verify the transfer.");
	}
}
