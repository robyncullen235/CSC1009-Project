package views;

import storage.DatabaseInterface;
import storage.FileStoreInterface;
import web.WebRequest;
import web.WebResponse;

public class Contact extends DynamicWebPage
{

	public Contact(DatabaseInterface db,FileStoreInterface fs)
	{
		super(db,fs);
	}

	public boolean process(WebRequest toProcess)
	{
		if(toProcess.path.equalsIgnoreCase("Contact.html"))
		{
			String stringToSendToWebBrowser = "";
			stringToSendToWebBrowser += "<!DOCTYPE html>\n";
			stringToSendToWebBrowser += "<html>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "<head>\n";
			stringToSendToWebBrowser += "  <meta charset=\"utf-8\">\n";
			stringToSendToWebBrowser += "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\" type=\"text/css\">\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"Project_CSS.css\" type=\"text/css\"> </head>\n";
			stringToSendToWebBrowser += "  <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n";
			stringToSendToWebBrowser += "  <script src=\"https://www.google.com/recaptcha/api.js\"></script>\n";
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
			stringToSendToWebBrowser += "            <a class=\"nav-link text-dark\" href=\"Help.html\">FAQ</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <li class=\"nav-item bg-primary\">\n";
			stringToSendToWebBrowser += "            <a class=\"nav-link text-info active\" href=\"Contact.html\">Contact</a>\n";
			stringToSendToWebBrowser += "          </li>\n";
			stringToSendToWebBrowser += "          <a class=\"btn navbar-btn ml-2 btn-primary text-secondary\" href=\"SignIn.html\"> <i class=\"fa d-inline fa-lg fa-user-circle-o\"></i> Sign in</a>\n";
			stringToSendToWebBrowser += "        </ul>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </nav>\n";
			stringToSendToWebBrowser += "  <div class=\"py-5 text-white bg-primary text-center w-100\" style=\"background-image: url('../contact-us-banner3.png');background-position:left center;\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <div class=\"row col-md-11\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-11\">\n";
			stringToSendToWebBrowser += "          <div class=\"row\">\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-10 m-0 p-0\">\n";
			stringToSendToWebBrowser += "              <div class=\"row m-0 p-0 col-md-5\">\n";
			stringToSendToWebBrowser += "                <div class=\"m-0 p-0 col-md-8\">\n";
			stringToSendToWebBrowser += "                  <h1 class=\"<&quot;text-dark text-dark\" m-0=\"\">Contact Us</h1>\n";
			stringToSendToWebBrowser += "                </div>\n";
			stringToSendToWebBrowser += "              </div>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "            <div class=\"m-0 p-0 col-md-7\">\n";
			stringToSendToWebBrowser += "              <p class=\"lead text-secondary w-100 d-flex\">&nbsp; You may be able to find the answer you want on our\n";
			stringToSendToWebBrowser += "                <a href=\"FAQ.html\">&nbsp;FAQ</a>.&nbsp;</p>\n";
			stringToSendToWebBrowser += "              <form class=\"form-inline m-0\">\n";
			stringToSendToWebBrowser += "                <input class=\"form-control mr-1 p-2 m-1 w-75\" type=\"text\" placeholder=\"Type in your question or keyword\">\n";
			stringToSendToWebBrowser += "                <button class=\"btn btn-success p-1\" type=\"submit\"><i class=\"material-icons\">search</i></button>\n";
			stringToSendToWebBrowser += "              </form>\n";
			stringToSendToWebBrowser += "              <p> </p>\n";
			stringToSendToWebBrowser += "            </div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <div class=\"py-5 bg-secondary\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "        <p> </p>\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "        <p> </p>\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "        <p> </p>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md- bg-light\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2312.258051154445!2d-5.939653884145173!3d54.581826980256494!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x486108ee869f9ea9%3A0xa9f41c9563d671de!2sComputer+Science+Building!5e0!3m2!1sen!2suk!4v1518377983243\"\n";
			stringToSendToWebBrowser += "              width=\"540\" height=\"710\" frameborder=\"0\" allowfullscreen=\"\" style=\"border:0\"></iframe>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-6 bg-primary\">\n";
			stringToSendToWebBrowser += "          <div class=\"col-md-12\">\n";
			stringToSendToWebBrowser += "            <h2 class=\"text-dark\">Get in touch with us</h2>\n";
			stringToSendToWebBrowser += "            <p class=\"text-success\"> To send us a message please fill out the fields below. </p>\n";
			
			
			
			
			// START of FORM 
			stringToSendToWebBrowser += "            <form action=\"https://formspree.io/streetdebugger12@gmail.com\" method=\"POST\">\n";
			
			
			// hidden subject 
			stringToSendToWebBrowser += "              <input type=\"hidden\" name=\"_subject\" value=\"New submission!\">\n";
			stringToSendToWebBrowser += "        <input type=\"hidden\" name=\"_next\" value=\"//localhost:8080/ThankYou.html\"/>\n";
			
			
			stringToSendToWebBrowser += "              <div class=\"form-group text-dark\"> <label for=\"InputName\" class=\"d-flex\"> <i class=\"material-icons\" style=\"font-size:25px;\">person </i>Your name </label>\n";
			stringToSendToWebBrowser += "                <input type=\"text\" id=\"InputName\" placeholder=\"John Smyth\" name=\"fullName\" class=\"form-control\"> </div>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group text-dark\"> <label for=\"InputEmail1\" class=\"d-flex\"> <i class=\"material-icons\" style=\"font-size:25px;\">email </i> Email Address *</label>\n";
			stringToSendToWebBrowser += "                <input type=\"email\" id=\"InputEmail1\" placeholder=\"streetdebugger12@gmail.com\" required=\"\" name=\"_replyto\" class=\"form-control\"> </div>\n";
			stringToSendToWebBrowser += "              <p> </p>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group text-dark\"> <label for=\"Number\" class=\"d-flex\"> <i class=\"material-icons\" style=\"font-size:25px;\">phone </i> Phone Number</label>\n";
			stringToSendToWebBrowser += "                <input type=\"text\" id=\"Number\" placeholder=\"07885262004\" name=\"phone_number\" class=\"form-control\"> </div> <label for=\"topic\" class=\"d-flex\"> Topic * </label> <select id=\"topic\" name=\"topic\" required=\"\">\n";
			stringToSendToWebBrowser += "     \n";
			stringToSendToWebBrowser += "    \n";
			stringToSendToWebBrowser += "              <option value=\"help\">I need help using the site</option>\n";
			stringToSendToWebBrowser += "      <option value=\"feedback\">Feedback about the site</option>\n";
			stringToSendToWebBrowser += "      <option value=\"notfixed\">My street problem hasn't been fixed</option>\n";
			stringToSendToWebBrowser += "      <option value=\"report\">I want to report a problem</option>\n";
			stringToSendToWebBrowser += "      <option value=\"other\">Other</option>\n";
			stringToSendToWebBrowser += "    </select>\n";
			stringToSendToWebBrowser += "              <p> </p>\n";
			stringToSendToWebBrowser += "              <div class=\"form-group text-dark\"> <label for=\"Textarea\" class=\"d-flex\"> <label for=\"Number\" class=\"d-flex\"> <i class=\"material-icons\" style=\"font-size:25px;\"><br></i> </label> Message *</label> <textarea class=\"form-control\" rows=\"6\" placeholder=\"Type here\" required=\"\" name=\"message\"\n";
			stringToSendToWebBrowser += "                  id=\"Textarea\"></textarea> </div>\n";
			stringToSendToWebBrowser += "              <p> </p>\n";
			stringToSendToWebBrowser += "              <div class=\"g-recaptcha\" data-sitekey=\"6Lf_SEoUAAAAAP5hKCNWYMxyPZ4eIMSXWAMS8Bok\"></div>\n";
			stringToSendToWebBrowser += "              <p> </p>\n";
			stringToSendToWebBrowser += "              <button type=\"submit\" class=\"btn btn-success\">Send message</button>\n";
			
			
			
			
			
			stringToSendToWebBrowser += "            </form>\n";
			// END of FORM
			
			
			
			
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "        <p> </p>\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "        <p> </p>\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 bg-primary\"></div>\n";
			stringToSendToWebBrowser += "        <p> </p>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
			stringToSendToWebBrowser += "  <div class=\"py-5 text-center text-white opaque-overlay bg-secondary\">\n";
			stringToSendToWebBrowser += "    <div class=\"container\">\n";
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"p-4 col-md-4\">\n";
			stringToSendToWebBrowser += "          <div class=\"row\">\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\"></div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\">\n";
			stringToSendToWebBrowser += "              <img class=\"img-fluid d-block\" src=\"PhoneIcon.png\" height=\"100\" width=\"100\"> </div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\"></div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <h2 class=\"my-3\">Phone</h2>\n";
			stringToSendToWebBrowser += "          <p>Call: 07885262004</p>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 p-4\">\n";
			stringToSendToWebBrowser += "          <div class=\"row\">\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\"></div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\">\n";
			stringToSendToWebBrowser += "              <img class=\"img-fluid d-block\" src=\"EmailIcon.png\" height=\"100\" width=\"100\"> </div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\"></div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <h2 class=\"my-3\">Email</h2>\n";
			stringToSendToWebBrowser += "          <p> Email:\n";
			stringToSendToWebBrowser += "            <a href=\"mailto:streetdebugger12@gmail.com\">streetdebugger12@gmail.com</a>\n";
			stringToSendToWebBrowser += "          </p>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-4 p-4\">\n";
			stringToSendToWebBrowser += "          <div class=\"row\">\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\"></div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\">\n";
			stringToSendToWebBrowser += "              <img class=\"img-fluid d-block\" src=\"LocationIcon.png\" height=\"100\" width=\"100\"> </div>\n";
			stringToSendToWebBrowser += "            <div class=\"col-md-4\"></div>\n";
			stringToSendToWebBrowser += "          </div>\n";
			stringToSendToWebBrowser += "          <h2 class=\"my-3\">Location</h2>\n";
			stringToSendToWebBrowser += "          <p> Address: 18 Malone Rd, Belfast BT9 6RT\n";
			stringToSendToWebBrowser += "            <br> </p>\n";
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </div>\n";
			stringToSendToWebBrowser += "  </div>\n";
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
			stringToSendToWebBrowser += "      <div class=\"row\">\n";
			stringToSendToWebBrowser += "        <div class=\"col-md-12 mt-3\">\n";
			
			stringToSendToWebBrowser += "        </div>\n";
			stringToSendToWebBrowser += "      </div>\n";
			stringToSendToWebBrowser += "    </b></b>\n";
			stringToSendToWebBrowser += "    </div><b><b>\n";
			stringToSendToWebBrowser += "  </b></b>\n";
			stringToSendToWebBrowser += "  </div>\n";
			
			//stringToSendToWebBrowser += "  <pingendo onclick=\"window.open('https://pingendo.com/', '_blank')\" style=\"cursor:pointer;position: fixed;bottom: 10px;right:10px;padding:4px;background-color: #00b0eb;border-radius: 8px; width:180px;display:flex;flex-direction:row;align-items:center;justify-content:center;font-size:14px;color:white\">Made with Pingendo&nbsp;&nbsp;\n";
			//stringToSendToWebBrowser += "    <img src=\"https://pingendo.com/site-assets/Pingendo_logo_big.png\" class=\"d-block\" alt=\"Pingendo logo\" height=\"16\">\n";
			//stringToSendToWebBrowser += "  </pingendo>\n";
			
			
			stringToSendToWebBrowser += "</body>\n";
			stringToSendToWebBrowser += "\n";
			stringToSendToWebBrowser += "</html>\n";
			toProcess.r = new WebResponse( WebResponse.HTTP_OK, WebResponse.MIME_HTML, stringToSendToWebBrowser );
			return true;
		}
		return false;
	}

}