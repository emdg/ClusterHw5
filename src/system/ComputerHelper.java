package system;

public class ComputerHelper {

	
	
	public static boolean boolInputHelper(String arg){
		if (arg.toLowerCase().equals("false")){
			return false;
		}
		else if (arg.toLowerCase().equals("true")){
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
}
