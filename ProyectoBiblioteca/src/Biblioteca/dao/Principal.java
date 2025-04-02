package Biblioteca.dao;

import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.modelo.Libro;
import entrada.Teclado;

public class Principal {

	public static void main(String[] args) {
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
				
				break;
			case 2:
				
				break;
			case 3:

				break;
			case 4:
				
				break;
			case 5:
				
				break;
			case 6:
				
				break;

			default:
				System.out.println("La opcion de menú debe estar comprendida entre 0 y 6.");	
			}
		}while(opcion !=0);

	}

}
