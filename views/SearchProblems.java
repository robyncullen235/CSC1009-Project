package views;

import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;


import java.util.List;

import org.h2.mvstore.MVMap;


import model.Problem;
import model.User;

public class SearchProblems extends DynamicWebPage
{

	public SearchProblems(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{	
			
	if(toProcess.path.startsWith("SearchProblem/pin/")) {
		String problemId = toProcess.path.substring("SearchProblem/pin/".length());			
		MVMap<String, Problem> problems = db.s.openMap("Problems");			
		Problem problem = problems.get(problemId);//get the problem that we want from the map using the id to retrieve it			
					
		MVMap<String, User> userMap = db.s.openMap("User");//open the user map			
		User user = userMap.get(userMap.firstKey());//this gets the user with the first key in the map(of which there should only be one)			
		user.pinnedProblems.add(problem);			
					
		MVMap<String, User> users = db.s.openMap("Users");//open the users map(different from the user map)			
		users.put(user.userId, user);			
		db.commit();			
		userMap.put(user.userId, user);//Update the user database as well			
		db.commit();//not sure if you need this twice?			
					
		//Redirect to the new forum that has just been created			
		String stringToSendToWebBrowser = "";			
		stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/SearchProblems.html\">";			
		//stringToSendToWebBrowser += "<p> <a href=\"Updates.html/PinnedProblem/"+firstProblem.problemId+"\">Redirect</a></p>";//For older browsers			
		toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );			
		return true;			
				
				
			}
			
			//Declare a boolean variable to make user code easier
			MVMap<String, User> userMap = db.s.openMap("User");
			boolean loggedIn = false;
			if(userMap.keyList() != null && !userMap.keyList().isEmpty()) {
				//If the map is not null and empty, then a user is logged in
				loggedIn = true;
			} else {
				//If not then there must be a user logged in
				loggedIn = false;
	
			
			} if(toProcess.path.equalsIgnoreCase("SearchProblems.html")) {
				MVMap<String, Problem> problems = db.s.openMap("Problems");
				List<String> problemKeys = problems.keyList();
				
				if (problemKeys.size() == 0) {
					Problem newProblem = new Problem();
					newProblem.problemId = "A001";
					newProblem.location = "Carrduff";
					newProblem.problemChoice = "Dangerous pot holes";
					newProblem.description = "Very dangerous pot holes around the carryduff area and is very hard to avoid";
					
					problems.put(newProblem.problemId, newProblem);
					db.commit();
					problemKeys = problems.keyList();
				}
		
			
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<!DOCTYPE html>\n";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "<head>\n";
			stringToSendToWebBrowser += "  <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"temp.css\" type=\"text/css\"> </head>\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n";
			stringToSendToWebBrowser += "  <style>\n";
// PIN BUTTON CSS
			stringToSendToWebBrowser += "	  button {\n";
	      	stringToSendToWebBrowser += "      display: inline-block;\n";
	      	stringToSendToWebBrowser += "  border-radius: 4px;\n";
	      	stringToSendToWebBrowser += "  background-color: #ffffff;\n";
	      	stringToSendToWebBrowser += "    border: none;\n";
	      	stringToSendToWebBrowser += "    color: #ffffff;\n";
	      	stringToSendToWebBrowser += "    height: 30px;\n";
	      	stringToSendToWebBrowser += "    padding: 0;\n";
	      	stringToSendToWebBrowser += "  margin: 0;\n";
	      	stringToSendToWebBrowser += "   vertical-align: top;\n";
	      	stringToSendToWebBrowser += "    width: 30px;\n";
	      	stringToSendToWebBrowser += "   }\n";

	      	stringToSendToWebBrowser += "  #close-CSS {\n";
	      	stringToSendToWebBrowser += "    background-image: url('pin.png');\n";
	      	stringToSendToWebBrowser += "    background-size: 30px 30px;\n";
	      	stringToSendToWebBrowser += "    height: 30px;\n";
	      	stringToSendToWebBrowser += "    width: 35px;\n";
	      	stringToSendToWebBrowser += "   }\n";
			// END
			stringToSendToWebBrowser += "    .button {\n";
			stringToSendToWebBrowser += "      display: inline-block;\n";
			stringToSendToWebBrowser += "      border-radius: 4px;\n";
			stringToSendToWebBrowser += "      background-color: #e6e6e6;\n";
			stringToSendToWebBrowser += "      border: none;\n";
			stringToSendToWebBrowser += "      color: #000000;\n";
			stringToSendToWebBrowser += "      text-align: left;\n";
			stringToSendToWebBrowser += "      font-size: 20px;\n";
			stringToSendToWebBrowser += "      padding: 20px;\n";
			stringToSendToWebBrowser += "      width: 200px;\n";
			stringToSendToWebBrowser += "      transition: all 0.5s;\n";
			stringToSendToWebBrowser += "      cursor: pointer;\n";
			stringToSendToWebBrowser += "      margin: 5px;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    .button span {\n";
			stringToSendToWebBrowser += "      cursor: pointer;\n";
			stringToSendToWebBrowser += "      display: inline-block;\n";
			stringToSendToWebBrowser += "      position: relative;\n";
			stringToSendToWebBrowser += "      transition: 0.5s;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    .button span:after {\n";
			stringToSendToWebBrowser += "      content: '\\00bb';\n";
			stringToSendToWebBrowser += "      position: absolute;\n";
			stringToSendToWebBrowser += "      opacity: 0;\n";
			stringToSendToWebBrowser += "      top: 0;\n";
			stringToSendToWebBrowser += "      right: -20px;\n";
			stringToSendToWebBrowser += "      transition: 0.5s;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    .button:hover span {\n";
			stringToSendToWebBrowser += "      padding-right: 25px;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    .button:hover span:after {\n";
			stringToSendToWebBrowser += "      opacity: 1;\n";
			stringToSendToWebBrowser += "      right: 0;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    * {\n";
			stringToSendToWebBrowser += "      box-sizing: border-box;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    #myInput {\n";
			stringToSendToWebBrowser += "      background-image: url('search.jpg');\n";
			stringToSendToWebBrowser += "      background-position: 10px 10px;\n";
			stringToSendToWebBrowser += "      background-repeat: no-repeat;\n";
			stringToSendToWebBrowser += "      width: 100%;\n";
			stringToSendToWebBrowser += "      font-size: 16px;\n";
			stringToSendToWebBrowser += "      padding: 12px 20px 12px 40px;\n";
			stringToSendToWebBrowser += "      border: 1px solid #ddd;\n";
			stringToSendToWebBrowser += "      margin-bottom: 12px;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    #myTable {\n";
			stringToSendToWebBrowser += "      border-collapse: collapse;\n";
			stringToSendToWebBrowser += "      width: 100%;\n";
			stringToSendToWebBrowser += "      border: 1px solid #ddd;\n";
			stringToSendToWebBrowser += "      font-size: 18px;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    #myTable th,\n";
			stringToSendToWebBrowser += "    #myTable td {\n";
			stringToSendToWebBrowser += "      text-align: left;\n";
			stringToSendToWebBrowser += "      padding: 12px;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    #myTable tr {\n";
			stringToSendToWebBrowser += "      border-bottom: 1px solid #ddd;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "    #myTable tr.header,\n";
			stringToSendToWebBrowser += "    #myTable tr:hover {\n";
			stringToSendToWebBrowser += "      background-color: #f1f1f1;\n";
			stringToSendToWebBrowser += "    }\n";
			stringToSendToWebBrowser += "  </style>\n";
			stringToSendToWebBrowser += "</head>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "<body class=\"\">\n";
			stringToSendToWebBrowser += "  <nav class=\"navbar navbar-expand-md navbar-light border-dark bg-primary d-flex flex-row justify-content-end align-items-end text-center\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\"> <span class=\"navbar-toggler-icon\"></span> </button>\n";
			stringToSendToWebBrowser += "      <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <img class=\"img-fluid d-block pi-draggable float-left\" src=\"logo1.png\" height=\"10\" width=\"30\">\n";
			stringToSendToWebBrowser += "            <a class=\"navbar-brand p-1 mx-3\" href=\"#\">  Street Debugger</a>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <ul class=\"navbar-nav ml-auto\">\n";
			stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-light text-dark\" href=\"Index.html\">Home</a>\n";
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
			stringToSendToWebBrowser += "            <a class=\"nav-link text-info active\" href=\"Contact.html\">Contact</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			
			
			// USER CODE FOR SIGN IN
			if(loggedIn) {
				//If the map is not null and empty, then a user is logged in
				
				List<String> userKey = userMap.keyList();
				//Get the logged in user
				
				User user = userMap.get(userKey.get(0));
				//Display their name
				
				stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-primary text-secondary\" href=\"SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i> "+user.username+"</a>\n";
			} else {
				//If there is no user logged in then display the sign in button as before
				
				stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-primary text-secondary\" href=\"SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i> Sign in</a>\n";
			}
			// END: user code
		
			
	
			stringToSendToWebBrowser += "        </ul>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </nav>\n";
			stringToSendToWebBrowser += "  <div class=\"row\">\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-secondary\"></div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-secondary\">\n";
			stringToSendToWebBrowser += "      <p> </p>\n";
			
			//SEARCH CODE 
			stringToSendToWebBrowser += "      <h3 class=\"text-primary\">Search for reported problems</h3>\n";
			stringToSendToWebBrowser += "      <input type=\"text\" id=\"myInput\" onkeyup=\"myFunction()\" placeholder=\"Enter your local Street name...\" title=\"Type in a name\">\n";
			stringToSendToWebBrowser += "      <p> </p>\n";
			
			
			// SEARCH TABLE JAVASCRIPT 
			stringToSendToWebBrowser += "      <script>\n";
			stringToSendToWebBrowser += "        function myFunction() {\n";
			stringToSendToWebBrowser += "          var input, filter, table, tr, td, i;\n";
			stringToSendToWebBrowser += "          input = document.getElementById(\"myInput\");\n";
			stringToSendToWebBrowser += "          filter = input.value.toUpperCase();\n";
			stringToSendToWebBrowser += "          table = document.getElementById(\"myTable\");\n";
			stringToSendToWebBrowser += "          tr = table.getElementsByTagName(\"tr\");\n";
			stringToSendToWebBrowser += "          for (i = 0; i < tr.length; i++) {\n";
			stringToSendToWebBrowser += "            td = tr[i].getElementsByTagName(\"td\")[0];\n";
			stringToSendToWebBrowser += "            if (td) {\n";
			stringToSendToWebBrowser += "              if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {\n";
			stringToSendToWebBrowser += "                tr[i].style.display = \"\";\n";
			stringToSendToWebBrowser += "              } else {\n";
			stringToSendToWebBrowser += "                tr[i].style.display = \"none\";\n";
			stringToSendToWebBrowser += "              }\n";
			stringToSendToWebBrowser += "            }       \n";
			stringToSendToWebBrowser += "          }\n";
			stringToSendToWebBrowser += "        }\n";
			stringToSendToWebBrowser += "      </script>\n";
			// END 
			
			
			
			
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-4 bg-secondary\"></div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <p> </p>\n";
			stringToSendToWebBrowser += "  <div class=\"row m-0 px-0\">\n";
			stringToSendToWebBrowser += "    <div class=\"py-0 mx-3 col-md-4 bg-primary\">\n";
			stringToSendToWebBrowser += "      <p> </p>\n";
			stringToSendToWebBrowser += "      <h2 class=\"text-primary p-3 bg-success\"> Reported Problems</h2>\n";
			
			// DROP DOWN BOXES 
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-6 col-md-12 bg-primary\">\n";
	
			stringToSendToWebBrowser += "      <div class=\"card\">\n";
			stringToSendToWebBrowser += "      <div class=\"card-body\">\n";
			stringToSendToWebBrowser += "       <div class=\"card\">\n";
			stringToSendToWebBrowser += "          <div class=\"card-body\">\n";
			stringToSendToWebBrowser += "            <p class=\"report-list-filters\"> <label for=\"sort\">Sort by</label> <select class=\"form-control w-50 form-control-sm\" name=\"sort\" id=\"sort\">\n";
		              
            stringToSendToWebBrowser += "         <option value=\"created-desc\" selected=\"\">Newest</option>\n";
            	stringToSendToWebBrowser += "       <option value=\"updated-desc\">Recently updated</option>\n";
			stringToSendToWebBrowser += "     <option value=\"updated-asc\">Oldest</option>\n";
			stringToSendToWebBrowser += "     </select> </p>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "       </div>\n";
			
			// TABLE TO DISPLAY PROBLEMS 
			
			stringToSendToWebBrowser += "              <table id=\"myTable\">\n";
			stringToSendToWebBrowser += "                <tbody>\n";
			stringToSendToWebBrowser += "                  <tr class=\"header\">\n";
			stringToSendToWebBrowser += "                    <th style=\"width:100%;\" class=\"\"> </th>\n";
			stringToSendToWebBrowser += "                  </tr>\n";
			
			
			// PRINT PROBLEMS 
			
			
			
						for(int i=0; i<problemKeys.size(); i++) {	
							String currentProblemId = problemKeys.get(i);
							Problem currentProblem = problems.get(currentProblemId); 
			
			
			stringToSendToWebBrowser += "                  <tr>\n";
			
			stringToSendToWebBrowser += "    		<td> <a href=\"ViewUpdates.html/"+currentProblem.problemId+"\">\n";
			
			stringToSendToWebBrowser += "                      <h5> "+currentProblem.problemChoice+" </h5>\n";
			stringToSendToWebBrowser += "                      <h6 class=\"text-muted\">at "+currentProblem.location+" </h6>\n";
			
			
			
			// Pin problem to updates page button code 
			if(loggedIn) {stringToSendToWebBrowser += "        <a href=\"/SearchProblem/pin/"+currentProblem.problemId+"\"><button class=\"bg-white\" id=\"close-CSS\"></button></a>\n";};				
			
			stringToSendToWebBrowser += "                    </td>\n";
			stringToSendToWebBrowser += "                  </tr>\n";
		// end of table and search 
						}
			
	
			stringToSendToWebBrowser += "              </table>\n";
						
			
			stringToSendToWebBrowser += "            </div>\n";
						
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "      <div class=\"card\"> </div>\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-6\">\n";
			stringToSendToWebBrowser += "          <div class=\"row\">\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-6\"></div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-6\"></div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-6\">\n";
			
			
			
			// GET UPDATES BUTTON (SPAN)
			{stringToSendToWebBrowser += "          <a href=\"/Updates.html\"> <button class=\"button d-flex flex-row-reverse\" style=\"vertical-align:middle\"><span>Get Updates </span></button></a>\n";};
			
	
			
			
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"col-md-6\">\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-12 p-0 m-0\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2312.258051154445!2d-5.939653884145173!3d54.581826980256494!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x486108ee869f9ea9%3A0xa9f41c9563d671de!2sComputer+Science+Building!5e0!3m2!1sen!2suk!4v1518377983243\"\n";
			stringToSendToWebBrowser += "              width=\"880\" height=\"650\" frameborder=\"0\" allowfullscreen=\"\" style=\"border:0\"></iframe>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <p> </p>\n";
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
			stringToSendToWebBrowser += "            <a href=\"mailto:info@pingendo.com\" class=\"text-dark\"><i class=\"fa d-inline mr-3 text-secondary fa-envelope-o\"></i>streetdebugger12@gmail.com</a>\n";
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