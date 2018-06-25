package email;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MailDispatcherServlet
 */
@WebServlet(name = "OdzyskajHasloServlet", urlPatterns = {"/OdzyskajHasloServlet"})
public class OdzyskajHasloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@EJB
	private OdzyskajHasloController mailSender;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OdzyskajHasloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");

		response.setCharacterEncoding("UTF-8");		
		
		response.setContentType("text/html; charset=UTF-8");	

		String email = request.getParameter("email");

	
		try (PrintWriter out = response.getWriter()) {

					mailSender.wyslij(email);  
					
					ServletContext ctx = this.getServletContext();
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/odzyskaj_haslo.xhtml");
					dispatcher.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
	}
	

}
