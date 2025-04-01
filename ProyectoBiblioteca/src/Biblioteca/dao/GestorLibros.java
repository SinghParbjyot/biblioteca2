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

	public static boolean estaLibro(int codigo) throws BDException, ExcepcionesLibro {
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT * FROM libro where codigo = ?";

			ps = conexion.prepareStatement(query);
			ps.setInt(1, codigo);
			ResultSet resultados = ps.executeQuery();

			while(resultados.next()) {
				int cod = resultados.getInt("codigo");

				if(cod == codigo) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}


		return false;

	}

	public static boolean insertarLibro(Libro libro) throws BDException, ExcepcionesLibro {

		Connection conexion = null;

		int filas = 0;

		try {

			// Conexi�n a la bd

			conexion = ConfigSQLLite.abrirConexion();

			if(estaLibro(libro.getCodigo())) {
				throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_INSERTAR_LIBRO);
			}

			String query = "INSERT INTO libro VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setInt(1, libro.getCodigo());

			ps.setString(2, libro.getIsbn());

			ps.setString(3, libro.getTitulo()); 

			ps.setString(4, libro.getEscritor());

			ps.setInt(5,libro.getAñoPublicacion());

			ps.setDouble(6, libro.getPuntuacion());

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

	public static boolean eliminarLibro(int codigo) throws BDException, ExcepcionesLibro {

		Connection conexion = null;

		int filas = 0;

		try {

			
			if(!estaLibro(codigo)) {
				throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_ELIMINAR_LIBRO);
			}
			
			if(GestorPrestamos.estaPrestamo(codigo, codSocio, fecha)) {
				throw new ExcepcionesLibro(ExcepcionesLibro.ERROR_ELIMINAR_LIBRO_PRESTADO);
			}
			conexion = ConfigSQLLite.abrirConexion();
			String query = "DELETE FROM libro where codigo = ?";

			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setInt(1, codigo);

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

}
