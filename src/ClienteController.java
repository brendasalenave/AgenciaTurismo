
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
    
    public void cadastraCliente(){
        //Dados da query
        String nome = this.ui.getTexFildNome().getText();
        String endereco = this.ui.getFormattedTextFielData().getText();
        String fone = this.ui.getFormattedTextFielFone().getText();
        String data = this.ui.getFormattedTextFielData().getText();
        String rg = this.ui.getFormattedTextFieldRg().getText();
        String cpf = this.ui.getFormattedTextFielCpf().getText();
        
        //Conexao e query
        String query = "INSERT INTO cliente (rg, cpf, nome, data, endereco, telefone) "
                        + "VALUES ('" + rg + "', '" + cpf + "', '" + nome + "', '" + data + "', '" 
                        + endereco + "', '" +  fone + "')";
        
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
        }else{
        }

        ConexaoMySQL.fechaConexao();
    }
    
    public boolean verificaCadastro(){
        String rg = this.ui.getFormattedTextFieldRg().getText();
        String cpf = this.ui.getFormattedTextFielCpf().getText();
        
        String query_rg = "SELECT rg FROM cliente WHERE rg = '" + rg + "'";
        String query_cpf = "SELECT rg FROM cliente WHERE cpf = '" + cpf + "'";
        
        Connection conexao = ConexaoMySQL.abreConexaoMySQL();
        Statement stmt = null;
        ResultSet rs = null;
        
        try{
            stmt = conexao.createStatement();
            rs = stmt.executeQuery(query_rg);
            rs.last();
            if(rs.getRow() == 0){
                return false;
            }       
        }catch(SQLException ex){
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public void limpaCampos(){
        this.ui.getTexFildNome().setText("");
        this.ui.getTextFieldEnderecoCliente().setText("");
        this.ui.getFormattedTextFielCpf().setText("");
        this.ui.getFormattedTextFielData().setText("");
        this.ui.getFormattedTextFielFone().setText("");
        this.ui.getFormattedTextFieldRg().setText("");
    }
}
