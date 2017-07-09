package inf1803.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import inf1803.db.ConnectionHandler;
import inf1803.utils.Util;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class ConnectionHandler {
	
	//Variaveis Locais
	private static Connection con = null;
	private static Statement comando;
	
	
	//Metodos Privados
	private static Connection getConnection() throws ClassNotFoundException,SQLException {
		try {
		      Class.forName("org.sqlite.JDBC");
		      con = DriverManager.getConnection("jdbc:sqlite:inf1803_lista1.db");
	    } catch ( Exception e ) {
	      Util.printError("Erro ao conectar no banco de dados", e);
	      throw new RuntimeException(e);
	    }
		return con;
	}
	
	//Metodos Publicos
	public static Statement openConnection() {
		try {
			con = ConnectionHandler.getConnection();
			comando = con.createStatement();
		} catch (ClassNotFoundException e) {
			Util.printError("Erro ao carregar o driver", null);
		} catch (SQLException e) {
			Util.printError("Erro ao conectar", e);
		}
		return comando;
	}
	
	public static void closeConnection() {
			try {
				comando.close();
				con.close();
			} catch (SQLException e) {
				Util.printError("Erro ao fechar conex�o", e);
			}
		}
	
}
