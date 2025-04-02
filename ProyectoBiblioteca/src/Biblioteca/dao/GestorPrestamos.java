package Biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.modelo.Libro;
import Biblioteca.modelo.Prestamo;
import Biblioteca.modelo.Socio;
import config.ConfigSQLLite;


public class GestorPrestamos {
	
	
	/**
	 *Insertar un préstamo en la base de datos.
	 Leerá por teclado el código de libro, el código de socio, la fecha de inicio y la fecha de fin del
	 préstamo a insertar
	 * @param prestamo
	 * @return 
	 * @throws BDException
	 * @throws ExcepcionesLibro 
	 */
	public static boolean insertarPrestamo(Prestamo prestamo) throws BDException, ExcepcionesLibro {
		
		  Connection conexion = null;
		int filas = 0;
		try {
			// Conexi�n a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "INSERT INTO prestamo VALUES (?, ?, ?, ? ,?)";
			PreparedStatement ps = conexion.prepareStatement(query);
			int codigoLibro = prestamo.getLibro().getCodigo();
			int codigoSocio = prestamo.getLibro().getCodigo();
			String fechaInicio = prestamo.getFechaInicio();
			String fechaFin = prestamo.getFechaFin();
			String fechaDevolucion = prestamo.getFechaDevolucion();
			ps.setInt(1,codigoLibro );
			ps.setInt(2, codigoSocio);
			ps.setString(3, fechaInicio);
			ps.setString(4, fechaFin);
			ps.setString(5, fechaDevolucion);
			filas = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}
		return filas > 0;
		 
	}
	
	/**
	 *  Actualizar un préstamo, por datos identificativos, de la base de datos.
		Leerá por teclado el código de libro, el código de socio, la fecha de inicio y la nueva fecha de
		devolución del préstamo a actualizar. 
	 * @param codigoLibro
	 * @param codigoSocio
	 * @param fechaInicio
	 * @param fechaDevolucion
	 * @return
	 * @throws BDException
	 * @throws ExcepcionesLibro 
	 */
	public static boolean actualizarPrestamo(int codigoLibro,int codigoSocio,String fechaInicio,String fechaDevolucion) throws BDException, ExcepcionesLibro {
		
		 Connection conexion = null;
		int filas = 0;
		try {
			// Conexi�n a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "UPDATE prestamo set fecha_devolucion = ? where codigo_libro= ? and codigo_socio=? and fecha_inicio= ? ";
			PreparedStatement ps = conexion.prepareStatement(query);
		
			ps.setString(1, fechaDevolucion);
			ps.setInt(2,codigoLibro );
			ps.setInt(3, codigoSocio);
			ps.setString(4, fechaInicio);
			filas = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}
		return filas > 0;
	}
	
	
	/**
	 * Este metodo nos indica si exite o no un prestamo en nuestra base de datos
	 * @param codigoLibro
	 * @param codigoSocio
	 * @param fechaInicio
	 * @return
	 * @throws BDException
	 * @throws ExcepcionesLibro 
	 */
	public static boolean estaPrestamo(int codigoLibro) throws BDException, ExcepcionesLibro {
		PreparedStatement ps = null;
		Connection conexion = null;
		try {
			// Conexi�n a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "Select * from prestamo where  codigo_libro=?";
			ps = conexion.prepareStatement(query);
			ps.setInt(1, codigoLibro);
			
			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				int codLibro = resultados.getInt("codigo_libro");
				if(codLibro == codigoLibro ) {
					return true;
				}
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}
		return false;
	}
	
