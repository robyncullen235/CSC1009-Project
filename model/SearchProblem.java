package model;

import java.util.ArrayList;

public class SearchProblem extends SmartSerializable
{
	private static final long serialVersionUID = 1L;
	
	public String problemId;
	public String problemName;
	public String description;
	public long date;
	public String fixed;
	
	public ArrayList<String> problems;
	
}