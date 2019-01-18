package daos;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pojos.ConnectionManager;
import pojos.Usuario;

public class UsuarioDAO {

	private static UsuarioDAO INSTANCE = null;
	
	//constructor privado solo accesible mediante getInstance();
	private UsuarioDAO() {
		super();
	}

	public synchronized static UsuarioDAO getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UsuarioDAO();
		}
		
		return INSTANCE;
		
	}
	
	
	public Usuario login(String usuario, String contrasena) {
		Usuario u = null;
		
		String sql = "SELECT id, nombre, contraseña FROM usuarios WHERE nombre = ? AND contraseña = ?;";
		
		try(Connection conn = ConnectionManager.getConnection(); 
			PreparedStatement pst = conn.prepareStatement(sql) ) {
			pst.setString(1, usuario);
			pst.setString(2, contrasena);
			
			try (ResultSet rs = pst.executeQuery()) {
				while(rs.next()) {
					u = new Usuario();
					u.setId(rs.getInt("id"));
					u.setNombre(rs.getString("nombre"));
					u.setPassword(rs.getString("contraseña"));
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return u;
		
	}


	
	
		
}
