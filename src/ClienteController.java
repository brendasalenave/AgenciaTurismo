
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mdonato
 */
public class ClienteController {
    ClienteUI ui;
    
    public ClienteController(ClienteUI ui){
        this.ui = ui;
    }
    
    public boolean cadastraCliente(){
        //Dados da query
        String nome = this.ui.getTexFildNome().getText();
        String endereco = this.ui.getFormattedTextFielData().getText();
        String fone = this.ui.getFormattedTextFielFone().getText();
        String data = this.ui.getFormattedTextFielData().getText();
        String rg = this.ui.getFormattedTextFieldRg().getText();
        String cpf = this.ui.getFormattedTextFielCpf().getText();
        
        //Conexao e query ERRO NA QUERY! ARRUMAR URGENTE :D
        String query = "INSERT INTO cliente (rg, cpf, nome, data, endereco, telefone) "
                        + "VALUES (" + rg + ", " + cpf + ", " + nome + ", " + data + ", " 
                        + endereco + ", " +  fone + ")";
        
        Connection conexao = ConexaoMySQL.abreConexaoMySQL();
        Statement stmt = null;
        try {
            stmt = conexao.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ResultSet result;
        try {
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return true;
    }
}
