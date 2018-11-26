package com.tests.bisys;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
public class UsersWrapper {

	final Collection<User> users;
	
	public UsersWrapper(Collection<User> users) {		
		this.users = users;
	}
	
	public UsersWrapper() {
		users = null;
	}
	
	@XmlElement(name = "user")
	public Collection<User> getUsers() {
		return users;
	}
}