package Biblioteca.excepciones;

public class ExcepcionesLibro extends Exception {
	public static final String ERROR_INSERTAR_LIBRO = "Ya existe un libro con ese código.";
	public static final String ERROR_ELIMINAR_LIBRO = "No existe ningún libro con ese código en la base de datos.";
	public static final String ERROR_ELIMINAR_LIBRO_PRESTADO = "El libro está referenciado en un préstamo de la base de datos.";
	public static final String ERROR_CONSULTAR_LIBROS = "No se ha encontrado ningún libro en la base de datos.";
	public static final String ERROR_CONSULTAR_LIBRO_ESCRITOR = "No existe ningún libro con ese escritor en la base de datos.";
	public static final String ERROR_LIBROS_NOPRESTADOS = "Todos los libros de la base de datos estan prestados.";
	public static final String ERROR_LIBROS_NODEVUELTOS = "No existe ningún libro devuelto en esa fecha en la base de datos.";
	
	
	public ExcepcionesLibro(String mensaje) {
		super("Error: " + mensaje);
	}
}
