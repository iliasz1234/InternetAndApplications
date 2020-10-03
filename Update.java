import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import java.sql.* ;

public class Update extends HttpServlet {

        public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
        {
		HttpSession session = request.getSession();
                String username = session.getAttribute("username").toString();

                String fullname = "";
                String birthdate = "";
                if(request.getParameter("fullname") != null){
                        fullname = request.getParameter("fullname");
                }
                if(request.getParameter("birthdate") != null){
                        birthdate = request.getParameter("birthdate");
                }

                String connectionURL = "jdbc:mysql://localhost:3306/mydatabase";
                Connection connection = null;
                Statement statement  = null;
                ResultSet rs = null;
	
		try {
                	Class.forName("com.mysql.jdbc.Driver").newInstance();
                        connection = DriverManager.getConnection(connectionURL,"test","test");
                        statement = connection.createStatement();
                        String sqlUpdate = "UPDATE data_table SET fullname =  \"" + fullname + "\" , birthdate = \"" + birthdate + "\" WHERE username = \"" + username + "\";";

                        int updateResult = statement.executeUpdate(sqlUpdate);

                        session.setAttribute("fullname",fullname);
                        session.setAttribute("birthdate",birthdate);

                        response.sendRedirect("home");
		}
                catch(SQLException ex){ex.printStackTrace(); System.exit(1);}
                catch(Exception ex){ex.printStackTrace(); System.exit(2);}
	}
 
        public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		
		HttpSession session = request.getSession();
                String fullname = session.getAttribute("fullname").toString();
                String birthdate = session.getAttribute("birthdate").toString();
		
		PrintWriter output;

                response.setContentType ("text/html");
                output = response.getWriter();
		output.println("<!Doctype HTML>");
		output.println("<html>");
		output.println("<head>");
		output.println("</head>");
		output.println("<body>");
		output.println("<h1 align='center'>Update Page</h1>");
		output.println("<form action='update' method='POST' style='position:relative; top:50px; width:300px; margin:auto;'>");
		output.println("<fieldset>");
		output.println("<label for='fullname'>Full Name:</label><br>");
		output.println("<input type='text' id='fullname' name='fullname' value=" + fullname + ">");
		output.println("<br>");
		output.println("<label for='birthdate'>Birthdate:</label><br>");
		output.println("<input type='date' id='birthdate' name='birthdate' value=" + birthdate + ">");
		output.println("<br><br>");
		output.println("<input type='submit' value='Submit'>");
		output.println("</fieldset>");
		output.println("</form>");
		output.println("</body>");
		output.println("</html>");
		output.close();
	}
}
