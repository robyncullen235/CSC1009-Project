package views;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.Comment;
import model.Forum;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class ViewForum extends DynamicWebPage
{

	public ViewForum(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		if(toProcess.path.equalsIgnoreCase("ViewForum.html/Async")){
			System.out.println("Async code started");//TEST CODE
			//Get the new value and forum id from the parameters passed 
			String postText = toProcess.params.get("postText");System.out.println(postText);
			String forumId = toProcess.params.get("forumId");
			
			//Save the new post text to a new temp forum object
			MVMap<String, Forum> forums = db.s.openMap("Forums");
			Forum tempForum = forums.get(forumId);
			tempForum.forumPostText = postText;
			
			//Overwrite new post text to the database
			forums.put(forumId, tempForum);
			db.commit();

		} else if(toProcess.path.startsWith("ViewForum.html/delete/Forum/")) { //NEW CODE to handle the deletion of a forum
			String forumId = toProcess.path.substring("ViewForum.html/delete/Forum/".length());
			//Delete From the MVMap
			MVMap<String, Forum> forums = db.s.openMap("Forums");//Open the map
			Forum forum = forums.get(forumId);//Get the forum being deleted
			forums.remove(forum.forumId);
			
			//Redirect to the main community forums page
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/CommunityForums.html\"/>";
			//stringToSendToWebBrowser += "<p><a href=\"Updates.html/PinnedProblem/"+firstProblem.problemId+"\">Redirect</a></p>";//For older browsers
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			
			return true;
			
		} else if(toProcess.path.equalsIgnoreCase("ViewForum.html/submit/Comment")) { 
			//Get forum id
			String forumId = toProcess.params.get("forumId");System.out.println(forumId);
			//Get comment test from the parameters passed 
			MVMap<String, User> userMV = db.s.openMap("User");
			List<String> userKey = userMV.keyList();
			String user = userKey.get(0);
			String text = toProcess.params.get("txtCommentPost");
			String date = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
			
			//Save the new post text to a new temp forum object
			MVMap<String, Forum> forums = db.s.openMap("Forums");
			Forum forum = forums.get(forumId);
			
			//Create a new comment object
			Comment newCom = new Comment();
			newCom.creatorName = user;
			newCom.commentText = text;
			newCom.dateOfCreation = date;
			//Add to the forum's comments arraylist 
			forum.forumComments.add(newCom);
			
			//Overwrite new post text to the database
			forums.put(forumId, forum);
			db.commit();
			
			//Redirect back to the forum
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/ViewForum.html/"+forumId+"\"/>";
			//stringToSendToWebBrowser += "<p><a href=\"Updates.html/PinnedProblem/"+firstProblem.problemId+"\">Redirect</a></p>";//For older browsers
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			
			return true;
			
		} else if(toProcess.path.startsWith("ViewForum.html/")){
			//Get the viewed forumId from the url
			String forumId = toProcess.path.substring("ViewForum.html/".length());
			//Pass the current forum into an object
			MVMap<String, Forum> forums = db.s.openMap("Forums");//Open the map
			Forum forum = forums.get(forumId);//pass the current forum into a temporary Forum object, using the id
			
			//Declare a boolean variables to make user code easier to read
			MVMap<String, User> userMap = db.s.openMap("User");
			List<String> userKey = userMap.keyList();
			User user = userMap.get(userKey.get(0));
			
			boolean loggedIn = false;
			boolean isCreator = false;//Store if the logged in user is the creator of the forum
			if(userMap.keyList()!=null && !userMap.keyList().isEmpty()) {
				//If the map is not null and empty, then a user is logged in
				loggedIn = true;
				if(forum.creatorUserName.equals(user.username)){
					isCreator = true;
				} else {
					isCreator = false;
				}//END if
			} else {
				//If not then there must be a user logged in
				loggedIn = false;
			}//END if else 
			
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<!DOCTYPE html>\n";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "<head>\n";
			stringToSendToWebBrowser += "  <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"http://localhost:8080/Project_CSS.css\" type=\"text/css\"> </head>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "<body class=\"\">\n";
			//NAV BAR
			stringToSendToWebBrowser += "  <nav class=\"navbar navbar-expand-md navbar-light border-dark bg-primary d-flex flex-row justify-content-end align-items-end text-center\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\"> <span class=\"navbar-toggler-icon\"></span> </button>\n";
			stringToSendToWebBrowser += "      <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <img class=\"img-fluid d-block pi-draggable float-left\" src=\"http://localhost:8080/logo1.png\" height=\"10\" width=\"30\">\n";
			stringToSendToWebBrowser += "            <a class=\"navbar-brand\" href=\"#\"><b class=\"p-3\">Street Debugger</b></a>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <ul class=\"navbar-nav ml-auto\">\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/Index.html\">Home</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/SearchProblems.html\">Problems</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-light text-info active\" href=\"http://localhost:8080/CommunityForums.html\">Discussion</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/Updates.html\">Updates</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/FAQ.html\">FAQ</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item bg-primary\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/Contact.html\">Contact</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			//START: user code
			if (loggedIn) {stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-secondary \" href=\"http://localhost:8080/SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i>"+user.username+"</a>\n";
			} else {stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-secondary \" href=\"http://localhost:8080/SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i> Sign in</a>\n";}
			//END: user code
			stringToSendToWebBrowser += "        </ul>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </nav>\n";
			
			stringToSendToWebBrowser += "  <div class=\"py-5 bg-secondary\"><b class=\"text-dark\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <div class=\"row border border-secondary\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-9	 bg-primary\"><h1 class=\"text-dark\"><b>"+forum.forumTitle+"</b></h1>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <div class=\"col-md-1 bg-primary\"></div>\n";
			//START: delete button
			stringToSendToWebBrowser += "  <div class=\"col-md-2 bg-primary\">";
			if(loggedIn && isCreator) {
				stringToSendToWebBrowser += "<script>\n";
				stringToSendToWebBrowser += "      function confirmDelete() {\n";
				stringToSendToWebBrowser += "        var result = confirm(\"WARNING: this forum will be delected permanently. Still wish to proceed?\");\n";
				stringToSendToWebBrowser += "        if (result) {\n";
				stringToSendToWebBrowser += "            window.location.href = \"http://localhost:8080/ViewForum.html/delete/Forum/"+forum.forumId+"\";\n";
				stringToSendToWebBrowser += "        }\n";
				stringToSendToWebBrowser += "      }\n";
				stringToSendToWebBrowser += "    </script>\n";
				stringToSendToWebBrowser += "    <button class=\"btn btn-secondary\" onclick=\"confirmDelete()\">Delete Forum</button>\n";
			}//END if
			stringToSendToWebBrowser += "  </div>\n";
			//END: delete button	
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <div class=\"row\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-2 bg-primary\">\n";
			stringToSendToWebBrowser += "      <p class=\"text-info\"> Created By:\n";
			stringToSendToWebBrowser += "        <br>Created on:\n";
			stringToSendToWebBrowser += "        <br>Subject:\n";
			stringToSendToWebBrowser += "        <br> </p>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-10 bg-primary\"> "+forum.creatorUserName+"\n";
			stringToSendToWebBrowser += "      <br>"+forum.dateOfCreation+"\n";
			stringToSendToWebBrowser += "      <br>"+forum.forumSubject+"\n";
			stringToSendToWebBrowser += "      <br> </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <div class=\"row bg-primary\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-1\"></div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-10\">\n";
			//START: forum post
			if(loggedIn && isCreator) {
				//javascript for async code
				stringToSendToWebBrowser += "  <script>\n";
				stringToSendToWebBrowser += "function asyncLoad(){\n";
				stringToSendToWebBrowser += "    if (window.XMLHttpRequest)\n";
				stringToSendToWebBrowser += "    {// code for IE7+, Firefox, Chrome, Opera, Safari\n";
				stringToSendToWebBrowser += "        xmlhttp=new XMLHttpRequest();\n";
				stringToSendToWebBrowser += "    }\n";
				stringToSendToWebBrowser += "    else\n";
				stringToSendToWebBrowser += "    {// code for IE6, IE5\n";
				stringToSendToWebBrowser += "        xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n";
				stringToSendToWebBrowser += "    }\n";
				stringToSendToWebBrowser += "    xmlhttp.onreadystatechange=myDataLoadedFunction;\n";
				stringToSendToWebBrowser += "     confirmPost() //Send pop up box";
				stringToSendToWebBrowser += "    //This line actually makes the request to the server\n";
				stringToSendToWebBrowser += "    xmlhttp.open(\"GET\",'http://localhost:8080/ViewForum.html/Async?postText='+document.getElementById(\"Textarea\").value+'&forumId="+forumId+"',true);\n";
				stringToSendToWebBrowser += "    xmlhttp.send();\n";
				stringToSendToWebBrowser += "}\n";
				
				stringToSendToWebBrowser += "function myDataLoadedFunction()\n";
				stringToSendToWebBrowser += "{\n";
				stringToSendToWebBrowser += "  if (xmlhttp.readyState==4 && xmlhttp.status==200)\n";
				stringToSendToWebBrowser += "  {\n";
				stringToSendToWebBrowser += "    //This line will set the html between the <body> tags\n";
				stringToSendToWebBrowser += "    //with what was returned by the webserver\n";
				stringToSendToWebBrowser += "    //this will make the page redraw\n";
				stringToSendToWebBrowser += "      document.getElementById(\"Textarea\").innerHTML = xmlhttp.responseText;\n";
				stringToSendToWebBrowser += "  }\n";
				stringToSendToWebBrowser += "}\n";
				
				stringToSendToWebBrowser += "function confirmPost() {\n";
				stringToSendToWebBrowser += "	alert(\"Post saved\");\n";
				stringToSendToWebBrowser += "}\n";
				stringToSendToWebBrowser += "  </script>\n";
				//ADD method = GET and action
				stringToSendToWebBrowser += "      <form class=\"form-group\" action=\"/ViewForum.html/submit/ForumPost\" method=\"GET\">"
													+ "<textarea class=\"form-control\" id=\"Textarea\" name=\"txaForumPost\" rows=\"3\" placeholder=\"Type your insightful forum post here\">"+forum.forumPostText+"</textarea><br> \n";
				stringToSendToWebBrowser += "      <button type=\"submit\" class=\"btn btn-secondary\" onclick='asyncLoad();return false;'>Submit</button>\n";
				stringToSendToWebBrowser+=  "	</form></div>\n";
			} else {
				//ADD method = GET and action
				stringToSendToWebBrowser += "      <form class=\"form-group\">"
													+ "<textarea readonly class=\"form-control\"  rows=\"3\" placeholder=\"The creator hasn't posted anything yet\">"+forum.forumPostText+"</textarea><br> \n";
				stringToSendToWebBrowser+=  "	</form></div>\n";
			}
			stringToSendToWebBrowser += "    <div class=\"col-md-1\"></div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			//END: forum post
		
			//START: Post Comment
			stringToSendToWebBrowser += "<div class=\"row bg-primary\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-10\">\n";
			if(loggedIn) {	
				// method = GET and action
				stringToSendToWebBrowser += "      <form class=\"form-group\" action=\"/ViewForum.html/submit/Comment\" method=\"GET\"> <label for=\"Textarea\"><h2>Comments</h2></label>"
													+ "<textarea class=\"form-control\" id=\"Textarea\" name=\"txtCommentPost\" rows=\"3\" placeholder=\"Type comment here\"></textarea><br> \n";
				stringToSendToWebBrowser += "      <button type=\"submit\" class=\"btn btn-secondary\">Post</button>\n";
				//Send the forumId as a hidden input
				stringToSendToWebBrowser += "    <input type=\"hidden\" id=\"hiddeninput\" name=\"forumId\" value=\""+forum.forumId+"\">\n";//NEW CODE
				stringToSendToWebBrowser+=  "	</form>\n";
			} else {
				stringToSendToWebBrowser += "  <h2>Comments</h2>\n";
				stringToSendToWebBrowser += "  <h4 class=\"text-dark\">You must be signed in to post a comment</h4>\n";
				stringToSendToWebBrowser += "  <a href=\"http://localhost:8080/SignIn.html\">\n";
				stringToSendToWebBrowser += "  <h4 class=\"text-info\">Click here to sign in</h4>\n";
				stringToSendToWebBrowser += "  </a><br>\n";
			}
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			//END: Post Comment
			stringToSendToWebBrowser += "  <div class=\"row bg-primary\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-12\">\n";
			//Print out comments
			if (forum.forumComments !=null && forum.forumComments.size() !=0) {	
				//print out all comments
				for(Comment eachCom: forum.forumComments) {
					stringToSendToWebBrowser += "      <ul class=\"media-list\">\n";
					stringToSendToWebBrowser += "        <li class=\"media\">\n";
					stringToSendToWebBrowser += "          <a class=\"pull-left\" href=\"#\">\n";
					stringToSendToWebBrowser += "            <div class=\"media-body\">\n";
					stringToSendToWebBrowser += "              <h4 class=\"text- text-secondary\"><b>Posted by "+eachCom.creatorName+" on "+eachCom.dateOfCreation+"</b></h4>\n";
					stringToSendToWebBrowser += "              <p class=\"text-dark\">"+eachCom.commentText+"</p>\n";
					stringToSendToWebBrowser += "            </div>\n";
					stringToSendToWebBrowser += "          </a>\n";
					stringToSendToWebBrowser += "        </li>\n";
					stringToSendToWebBrowser += "        <a class=\"pull-left\" href=\"#\"> </a>\n";
					stringToSendToWebBrowser += "      </ul>\n";
				}//END for
			} else {
				stringToSendToWebBrowser += "  <h4 class=\"text- text-secondary\"><b>No one has posted yet, be the first!</b></h4>\n";
			}//END if else

			stringToSendToWebBrowser += "      <a class=\"pull-left\" href=\"#\"> </a>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <a class=\"pull-left\" href=\"#\"> </a>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <a class=\"pull-left\" href=\"#\"> </a>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <a class=\"pull-left\" href=\"#\"> </a>\n";
			stringToSendToWebBrowser += "  </b>\n";
			stringToSendToWebBrowser += "  </div><b class=\"text-dark\"><a class=\"pull-left\" href=\"#\">\n";
			stringToSendToWebBrowser += "  <div class=\"text-primary\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\"> <b><b></b></b>\n";
			stringToSendToWebBrowser += "  </div><b><b>\n";
			stringToSendToWebBrowser += "  </b></b>\n";
			stringToSendToWebBrowser += "  </div><b><b>\n";
			stringToSendToWebBrowser += "  <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "  <pingendo onclick=\"window.open('https://pingendo.com/', '_blank')\" style=\"cursor:pointer;position: fixed;bottom: 10px;right:10px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:180px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white\">Made with Pingendo&nbsp;&nbsp;<img src=\"https://pingendo.com/site-assets/Pingendo_logo_big.png\" class=\"d-block\" alt=\"Pingendo logo\" height=\"16\"></pingendo>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</b></b>\n";
			stringToSendToWebBrowser += "  </a>\n";
			stringToSendToWebBrowser += "  </b>\n";
			
			//FOOTER
			stringToSendToWebBrowser += "  <div class=\"text-primary bg-primary\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"p-4 col-md-4\">\n";
			stringToSendToWebBrowser += "          <h2 class=\"mb-4 text-info\"><b>Street Debugger</b></h2>\n";
			stringToSendToWebBrowser += "          <p class=\"text-dark\">Mapping and reporting street problems to the councils responsible for fixing them \u00C3\u00A2\u00C2\u0080\u00C2\u0093 anywhere in the UK.</p>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <div class=\"p-4 col-md-4\">\n";
			stringToSendToWebBrowser += "          <h2 class=\"mb-4 text-info\"><b>Contact</b></h2>\n";
			stringToSendToWebBrowser += "          <p class=\"text-dark\">\n";
			stringToSendToWebBrowser += "            <a href=\"tel:+246 - 542 550 5462\" class=\"text-dark\"><i class=\"fa d-inline mr-3 text-secondary fa-phone\"></i>07885262004</a>\n";
			stringToSendToWebBrowser += "          </p>\n";
			stringToSendToWebBrowser += "          <p class=\"text-dark\">\n";
			stringToSendToWebBrowser += "            <a href=\"mailto:info@pingendo.com\" class=\"text-dark\"><i class=\"fa d-inline mr-3 text-secondary fa-envelope-o\"></i>s</a>treetdebugger12@gmail.com</p>\n";
			stringToSendToWebBrowser += "          <p class=\"text-dark\">18 Malone Rd, Belfast BT9 6RT</p>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <div class=\"p-4 col-md-4\">\n";
			stringToSendToWebBrowser += "          <h2 class=\"mb-4 text-info\"><b>Follow us on</b></h2>\n";
			stringToSendToWebBrowser += "          <div class=\"row\">\n";
			stringToSendToWebBrowser += "            <div class=\"col-4 col-md-4 align-self-center\">\n";
			stringToSendToWebBrowser += "              <a href=\"https://www.facebook.com\" target=\"_blank\"><i class=\"fa fa-fw fa-facebook fa-3x text-dark\"></i></a>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-4 col-md-4 align-self-center\">\n";
			stringToSendToWebBrowser += "              <a href=\"https://twitter.com\" target=\"_blank\"><i class=\"fa fa-fw fa-twitter fa-3x text-dark\"></i></a>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-4 col-md-4 align-self-center\">\n";
			stringToSendToWebBrowser += "              <a href=\"https://www.instagram.com\" target=\"_blank\"><i class=\"fa fa-fw fa-instagram fa-3x text-dark\"></i></a>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div><b><b>\n";
			stringToSendToWebBrowser += "  </b></b> <b><b>\n";
			stringToSendToWebBrowser += "  <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "  <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "  <pingendo onclick=\"window.open('https://pingendo.com/', '_blank')\" style=\"cursor:pointer;position: fixed;bottom: 10px;right:10px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:110px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white\">made with&nbsp;&nbsp;<img src=\"https://pingendo.com/site-assets/Pingendo_logo_big.png\" class=\"d-block\" alt=\"Pingendo logo\" height=\"16\"></pingendo>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</b></b>\n";
			stringToSendToWebBrowser += "  </b>\n";
			stringToSendToWebBrowser += "</body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";
			
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
	 }//END if else
	return false;
	}
}
