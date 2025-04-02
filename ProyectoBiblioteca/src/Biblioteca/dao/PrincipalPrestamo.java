package Biblioteca.dao;

import java.util.ArrayList;

import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.modelo.Libro;
import Biblioteca.modelo.Prestamo;
import Biblioteca.modelo.Socio;
import entrada.Teclado;

public class PrincipalPrestamo {
	public static int escribirMenuOpciones() {
		System.out.println("\n Menú de opciones:");
		System.out.println("1) Insertar un préstamo en la base de datos.");
		System.out.println("2) Actualizar un préstamo, por datos identificativos, de la base de datos");
		System.out.println("3) Eliminar un préstamo, por datos identificativos, de la base de datos.");
		System.out.println("4) Consultar todos los préstamos de la base de datos.");
		System.out.println("5) Consultar los préstamos no devueltos de la base de datos.");
		System.out.println("6) Consultar DNI y nombre de socio, ISBN y título de libro y fecha de devolución de los\r\n"
				+ "préstamos realizados en una fecha de la base de datos");
		System.out.print("Seleccione una opción: ");

		int opcion = Teclado.leerEntero("¿Opción (0-4)? ");
		return opcion;
	}

	public static void main(String[] args) throws ExcepcionesLibro {

		int opcion;
		do {
			opcion = escribirMenuOpciones();
			switch (opcion) {
			case 1:
				int codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");

				int codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");

				String fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio:");

				String fechaFin = Teclado.leerCadena("Introduzca la fecha de fin :");
				String fechaDevolucion = Teclado.leerCadena("Introduzca la fecha de devolucion :");
				Libro l = new Libro(codigoLibro);
				Socio s = new Socio(codigoSocio);
				Prestamo prestamo = new Prestamo(l, s, fechaInicio, fechaFin,fechaDevolucion);
				System.out.println(prestamo);
				try {
					boolean insertado =	GestorPrestamos.insertarPrestamo(prestamo);
					if(insertado) {
						System.out.println("Se ha insertado");
					}else {
						System.out.println("No se ha insertado");
					}
				} catch (BDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 2:
				codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");

				codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");

				fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio:");

				fechaDevolucion = Teclado.leerCadena("Introduzca la fecha de fin :");
				try {
					boolean actualizar = GestorPrestamos.actualizarPrestamo(codigoLibro,codigoSocio,fechaInicio,fechaDevolucion);
					if(actualizar) {
						System.out.println("Se ha actualizado");
					}else {
						System.out.println("No se ha actualizado");
					}
				} catch (BDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 3:

				codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");

				codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");

				fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio:");
				try {
					boolean eliminar = GestorPrestamos.eliminarPrestamo(codigoLibro, codigoSocio, fechaInicio);
					if(eliminar) {
						System.out.println("Se ha eliminado");
					}else {
						System.out.println("No se ha eliminado");
					}
				} catch (BDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) GestorPrestamos.consultaPrestamos();
					if(prestamos.size() == 0) {
						System.out.println("No hay prestamos");
					}else {
						
						for(Prestamo p : prestamos) {
							System.out.println(p);
						}
						
					}
				} catch (BDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case 5:
				try {
					ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) GestorPrestamos.consultarPrestamosNoDevueltos();
					if(prestamos.size() == 0) {
						System.out.println("No hay prestamos sin devolucion");
					}else {
						for(Prestamo p : prestamos) {
							System.out.println(p);
						}
					}
				} catch (BDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 6:
				fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio:");
				try {
					ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) GestorPrestamos.consultarPrestamosFechaDevolucion(fechaInicio);
					if(prestamos.size() == 0) {
						System.out.println("No hay prestamos con dicha fecha de  inicio");
					}else {
						for(Prestamo p : prestamos) {
							System.out.println(p.toString2());
						}
					}
				} catch (BDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				System.out.println("Opción inválida. Intente de nuevo.");
			}
		} while (opcion != 0);

		System.out.println("Programa finalizado sin errores.");

	}
}
