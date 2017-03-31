package com.panaceum.model;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
//@XmlType(propOrder={"email", "password", "token"})
public class User {
    
    @Id
    private String email;
    private String password;
    private String token;
	
    public String getEmail() {
    	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
    
}
