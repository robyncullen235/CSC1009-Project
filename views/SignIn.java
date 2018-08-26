package views;

import java.util.ArrayList;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.Comment;
import model.Forum;
import model.Problem;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class SignIn extends DynamicWebPage
{

	public SignIn(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		if(toProcess.path.equalsIgnoreCase("SignIn.html/Logout")) {
			//Open users map
			MVMap<String, User> user = db.s.openMap("User");
			user.clear();System.out.println("User signed out");
			//Redirect to the home page
			String stringToSendToWebBrowser = "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/Index.html\"/>";System.out.println("Password was incorrect");
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
		} else if(toProcess.path.equalsIgnoreCase("SignIn.html/Login")) {
			//Get user input
			String userNameIn = toProcess.params.get("inputUsername");
			String passwordIn = toProcess.params.get("inputPassword");
			
			//Open users map
			MVMap<String, User> users = db.s.openMap("Users");
			
			//TEST CODE: make test user
				User u1 = new User();
				u1.username = "Bob";
				u1.emailAddress = "b@BOB";
				u1.password = "123";
				ArrayList<Problem> pinnedProblems = new ArrayList<Problem>();
				u1.pinnedProblems = pinnedProblems;
				u1.employee = true;
				users.put(u1.username, u1);
				db.commit();
				System.out.println("User made");
			
			
			String stringToSendToWebBrowser = "";
			
			//Check if user exists
			if (!users.containsKey(userNameIn)) {
				//Redirect to the signin page page
				stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/SignIn.html\"/>";
				toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );System.out.println("User does not exist");
				return true;
			} else {
				//Get the user
				User user = users.get(userNameIn);
				
				//Check if password is correct
				if (passwordIn.equals(user.password)) {
					//Save to the user map to sign them in
					MVMap<String, User> userMap = db.s.openMap("User");
					//Clear map in case another user is signed in
					userMap.clear();
					userMap.put(user.username, user);
					db.commit();System.out.println("User "+user.username+" logged in");
					
					//Redirect to the home page
					stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/Index.html\"/>";
					toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
					return true;
				} else {
					//Redirect to the home page if password is incorrect
					stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/SignIn.html\"/>";System.out.println("Password was incorrect");
					toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
				}//END if else
				
			}//END if else 

		} else if(toProcess.path.equalsIgnoreCase("SignIn.html/create/Account")) {
			//Create user object
			User newUser = new User();
			newUser.username = toProcess.params.get("inputUsername");
			newUser.emailAddress = toProcess.params.get("inputEmail");
			newUser.password = toProcess.params.get("inputPassword");
			ArrayList<Problem> pinnedProblems = new ArrayList<Problem>();
			newUser.pinnedProblems = pinnedProblems;
			
			//Save to the users map 
			MVMap<String, User> users = db.s.openMap("Users");
			users.put(newUser.username, newUser);
			db.commit();System.out.println("User "+newUser.username+" saved");
			//Save to the user map to sign them in
			MVMap<String, User> user = db.s.openMap("User");
			//Clear map in case another user is signed in
			user.clear();
			user.put(newUser.username, newUser);
			db.commit();System.out.println("User "+newUser.username+" logged in");
			
			//Redirect to the home page
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/Index.html/\"/>";
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );

			return true;
		} else if(toProcess.path.equalsIgnoreCase("SignIn.html")) {
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <head></head>\n";
			stringToSendToWebBrowser += "  \n";
			stringToSendToWebBrowser += "  <body>rna\n";
			stringToSendToWebBrowser += "    <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"\n";
			stringToSendToWebBrowser += "    type=\"text/css\">\n";
			stringToSendToWebBrowser += "    <link rel=\"stylesheet\" href=\"Project_CSS.css\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "    <div class=\"py-5\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <ul class=\"nav nav-pills\">\n";
			stringToSendToWebBrowser += "              <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "                <a href=\"Home.html\" class=\"active nav-link\">Home</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "                <a href=\"SignIn.html\" class=\"nav-link\">Sign In</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "                <a class=\"nav-link\" href=\"CommunityForums.html\">Community Forums</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "                <a class=\"nav-link\" href=\"SearchProblems.html\">Search Problem</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "                <a class=\"nav-link\" href=\"Contact.html\">Contact</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "              <li class=\"nav-item\">\n";
			stringToSendToWebBrowser += "                <a class=\"nav-link\" href=\"FAQ.html\">Help</a>\n";
			stringToSendToWebBrowser += "              </li>\n";
			stringToSendToWebBrowser += "            </ul>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"py-5\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <h1 class=\"display-3 text-center text-success\">Sign In</h1>\n";
			stringToSendToWebBrowser += "            <p class=\"\">Sign in to report a problem in your area</p>\n";
			//START: sign out button
			stringToSendToWebBrowser += "    		 <a href=\"/SignIn.html/Logout\"><button>Sign Out</button></a>\n";
			//END: sign out button
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"py-5\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			//START: log in code
			stringToSendToWebBrowser += "            <form class=\"\" role=\"form\" action=\"/SignIn.html/Login\" method=\"GET\">\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <label for=\"exampleInputSurname\" contenteditable=\"true\">Username</label>\n";
			stringToSendToWebBrowser += "                <input type=\"Username\" class=\"form-control\" id=\"exampleInputSurname\" name=\"inputUsername\"\n";
			stringToSendToWebBrowser += "                placeholder=\"Username\">\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <label for=\"exampleInputPassword1\">Password</label>\n";
			stringToSendToWebBrowser += "                <input type=\"password\" class=\"form-control\" id=\"exampleInputPassword1\" name=\"inputPassword\"\n";
			stringToSendToWebBrowser += "                placeholder=\"Password\">\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "          	<button type=\"submit\" class=\"btn btn-primary\">Log In</button>\n";
			stringToSendToWebBrowser += "            </form>\n";
			//END: log in code 
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"py-5\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <h1>If you are not already a member you can register here</h1>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "    <div class=\"py-5\">\n";
			stringToSendToWebBrowser += "      <div class=\"container\">\n";
			stringToSendToWebBrowser += "        <div class=\"row\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			//START: sign up code
			stringToSendToWebBrowser += "            <form class=\"\" role=\"form\" action=\"/SignIn.html/create/Account\" method=\"GET\">\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <label for=\"exampleInputSurname\" contenteditable=\"true\">Username</label>\n";
			stringToSendToWebBrowser += "                <input type=\"Username\" class=\"form-control\" id=\"exampleInputSurname\" name=\"inputUsername\"\n";
			stringToSendToWebBrowser += "                placeholder=\"Username\">\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <label for=\"exampleInputEmail1\">Email address</label>\n";
			stringToSendToWebBrowser += "                <input type=\"email\" class=\"form-control\" id=\"exampleInputEmail1\" name=\"inputEmail;\"\n";
			stringToSendToWebBrowser += "                aria-describedby=\"emailHelp\" placeholder=\"Enter email\">\n";
			stringToSendToWebBrowser += "                <small id=\"emailHelp\" class=\"form-text text-muted\">We'll never share your email with anyone else.</small>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group\">\n";
			stringToSendToWebBrowser += "                <label for=\"exampleInputPassword1\">Password</label>\n";
			stringToSendToWebBrowser += "                <input type=\"password\" class=\"form-control\" id=\"exampleInputPassword1\" name=\"inputPassword\"\n";
			stringToSendToWebBrowser += "                placeholder=\"Password\">\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "          	<button type=\"submit\" class=\"btn btn-primary\">Sign Up</button>\n";
			stringToSendToWebBrowser += "            </form>\n";
			//END: sign up code
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\"\n";
			stringToSendToWebBrowser += "        crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\"\n";
			stringToSendToWebBrowser += "        integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\"\n";
			stringToSendToWebBrowser += "        crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\"\n";
			stringToSendToWebBrowser += "        integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\"\n";
			stringToSendToWebBrowser += "        crossorigin=\"anonymous\"></script>\n";
			stringToSendToWebBrowser += "        <pingendo onclick=\"window.open('https://pingendo.com/', '_blank')\"\n";
			stringToSendToWebBrowser += "        style=\"cursor:pointer;position: fixed;bottom: 10px;right:10px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:180px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white\">Made with Pingendo&nbsp;&nbsp;\n";
			stringToSendToWebBrowser += "          <img src=\"https://pingendo.com/site-assets/Pingendo_logo_big.png\"\n";
			stringToSendToWebBrowser += "          class=\"d-block\" alt=\"Pingendo logo\" height=\"16\">\n";
			stringToSendToWebBrowser += "        </pingendo>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
		}
		return false;
	}

}