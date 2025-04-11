package Biblioteca.dao;

import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.excepciones.ExcepcionesPrestamo;
import Biblioteca.excepciones.ExcepcionesSocio;
import entrada.Teclado;

public class MegaPrincipal {

	public static void main(String[] args) throws BDException, ExcepcionesLibro, ExcepcionesSocio, ExcepcionesPrestamo {
		int opcion;
		
		do {
			
			System.out.println("0. Salir del programa.");
			System.out.println("1. Menú de libros.");
			System.out.println("2. Menú de socios.");
			System.out.println("3. Menú de prestamos.");
			System.out.println("4. Menu opciones extras.");
			opcion = Teclado.leerEntero("Seleccione una opción: ");
			try {
			switch(opcion) {
			case 0:
				System.out.println("Saliendo del programa...");
				break;
			case 1:
				PrincipalLibro.main(args);
				break;
			case 2:
				PrincipalSocio.main(args);
				break;
			case 3:
				PrincipalPrestamo.main(args);
			case 4:
				Principal.main(args);
				break;
			}
			}catch (BDException e) {
	            System.err.println("Error de base de datos: " + e.getMessage());
	        } catch(ExcepcionesLibro el) {
	        	System.out.println(el.getMessage());
	        } catch(ExcepcionesSocio s) {
	        	System.out.println(s.getMessage());
	        }
		}while(opcion !=0);

	}

}
