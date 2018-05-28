package proiectpao.beans;

public class User {
	
	   private String userName;
	   private String password;
	   private int privilege;
	   private String disabled;
	   private String email;
	 
	   public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPrivilege() {
		return privilege;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
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
