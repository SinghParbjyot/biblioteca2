package Biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
import Biblioteca.excepciones.ExcepcionesSocio;
import Biblioteca.modelo.Socio;
import config.ConfigSQLLite;



public class GestorSocios {
	
	public static boolean validarCodigo(int cod) throws BDException{
		PreparedStatement ps = null;
	    Connection conexion = null;
	    boolean existe =false;

	    try {
	        //Primero Conexión a la base de datos
	        conexion = ConfigSQLLite.abrirConexion();

	        
	        String query = "Select codigo from socio where codigo = ?";
	        ps = conexion.prepareStatement(query);
	        
	        ps.setInt(1, cod);
	        
	        ResultSet resultados = ps.executeQuery();
	     // Usar executeUpdate() para operaciones UPDATE, INSERT O DELETE
	       //filas = ps.executeUpdate();
	        
	       while(resultados.next()) {
	    	   int codigo = resultados.getInt("codigo");
	       }
	     

	    } catch (SQLException e) {
	        throw new BDException(BDException.ERROR_QUERY + e.getMessage());
	    } finally {
	    	ConfigSQLLite.cerrarConexion(conexion);
	        
	    }

	    return existe;  
	    
	}
	public static boolean insertarSocio(Socio socio) throws BDException {

		Connection conexion = null;

		int filas = 0;

		try {

			// Conexi�n a la bd

			conexion = ConfigSQLLite.abrirConexion();

			String query = "INSERT INTO socio VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setInt(1, socio.getCodigo());

			ps.setString(2, socio.getDni());

			ps.setString(3, socio.getNombre()); 

			ps.setString(4, socio.getDomicilio());

			ps.setString(5,socio.getTelefono());

			ps.setString(6, socio.getCorreo());

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

	public static boolean eliminarSocio(int codigo) throws BDException, ExcepcionesSocio {

		Connection conexion = null;

		int filas = 0;

		try {

			if(estaPrestamo(codigo)) {

				throw new ExcepcionesSocio(ExcepcionesSocio.ERROR_CONSULTAR_SOCIOS_PRESTADOS);
			}

				conexion = ConfigSQLLite.abrirConexion();
				String query = "DELETE FROM socio where codigo = ?";

				PreparedStatement ps = conexion.prepareStatement(query);

				ps.setInt(1, codigo);
				filas = ps.executeUpdate();
			
			if(filas == 0) {
				throw new ExcepcionesSocio(ExcepcionesSocio.ERROR_ELIMINAR_);
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

	public static ArrayList<Socio> consultarSocios() throws BDException, ExcepcionesLibro {
		
		ArrayList<Socio> listaSocios = new ArrayList<Socio>();
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexi�n a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT * FROM socio";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);

			while (resultados.next()) {
				Socio socio = new Socio(resultados.getInt("codigo"),resultados.getString("dni"),
						resultados.getString("nombre"), resultados.getString("domicilio"),resultados.getString("telefono"),resultados.getString("correo"));
				listaSocios.add(socio);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		} finally {
			if (conexion != null) {
				ConfigSQLLite.cerrarConexion(conexion);
			}
		}
		return listaSocios;

	}

	public static ArrayList<Socio> consultarSocioPorLocalidadOrdenadosPorNombre(String domicilio) throws BDException, ExcepcionesLibro {
		ArrayList<Socio> listaSocios = new ArrayList<Socio>();
		PreparedStatement ps = null ;
		Connection conexion = null;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT * FROM socio WHERE domicilio like ? ORDER BY nombre asc";
			ps = conexion.prepareStatement(query);
					
			
			ps.setString(1, domicilio);
		
			ResultSet resultados = ps.executeQuery();
			
			
		

			while (resultados.next()) {
				Socio socio = new Socio(resultados.getInt("codigo"),resultados.getString("dni"),
						resultados.getString("nombre"), resultados.getString("domicilio"),resultados.getString("telefono"),resultados.getString("correo"));
				listaSocios.add(socio);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaSocios;
	}
	
	public static ArrayList<Socio> consultarSocioSinPrestamo() throws BDException, ExcepcionesLibro {
		ArrayList<Socio> listaSocios = new ArrayList<Socio>();
		PreparedStatement ps = null;
		Connection conexion = null;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			
			String query = "SELECT * FROM socio join prestamo on (socio.codigo = prestamo.codigo_socio) where prestamo.codigo_socio = null";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);

			while (resultados.next()) {
				Socio socio = new Socio(resultados.getInt("codigo"),resultados.getString("dni"),
						resultados.getString("nombre"), resultados.getString("domicilio"),resultados.getString("telefono"),resultados.getString("correo"));
				listaSocios.add(socio);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaSocios;
	}
	
	public static ArrayList<Socio> consultarSocioConPrestamosPorFecha(String fecha_inicio) throws BDException, ExcepcionesLibro {
		ArrayList<Socio> listaSocios = new ArrayList<Socio>();
		PreparedStatement ps = null;
		Connection conexion = null;
		int filas = 0;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			
			String query = "SELECT * FROM socio join prestamo on (socio.codigo = prestamo.codigo_socio) where fecha_inicio = ? ";
			Statement sentencia = conexion.createStatement();
			PreparedStatement pss = conexion.prepareStatement(query);
			
			ps.setString(1, fecha_inicio);

			
			
			ResultSet resultados = sentencia.executeQuery(query);

			while (resultados.next()) {
				Biblioteca.modelo.Socio socio = new Biblioteca.modelo.Socio(resultados.getInt("codigo"),resultados.getString("dni"),
						resultados.getString("nombre"), resultados.getString("domicilio"),resultados.getString("telefono"),resultados.getString("correo"));
				listaSocios.add(socio);
			}

		} catch (SQLException e) {
			throw new BDException(BDException.ERROR_QUERY + e.getMessage());
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaSocios;
	}

	public static ArrayList<Socio> consultarSocioConMasPrestamosSuperiorMedia() throws BDException {
		ArrayList<Socio> listaSocios = new ArrayList<Socio>();
		PreparedStatement ps = null;
		Connection conexion = null;
		int filas = 0;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			
			String query = "SELECT s.codigo, s.nombre, COUNT(p.codigo_socio) AS total_prestamos"
					+ "FROM socio "
					+ "JOIN prestamo p ON s.codigo = p.codigo_socio"
					+ "GROUP BY s.codigo, s.nombre"
					+ "HAVING COUNT(p.codigo_socio) > ("
					+ "    SELECT AVG(prestamos_por_socio)"
					+ "    FROM ("
					+ "        SELECT COUNT(codigo_socio) AS prestamos_por_socio"
					+ "        FROM prestamo"
					+ "        GROUP BY codigo_socio"
					+ "    )"
					+ "); ";
			
			
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);

			while (resultados.next()) {
				Socio socio = new Socio(resultados.getInt("codigo"),resultados.getString("dni"),
						resultados.getString("nombre"), resultados.getString("domicilio"),resultados.getString("telefono"),resultados.getString("correo"));
				listaSocios.add(socio);
			}

		} catch (SQLException e) {
			
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaSocios;
	}
	
	public static ArrayList<Socio> consultarSocioConMasPrestamos() throws BDException {
		ArrayList<Socio> listaSocios = new ArrayList<Socio>();
		PreparedStatement ps = null;
		Connection conexion = null;
		int filas = 0;

		try {
			// Conexión a la bd
			conexion = ConfigSQLLite.abrirConexion();
			
			String query = "SELECT s.codigo, s.nombre, COUNT(p.codigo_socio) AS total_prestamos"
					+ "FROM socio s"
					+ "JOIN prestamo p ON s.codigo = p.codigo_socio"
					+ "GROUP BY s.codigo, s.nombre"
					+ "HAVING COUNT(p.codigo_socio) = ("
					+ "    SELECT COUNT(codigo_socio) AS max_prestamos"
					+ "    FROM prestamo"
					+ "    GROUP BY codigo_socio"
					+ "    ORDER BY max_prestamos DESC"
					+ "    LIMIT 1"
					+ ")"
					+ "ORDER BY total_préstamos DESC; ";
			
			
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(query);

			while (resultados.next()) {
				Socio socio = new Socio(resultados.getInt("codigo"),resultados.getString("dni"),
						resultados.getString("nombre"), resultados.getString("domicilio"),resultados.getString("telefono"),resultados.getString("correo"));
				listaSocios.add(socio);
			}

		} catch (SQLException e) {
			
		}

		finally {
			ConfigSQLLite.cerrarConexion(conexion);
		}

		return listaSocios;
	}
	
	public static boolean estaPrestamo(int codigoSocio) throws BDException {
		Connection conexion = null;
		try {
			conexion = ConfigSQLLite.abrirConexion();
			String query = "SELECT * FROM prestamo WHERE codigo_socio = ? and fecha_devolucion is null";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, codigoSocio);

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

}
