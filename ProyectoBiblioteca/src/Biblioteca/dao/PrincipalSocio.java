package Biblioteca.dao;

import java.util.ArrayList;

import Biblioteca.dao.GestorSocios;
import Biblioteca.modelo.Socio;
import entrada.Teclado;
import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;

public class PrincipalSocio {
	
	public static void main(String[] args) throws BDException, ExcepcionesLibro {
		ArrayList<Socio> socios = null;
		int opcion;
		do {
			System.out.println("0. Salir del programa.");
			System.out.println("1. Insertar un socio en la base de datos.");
			System.out.println("2. Eliminar un socio, por código, de la base de datos. ");
			System.out.println("3. Consultar todos los socios de la base de datos.");
			System.out
					.println("4. Consultar varios socios, por localidad, de la base de datos, ordenados por nombre \r\n"
							+ "ascendente.");
			System.out.println("5. Consultar los socios sin préstamos de la base de datos. ");
			System.out.println("6. Consultar los socios con préstamos en una fecha de la base de datos.  ");
			opcion = Teclado.leerEntero("Seleccione una opcion:");
			switch (opcion) {

			case 0:
				System.out.println("Saliendo del programa...");
				break;
			case 1:
				try {
					int codigo = Teclado.leerEntero("Codigo: ");
					String dni = Teclado.leerCadena("DNI: ");
					String nombre = Teclado.leerCadena("Nombre: ");
					String domicilio = Teclado.leerCadena("Domicilio: ");
					String telefono = Teclado.leerCadena("Telefono: ");
					String correo = Teclado.leerCadena("Correo: ");
					Socio socio = new Socio(codigo, dni, nombre, domicilio, telefono, correo);

					GestorSocios.insertarSocio(socio);
					
					

					System.out.println("Se ha insertado un socio en la base de datos.");

				} catch (BDException e) {
					throw new BDException(BDException.ERROR_QUERY + e.getMessage());
				}
				break;

			case 2:
				try {
					
					int codigo = Teclado.leerEntero("Codigo : ");
					
					if(!GestorSocios.validarCodigo(codigo)) {
						System.out.println("No se ha encontrado codigo");
					} else {
					
					Socio socio = new Socio(codigo);

					GestorSocios.eliminarSocio(socio);

					System.out.println("Se ha eliminado un socio de la base de datos.");
					
					}
					

				} catch (BDException e) {
					throw new BDException(BDException.ERROR_QUERY + e.getMessage());
				}
				break;
			case 3:

				try {
					socios = GestorSocios.consultarSocios();

					if (socios.size() == 0) {
						System.out.println("No se ha encontrado ningún socio en la base de datos.");
					} else {
						for (Socio socio : socios) {
							System.out.println(socio.toString());
						}
						System.out
								.println("Se han consultado " + socios.size() + " socios en la base de datos.");
					}
				} catch (BDException e) {
					throw new BDException(BDException.ERROR_QUERY + e.getMessage());
				}
				break;
			case 4:
				try {
					
					String domicilio = Teclado.leerCadena("¿domicilio? ");
					socios = GestorSocios.consultarSocioPorLocalidadOrdenadosPorNombre(domicilio);

					if (socios.size() == 0) {
						System.out.println("No exise ningún socio con ese domicilio en la base de datos.");
					} else {
						for (Socio socio : socios) {
							System.out.println(socio.toString());
						}
						System.out
								.println("Se han consultado " + socios.size() + " socios en la base de datos.");
					}
				} catch (BDException e) {
					throw new BDException(BDException.ERROR_QUERY + e.getMessage());
				}
				break;
			case 5:
				
				try {
				socios = GestorSocios.consultarSocioSinPrestamo();

				if (socios.size() == 0) {
					System.out.println("No exise ningún socio sin prestamos en la base de datos.");
				} else {
					for (Socio socio : socios) {
						System.out.println(socio.toString());
					}
					System.out
							.println("Se han consultado " + socios.size() + " socios en la base de datos.");
				}
			} catch (BDException e) {
				throw new BDException(BDException.ERROR_QUERY + e.getMessage());
			}
			
				break;
			case 6:
					try {
					
					String fecha_inicio = Teclado.leerCadena("¿fecha_inicio? ");
					socios = GestorSocios.consultarSocioConPrestamosPorFecha(fecha_inicio);

					if (socios.size() == 0) {
						System.out.println("No exise ningún socio en la base de datos.");
					} else {
						for (Socio socio : socios) {
							System.out.println(socio.toString());
						}
						System.out
								.println("Se han consultado " + socios.size() + " socios en la base de datos.");
					}
				} catch (BDException e) {
					throw new BDException(BDException.ERROR_QUERY + e.getMessage());
				}
				

				break;

			default:
				System.out.println("Opcion no valida.");
			}
		} while (opcion != 0);
	}


}
