package model;

import java.util.ArrayList;
import java.util.Date;

public class Forum extends SmartSerializable
{
	private static final long serialVersionUID = 1L;
	
	//Instance Variables
    public String forumId;
	public String forumTitle;
	public String forumSubject;
    public String forumPostText;
	public ArrayList<Comment> forumComments;//Might need to change the data type to Comment object when it's created
	public String creatorUserName;
	public String dateOfCreation;

}