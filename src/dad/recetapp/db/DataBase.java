package dad.recetapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Clase para abrir y cerrar conexiones con bases 
 * de datos mediante JDBC.
 * @author Fran Vargas
 */
public class DataBase {
	
	private static final ResourceBundle CONFIG = ResourceBundle.getBundle(DataBase.class.getPackage().getName() + ".database");
	private static Connection connection = null;
	
	private DataBase() {}
	
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = connect();
		}
		return connection;
	}
	
	private static final void registerDriver() {
		try {
			Class.forName(CONFIG.getString("db.driver.classname"));
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver JDBC");
		}
	}

	/**
	 * Abre una conexión con la base de datos especificada en los parámetros.
	 * @param url URL de conexión.
	 * @param username Nombre de usuario.
	 * @param password Contraseña.
	 * @return Conexión abierta.
	 * @throws SQLException En caso de no poder abrir la conexión por algún motivo.
	 */
	public static Connection connect(String url, String username, String password) throws SQLException {
		registerDriver();
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * Abre una conexión con la base de datos usando los parámetros del fichero
	 * de propiedades "database.properties".
	 * @return Conexión abierta.
	 * @throws SQLException En caso de no poder abrir la conexión por algún motivo.
	 */
	public static Connection connect() throws SQLException {
		return connect(CONFIG.getString("db.url"), CONFIG.getString("db.username"), CONFIG.getString("db.password"));
	}
	
	/**
	 * Cierra la conexión.
	 * @param connection Conexión abierta que queremos cerrar.
	 * @throws SQLException En caso de que no pueda cerrar la conexión por algún
	 * motivo.
	 */
	public static void disconnect(Connection connection) throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
	
	/**
	 * Cierra la conexión abierta previamente mediante "{@link #getConnection()}".
	 * @throws SQLException En caso de que no pueda cerrar la conexión por algún
	 * motivo. 
	 */
	public static void disconnect() throws SQLException {
		disconnect(connection);
		connection = null;
	}
	
	/**
	 * Comprueba la conexión con la base de datos.
	 * @return Devuelve "true" si abre y cierra la conexión sin errores. "false"
	 * en caso contrario.
	 */
	public static Boolean test() {
		boolean testOk = false;
		try {
			Connection c = DataBase.connect();
			DataBase.disconnect(c);
			testOk = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return testOk;
	}

}




