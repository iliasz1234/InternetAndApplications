import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ProcessProduct extends HttpServlet {

        public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
        {
		String product_id = "0";
		if(request.getParameter("product") != null){
                	product_id = request.getParameter("product");
        	}
		
		if(!product_id.equals("0")){
			HttpSession session = request.getSession();
			session.setAttribute("product"+product_id,true);
		}
	}
}
