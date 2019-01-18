package controladores;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.log4j.BasicConfigurator;

import org.apache.log4j.Logger;


import daos.UsuarioDAO;
import pojos.Usuario;


@WebServlet("/login")
public class LoginController extends HttpServlet {
	UsuarioDAO dao;
	private ValidatorFactory factory;
	private Validator validator;
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(LoginController.class);
	
	@Override
		public void init(ServletConfig config) throws ServletException {
			// TODO Auto-generated method stub
			super.init(config);
			dao = UsuarioDAO.getInstance();
			factory  = Validation.buildDefaultValidatorFactory();
	    	validator  = factory.getValidator();
		}
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usuario = request.getParameter("usuario");
		String contrasena = request.getParameter("contraseña");
		boolean redirect = false;
		
		
		try {
			HttpSession session = request.getSession();
			
			Usuario user = new Usuario();
			user.setNombre(usuario);
			user.setPassword(contrasena);
			
			Set<ConstraintViolation<Usuario>> violations = validator.validate(user);
			
			if ( violations.size() > 0) {			// validacion NO PASA
				String errores = "<ul>"; 
				 for (ConstraintViolation<Usuario> violation : violations) {					 	
					 errores += String.format("<li> %s : %s </li>" , violation.getPropertyPath(), violation.getMessage() );					
				 }
				 errores += "</ul>";				 
				 request.setAttribute("mensaje", errores);
				
			}else {                                // validacion OK
			
				user = dao.login(usuario, contrasena);
				
				if(user == null) {
					LOG.debug("Usuario nulo");	
				}else {
					session.setMaxInactiveInterval(60*5);
					// asociamos un listener para listar usuarios @see UsuariosListener
					session.setAttribute("usuario", user);
					redirect = true;
					LOG.debug("guardamos en session el usuario");		
				}
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			
			if(redirect) {
				request.getRequestDispatcher("loginExito.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			
		}
		
		
		
//		if(usuario.equals("antonio") && contrasena.equals("antonio")) {
//			request.getRequestDispatcher("loginExito.jsp").forward(request, response);
//		}else {
//			request.getRequestDispatcher("index.jsp").forward(request, response);
//		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
