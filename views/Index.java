package views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.Problem;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class Index extends DynamicWebPage
{

	public Index(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		if(toProcess.path.equalsIgnoreCase("Index.html"))
		{
			
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
			
			//Create MV map and list for the database 
			MVMap<String, Problem> problems = db.s.openMap("Problems");
			List<String> problemKeys = problems.keyList();
			
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<!DOCTYPE html>\n";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "<head>\n";
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
			stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-light text-info active\" href=\"Index.html\">Home</a>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"SearchProblems.html\">Problems</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"CommunityForums.html\">Discussion</a>\n";
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
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "          <div class=\"card text-white p-5 bg-primary\">\n";
			stringToSendToWebBrowser += "            <div class=\"card-body\">\n";
			stringToSendToWebBrowser += "              <h1 class=\"text-dark\"><b>Report Problem</b></h1>\n";
			
			//Form code
			
			stringToSendToWebBrowser += "  <form role=\"form\" action=\"/Index.html/submit/Problems\" method=\"GET\">\n";
			stringToSendToWebBrowser += "    <div class=\"form-group\"> <label class=\"lead text-info\"><b>Email address</b></label>\n";
			stringToSendToWebBrowser += "      <input type=\"email\" class=\"form-control\" id=\"inputEmail\" name =\"inputEmail\" placeholder=\"Enter email\"> </div>\n";
			stringToSendToWebBrowser += "    <div class=\"form-group\"> <label class=\"lead text-info\"><b>Location</b></label>\n";
			stringToSendToWebBrowser += "      <br>\n";
			stringToSendToWebBrowser += "      <input type=\"text\" class=\"form-control text-dark\" id=\"inputLocation\" name =\"inputLocation\" placeholder=\"Enter the PostCode of where the problem occurs\"> </div>\n";
			stringToSendToWebBrowser += "    <div class=\"form-group text-dark\"> <label class=\"lead text-info\"><b>Problem Type</b></label>\n";
			stringToSendToWebBrowser += "      <br>\n";
			stringToSendToWebBrowser += "       <select name=\"ProblemType\">\n";
			stringToSendToWebBrowser += "         <option selected=\"selected\" value=\"Select Problem\">Problem Types</option>\n";
			stringToSendToWebBrowser += "         <option value=\"Noise\">Noise</option>\n";
			stringToSendToWebBrowser += "         <option value=\"Road Safety\">Road Safety</option>\n";
			stringToSendToWebBrowser += "         <option value=\"Rubbish\">Rubbish</option>\n";
			stringToSendToWebBrowser += "         <option value=\"Vandalism\">Vandalism</option>\n";
			stringToSendToWebBrowser += "      </select>\n";
			stringToSendToWebBrowser += "      <br>\n";
			stringToSendToWebBrowser += "      <br>\n";
			stringToSendToWebBrowser += "      <div class=\"form-group\"> <label class=\"lead text-info\"><b>Problem Description</b></label>\n";
			stringToSendToWebBrowser += "        <br>\n";
			stringToSendToWebBrowser += "        <input type=\"text\" class=\"form-control\" id=\"inputProbDescription\" name =\"inputProbDescription\" placeholder=\"Enter the description of the problem you wish to report \"> </div>\n";
			stringToSendToWebBrowser += "      <div class=\"form-group\"> <label class=\"lead text-info\"><b>Action</b></label>\n";
			stringToSendToWebBrowser += "        <br>\n";
			stringToSendToWebBrowser += "       <select name=\"ActionType\">\n";
			stringToSendToWebBrowser += "         <option selected=\"selected\" value=\"Select Action\">Action Type</option>\n";
			stringToSendToWebBrowser += "         <option value=\"Fix Yourself\">Fix Yourself</option>\n";
			stringToSendToWebBrowser += "         <option value=\"Send Problem to Council\">Send to Council</option>\n";
			stringToSendToWebBrowser += "         <option value=\"Both\">Both</option>\n";
			stringToSendToWebBrowser += "      </select>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  <button type=\"submit\" class=\"text-white btn-secondary\">Submit</button>\n";
			stringToSendToWebBrowser += "  </form>\n";
			stringToSendToWebBrowser += "  </div>\n";			
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			
			//footer
			
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
		}  else if(toProcess.path.equalsIgnoreCase("Index.html/submit/Problems")) { //NEW CODE to handle the submission from the form
			
			Problem newProb = new Problem();
			newProb.problemId = "problem_"+System.currentTimeMillis();
			newProb.email = toProcess.params.get("inputEmail");//get value in the labels
			newProb.location = toProcess.params.get("inputLocation");
			newProb.problemChoice = toProcess.params.get("ProblemType");
			newProb.description = toProcess.params.get("inputProbDescription");
			newProb.action = toProcess.params.get("ActionType");
			
			MVMap<String, User> userMap = db.s.openMap("User");
			List<String> userKey = userMap.keyList();
			User user = userMap.get(userKey.get(0));
			if (user.username.equals(null)) {
				newProb.postedBy = "Anonymous";
			} else {
				newProb.postedBy = user.username;
			}
			
			newProb.date = LocalDate.now();
			newProb.time = LocalTime.now();
			
			//Add to the database rather than an arraylist
			//Add new forum to the MVMap
			MVMap<String, Problem> problems = db.s.openMap("Problems");
			problems.put(newProb.problemId, newProb);
			db.commit();
			
			
			
			//Refresh the page to allow a new problem to be reported
			String url = null;
			if (newProb.action.equals("Fix Yourself") || newProb.action.equals("Both")) {
				url= "http://localhost:8080/ProblemAdvice.html";
			} else {
				url= "http://localhost:8080/Index.html";
			}
			toProcess.r = new WebResponse( WebResponse.HTTP_REDIRECT, WebResponse.MIME_HTML, "<head><body>Redirected: <a href=\"" + url + "\">" +
			url + "</a></body></head>");
			
			toProcess.r.addHeader("Location", url);
			return true;
		}
		return false;
	}

}