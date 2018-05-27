package proiectpao.beans;

public class User {
	
	   private String userName;
	   private String password;
	   private int privilege;
	    
	 
	   public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public User() {
	        
	   }
	    
	   public String getUserName() {
	       return userName;
	   }
	 
	   public void setUserName(String userName) {
	       this.userName = userName;
	   }

	   public String getPassword() {
	       return password;
	   }
	 
	   public void setPassword(String password) {
	       this.password = password;
	   }
}
