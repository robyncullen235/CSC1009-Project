package views;

import java.util.ArrayList;
import java.util.List;

import org.h2.mvstore.MVMap;

import model.Question;
import model.User;
import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class FAQ extends DynamicWebPage
{

	public FAQ(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		if(toProcess.path.equalsIgnoreCase("FAQ.html"))
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
			MVMap<String, Question> faqs = db.s.openMap("FAQs");
			List<String> faqKeys = faqs.keyList();

			//Create default question objects
			if (faqKeys.size()==0) {
				Question question1 = new Question();
				question1.questionId = "Q0001";
				question1.question = "What does this web application do?";
				question1.answer = "Street  Debuggger allows users to report problems about noise, the roads, pollution and other categories. From this it allows councils, businesses and the public to view these problems so they may fix them, avoid them or build a business in a certain area, through updates and searching for problems in their area.";
				
				Question question2 = new Question();
				question2.questionId = "Q0002";
				question2.question = "Is there a way to track problems that I have submitted?";
				question2.answer = "Yes, once you are logged in after submitting a problem, in the Updates page all the poblems that you have submitted will be there deascribing what the problem is, where it is and action taken";
				
				ArrayList<Question> allQs = new ArrayList<Question>();
				allQs.add(question1);
				allQs.add(question2);
				
				
				
				//Add default questions to the MVMap
				for (int i = 0; i < allQs.size(); i++) {
					faqs.put(allQs.get(i).questionId, allQs.get(i));
				}
				db.commit();
				
				//Update the keylist
				faqKeys = faqs.keyList();
			}	
			
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
			stringToSendToWebBrowser += "            <a class=\"nav-link text-info active\" href=\"FAQ.html\">FAQ</a>\n";
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
			stringToSendToWebBrowser += "  </b>\n";
			
			stringToSendToWebBrowser += "  <!--Row 1-->\n";
			
			stringToSendToWebBrowser += "  <div class=\"py-5 bg-secondary\"><b class=\"text-dark\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "          <div class=\"card text-white p-5 bg-primary\">\n";
			stringToSendToWebBrowser += "            <div class=\"card-body\">\n";
			stringToSendToWebBrowser += "              <h1 class=\"text-dark\"><b>Frequently Asked Questions (FAQ)</b></h1>\n";
			stringToSendToWebBrowser += "    <br>\n";
			//Print all questions
			
			stringToSendToWebBrowser += "    <div class=\"row\">\n";
			stringToSendToWebBrowser += "      <div class=\"col-md-12\">\n";
			
			for(int i=0; i<faqKeys.size(); i++) {	
				String currentQuestionID = faqKeys.get(i);
				Question currentQuestion = faqs.get(currentQuestionID);
			
				
			stringToSendToWebBrowser += "        <ul class=\"media-list\">\n";
			stringToSendToWebBrowser += "          <li class=\"media\">\n";
			stringToSendToWebBrowser += "            <div class=\"media-body\">\n";
			stringToSendToWebBrowser += "              <h4 class=\"media-heading text-dark\"><b>"+currentQuestion.question+"</b></h4>\n";
			stringToSendToWebBrowser += "              <p class=\"text-dark\">"+currentQuestion.answer+"</p>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "        </ul>\n";
			
				
			}//END for 
			
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";	
			
			
			//form
			
			stringToSendToWebBrowser += "    <form action=\"FAQ.html/submit/Questions\" method=\"GET\">\n";
			stringToSendToWebBrowser += "      <div class=\"form-group\"> <label class=\"lead text-info\"><b>If you cannot find an answer to your problems, please send us a question so we may help with your problem</b></label>\n";
			stringToSendToWebBrowser += "        <input type=\"text\" class=\"form-control\" name=\"inputQuestion\" placeholder=\"Enter Question\"> </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "    <div>\n";
			stringToSendToWebBrowser += "  		  <button type=\"submit\" class=\"text-white btn-secondary mr-auto\">Submit</button>\n";
			stringToSendToWebBrowser += "          <a class=\"btn text-white btn-secondary mr-auto\" id=\"answer\" role=\"button\" href=\"AnswerQuestion.html\">Answer Questions</a>\n";
			
			List<String> userKey = userMap.keyList();
			User user = userMap.get(userKey.get(0));		
			if (!loggedIn || (loggedIn && user.employee == false) || userMap.keyList() == null) {
					stringToSendToWebBrowser += "      <script>\n";
					stringToSendToWebBrowser += "          var x = document.getElementById(\"answer\");\n";
					stringToSendToWebBrowser += "              if (x.style.display === \"none\") {\n";
					stringToSendToWebBrowser += "                x.style.display = \"block\";\n";
					stringToSendToWebBrowser += "              } else {\n";
					stringToSendToWebBrowser += "                x.style.display = \"none\";\n";
					stringToSendToWebBrowser += "        }\n";
					stringToSendToWebBrowser += "      </script>\n";
	
			}
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "    </form>\n";
			
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  </b>\n";
			stringToSendToWebBrowser += "  </div><b class=\"text-dark\">\n";
			
			
			
			stringToSendToWebBrowser += "  <!--Footer-->\n";
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
		} else if(toProcess.path.equalsIgnoreCase("FAQ.html/submit/Questions")) {
			Question newQuestion = new Question();
			newQuestion.questionId = "question_"+System.currentTimeMillis();
			newQuestion.question = toProcess.params.get("inputQuestion");//get value in the labels
			
			
			//Add to the database rather than an arraylist
			//Add new forum to the MVMap
			MVMap<String, Question> questions = db.s.openMap("Questions");
			List<String> qKeys = questions.keyList();
			questions.put(newQuestion.questionId, newQuestion);
			db.commit();
			
			
			
			//Refresh the page to allow a new problem to be reported
			String url = "http://localhost:8080/FAQ.html";
			
			toProcess.r = new WebResponse( WebResponse.HTTP_REDIRECT, WebResponse.MIME_HTML, "<head><body>Redirected: <a href=\"" + url + "\">" +
			url + "</a></body></head>");
			
			toProcess.r.addHeader("Location", url);
			return true;
		}
		return false;
	}

}
