package views;

import java.util.List;

import org.h2.mvstore.MVMap;


import model.Problem;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class ViewUpdates extends DynamicWebPage
{

	public ViewUpdates(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		 if(toProcess.path.startsWith("ViewUpdates.html/")){
				
				String problemId = toProcess.path.substring("ViewUpdates.html/".length());
			
				MVMap<String, Problem> problems = db.s.openMap("Problems");
				Problem problem = problems.get(problemId);
	
		 
				//Declare a boolean variables to make user code easier to read
				MVMap<String, User> userMap = db.s.openMap("User");
				List<String> userKey = userMap.keyList();
				User user = userMap.get(userKey.get(0));
				
				boolean loggedIn = false;
				if(userMap.keyList() != null && !userMap.keyList().isEmpty()) {
					//If the map is not null and empty, then a user is logged in
					loggedIn = true;
				} else {
					//If not then there must be a user logged in
					loggedIn = false;
				}
			
					

			
			
			
				
				String stringToSendToWebBrowser = "";
				stringToSendToWebBrowser += "<!DOCTYPE html>\n";
				stringToSendToWebBrowser += "<html>\n";
				stringToSendToWebBrowser += "\n";
				stringToSendToWebBrowser += "<head>\n";
				stringToSendToWebBrowser += "  <meta charset=\"utf-8\">\n";
				stringToSendToWebBrowser += "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
				stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\" type=\"text/css\">\n";
				stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"http://localhost:8080/temp.css\" type=\"text/css\"> </head>\n";
				stringToSendToWebBrowser += "\n";
				stringToSendToWebBrowser += "<body class=\"\">\n";
				stringToSendToWebBrowser += "  <nav class=\"navbar navbar-expand-md navbar-light border-dark bg-primary d-flex flex-row justify-content-end align-items-end text-center\">\n";
				stringToSendToWebBrowser += "    <div class=\"container\">\n";
				stringToSendToWebBrowser += "      <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\"> <span class=\"navbar-toggler-icon\"></span> </button>\n";
				stringToSendToWebBrowser += "      <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n";
				stringToSendToWebBrowser += "        <div class=\"row\">\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "            <img class=\"img-fluid d-block pi-draggable float-left\" src=\"http://localhost:8080/logo1.png\" height=\"10\" width=\"30\">\n";
				stringToSendToWebBrowser += "            <a class=\"navbar-brand p-1 mx-3\" href=\"#\">  Street Debugger</a>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "        <ul class=\"navbar-nav ml-auto\">\n";
				stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-light text-dark\" href=\"http://localhost:8080/Index.html\">Home</a>\n";
				stringToSendToWebBrowser += "          <li class=\"nav-item text-info\">\n";
				stringToSendToWebBrowser += "            <a class=\"nav-link active text-info\" href=\"http://localhost:8080/SearchProblems.html\">Problems</a>\n";
				stringToSendToWebBrowser += "          </li>\n";
				stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
				stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/CommunityForums.html\">Discussion</a>\n";
				stringToSendToWebBrowser += "          </li>\n";
				stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
				stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/Updates.html\">Updates</a>\n";
				stringToSendToWebBrowser += "          </li>\n";
				stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
				stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/FAQ.html\">FAQ</a>\n";
				stringToSendToWebBrowser += "          </li>\n";
				stringToSendToWebBrowser += "          <li class=\"nav-item text-secondary\">\n";
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
				stringToSendToWebBrowser += "  <div class=\"py-1 text-white text-center w-100 bg-secondary\" background-position:left=\"\">\n";
				stringToSendToWebBrowser += "    <div class=\"row pi-draggable\" draggable=\"true\">\n";
				stringToSendToWebBrowser += "      <div class=\"col-md-6\"></div>\n";
				stringToSendToWebBrowser += "      <div class=\"col-md-6\"></div>\n";
				stringToSendToWebBrowser += "    </div>\n";
				stringToSendToWebBrowser += "  </div>\n";
				stringToSendToWebBrowser += "  <p> </p>\n";
				stringToSendToWebBrowser += "  <div class=\"row\">\n";
				stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
				stringToSendToWebBrowser += "      <p> </p>\n";
				
				//BACK TO ALL REPORTS BUTTON 
				stringToSendToWebBrowser += "      <a href=\"http://localhost:8080/SearchProblems.html\"SearchProblems.html\" class=\"previous text-secondary px-3\">Â« Back to all reports</a>\n";
				
				
				stringToSendToWebBrowser += "      <p> </p>\n";
				stringToSendToWebBrowser += "      <div class=\"row\">\n";
				stringToSendToWebBrowser += "        <div class=\"col-md-12 bg-primary\">\n";
				

				
				// MORE INFO ON PROBLEM 
				stringToSendToWebBrowser += "          <div class=\"card\">\n";
				stringToSendToWebBrowser += "            <div class=\"card-body\">\n";
				stringToSendToWebBrowser += "              <h4> "+  problem.problemChoice +" </h4>\n";
				stringToSendToWebBrowser += "              <h6 class=\"text-muted\">Posted by "+problem.postedBy+"</h6>\n";
				stringToSendToWebBrowser += "              <div class=\"row\">\n";
				stringToSendToWebBrowser += "                <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "                  <p> </p>\n";
				stringToSendToWebBrowser += "                  <p class=\"\">"+problem.description+"</p>\n";
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "              </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				
				stringToSendToWebBrowser += "            <div class=\"card-body border border-warning\">\n";
				
				// UPDATES SECTION 
				stringToSendToWebBrowser += "              <h4>Updates</h4>\n";
				stringToSendToWebBrowser += "              <h6 class=\"text-muted\">Posted by ;</h6>\n";
				stringToSendToWebBrowser += "              <div class=\"row\">\n";
				stringToSendToWebBrowser += "                <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "                  <p> </p>\n";
				stringToSendToWebBrowser += "                  <p class=\"lead\">Provide an update for this problem below.</p>\n";
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "              </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "            <div class=\"card-body border-warning\">\n";
				stringToSendToWebBrowser += "              <h4>Provide an Update</h4>\n";
				stringToSendToWebBrowser += "              <div class=\"row\">\n";
				stringToSendToWebBrowser += "                <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "                  <p> </p>\n";
				
				
				
				// UPDATE FORM 
				stringToSendToWebBrowser += "    		<form class=\"form-horizontal\" role=\"form\" action=\"/ViewUpdates.html/submit/Problem\" method=\"GET\">\n";
				stringToSendToWebBrowser += "                  <div class=\"form-group text-dark\"> <label for=\"InputEmail1\">Email address:</label>\n";
				stringToSendToWebBrowser += "                    <input type=\"email\" class=\"form-control\" id=\"InputEmail1\" name=\"inputEmail1\" placeholder=\"Enter email\"> </div>\n";
				stringToSendToWebBrowser += "                  <div class=\"form-group text-dark border\"> <label for=\"Textarea\">Write update here:</label> <textarea class=\"form-control\" id=\"Textarea\" name=\"inputTextArea\" rows=\"7\" placeholder=\"Type here\"></textarea> </div>\n";
				stringToSendToWebBrowser += "                  <button type=\"submit\" class=\"btn btn-secondary\">Post</button>\n";
				
				
				
				
				
				stringToSendToWebBrowser += "                </div>\n";
				stringToSendToWebBrowser += "              </div>\n";
				stringToSendToWebBrowser += "            </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "          <div class=\"row\">\n";
				stringToSendToWebBrowser += "            <div class=\"col-md-12\"> </div>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";
				stringToSendToWebBrowser += "    <div class=\"col-md-4\">\n";
				stringToSendToWebBrowser += "      <div class=\"row\">\n";
				stringToSendToWebBrowser += "        <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
				stringToSendToWebBrowser += "            <iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2312.258051154445!2d-5.939653884145173!3d54.581826980256494!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x486108ee869f9ea9%3A0xa9f41c9563d671de!2sComputer+Science+Building!5e0!3m2!1sen!2suk!4v1518377983243\"\n";
				stringToSendToWebBrowser += "              width=\"760\" height=\"600\" frameborder=\"0\" allowfullscreen=\"\" style=\"border:0\" class=\"m-0 p-0\"></iframe>\n";
				stringToSendToWebBrowser += "          </div>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </div>\n";
				stringToSendToWebBrowser += "    <div class=\"col-md-4\"></div>\n";
				stringToSendToWebBrowser += "  </div>\n";
				stringToSendToWebBrowser += "	<div class=\"py-5\"><div class=\"container\"><div class=\"row\">\n";
				stringToSendToWebBrowser += " <div class=\"col-md-6\"></div>\n";
				stringToSendToWebBrowser += " <div class=\"col-md-6\"></div>\n";
				stringToSendToWebBrowser += " </div></div></div>\n";
				stringToSendToWebBrowser += "  <p> </p>\n";
				stringToSendToWebBrowser += "  <div class=\"text-primary bg-primary\">\n";
				stringToSendToWebBrowser += "    <div class=\"container\">\n";
				stringToSendToWebBrowser += "      <div class=\"row\">\n";
				stringToSendToWebBrowser += "        <div class=\"p-4 col-md-4\">\n";
				stringToSendToWebBrowser += "          <h2 class=\"mb-4 text-info\"><b>Street Debugger</b></h2>\n";
				stringToSendToWebBrowser += "          <p class=\"text-dark\">Mapping and reporting street problems to the councils responsible for fixing themÂ anywhere in the UK.</p>\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "        <div class=\"p-4 col-md-4\">\n";
				stringToSendToWebBrowser += "          <h2 class=\"mb-4 text-info\"><b>Contact</b></h2>\n";
				stringToSendToWebBrowser += "          <p class=\"text-dark\">\n";
				stringToSendToWebBrowser += "            <a href=\"07885262004\" class=\"text-dark\"><i class=\"fa d-inline mr-3 text-secondary fa-phone\"></i>07885262004</a>\n";
				stringToSendToWebBrowser += "          </p>\n";
				stringToSendToWebBrowser += "          <p class=\"text-dark\">\n";
				stringToSendToWebBrowser += "            <a href=\"streetdebugger12@gmail.com\" class=\"text-dark\"><i class=\"fa d-inline mr-3 text-secondary fa-envelope-o\"></i>streetdebugger12@gmail.com</a>\n";
				stringToSendToWebBrowser += "          </p>\n";
				stringToSendToWebBrowser += "          <p>\n";
				stringToSendToWebBrowser += "            <a href=\"https://goo.gl/maps/AUq7b9W7yYJ2\" class=\"text-dark\" target=\"_blank\"><i class=\"fa d-inline mr-3 fa-map-marker text-secondary\"></i>18 Malone Rd, Belfast BT9 6RT</a>\n";
				stringToSendToWebBrowser += "          </p>\n";
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
				stringToSendToWebBrowser += "      <div class=\"row\">\n";
				stringToSendToWebBrowser += "        <div class=\"col-md-12 mt-3\">\n";
				stringToSendToWebBrowser += "        </div>\n";
				stringToSendToWebBrowser += "      </div>\n";
				stringToSendToWebBrowser += "    </b></b>\n";
				stringToSendToWebBrowser += "    </div><b><b>\n";
				stringToSendToWebBrowser += "  </b></b>\n";
				stringToSendToWebBrowser += "  </div>\n";
				stringToSendToWebBrowser += "</body>\n";
				stringToSendToWebBrowser += "\n";
				stringToSendToWebBrowser += "</html>\n";
				toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
				return true;
		
			
		}
			 return false;
		}
	}
