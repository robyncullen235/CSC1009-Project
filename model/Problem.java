package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Problem extends SmartSerializable
{
	private static final long serialVersionUID = 1L;
	
	public String problemId;
	public String email;
	public String location;
	public String problemChoice;
	public String description;
	public String action;
	public String postedBy;
	public LocalDate date;
	public LocalTime time;
	
}