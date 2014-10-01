/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dalvan.login.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author filial
 */
public class Conexao {
    public static Connection conectar(){
        Connection con = null;
        try{
            Class.forName("org.postgresql.Driver");
            
			String nomeServidor = "localhost:5432";
			String database = "login";
			String url = "jdbc:postgresql://" + nomeServidor + "/" + database;
			String username = "postgres";
			String password = "postgres";
                        
            con = DriverManager.getConnection(url, username, password);
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        
        return con;
    }
    
    public static void desconectar(Connection con){
        try{
           con.close();  
        } catch(SQLException e){
            e.printStackTrace();
        }
       
    }
}
