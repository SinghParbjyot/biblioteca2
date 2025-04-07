package Biblioteca.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.modelo.Libro;
import Biblioteca.modelo.Prestamo;
import config.ConfigSQLLite;
import entrada.Teclado;

public class Principal {

	public static void main(String[] args) throws ExcepcionesLibro {
		List<Libro> libros;
		int opcion;

		do {
			System.out.println("0. Salir del programa.");
			System.out.println("1. Consultar el libro o los libros que ha/n sido prestado/s menos veces (y que como mínimo haya/n sido prestado/s una vez). ");
			System.out.println("2. Consultar el socio o los socios que ha/n realizado más préstamos. ");
			System.out.println("3. Consultar los libros que han sido prestados (incluyendo los libros no devueltos) una cantidad de veces inferior a la media. ");
			System.out.println("4. Consultar los socios que han realizado una cantidad de préstamos superior a la media. ");
			System.out.println("5. Consultar el ISBN, el título y el número de veces de los libros que han sido prestados, ordenados por el número de préstamos descendente.");
			System.out.println("6. Consultar el DNI, el nombre y el número de veces de los socios que han realizado préstamos, ordenados por el número de préstamos descendente. ");
			opcion = Teclado.leerEntero("Seleccione una opcion:");
			switch(opcion) {
			case 0:
				System.out.println("Saliendo del programa...");
				break;
			case 1:
				try {
					libros = GestorLibros.consultarLibroMenosPrestado();

					if (libros.size() == 0) {
						throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_CONSULTAR_LIBROS);
					} else {
						for (Libro libro : libros) {
							System.out.println(libro.toString());
						}
						System.out.println("Se han consultado " + libros.size() + " libros de la base de datos.");
					}
				} catch (BDException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:

				break;
			case 3:
				try {
					libros = GestorLibros.consultarLibrosPrestadosInferiorMedia();

					if (libros.size() == 0) {
						throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_CONSULTAR_LIBROS);
					} else {
						for (Libro libro : libros) {
							System.out.println(libro.toString());
						}
						System.out.println("Se han consultado " + libros.size() + " libros de la base de datos.");
					}
				} catch (BDException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:

				break;
			case 5:
				try {
					List<Prestamo> prestamos = GestorPrestamos.consultarLibrosNumeroPrestamosOrdenadoDescendente();

					for (Prestamo p : prestamos) {
						System.out.println(p);
					}
				} catch (BDException e) {
					System.err.println("Error: " + e.getMessage());
				}
				break;
			case 6:
				try {
					List<Prestamo> prestamos = GestorPrestamos.consultarSociosNumeroPrestamosOrdenadoDescendente();

					// Mostrar los préstamos usando toString4
					for (Prestamo p : prestamos) {
						System.out.println(p.toString4());
					}
				} catch (BDException e) {
					System.err.println("Error: " + e.getMessage());
				}

				break;

			default:
				System.out.println("La opcion de menú debe estar comprendida entre 0 y 6.");	
			}
		}while(opcion !=0);

	}

}
