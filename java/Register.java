import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import java.sql.* ;

public class Register extends HttpServlet {

   	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
   	{
		PrintWriter output;
	
		String username = "";
		String password = "";
		String fullname = "";
		String birthdate = "";
		if(request.getParameter("username") != null){
			username = request.getParameter("username"); 
		}
		if(request.getParameter("password") != null){
			password = request.getParameter("password");
		}
		if(request.getParameter("fullname") != null){
			fullname = request.getParameter("fullname");
		}
		if(request.getParameter("birthdate") != null){
			birthdate = request.getParameter("birthdate");
		}
		if(username.equals("") || password.equals("") || fullname.equals("") || birthdate.equals("")){
			response.sendRedirect("register");
		}
		
		String connectionURL = "jdbc:mysql://localhost:3306/mydatabase";
		Connection connection = null;
		Statement statement  = null;
		ResultSet rs = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL,"test","test");
			statement = connection.createStatement();
			String sqlSelect = "SELECT * FROM users_table WHERE username =\"" + username  + "\" ;";	
			rs = statement.executeQuery(sqlSelect);
	
			if(rs == null || !rs.next()) {
				//register user
				String sqlInsert1 = "INSERT INTO users_table (username, password) VALUES( \"" + username  + "\" ,\"" + password + "\") ;";
				String sqlInsert2 = "INSERT INTO data_table (username, fullname, birthdate) VALUES( \"" + username  + "\" ,\"" + fullname + "\" ,\"" + birthdate + "\") ;";

				int updateResult1 = statement.executeUpdate(sqlInsert1);
				int updateResult2 = statement.executeUpdate(sqlInsert2);
			
				HttpSession session = request.getSession();
                       		session.setAttribute("username",username);		
                        	session.setAttribute("fullname",fullname);		
                        	session.setAttribute("birthdate",birthdate);		
				session.setAttribute("loginerror",false);

				response.sendRedirect("home");
			}else {
				//user already exists
				response.sendRedirect("user.html");
			}

			rs.close();
		}
		catch(SQLException ex){ex.printStackTrace(); System.exit(1);}
		catch(Exception ex){ex.printStackTrace(); System.exit(2);}
  	}

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		PrintWriter output;
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
        	output.println("<input type='text' id='username' name='username'>");
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
}
