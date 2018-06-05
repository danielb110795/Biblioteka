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
@WebServlet(name = "EmailServlet", urlPatterns = {"/EmailServlet"})
public class EmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@EJB
	private EmailController mailSender;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailServlet() {
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
		String imie = request.getParameter("imie");
		String nazwisko = request.getParameter("nazwisko");
		String email = request.getParameter("adres");
		String temat = request.getParameter("temat");
		String komentarz = request.getParameter("komentarz");
	
		try (PrintWriter out = response.getWriter()) {

					mailSender.wyslij(imie, nazwisko, email, temat, komentarz);
					
					ServletContext ctx = this.getServletContext();
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/kontakt.xhtml");
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
