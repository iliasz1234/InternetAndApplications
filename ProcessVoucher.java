import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ProcessVoucher extends HttpServlet {

        public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
        {
		String voucherCode = "";
                if(request.getParameter("voucher") != null){
                        voucherCode = request.getParameter("voucher");
                }
		
		HttpSession session = request.getSession();
		if(voucherCode.equals("studentdiscount")){
                	session.setAttribute("voucher",true);	
		}
		else{
                	session.setAttribute("voucher",false);	
		}
		response.sendRedirect("basket");
	}
}

