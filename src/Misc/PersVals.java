package Misc;

public class PersVals {
	String valLabel = null;
	String value = null;
	
	public PersVals(){
		valLabel = null;
		value = null;
	}
	
	public PersVals (String valLab, String val){
		valLabel = valLab;
		value = val;
	}
	
	public String getLabel(){
		return valLabel;
	}
	
	public String getValueStr(){
		return value;
	}
	
	public int getValueInt(){
		return Integer.parseInt(value);
	}
}
