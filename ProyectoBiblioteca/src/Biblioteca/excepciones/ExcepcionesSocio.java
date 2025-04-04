package Biblioteca.excepciones;

public class ExcepcionesSocio extends Exception{
	public static final String ERROR_INSERTAR_SOCIO = "Ya existe un socio con ese código.";
	public static final String ERROR_ELIMINAR_ = "No existe ningún socio con ese código en la base de datos.";
	public static final String ERROR_CONSULTAR_SOCIOS_PRESTADOS = "El socio está referenciado en un préstamo de la base de datos.";
	public static final String ERROR_CONSULTAR_SOCIO_LOCALIDAD = "No existe ningún socio con esa localidad en la base de datos.";
	public static final String ERROR_CONSULTAR_SOCIO_SIN_PRESTAMOS = "No existe ningún socio sin préstamos en la base de datos. ";
	public static final String ERROR_CONSULTAR_SOCIO_CON_PRESTAMOS_POR_FECHA = "No existe ningún socio con préstamos en esa fecha en la base de datos.";
	
	
	public ExcepcionesSocio(String mensaje) {
		super("Error"+mensaje);
	}
	

}
