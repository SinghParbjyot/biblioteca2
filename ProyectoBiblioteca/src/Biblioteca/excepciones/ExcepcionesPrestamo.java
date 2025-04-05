package Biblioteca.excepciones;

public class ExcepcionesPrestamo extends Exception{
	public static final String MENSAJE_INSERTAR_PRESTAMO = "Se ha prestado ese libro a un socio y éste aún no lo ha devuelto";
	public static final String MENSAJE_EXISTE_PRESTAMO = "No existe ningún préstamo con esos datos identificativos en la base de datos.";
	public static final String MENSAJE_NO_EXISTE_PRESTAMO = "No se ha encontrado ningún préstamo en la base de datos.";
	
	public ExcepcionesPrestamo(String mensaje) {
		super("Error "+mensaje);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
