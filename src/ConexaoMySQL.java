/**
 *
 * @author mdonato
 */

/*
    Banco de dados default = chamar de modo static
    Alterar dados = instanciar classe e depois charmar de modo static
*/

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoMySQL{
    private static final String driver = "com.mysql.jdbc.Driver";
    private static String server = "localhost:3306";
    private static String user = "root";
    private static String password = "password";
    private static String database = "bd";
    private static String status = "Não conectado!";
    
    public ConexaoMySQL(String server, String user, String password, String database){
        ConexaoMySQL.server = server;
        ConexaoMySQL.user = user;
        ConexaoMySQL.password = password;
        ConexaoMySQL.database = database;
    }
    
    public static Connection abreConexaoMySQL(){
        Connection conexao = null;
        
        try{
            String url = "jdbc:mysql://" + ConexaoMySQL.server + "/" + ConexaoMySQL.database;
            
           
            try {
                Class.forName(ConexaoMySQL.driver).newInstance();
                conexao = DriverManager.getConnection(url, ConexaoMySQL.user, ConexaoMySQL.password);
            } catch (SQLException ex) {
                ConexaoMySQL.status = "Erro de conexao!";
                return null;
            } catch (InstantiationException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(conexao != null){
                ConexaoMySQL.status = "Conectado!";
                
            }
            
            return conexao;
                    
        }catch(ClassNotFoundException e){
            ConexaoMySQL.status = "Erro de driver!";
            return null;
        }    
    }
    
    public static boolean fechaConexao(){ 
        try { 
            ConexaoMySQL.abreConexaoMySQL().close();
            return true; 
        } catch (SQLException e) { 
            return false; 
        }
    }
    
}
