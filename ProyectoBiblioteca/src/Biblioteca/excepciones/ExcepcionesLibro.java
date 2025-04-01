package Biblioteca.excepciones;

public class ExcepcionesLibro extends Exception {
	public static final String ERROR_INSERTAR_LIBRO = "Ya existe un libro con ese código.";
	public static final String ERROR_ELIMINAR_LIBRO = "No existe ningún libro con ese código en la base de datos.";
	public static final String ERROR_ELIMINAR_LIBRO_PRESTADO = "El libro está referenciado en un préstamo de la base de datos.";
	public static final String ERROR_CONSULTAR_LIBROS = "No se ha encontrado ningún libro en la base de datos.";
	public static final String ERROR_CONSULTAR_LIBRO_ESCRITOR = "No existe ningún libro con ese escritor en la base de datos.";
	public static final String ERROR_LIBROS_NOPRESTADOS = "No existe ningún libro no prestado en la base de datos.";
	public static final String ERROR_LIBROS_NODEVUELTOS = "No existe ningún libro devuelto en esa fecha en la base de datos.";
	public static final String ERROR_ABRIR_CONEXION = "Error al abrir conexion ";
	public static final String ERROR_QUERY = "Error en la consulta ";
	public static final String ERROR_CERRAR_CONEXION = "Error al cerrar conexion ";
	public static final String ERROR_CARGAR_DRIVER = "Error al cargar driver";
	
	public ExcepcionesLibro(String mensaje) {
		super("Error: " + mensaje);
	}
}
