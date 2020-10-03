import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import java.sql.* ;

import java.util.Enumeration;
import java.util.ArrayList;

public class ProcessOrder extends HttpServlet {

        public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
        {
 		ArrayList<String> products = new ArrayList<String>();

                HttpSession session = request.getSession();
			
		String username = "";
                if(session.getAttribute("username") != null){
                       	username = session.getAttribute("username").toString();
                }
		if(username.equals("")){
			session.setAttribute("loginerror",true);
			response.sendRedirect("basket");
		}
		else{
                	Enumeration e = session.getAttributeNames();
                	while(e.hasMoreElements()){
                       		String name = (String) e.nextElement();
                       		String value = session.getAttribute(name).toString();
                    		if(name.contains("product") && value.equals("true")){
                                	products.add(name.substring(7));
                       		}
               		}
			
			if(products.size() == 0){response.sendRedirect("basket");}
			
			String productsString = "";
			for(int i=0; i<products.size(); i++){
				if(i > 0){productsString = productsString + "," + products.get(i);}
				else{productsString = productsString + products.get(i);}
			}

			String connectionURL = "jdbc:mysql://localhost:3306/mydatabase";
			Connection connection = null;
			Statement statement  = null;

 			try {
        		        Class.forName("com.mysql.jdbc.Driver").newInstance();
        		   	connection = DriverManager.getConnection(connectionURL,"test","test");
        			statement = connection.createStatement();

				String sqlInsert = "INSERT INTO orders_table (username, products) VALUES( \"" + username  + "\" ,\"" + productsString + "\") ;";
				int insertResult = statement.executeUpdate(sqlInsert);
			}
			catch(SQLException ex){ex.printStackTrace(); System.exit(1);}
       			catch(Exception ex){ex.printStackTrace(); System.exit(2);}

			try {
				FileWriter filewrt = new FileWriter(getServletContext().getRealPath("/orders/orders.txt"),true);
				BufferedWriter fileout = new BufferedWriter(filewrt);

				fileout.write("username: " + username + " products: " + productsString);
				fileout.newLine();
				fileout.close();
			}
			catch (IOException ex) {}
					
			response.sendRedirect("success.html");				
		}
	}
}

