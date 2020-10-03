import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import java.sql.* ;

public class Login extends HttpServlet {

   public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
   {
	PrintWriter output;
	
	String username = "";
	String password = "";
	if(request.getParameter("username") != null){
		username = request.getParameter("username"); 
	}
	if(request.getParameter("password") != null){
		password = request.getParameter("password");
	}

	String connectionURL = "jdbc:mysql://localhost:3306/mydatabase";
	Connection connection = null;
	Statement statement  = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	
		connection = DriverManager.getConnection(connectionURL,"test","test");
		statement = connection.createStatement();

		String sqlSelect1 = "SELECT * FROM users_table WHERE username =\"" + username  + "\" ;";	
		rs1 = statement.executeQuery(sqlSelect1);
		
		if(rs1 == null || !rs1.next()) {
			rs1.close();
			//new user page
			response.setContentType ("text/html");
			output = response.getWriter();
			output.println("<!Doctype HTML>");
			output.println("<html>");
			output.println("<head></head>");
			output.println("<body>");
			output.println("<h1 align='center'>New User</h1>");
  			output.println("<form action='register' method='POST' style='position:relative; top:50px; width:300px; margin:auto;'>");
			output.println("<fieldset>");
			output.println("<label for='username'>Username:</label><br>");
			output.println("<input type='text' id='username' name='username' value="+username+">");
			output.println("<br>");
			output.println("<label for='fullname'>Full Name:</label><br>");
			output.println("<input type='text' id='fullname' name='fullname'>");
			output.println("<br>");
			output.println("<label for='birthdate'>Birthdate:</label><br>");
			output.println("<input type='date' id='birthdate' name='birthdate'>");
			output.println("<br>");
			output.println("<label for='password'>Password:</label><br>");
			output.println("<input type='password' id='password' name='password'>");
 			output.println("<br><br>");
 			output.println("<input type='submit' value='Submit'>");
			output.println("</fieldset>");
			output.println("</form>");
			output.println("</body>");
			output.println("</html>");
			output.close();
		}
		else {	//user exists
			String dbPassword = rs1.getString(2);
			rs1.close();
			if(dbPassword.equals(password)){
				//save username and data at session
				HttpSession session = request.getSession();
				session.setAttribute("username",username);
				
				String sqlSelect2 = "SELECT * FROM data_table WHERE username =\"" + username  + "\" ;";	
				rs2 = statement.executeQuery(sqlSelect2);
				String fullname = "";
				String birthdate = "";
				if(rs2 == null || !rs2.next()){System.exit(1);}
				else{
					fullname = rs2.getString(2);
					birthdate = rs2.getString(3);
				}
				rs2.close();
				
				session.setAttribute("fullname",fullname);
				session.setAttribute("birthdate",birthdate);
				session.setAttribute("loginerror",false);				

				response.sendRedirect("home");
			}
			else{	//wrong password
				response.sendRedirect("login.html");
			}
		}
	}
	catch(SQLException ex){ex.printStackTrace(); System.exit(1);}
	catch(Exception ex){ex.printStackTrace(); System.exit(2);}

  }
}
