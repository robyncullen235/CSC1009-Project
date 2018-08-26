package views;

import java.util.ArrayList;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.Problem;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class Updates extends DynamicWebPage
{

	public Updates(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}
	
	public boolean process(WebRequest toProcess)
	{
		if(toProcess.path.startsWith("Updates.html/remove/Problem/")) {
			//Get the problem id being removed
			String probId = toProcess.path.substring("Updates.html/remove/Problem/".length());
        	
        	//Get the user
        	MVMap<String, User> userMap = db.s.openMap("User");
        	User user = userMap.get(userMap.firstKey());
        	//Remove problem
        	for(int i=0; i<user.pinnedProblems.size(); i++) {
        		if(user.pinnedProblems.get(i).problemId.equals(probId)) {
        			user.pinnedProblems.remove(i);
        			break;
        		}//END if
        	}//END for
        	
        	//Update the user and users maps
        	MVMap<String, User> users = db.s.openMap("Users");
        	users.put(user.userId, user);
			db.commit();
			userMap.put(user.userId, user);
			db.commit();
			
			//Redirect back to the updates page
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/Updates.html\"/>";
			//stringToSendToWebBrowser += "<p><a href=\"Updates.html/PinnedProblem/"+firstProblem.problemId+"\">Redirect</a></p>";//For older browsers
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
			
		} else if(toProcess.path.equalsIgnoreCase("Updates.html")) {
			//Open the current user map
			MVMap<String, User> user = db.s.openMap("User");
			
			//Save the signed in user into an object if there is one
			if (user!=null && !user.isEmpty()) {
				String curretUserId = user.keyList().get(0);
				User currentUser = user.get(curretUserId);
				
				if (currentUser.pinnedProblems != null && !currentUser.pinnedProblems.isEmpty()) {
					//Get first pinned problem id from the array list
					String firstProblemId = currentUser.pinnedProblems.get(0).problemId;System.out.println("First id: "+firstProblemId);
					//Redirect the page page to one with the first problem ID in it
					String stringToSendToWebBrowser = "";
					stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=Updates.html/PinnedProblem/"+firstProblemId+"\"/>";
					toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
					return true;
				} else {
					String stringToSendToWebBrowser = ViewUpdatesNone();
					toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
					return true;
				}//END if else
				
			} else {
				String stringToSendToWebBrowser = ViewUpdatesNone();
				toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
				return true;
			}//END if else			
			
		} else if(toProcess.path.startsWith("Updates.html/PinnedProblem/")) { 
	
			//Get the id of viewed problem
			String selectedProblemId = toProcess.path.substring("CommunityForums.html/Forum/".length());
			//Get the current user
			MVMap<String, User> userMV = db.s.openMap("User");
			List<String> userKey = userMV.keyList();
			User currentUser = userMV.get(userKey.get(0));
			//Declare a boolean variables to make user code easier to read
			boolean loggedIn = false;
			if(userMV.keyList()!=null && !userMV.keyList().isEmpty()) {loggedIn = true;}else{loggedIn = false;};
			//Search using a for loop
			Problem selectedProblem = null;
			for(Problem eachPro: currentUser.pinnedProblems) {
				if(eachPro.problemId.equals(selectedProblemId)) {
					selectedProblem = eachPro;
				}//END if
			}//END for
			
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
			stringToSendToWebBrowser += "<body>\n";	
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
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/CommunityForums.html\">Discussion</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-light text-info active\" href=\"http://localhost:8080/Updates.html\">Updates</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/FAQ.html\">FAQ</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item bg-primary\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/Contact.html\">Contact</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			//START: user code
			if (loggedIn) {stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-secondary \" href=\"http://localhost:8080/SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i>"+currentUser.username+"</a>\n";
			} else {stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-secondary \" href=\"http://localhost:8080/SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i> Sign in</a>\n";}
			//END: user code
			stringToSendToWebBrowser += "        </ul>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </nav>\n";
			
			stringToSendToWebBrowser += "  <div class=\"py-5 bg-secondary\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <!--ROW 1-->\n";
			stringToSendToWebBrowser += "      <div class=\"row border border-secondary\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"><h1 class=\"text-dark\"><b>Problem Updates</b></h1>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "  <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <!--ROW 2-->\n";
			stringToSendToWebBrowser += "  <div class=\"row\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-3 bg-primary\">\n";
			stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Problems Pinned</b></p>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-1 bg-primary\">\n";
			//START: remove selected problem
			stringToSendToWebBrowser += "<script>\n";
			stringToSendToWebBrowser += "      function confirmRemove() {\n";
			stringToSendToWebBrowser += "        var result = confirm(\"WARNING: problem "+selectedProblem.problemId+" will be removed from this list. Still wish to proceed?\");\n";
			stringToSendToWebBrowser += "        if (result) {\n";
			stringToSendToWebBrowser += "            window.location.href = \"http://localhost:8080/Updates.html/remove/Problem/"+selectedProblemId+"\";\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "      }\n";
			stringToSendToWebBrowser += "    </script>\n";
			stringToSendToWebBrowser += "    <button class=\"btn btn-primary\" onclick=\"confirmRemove()\">Remove</button>\n";
			//END: remove selected problem
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
			stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Details</b></p>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
			stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Map</b></p>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <!--ROW 3-->\n";
			stringToSendToWebBrowser += "  <div class=\"row\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
			//Display all problems
			for(Problem eachPro: currentUser.pinnedProblems) {
				//Change colour of background if selected
				if (eachPro.problemId.equals(selectedProblemId)) {
					stringToSendToWebBrowser += "      <ul class=\"media-list bg-secondary\">\n";
					stringToSendToWebBrowser += "        <li class=\"media\">\n";
					stringToSendToWebBrowser += "          <a class=\"pull-left\" href=\"#\">\n";
					stringToSendToWebBrowser += "            <img class=\"media-object\" src=\"http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png\" height=\"64\" width=\"64\"> </a>\n";
					stringToSendToWebBrowser += "          <div class=\"media-body\">\n";
					stringToSendToWebBrowser += "            	<h4 class=\"text-info\"><b>"+eachPro.problemChoice+"</b></h4>\n";
					stringToSendToWebBrowser += "            	<p class=\"text\">"+eachPro.location+"</p>\n";//add link to user email later
					stringToSendToWebBrowser += "          </div>\n";
					stringToSendToWebBrowser += "        </li>\n";
					stringToSendToWebBrowser += "      </ul>\n";
				} else {
					stringToSendToWebBrowser += "      <ul class=\"media-list bg-primary\">\n";
					stringToSendToWebBrowser += "        <li class=\"media\">\n";
					stringToSendToWebBrowser += "          <a class=\"pull-left\" href=\"#\">\n";
					stringToSendToWebBrowser += "            <img class=\"media-object\" src=\"http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png\" height=\"64\" width=\"64\"> </a>\n";
					stringToSendToWebBrowser += "          <div class=\"media-body\">\n";
					//Add a link for each problem
					stringToSendToWebBrowser += "			<a href=\"http://localhost:8080/Updates.html/PinnedProblem/"+eachPro.problemId+"\">\n";//Link to unique page for each problem
					stringToSendToWebBrowser += "            	<h4 class=\"media-heading\"><b>"+eachPro.problemChoice+"</b></h4>\n";
					stringToSendToWebBrowser += "            </a>\n";
					stringToSendToWebBrowser += "            <p class=\"text\">"+eachPro.location+"</p>\n";//add link to user email later
					stringToSendToWebBrowser += "          </div>\n";
					stringToSendToWebBrowser += "        </li>\n";
					stringToSendToWebBrowser += "      </ul>\n";
				}//END if else
			}//END for
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
			//Display selected problem
			stringToSendToWebBrowser += "      <p class=\"text\"><b>Description:</b> "+selectedProblem.description+"\n";
			stringToSendToWebBrowser += "        <br><b>Action:</b> "+selectedProblem.action+"\n";
			stringToSendToWebBrowser += "        <br><b>Choice/Posted by:</b> "+selectedProblem.postedBy+"</p>\n";
			stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Photos</b></p>\n";
			stringToSendToWebBrowser += "      <div class=\"carousel slide\" data-ride=\"carousel\">\n";
			stringToSendToWebBrowser += "        <div class=\"carousel-inner\" role=\"listbox\">\n";
			stringToSendToWebBrowser += "          <div class=\"carousel-item\">\n";
			stringToSendToWebBrowser += "            <img class=\"d-block img-fluid\" src=\"https://pingendo.com/assets/photos/wireframe/photo-1.jpg\" data-holder-rendered=\"true\">\n";
			stringToSendToWebBrowser += "            <div class=\"carousel-caption\">\n";
			stringToSendToWebBrowser += "              <h3>Add Photos</h3>\n";
			stringToSendToWebBrowser += "              <p>Images will appear here</p>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <div class=\"carousel-item active\">\n";
			stringToSendToWebBrowser += "            <img class=\"d-block img-fluid\" src=\"https://pingendo.com/assets/photos/wireframe/photo-1.jpg\" data-holder-rendered=\"true\">\n";
			stringToSendToWebBrowser += "            <div class=\"carousel-caption\">\n";
			stringToSendToWebBrowser += "              <h3>Second slide label</h3>\n";
			stringToSendToWebBrowser += "              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <a class=\"carousel-control-prev\" href=\"#carouselExampleCaptions\" role=\"button\" data-slide=\"prev\"> <span class=\"carousel-control-prev-icon\" aria-hidden=\"true\"></span> <span class=\"sr-only\">Previous</span> </a>\n";
			stringToSendToWebBrowser += "        <a class=\"carousel-control-next\" href=\"#carouselExampleCaptions\" role=\"button\" data-slide=\"next\"> <span class=\"carousel-control-next-icon\" aria-hidden=\"true\"></span> <span class=\"sr-only\">Next</span> </a>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
			stringToSendToWebBrowser += "      <iframe src=https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2312.258051154441!2d-5.93965388411461!3d54.581826980256565!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x486108ee869f9ea9%3A0xa9f41c9563d671de!2sComputer+Science+Building!5e0!3m2!1sen!2suk!4v1521234674033\n";
			stringToSendToWebBrowser += "        width=\"340\" height=\"400\" frameborder=\"0\" style=\"border:0\" allowfullscreen=\"\" class=\"\"></iframe>\n";
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
	
	//METHOD: to display the default updates page as we need to load is twice
	public String ViewUpdatesNone() {
		//Get the current user
		MVMap<String, User> userMV = db.s.openMap("User");
		List<String> userKey = userMV.keyList();
		User currentUser = userMV.get(userKey.get(0));
		//Declare a boolean variables to make user code easier to read
		boolean loggedIn = false;
		if(userMV.keyList()!=null && !userMV.keyList().isEmpty()) {loggedIn = true;}else{loggedIn = false;};
		
		String stringToSendToWebBrowser = "";
		stringToSendToWebBrowser += "\n";
		stringToSendToWebBrowser += "<!DOCTYPE html>\n";
		stringToSendToWebBrowser += "<html>\n";
		stringToSendToWebBrowser += "\n";
		stringToSendToWebBrowser += "<head>\n";
		stringToSendToWebBrowser += "  <meta charset=\"utf-8\">\n";
		stringToSendToWebBrowser += "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
		stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\" type=\"text/css\">\n";
		stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"Project_CSS.css\" type=\"text/css\"> </head>\n";
		stringToSendToWebBrowser += "\n";
		stringToSendToWebBrowser += "<body>\n";
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
		stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/CommunityForums.html\">Discussion</a>\n";
		stringToSendToWebBrowser += "          </li>\n";
		stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
		stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-light text-info active\" href=\"http://localhost:8080/Updates.html\">Updates</a>\n";
		stringToSendToWebBrowser += "          </li>\n";
		stringToSendToWebBrowser += "          <li class=\"nav-item\">\n";
		stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/FAQ.html\">FAQ</a>\n";
		stringToSendToWebBrowser += "          </li>\n";
		stringToSendToWebBrowser += "          <li class=\"nav-item bg-primary\">\n";
		stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"http://localhost:8080/Contact.html\">Contact</a>\n";
		stringToSendToWebBrowser += "          </li>\n";
		//START: user code
		if (loggedIn) {stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-secondary \" href=\"http://localhost:8080/SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i>"+currentUser.username+"</a>\n";
		} else {stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-secondary \" href=\"http://localhost:8080/SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i> Sign in</a>\n";}
		//END: user code
		stringToSendToWebBrowser += "        </ul>\n";
		stringToSendToWebBrowser += "      </div>\n";
		stringToSendToWebBrowser += "    </div>\n";
		stringToSendToWebBrowser += "  </nav>\n";
		
		stringToSendToWebBrowser += "  <div class=\"py-5 bg-secondary\">\n";
		stringToSendToWebBrowser += "    <div class=\"container\">\n";
		stringToSendToWebBrowser += "      <!--ROW 1-->\n";
		stringToSendToWebBrowser += "      <div class=\"row border border-secondary\">\n";
		stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"><h1 class=\"text-dark\"><b>Problem Updates</b></h1>\n";
		stringToSendToWebBrowser += "  </div>\n";
		stringToSendToWebBrowser += "  <div class=\"col-md-4 bg-primary\"></div>\n";
		stringToSendToWebBrowser += "  <div class=\"col-md-4 bg-primary\"></div>\n";
		stringToSendToWebBrowser += "  </div>\n";
		stringToSendToWebBrowser += "  <!--ROW 2-->\n";
		stringToSendToWebBrowser += "  <div class=\"row\">\n";
		stringToSendToWebBrowser += "    <div class=\"col-md-2 bg-primary\">\n";
		stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Problems Pinned</b></p>\n";
		stringToSendToWebBrowser += "    </div>\n";
		stringToSendToWebBrowser += "    <div class=\"col-md-2 bg-primary\">\n";
		stringToSendToWebBrowser += "    </div>\n";
		stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
		stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Details</b></p>\n";
		stringToSendToWebBrowser += "    </div>\n";
		stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
		stringToSendToWebBrowser += "      <p class=\"lead text-info\"><b>Map</b></p>\n";
		stringToSendToWebBrowser += "    </div>\n";
		stringToSendToWebBrowser += "  </div>\n";
		stringToSendToWebBrowser += "  <!--ROW 3-->\n";
		stringToSendToWebBrowser += "  <div class=\"row\">\n";
		stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
		stringToSendToWebBrowser += "      <h4 class=\"text-dark\">It seems you haven't pinned any problems yet</h4>\n";
		stringToSendToWebBrowser += "    </div>\n";
		stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
		stringToSendToWebBrowser += "      <h5 class=\"text-dark\">Pinning problems allows you to see updates to problems that you care about</h5>\n";
		stringToSendToWebBrowser += "    </div>\n";
		stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-primary\">\n";
		stringToSendToWebBrowser += "      <a href=\"SearchProblems.html\">\n";
		stringToSendToWebBrowser += "        <h5 class=\"text-dark\">Click here to start finding problems that interest you</h5>\n";
		stringToSendToWebBrowser += "      </a>\n";
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
		
		return stringToSendToWebBrowser;
	}
}
