package Biblioteca.dao;

import java.util.List;

import Biblioteca.dao.GestorLibros;
import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.modelo.Libro;
import entrada.Teclado;


public class PrincipalLibro {

	public static void main(String[] args) throws BDException, ExcepcionesLibro {
		List<Libro> libros;
		int opcion;
		do {
			System.out.println("0. Salir del programa.");
			System.out.println("1. Insertar un libro en la base de datos.");
			System.out.println("2. Eliminar un libro, por código, de la base de datos.");
			System.out.println("3. Consultar todos los libros de la base de datos.");
			System.out.println("4. Consultar varios libros, por escritor, de la base de datos, ordenados por puntuación decendente.");
			System.out.println("5. Consultar los libros no prestados de la base de datos. ");
			System.out.println("6. Consultar los libros devueltos, en una fecha, de la base de datos. ");
			opcion = Teclado.leerEntero("Seleccione una opcion:");
			
			
			switch(opcion) {
			case 0:
				System.out.println("Saliendo del programa...");
				break;
			case 1:
				try {
					int codigo = Teclado.leerEntero("Codigo del libro: ");
					String isbn = Teclado.leerCadena("ISBN del libro: ");
					String titulo = Teclado.leerCadena("Titulo del libro");
					String escritor = Teclado.leerCadena("Escritor del libro");
					int añoPublicacion = Teclado.leerEntero("Año de publicacion del libro: ");
					double puntuacion = Teclado.leerReal("Puntuacion del libro: ");
					Libro libro  = new Libro(codigo,isbn,titulo,escritor,añoPublicacion,puntuacion);

					GestorLibros.insertarLibro(libro);

					System.out.println("Se ha insertado un libro en la base de datos.");

				}catch (BDException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				try {

					int codigo = Teclado.leerEntero("Codigo del libro: ");

					GestorLibros.eliminarLibro(codigo);

					System.out.println("Se ha eliminado un libro de la base de datos.");

				}catch (BDException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:

				try {
					libros = GestorLibros.consultarLibros();

					if (libros.size() == 0) {
						throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_CONSULTAR_LIBROS);
					} else {
						for (Libro libro : libros) {
							System.out.println(libro.toString());
						}
						System.out.println("Se han consultado " + libros.size() + " departamentos de la base de datos.");
					}
				} catch (BDException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				try {
					libros = GestorLibros.consultarLibroPorEscritorOrdenadosPorPuntuacion();

					if(libros.size() == 0) {
						throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_CONSULTAR_LIBRO_ESCRITOR);
					}else {
						for (Libro libro : libros) {
							System.out.println(libro.toString());
						}
						System.out.println("Se han consultado " + libros.size() + " departamentos de la base de datos.");
					}
				}catch (BDException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				try {
					libros = GestorLibros.librosNoPrestados();

					if(libros.size() == 0) {
						throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_LIBROS_NOPRESTADOS);
					}else {
						for (Libro libro : libros) {
							System.out.println(libro.toString());
						}
						System.out.println("Se han consultado " + libros.size() + " departamentos de la base de datos.");
					}
				}catch (BDException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 6:
				try {
					
					String fechaDevolucion = Teclado.leerCadena("Introduce la fecha de devolucion(YYYY-MM-DD): ");
					libros = GestorLibros.librosDevueltosFecha(fechaDevolucion);

					if(libros.size() == 0) {
						throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_LIBROS_NODEVUELTOS);
					}else {
						for (Libro libro : libros) {
							System.out.println(libro.toString());
						}
						System.out.println("Se han consultado " + libros.size() + " departamentos de la base de datos.");
					}
				}catch (BDException e) {
					System.out.println(e.getMessage());
				}
				break;

			default:
				System.out.println("Opcion no valida.");	
			}
		}while(opcion !=0);
	}

}

