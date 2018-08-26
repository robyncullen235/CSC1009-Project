package views;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.Comment;
import model.Forum;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class CommunityForums extends DynamicWebPage
{

	public CommunityForums(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		if(toProcess.path.equalsIgnoreCase("CommunityForums.html/submit/Forum")) {
			//Create new forum
			Forum newForum = new Forum();
			newForum.forumId = "forum_"+System.currentTimeMillis();System.out.println("ID: "+newForum.forumId);
			newForum.forumTitle = toProcess.params.get("inputForumName");//get value in the labels
			newForum.forumSubject = toProcess.params.get("inputForumSubject");
			newForum.forumPostText = "";//Will be added by the user later
			MVMap<String, User> userMV = db.s.openMap("User");
			List<String> userKey = userMV.keyList();
			User user = userMV.get(userKey.get(0));
			newForum.creatorUserName = user.username;System.out.println("Creator: "+newForum.creatorUserName);
			ArrayList<Comment> allComments = new ArrayList<Comment>();
			newForum.forumComments = allComments;
			newForum.dateOfCreation = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);

			//Add new forum to the MVMap of forums
			MVMap<String, Forum> forums = db.s.openMap("Forums");
			forums.put(newForum.forumId, newForum);
			db.commit();

			//Redirect to the new forum that has just been created
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/ViewForum.html/"+newForum.forumId+"\"/>";
			//stringToSendToWebBrowser += "<p><a href=\"Updates.html/PinnedProblem/"+firstProblem.problemId+"\">Redirect</a></p>";//For older browsers
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );

			return true;
		} else if(toProcess.path.equalsIgnoreCase("CommunityForums.html")) {
			//Create MV map and list for the database 
			MVMap<String, Forum> forums = db.s.openMap("Forums");
			List<String> forumKeys = forums.keyList();	
			
			//Declare a boolean variable to make user code easier
			MVMap<String, User> userMap = db.s.openMap("User");
			boolean loggedIn = false;
			if(userMap.keyList() != null && !userMap.keyList().isEmpty()) {
				//If the map is not null and empty, then a user is logged in
				loggedIn = true;
			} else {
				//If not then there must be a user logged in
				loggedIn = false;
			}//END if else 

			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<!DOCTYPE html>\n";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "<head title=\"Community Forums Page\">\n";
			stringToSendToWebBrowser += "  <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"Project_CSS.css\" type=\"text/css\"> </head>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "<body class=\"\">\n";
			stringToSendToWebBrowser += "  <nav class=\"navbar navbar-expand-md navbar-light border-dark bg-primary d-flex flex-row justify-content-end align-items-end text-center\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\"> <span class=\"navbar-toggler-icon\"></span> </button>\n";
			stringToSendToWebBrowser += "      <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <img class=\"img-fluid d-block pi-draggable float-left\" src=\"logo1.png\" height=\"10\" width=\"30\">\n";
			stringToSendToWebBrowser += "            <a class=\"navbar-brand\" href=\"#\"><b class=\"p-3\">Street Debugger</b></a>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <ul class=\"navbar-nav ml-auto\">\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"Index.html\">Home</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"SearchProblems.html\">Problems</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-light text-info active\" href=\"CommunityForums.html\">Discussion</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"Updates.html\">Updates</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"FAQ.html\">FAQ</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item bg-primary\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"Contact.html\">Contact</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			//START: user code
			if(loggedIn) {
				//If the map is not null and empty, then a user is logged in
				List<String> userKey = userMap.keyList();
				//Get the logged in user
				User user = userMap.get(userKey.get(0));
				//Display their name
				stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 text-white btn-secondary\" href=\"SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i> "+user.username+"</a>\n";
			} else {
				//If there is no user logged in then display the sign in button as before
				stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 text-white btn-secondary\" href=\"SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i> Sign in</a>\n";
			}//END if else 
			//END: user code
			stringToSendToWebBrowser += "        </ul>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </nav>\n";
			stringToSendToWebBrowser += "  <div class=\"py-5 bg-secondary\">\n";
			stringToSendToWebBrowser += "    <div class=\"container bg-primary\">\n";
			stringToSendToWebBrowser += "  <!--ROW 1-->   \n";
			stringToSendToWebBrowser += "  <div class=\"row border border-secondary\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"><h1 class=\"text-dark\"><b>Community Forums</b></h1>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "  <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <!--ROW 2-->\n";
			stringToSendToWebBrowser += "  <div class=\"row\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-2 bg-primary\">\n";
			stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Existing Forums</b></p>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-2 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-3 bg-primary\">\n";
			//START: search code
			stringToSendToWebBrowser += "      <input type=\"text\" class=\"form-control\" id=\"myInput\" onkeyup=\"myFunction()\" placeholder=\"Search for forums..\">";
			//javascript for label and table
			stringToSendToWebBrowser += "<script>\n";
			stringToSendToWebBrowser += "function myFunction() {\n";
			stringToSendToWebBrowser += "  // Declare variables \n";
			stringToSendToWebBrowser += "  var input, filter, table, tr, td, i;\n";
			stringToSendToWebBrowser += "  input = document.getElementById(\"myInput\");\n";
			stringToSendToWebBrowser += "  filter = input.value.toUpperCase();\n";
			stringToSendToWebBrowser += "  table = document.getElementById(\"myTable\");\n";
			stringToSendToWebBrowser += "  tr = table.getElementsByTagName(\"tr\");\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "  // Loop through all table rows, and hide those who don't match the search query\n";
			stringToSendToWebBrowser += "  for (i = 0; i < tr.length; i++) {\n";
			stringToSendToWebBrowser += "    td = tr[i].getElementsByTagName(\"td\")[0];\n";
			stringToSendToWebBrowser += "    if (td) {\n";
			stringToSendToWebBrowser += "      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {\n";
			stringToSendToWebBrowser += "        tr[i].style.display = \"\";\n";
			stringToSendToWebBrowser += "      } else {\n";
			stringToSendToWebBrowser += "        tr[i].style.display = \"none\";\n";
			stringToSendToWebBrowser += "      }\n";
			stringToSendToWebBrowser += "    } \n";
			stringToSendToWebBrowser += "  }\n";
			stringToSendToWebBrowser += "}\n";
			stringToSendToWebBrowser += "</script>\n";
			//END: search code
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-1 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-3 bg-primary\">\n";
			stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Create New Discussion</b></p>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </div>\n";

			stringToSendToWebBrowser += "  <!--ROW 3-->\n";
			stringToSendToWebBrowser += "  <div class=\"row\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-8\">\n";
			//Check if there are any forum
			if(forumKeys!=null && !forumKeys.isEmpty()) {
				//START: Table to display all forums
				stringToSendToWebBrowser += "<table id=\"myTable\">\n";
				stringToSendToWebBrowser += "  <tr class=\"header\">\n";
				stringToSendToWebBrowser += "    <th style=\"width:100%;\"></th>\n";
				stringToSendToWebBrowser += "  </tr>\n";
				//Print all forums
				for(int i=0; i<forumKeys.size(); i++) {	
					String currentForumID = forumKeys.get(i);
					Forum currentForum = forums.get(currentForumID);//pass the current forum into a temporary Forum object, using the id
					
					stringToSendToWebBrowser += "  <tr>\n";
					stringToSendToWebBrowser += "    <td><a href=\"ViewForum.html/"+currentForum.forumId+"\">\n";
					stringToSendToWebBrowser += "	 	<h3 class=\"media-heading\"><b>"+currentForum.forumTitle+"</b></h3></a>\n";
					stringToSendToWebBrowser += "       <p>"+currentForum.forumSubject+"</p>\n";
					stringToSendToWebBrowser += "  	 </td>\n";
					stringToSendToWebBrowser += "  </tr>\n";
				}//END for 
				stringToSendToWebBrowser += "</table>\n";
				//END search table code 
			} else {
				stringToSendToWebBrowser += "	 	<h3 class=\"text\">There are no discussions yet, be the first!</h3>\n";
				stringToSendToWebBrowser += "	 	<h4>Fill in the form to begin -----></h4>\n";
			}
			stringToSendToWebBrowser += "    </div>\n";
			
			//START: User Login code | Only display the form if a user is signed in 
			if(loggedIn) {
				//START FORM CODE | add method = get and action
				stringToSendToWebBrowser += "    <div class=\"col-md-2\"> <form class=\"form-horizontal\" role=\"form\" action=\"/CommunityForums.html/submit/Forum\" method=\"GET\">\n";
				stringToSendToWebBrowser +=	"		<label for=\"labelForumName\" class=\"control-label\">Name</label>\n";
				stringToSendToWebBrowser += "      <br><label for=\"labelForumSubject\" class=\"control-label\">Discription</label></div>\n";
				stringToSendToWebBrowser += "    <div class=\"col-md-2\">\n";
				stringToSendToWebBrowser += "      <input type=\"text\" class=\"form-control\" id=\"inputForumName\" name=\"inputForumName\" placeholder=\"New forum name\">\n";
				stringToSendToWebBrowser += "      <input type=\"text\" class=\"form-control\" id=\"inputForumSubject\" placeholder=\"Provide a subject\" name=\"inputForumSubject\">\n";
				stringToSendToWebBrowser += "      <br>\n";
				stringToSendToWebBrowser += "      <button class=\"btn btn-secondary\" type=\"submit\">Create</button>\n";
				//END FORM CODE
			} else {
				//Display message and button if there is no logged in user
				stringToSendToWebBrowser += "  <div class=\"col-md-4 bg-primary\">\n";
				stringToSendToWebBrowser += "  		<h5 class=\"text-dark\">You must be signed in to create a forum</h5>\n";
				stringToSendToWebBrowser += "  		<a href=\"/SignIn.html\"><button class=\"btn btn-secondary\">Sign In/Up</button></a>\n";
			}//END if else 
			//END: user code
			stringToSendToWebBrowser += "    </div>\n";	
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";

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