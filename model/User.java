package model;

import java.util.ArrayList;
public class User extends SmartSerializable
{
	private static final long serialVersionUID = 1L;
	//Instance Variables 
	public String userId, emailAddress, username, password;
	public ArrayList<Problem> pinnedProblems; 
	public boolean employee = false;
}