package Biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import Biblioteca.excepciones.BDException;
import Biblioteca.excepciones.ExcepcionesLibro;
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

	public static boolean eliminarSocio(Socio socio) throws BDException, ExcepcionesLibro {

		Connection conexion = null;

		int filas = 0;

		try {

			// Conexi�n a la bd

			conexion = ConfigSQLLite.abrirConexion();

			String query = "DELETE FROM socio where codigo = ?";

			PreparedStatement ps = conexion.prepareStatement(query);

			ps.setInt(1, socio.getCodigo());

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


}
