import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ProcessCountry extends HttpServlet {

        public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
        {
                String country = "Greece";
                if(request.getParameter("country") != null){
                        country = request.getParameter("country");
                }

                HttpSession session = request.getSession();
                session.setAttribute("country",country);
                response.sendRedirect("basket");
        }
}
