import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import java.util.Enumeration;
import java.util.ArrayList;

public class Basket extends HttpServlet {

        public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
        {

		int[] ProductPrices = {0,500,1000,1500,400,700,1000};
		String[] Countries = {"Greece","Germany","France","UK","USA"};
		double[] Vat = {1.24,1.19,1.20,1.20,1.0725};

		ArrayList<String> products = new ArrayList<String>();


                HttpSession session = request.getSession();
		
		boolean loginerror = false;
		if(session.getAttribute("loginerror") != null){
			loginerror = Boolean.valueOf(session.getAttribute("loginerror").toString());
		}

		boolean voucher = false;
		if(session.getAttribute("voucher") != null){
			voucher = Boolean.valueOf(session.getAttribute("voucher").toString());
		}

		String country = "Greece";
		if(session.getAttribute("country") != null){
			country = session.getAttribute("country").toString();
		}
		
		int countryId=1;
		for(int i=0; i<Countries.length; i++){
			if(Countries[i].equals(country)){countryId = i; break;}
		}
		double vat = Vat[countryId];
		

		double totalPrice = 0;
		Enumeration e = session.getAttributeNames();
		while(e.hasMoreElements()){
			String name = (String) e.nextElement();
			String value = session.getAttribute(name).toString();
			if(name.contains("product") && value.equals("true")){
				products.add(name.substring(7));
				totalPrice += ProductPrices[Integer.parseInt(name.substring(7))];
			}

		}

		double finalPrice = totalPrice*vat;
		if(voucher){finalPrice = 0.8 * finalPrice;} 

                PrintWriter output;

                response.setContentType ("text/html");
                output = response.getWriter();
		
		output.println("<!Doctype HTML>");
		output.println("<html>");
		output.println("<head>");
		output.println("<link rel='stylesheet' href='basket_style.css'>");
		output.println("<script>");
		output.println("function process_voucher(voucher_code) {");
		output.println("window.location = 'processvoucher?voucher='+voucher_code");
		output.println("}");
		output.println("function process_country(country) {");
		output.println("window.location = 'processcountry?country='+country");
		output.println("}");
		output.println("function login_error() {");
		output.println("window.open('error.html');");
		output.println("}");
		output.println("function complete_order() {");
		output.println("window.location = 'processorder';");
		output.println("}");
		if(loginerror){output.println("window.addEventListener('load',login_error);");}
		output.println("</script>");
		output.println("</head>");

		output.println("<body>");
		output.println("<h1 align='center'>Basket</h1>");
		output.println("<div class='products_container'>");


		for(int i=0; i<products.size(); i++){
			String productName;
			int productId = Integer.parseInt(products.get(i));
			if(productId <= 3){productName = "laptop" + productId;}
			else{productName = "desktop" + Integer.toString(productId - 3);}
			output.println("<div class='product'>");
			output.println("<div class='product_img'>");
			output.println("<img src='images/" + productName + ".jpeg' style='margin:25px; width:150px; height:150px;'>");
			output.println("</div>");
			output.println("<div class='product_description'>");
			output.println("<div class='product_name'>");
			output.println(productName);
			output.println("</div>");
			output.println("<div class='product_price'>");
			output.println(ProductPrices[productId] * vat+"&euro;");
			output.println("</div>");
			output.println("</div>");
			output.println("</div>");
		}

		output.println("</div>");
		output.println("<div class='extras_container'>");
		output.println("<div class='voucher'>");
		output.println("<p>Voucher:</p>");
		output.println("<textarea id='voucher' name='voucher' onchange=process_voucher(this.value);></textarea>");
		output.println("</div>");
		output.println("<div class='country'>");
		output.println("<p>Country:</p>");
		output.println("<select id='country' name='country' onchange=process_country(this.value);>");
		if(country.equals("Greece")){output.println("<option selected value='Greece'>Greece</option>");}
		else{output.println("<option value='Greece'>Greece</option>");}
		if(country.equals("Germany")){output.println("<option selected value='Germany'>Germany</option>");}
		else{output.println("<option value='Germany'>Germany</option>");}
		if(country.equals("France")){output.println("<option selected value='France'>France</option>");}
		else{output.println("<option value='France'>France</option>");}
		if(country.equals("UK")){output.println("<option selected value='UK'>UK</option>");}
		else{output.println("<option value='UK'>UK</option>");}
		if(country.equals("USA")){output.println("<option selected value='USA'>USA</option>");}
		else{output.println("<option value='USA'>USA</option>");}
		output.println("</select> ");
		output.println("</div>");
		output.println("<div class='sum'>");
		output.println("<p>Sum:</p>");
		if(voucher){output.println("<p style='color:red'>"+finalPrice+"&euro;</p>");}
		else{output.println("<p>"+finalPrice+"&euro;</p>");}
		output.println("</div>");
		output.println("</div>");
		output.println("<div class='order_container'>");
		output.println("<div class='order'>");
		output.println("<button onclick=complete_order();>Complete Order</button>");
		output.println("</div>");
		output.println("</div>");
		output.println("</body>");
		output.println("</html>");
	}
}
