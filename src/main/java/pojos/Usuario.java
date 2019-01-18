package pojos;

public class Usuario {
	int id;
	String nombre;
	String password;
	
	public Usuario() {
		super();
		id = -1;
		nombre = "defecto";
		password = "defecto";
	}
	
	public Usuario(int id, String nombre, String password) {
		this();
		setId(id);
		setNombre(nombre);
		setPassword(password);
	}
	
	//GETTERS & SETTERS
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//ToString
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", password=" + password + "]";
	}
	
	
}