	/**
	 * Elimina un préstamo, por datos identificativos, de la base de datos.
	   Leerá por teclado el código de libro, el código de socio y la fecha de inicio del préstamo a eliminar.
	   Se conectará a la base de datos biblioteca, creada en SQLite, ejecutará una sentencia SQL de
	   eliminación para borrar un préstamo, por datos identificativos, de la base de datos y cerrará la
	   conexión
	 * @param codigoLibro
	 * @param codigoSocio
	 * @param fechaInicio
	 * @return
	 * @throws Exception
	 */
	public static boolean eliminarPrestamo(int codigoLibro,int codigoSocio,String fechaInicio) throws Exception {
		 Connection conexion = null;
			int filas = 0;
			try {
				// Conexi�n a la bd
				
				if(!estaPrestamo(codigoLibro)) {
					throw new Exception("Ya existe");
				}
				conexion = ConfigSQLLite.abrirConexion();
				String query = "Delete from prestamo where codigo_libro=? and codigo_socio=? and fecha_inicio=? ";
				PreparedStatement ps = conexion.prepareStatement(query);
				ps.setInt(1,codigoLibro );
				ps.setInt(2, codigoSocio);
				ps.setString(3, fechaInicio);
				filas = ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new BDException(BDException.ERROR_QUERY + e.getMessage());
			} finally {
				if (conexion != null) {
					ConfigSQLLite.cerrarConexion(conexion);
				}
			}
			return filas > 0;
	}
	
	
	/**
	 * Consultar todos los préstamos de la base de datos
	 * @return
	 * @throws BDException
	 * @throws ExcepcionesLibro 
	 */
	public static List<Prestamo> consultaPrestamos() throws BDException, ExcepcionesLibro {
	    List<Prestamo> prestamos = new ArrayList<>();
	    Connection conexion = null;

	    try {
	        // Conexión a la BD
	        conexion = ConfigSQLLite.abrirConexion();
	        String query = "SELECT " +
	                "libro.codigo AS codigo_libro, libro.isbn, libro.titulo, libro.escritor, libro.año_publicacion, libro.puntuacion, " +
	                "socio.codigo AS codigo_socio, socio.dni, socio.nombre, socio.domicilio, socio.telefono, socio.correo, " +
	                "prestamo.fecha_inicio, prestamo.fecha_fin, prestamo.fecha_devolucion " + 
	                "FROM prestamo " +
	                "JOIN libro ON prestamo.codigo_libro = libro.codigo " +
	                "JOIN socio ON prestamo.codigo_socio = socio.codigo";

	        Statement sentencia = conexion.createStatement();
	        ResultSet resultados = sentencia.executeQuery(query);

	        while (resultados.next()) {
	            Libro libro = new Libro(
	                resultados.getInt("codigo_libro"),
	                resultados.getString("isbn"),
	                resultados.getString("titulo"),
	                resultados.getString("escritor"),
	                resultados.getInt("año_publicacion"),
	                resultados.getDouble("puntuacion")
	            );
	            
	            Socio socio = new Socio(
	                resultados.getInt("codigo_socio"),
	                resultados.getString("dni"),
	                resultados.getString("nombre"),
	                resultados.getString("domicilio"),
	                resultados.getString("telefono"),
	                resultados.getString("correo")
	            );
	            
	            Prestamo prestamo = new Prestamo(
	                libro,socio,
	                resultados.getString("fecha_inicio"),
	                resultados.getString("fecha_fin"),resultados.getString("fecha_devolucion")
	            );
	            
	            prestamos.add(prestamo);
	        }
	    } catch (SQLException e) {
	        throw new BDException(BDException.ERROR_QUERY + e.getMessage());
	    } finally {
	        if (conexion != null) {
	        	ConfigSQLLite.cerrarConexion(conexion);
	        }
	    }
	    return prestamos;
	}
	
