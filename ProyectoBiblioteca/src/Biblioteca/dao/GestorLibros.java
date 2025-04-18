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
import config.ConfigSQLLite;
import entrada.Teclado;


public class GestorLibros {

	public static boolean insertarLibro(Libro libro) throws BDException, ExcepcionesLibro {

		Connection conexion = null;

		int filas = 0;

		try {

			// Conexi�n a la bd

			conexion = ConfigSQLLite.abrirConexion();

			String query = "INSERT INTO libro(isbn,titulo,escritor,año_publicacion,puntuacion) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setString(1, libro.getIsbn());

			ps.setString(2, libro.getTitulo()); 

			ps.setString(3, libro.getEscritor());

			ps.setInt(4,libro.getAñoPublicacion());

			ps.setDouble(5, libro.getPuntuacion());

			filas = ps.executeUpdate();

			if(filas == 0) {
				throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_INSERTAR_LIBRO);
			}

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_INSERTAR_LIBRO);

		} finally {

			if (conexion != null) {

				ConfigSQLLite.cerrarConexion(conexion);

			}

		}

		return filas > 0;

	}

	public static boolean eliminarLibro(int codigo) throws BDException, ExcepcionesLibro {

		Connection conexion = null;

		int filas = 0;

		try {

			if(GestorPrestamos.estaPrestamo(codigo)) {

				throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_ELIMINAR_LIBRO_PRESTADO);
			}

				conexion = ConfigSQLLite.abrirConexion();
				String query = "DELETE FROM libro where codigo = ?";

				PreparedStatement ps = conexion.prepareStatement(query);

				ps.setInt(1, codigo);
				filas = ps.executeUpdate();
			
			if(filas == 0) {
				throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_ELIMINAR_LIBRO);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());

		} finally {

			if (conexion != null) {

				ConfigSQLLite.cerrarConexion(conexion);

			}

		}

		return filas > 0;

	}

	public static List<Libro> consultarLibros() throws BDException, ExcepcionesLibro {
		List<Libro> listaLibros = new ArrayList<Libro>();
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexi�n a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT * FROM libro";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);

			while (resultados.next()) {
				Libro libro = new Libro(resultados.getInt("codigo"),resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),resultados.getInt("año_publicacion"),resultados.getDouble("puntuacion"));
				listaLibros.add(libro);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}
		return listaLibros;

	}

	public static List<Libro> consultarLibroPorEscritorOrdenadosPorPuntuacion() throws BDException, ExcepcionesLibro {
		List<Libro> listaLibros = new ArrayList<Libro>();
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String escritor = Teclado.leerCadena("Escritor: ");
			String query = "SELECT * FROM libro WHERE escritor = '"+escritor+"' ORDER BY puntuacion desc";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);

			while (resultados.next()) {
				Libro libro = new Libro(resultados.getInt("codigo"),resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),resultados.getInt("año_publicacion"),resultados.getDouble("puntuacion"));
				listaLibros.add(libro);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaLibros;
	}

	public static List<Libro> librosNoPrestados() throws ExcepcionesLibro, BDException{
		List<Libro> listaLibros = new ArrayList<Libro>();
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT * FROM libro where codigo not in(select codigo_libro from prestamo)";
			ps = conexion.prepareStatement(query);
			ResultSet resultados = ps.executeQuery();

			while (resultados.next()) {
				Libro libro = new Libro(resultados.getInt("codigo"),resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),resultados.getInt("año_publicacion"),resultados.getDouble("puntuacion"));
				listaLibros.add(libro);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaLibros;
	}

	public static List<Libro> librosDevueltosFecha(String fechaDevolucion) throws ExcepcionesLibro, BDException{
		List<Libro> listaLibros = new ArrayList<Libro>();

		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT l.*,fecha_devolucion FROM libro l join prestamo p on(l.codigo = p.codigo_libro) where fecha_devolucion = ?";
			ps = conexion.prepareStatement(query);

			ps.setString(1, fechaDevolucion);
			ResultSet resultados = ps.executeQuery();

			while (resultados.next()) {
				Libro libro = new Libro(resultados.getInt("codigo"),resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),resultados.getInt("año_publicacion"),resultados.getDouble("puntuacion"));
				listaLibros.add(libro);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaLibros;
	}
	
	public static List<Libro> consultarLibroMenosPrestado() throws ExcepcionesLibro, BDException{
		List<Libro> listaLibros = new ArrayList<Libro>();
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT l.*, COUNT(p.codigo_libro) AS veces_prestado\r\n"
					+ "FROM libro l\r\n"
					+ "JOIN prestamo p ON l.codigo = p.codigo_libro\r\n"
					+ "GROUP BY l.codigo, l.isbn, l.titulo, l.escritor, l.\"año_publicacion\", l.puntuacion\r\n"
					+ "HAVING veces_prestado = (\r\n"
					+ "    SELECT MIN(cantidad) FROM (\r\n"
					+ "        SELECT COUNT(p2.codigo_libro) AS cantidad\r\n"
					+ "        FROM prestamo p2\r\n"
					+ "        GROUP BY p2.codigo_libro\r\n"
					+ "    )\r\n"
					+ ");";
			ps = conexion.prepareStatement(query);
			ResultSet resultados = ps.executeQuery();

			while (resultados.next()) {
				Libro libro = new Libro(resultados.getInt("codigo"),resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),resultados.getInt("año_publicacion"),resultados.getDouble("puntuacion"));
				listaLibros.add(libro);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaLibros;
	}
	
	public static List<Libro> consultarLibrosPrestadosInferiorMedia() throws ExcepcionesLibro, BDException{
		List<Libro> listaLibros = new ArrayList<Libro>();
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT l.*, COUNT(p.codigo_libro) AS veces_prestado\r\n"
					+ "FROM libro l\r\n"
					+ "JOIN prestamo p ON l.codigo = p.codigo_libro\r\n"
					+ "GROUP BY l.codigo, l.isbn, l.titulo, l.escritor, l.\"año_publicacion\", l.puntuacion\r\n"
					+ "HAVING veces_prestado > ("
					+ "    SELECT AVG(cantidad) FROM ("
					+ "        SELECT COUNT(p2.codigo_libro) AS cantidad"
					+ "        FROM prestamo p2"
					+ "        GROUP BY p2.codigo_libro));";
			ps = conexion.prepareStatement(query);
			ResultSet resultados = ps.executeQuery();

			while (resultados.next()) {
				Libro libro = new Libro(resultados.getInt("codigo"),resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),resultados.getInt("año_publicacion"),resultados.getDouble("puntuacion"));
				listaLibros.add(libro);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaLibros;
	}

}
