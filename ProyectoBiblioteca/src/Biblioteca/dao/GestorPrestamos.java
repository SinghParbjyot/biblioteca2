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
import Biblioteca.excepciones.ExcepcionesPrestamo;
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
	 * @throws ExcepcionesPrestamo 
	 */
	public static void insertarPrestamo(Prestamo prestamo) throws BDException, ExcepcionesPrestamo {
		Connection conexion = null;
		PreparedStatement ps = null;
		int filas = 0;
		try {
			conexion = ConfigSQLLite.abrirConexion();
			int cod = prestamo.getLibro().getCodigo();
			int codSocio = prestamo.getSocio().getCodigo();
			if(estaPrestamo(cod) || estaPrestamo1(codSocio)){
				throw new ExcepcionesPrestamo(ExcepcionesPrestamo.MENSAJE_INSERTAR_PRESTAMO);
			}
			
			String query = "INSERT INTO prestamo " +
					"VALUES (?, ?, date('now'), date('now', '+15 days'),null)";

			ps = conexion.prepareStatement(query);
			ps.setInt(1, prestamo.getLibro().getCodigo());
			ps.setInt(2, prestamo.getSocio().getCodigo());	        
			filas = ps.executeUpdate();
			if (filas == 0) {
				throw new ExcepcionesPrestamo(ExcepcionesPrestamo.MENSAJE_INSERTAR_PRESTAMO);
			}
		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {	       
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}
	}

	public static void devolucionPrestamo(int codigoLibro, int codigoSocio, String fechaInicio, String fechaDevolucion) throws BDException, ExcepcionesPrestamo, ExcepcionesLibro {
		Connection conexion = null;
		int filas = 0;
		try {
			conexion = ConfigSQLLite.abrirConexion();
			String query = "UPDATE prestamo SET fecha_devolucion = ? WHERE codigo_libro = ? AND codigo_socio = ? AND fecha_inicio = ?";
			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setString(1, fechaDevolucion);
			ps.setInt(2, codigoLibro);
			ps.setInt(3, codigoSocio);
			ps.setString(4, fechaInicio);

			filas = ps.executeUpdate();
			if (filas == 0) {
				throw new ExcepcionesPrestamo(ExcepcionesPrestamo.MENSAJE_EXISTE_PRESTAMO);
			}
		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}

	}

	public static boolean estaPrestamo(int codigoLibro) throws BDException {
		Connection conexion = null;
		try {
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT * FROM prestamo WHERE codigo_libro = ? and fecha_devolucion is null";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, codigoLibro);

			ResultSet resultados = ps.executeQuery();
			return resultados.next();
		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}
		
	}
	public static boolean estaPrestamo1(int codigoSocio) throws BDException {
		Connection conexion = null;
		try {
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT * FROM prestamo WHERE codigo_socio = ? and fecha_devolucion is null";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, codigoSocio);

			ResultSet resultados = ps.executeQuery();
			return resultados.next(); // Devuelve true si hay resultados
		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}
	}

	public static void eliminarPrestamo(int codigoLibro, int codigoSocio, String fechaInicio) throws BDException, ExcepcionesPrestamo, ExcepcionesLibro {
		Connection conexion = null;
		int filas = 0;
		try {
			if (estaPrestamo(codigoLibro)|| estaPrestamo1(codigoSocio)) {
				throw new ExcepcionesPrestamo(ExcepcionesPrestamo.MENSAJE_INSERTAR_PRESTAMO);
			}

			conexion = ConfigSQLLite.abrirConexion();
			String query = "DELETE FROM prestamo WHERE codigo_libro = ? AND codigo_socio = ? AND fecha_inicio = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, codigoLibro);
			ps.setInt(2, codigoSocio);
			ps.setString(3, fechaInicio);

			filas = ps.executeUpdate();
			if (filas == 0) {
				throw new ExcepcionesPrestamo(ExcepcionesPrestamo.MENSAJE_EXISTE_PRESTAMO);
			}
		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}

	}

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
	public static List<Prestamo>  consultarPrestamosFechaInicio(String fechaInicio) throws BDException, ExcepcionesLibro {
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
