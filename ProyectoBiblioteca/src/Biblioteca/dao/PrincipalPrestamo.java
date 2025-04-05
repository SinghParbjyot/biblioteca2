package Biblioteca.dao;

import java.util.ArrayList;

import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.excepciones.ExcepcionesPrestamo;
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

		int opcion = Teclado.leerEntero("¿Opción (0-6)"
				+ "? ");
		return opcion;
	}

	public static void main(String[] args)    {
		  int opcion = -1;
		
		  String fechaInicio;
		    do {
		        try {
		            opcion = escribirMenuOpciones();
		            switch (opcion) {
		                case 1:
		                    int codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");
		                    int codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");
		                    
		                    Libro l = new Libro(codigoLibro);
		                    Socio s = new Socio(codigoSocio);
		                    Prestamo prestamo = new Prestamo(l, s);
		                    
		                    GestorPrestamos.insertarPrestamo(prestamo);
		                    System.out.println("Préstamo insertado correctamente.");
		                    break;
		                
		                case 2:
		                    codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");
		                    codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");
		                    fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio(YYYY-MM-DD):");
		                    String  fechaDevolucion = Teclado.leerCadena("Introduzca la nueva fecha de devolucion(YYYY-MM-DD):");
		                    
		                    GestorPrestamos.devolucionPrestamo(codigoLibro, codigoSocio, fechaInicio, fechaDevolucion);
		                    System.out.println("Préstamo actualizado correctamente.");
		                    break;
		                
		                case 3:
		                    codigoLibro = Teclado.leerEntero("Introduzca el codigo de libro:");
		                    codigoSocio = Teclado.leerEntero("Introduzca su codigo de socio:");
		                    fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio(YYYY-MM-DD):");
		                    
		                    GestorPrestamos.eliminarPrestamo(codigoLibro, codigoSocio, fechaInicio);
		                    System.out.println("Préstamo eliminado correctamente.");
		                    break;
		                
		                case 4:
		                    ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) GestorPrestamos.consultaPrestamos();
		                    if (prestamos.isEmpty()) {
		                        System.out.println("No hay préstamos en la base de datos");
		                    } else {
		                        for (Prestamo p : prestamos) {
		                            System.out.println(p.toString());
		                        }
		                        System.out.println("Se han consultado " + prestamos.size() + " prestamos de la base de datos.");
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
		                        System.out.println("Se han consultado " + prestamos.size() + " prestamos de la base de datos.");

		                    }
		                    break;
		                
		                case 6:
		                    fechaInicio = Teclado.leerCadena("Introduzca la fecha de inicio(YYYY-MM-DD):");
		                    prestamos = (ArrayList<Prestamo>) GestorPrestamos.consultarPrestamosFechaInicio(fechaInicio);
		                    if (prestamos.isEmpty()) {
		                        System.out.println("No hay préstamos con esa fecha de inicio.");
		                    } else {
		                        for (Prestamo p : prestamos) {
		                            System.out.println(p.toString2());
		                        }
		                        System.out.println("Se han consultado " + prestamos.size() + " prestamos de la base de datos.");

		                    }
		                    break;
		                
		                default:
		                    System.out.println("Opción inválida. Intente de nuevo.");
		            }
		        } catch (BDException e) {
		            System.err.println("Error de base de datos: " + e.getMessage());
		        
		        } catch (ExcepcionesPrestamo e) {
		            System.err.println( e.getMessage());
		        } catch(ExcepcionesLibro el) {
		        	System.out.println(el.getMessage());
		        }
		    } while (opcion != 0);
		    
		    System.out.println("Programa finalizado sin errores.");
	}
}
