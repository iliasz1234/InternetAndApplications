import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Home extends HttpServlet {

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		String fullname = session.getAttribute("fullname").toString();
		String birthdate = session.getAttribute("birthdate").toString();

        	PrintWriter output;

		response.setContentType ("text/html");
		output = response.getWriter();
                output.println("<!Doctype HTML>");
                output.println("<html>");
                output.println("<head>");
                output.println("<style>");
                output.println(".links_container {");
                output.println("position:relative;");
                output.println("float:left;");
                output.println("top:100px;");
                output.println("width:150px;");
                output.println("height:250px;");
                output.println("}");
                output.println(".data_container {");
                output.println("position:relative;");
                output.println("top:100px;");
                output.println("width:400px;");
                output.println("height:250px;");
                output.println("margin:auto;");
                output.println("text-align:center;");
                output.println("border:1px solid black;");
                output.println("}");
                output.println("table {");
                output.println("width:100%;");
                output.println("height:100%;");
                output.println("border:1px solid black;");
                output.println("}");
                output.println("td {");
                output.println("border:1px solid black;");
                output.println("text-align:center;");
                output.println("}");
                output.println("</style>");
                output.println("</head>");
		output.println("<body>");
                output.println("<h1 align='center'>Home Page</h1>");
                output.println("<div class='links_container'>");
                output.println("<table>");
                output.println("<tr><td>");
                output.println("<a href='update'>Update</a>");
                output.println("</td></tr>");
                output.println("<tr><td>");
                output.println("<a href='products.html'>Products</a>");
                output.println("</td></tr>");
                output.println("<tr><td>");
                output.println("<a href='basket'>Basket</a>");
                output.println("</td></tr>");
                output.println("</table>");
                output.println("</div>");
                output.println("<div class='data_container'>");
                output.println("<p>Username:</p>");
                output.println("<p>"+username+"</p>");
                output.println("<p>Full Name:</p>");
                output.println("<p>"+fullname+"</p>");
                output.println("<p>Birthdate:</p>");
                output.println("<p>"+birthdate+"</p>");
                output.println("</div>");
                output.println("</body>");
                output.println("</html>");
                output.close ();
	}
}
