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

	public static void main(String[] args)  {

		int opcion = -1;
	    do {
	        try {
	            opcion = escribirMenuOpciones();
	            switch (opcion) {
	                case 1:
	                    int codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");
	                    int codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");
	                    String fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio:");
	                    String fechaFin = Teclado.leerCadena("Introduzca la fecha de fin:");
	                    String fechaDevolucion = Teclado.leerCadena("Introduzca la fecha de devolucion:");
	                    
	                    Libro l = new Libro(codigoLibro);
	                    Socio s = new Socio(codigoSocio);
	                    Prestamo prestamo = new Prestamo(l, s, fechaInicio, fechaFin, fechaDevolucion);
	                     
	                    GestorPrestamos.insertarPrestamo(prestamo);
	                    System.out.println("Préstamo insertado correctamente.");
	                    break;
	                
	                case 2:
	                    codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");
	                    codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");
	                    fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio:");
	                    fechaDevolucion = Teclado.leerCadena("Introduzca la nueva fecha de devolucion:");
	                    
	                    GestorPrestamos.actualizarPrestamo(codigoLibro, codigoSocio, fechaInicio, fechaDevolucion);
	                    System.out.println("Préstamo actualizado correctamente.");
	                    break;
	                
	                case 3:
	                    codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");
	                    codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");
	                    fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio:");
	                    
	                    GestorPrestamos.eliminarPrestamo(codigoLibro, codigoSocio, fechaInicio);
	                    System.out.println("Préstamo eliminado correctamente.");
	                    break;
	                
	                case 4:
	                    ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) GestorPrestamos.consultaPrestamos();
	                    if (prestamos.isEmpty()) {
	                        System.out.println("No hay préstamos registrados.");
	                    } else {
	                        for (Prestamo p : prestamos) {
	                            System.out.println(p);
	                        }
	                    }
	                    break;
	                
	                case 5:
	                    prestamos = (ArrayList<Prestamo>) GestorPrestamos.consultarPrestamosNoDevueltos();
	                    if (prestamos.isEmpty()) {
	                        System.out.println("No hay préstamos sin devolución.");
	                    } else {
	                        for (Prestamo p : prestamos) {
	                            System.out.println(p);
	                        }
	                    }
	                    break;
	                
	                case 6:
	                    fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio:");
	                    prestamos = (ArrayList<Prestamo>) GestorPrestamos.consultarPrestamosFechaDevolucion(fechaInicio);
	                    if (prestamos.isEmpty()) {
	                        System.out.println("No hay préstamos con esa fecha de inicio.");
	                    } else {
	                        for (Prestamo p : prestamos) {
	                            System.out.println(p.toString2());
	                        }
	                    }
	                    break;
	                
	                default:
	                    System.out.println("Opción inválida. Intente de nuevo.");
	            }
	        } catch (BDException e) {
	            System.err.println("Error de base de datos: " + e.getMessage());
	        } catch (ExcepcionesLibro e) {
	            System.err.println("Error con el libro: " + e.getMessage());
	        } catch (Exception e) {
	            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
	        }
	    } while (opcion != 0);
	    
	
	}
}
