package application;

public class loginVerification {
	public static boolean verifyLogin(String login, String password){

		String correctUsername = "Doctor";
		String correctPassword = "panaceum";
		if(login.equals(correctUsername) && password.equals(correctPassword)){
			return true;
		}else {
			return false;
			}
	}

}
