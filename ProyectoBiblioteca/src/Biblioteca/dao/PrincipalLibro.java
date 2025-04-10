package Biblioteca.dao;

import java.util.List;

import Biblioteca.dao.GestorLibros;
import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.modelo.Libro;
import entrada.Teclado;


public class PrincipalLibro {

	public static void main(String[] args)  {
		List<Libro> libros;
		
		int opcion;
		do {
			System.out.println("0. Salir del programa.");
			System.out.println("1. Insertar un libro en la base de datos.");
			System.out.println("2. Eliminar un libro, por código, de la base de datos.");
			System.out.println("3. Consultar todos los libros de la base de datos.");
			System.out.println("4. Consultar varios libros, por escritor, de la base de datos, ordenados por puntuación descendiente.");
			System.out.println("5. Consultar los libros no prestados de la base de datos. ");
			System.out.println("6. Consultar los libros devueltos, en una fecha, de la base de datos. ");
			opcion = Teclado.leerEntero("Seleccione una opcion:");
			try {
				switch(opcion) {
				case 0:
					System.out.println("Saliendo del programa...");
					break;
				case 1:
				
						String isbn = Teclado.leerCadena("ISBN del libro: ");
						String titulo = Teclado.leerCadena("Titulo del libro: ");
						String escritor = Teclado.leerCadena("Escritor del libro: ");
						int añoPublicacion = Teclado.leerEntero("Año de publicacion del libro: ");
						double puntuacion = Teclado.leerReal("Puntuacion del libro: ");
						Libro libro  = new Libro(isbn,titulo,escritor,añoPublicacion,puntuacion);

						GestorLibros.insertarLibro(libro);
						
						System.out.println("Se ha insertado un libro en la base de datos.");				
					break;
				case 2:
						int codigo = Teclado.leerEntero("Codigo del libro: ");

						GestorLibros.eliminarLibro(codigo);

						System.out.println("Se ha eliminado un libro de la base de datos.");				
					break;
				case 3:

					
						libros = GestorLibros.consultarLibros();

						if (libros.size() == 0) {
							throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_CONSULTAR_LIBROS);
						} else {
							for ( Libro li : libros) {
								System.out.println(li.toString());
							}
							System.out.println("Se han consultado " + libros.size() + " libros de la base de datos.");
						}
					
					break;
				case 4:
					
						libros = GestorLibros.consultarLibroPorEscritorOrdenadosPorPuntuacion();

						if(libros.size() == 0) {
							throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_CONSULTAR_LIBRO_ESCRITOR);
						}else {
							for (Libro li : libros) {
								System.out.println(li.toString());
							}
							System.out.println("Se han consultado " + libros.size() + " libros de la base de datos.");
						}
					
					break;
				case 5:
					
						libros = GestorLibros.librosNoPrestados();

						if(libros.size() == 0) {
							throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_LIBROS_NOPRESTADOS);
						}else {
							for ( Libro li : libros) {
								System.out.println(li.toString());
							}
							System.out.println("Se han consultado " + libros.size() + " libros de la base de datos.");
						}
					break;
				case 6:
						String fechaDevolucion = Teclado.leerCadena("Introduce la fecha de devolucion(YYYY-MM-DD): ");
						libros = GestorLibros.librosDevueltosFecha(fechaDevolucion);

						if(libros.size() == 0) {
							throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_LIBROS_NODEVUELTOS);
						}else {
							for (Libro l : libros) {
								System.out.println(l.toString());
							}
							System.out.println("Se han consultado " + libros.size() + " libros de la base de datos.");
						}
					
					break;

				default:
					System.out.println("Opcion no valida.");	
				}
			}catch(ExcepcionesLibro l) {
				System.out.println(l.getMessage());
			}
			catch (BDException e) {
				System.out.println(e.getMessage());
			}
		}while(opcion !=0);
	}

}