	/**
	 * Consultar los préstamos no devueltos de la base de datos.
	 * @return
	 * @throws BDException
	 * @throws ExcepcionesLibro 
	 */
	public static List<Prestamo>  consultarPrestamosNoDevueltos() throws BDException, ExcepcionesLibro {
		 List<Prestamo> prestamos = new ArrayList<>();
		 Connection conexion = null;

		    try {
		        // Conexión a la BD
		        conexion = ConfigSQLLite.abrirConexion();
		        String query = "SELECT " +
		                "libro.codigo AS codigo_libro, libro.isbn, libro.titulo, libro.escritor, libro.año_publicacion, libro.puntuacion, " +
		                "socio.codigo AS codigo_socio, socio.dni, socio.nombre, socio.domicilio, socio.telefono, socio.correo, " +
		                "prestamo.fecha_inicio, prestamo.fecha_fin, prestamo.fecha_devolucion " + // <-- Espacio añadido aquí
		                "FROM prestamo " +
		                "JOIN libro ON (prestamo.codigo_libro = libro.codigo) " +
		                "JOIN socio ON (prestamo.codigo_socio = socio.codigo) where fecha_devolucion is null";

		        Statement sentencia = conexion.createStatement();
		        ResultSet resultados = sentencia.executeQuery(query);

		        while (resultados.next()) {
		            Libro libro = new Libro(
		                resultados.getInt("codigo_libro"),
		                resultados.getString("isbn"),
		                resultados.getString("titulo"),
		                resultados.getString("escritor"),
		                resultados.getInt("año_publicacion"),
		                resultados.getDouble("puntuacion")
		            );
		            
		            Socio socio = new Socio(
		                resultados.getInt("codigo_socio"),
		                resultados.getString("dni"),
		                resultados.getString("nombre"),
		                resultados.getString("domicilio"),
		                resultados.getString("telefono"),
		                resultados.getString("correo")
		            );
		            
		            Prestamo prestamo = new Prestamo(
		                libro,socio,
		                resultados.getString("fecha_inicio"),
		                resultados.getString("fecha_fin"),resultados.getString("fecha_devolucion")
		            );
		            
		            prestamos.add(prestamo);
		        }
		    } catch (SQLException e) {
		        throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		    } finally {
		        if (conexion != null) {
		        	ConfigSQLLite.cerrarConexion(conexion);
		        }
		    }
		    return prestamos;
	}
	
	/**
	 * Consultar DNI y nombre de socio, ISBN y título de libro y fecha de devolución de los
	   préstamos realizados en una fecha de la base de datos
	 * @param fechaInicio
	 * @return
	 * @throws BDException
	 * @throws ExcepcionesLibro 
	 */
	public static List<Prestamo>  consultarPrestamosFechaDevolucion(String fechaInicio) throws BDException, ExcepcionesLibro {
		 List<Prestamo> prestamos = new ArrayList<>();
		 Connection conexion = null;
		    try {
		        // Conexión a la BD
		        conexion = ConfigSQLLite.abrirConexion();
		        String query = "SELECT socio.dni, socio.nombre, libro.isbn, libro.titulo, prestamo.fecha_devolucion " +
	                       "FROM prestamo " +
	                       "JOIN libro ON prestamo.codigo_libro = libro.codigo " +
	                       "JOIN socio ON prestamo.codigo_socio = socio.codigo " +
	                       "WHERE prestamo.fecha_inicio LIKE ?";

		      PreparedStatement ps  = conexion.prepareStatement(query);
		      ps.setString(1, fechaInicio);
		        ResultSet resultados = ps.executeQuery();

		        while (resultados.next()) {
		            Libro libro = new Libro(		               
		                resultados.getString("isbn"),
		                resultados.getString("titulo")
		            );
		            
		            Socio socio = new Socio(		               
		                resultados.getString("dni"),
		                resultados.getString("nombre")		             
		            );
		            
		            Prestamo prestamo = new Prestamo(
			                libro,socio,
			                resultados.getString("fecha_devolucion")			             
			            );
		            
		            prestamos.add(prestamo);
		        }
		    } catch (SQLException e) {

		    } finally {
		        if (conexion != null) {
		        	ConfigSQLLite.cerrarConexion(conexion);
		        }
		    }
		    return prestamos;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
