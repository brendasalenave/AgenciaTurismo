
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

/**
 *
 * @author mdonato
 */
public class ClienteUpdateController {
    ClienteUpdateUI view;
    
    public ClienteUpdateController(ClienteUpdateUI view){
        this.view = view;
    }
    
    public void listaClientes(){
        JComboBox lista = view.getComboBoxClientes();
        Connection conexao = ConexaoMySQL.abreConexaoMySQL();
        Statement stmt = null;
        ResultSet rs = null;
        String query = "SELECT nome, rg FROM cliente";
        
        try{
            stmt = conexao.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                lista.addItem(rs.getString(1) + " [RG: " + rs.getString(2) + "]");
            }
        }catch(SQLException ex){
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConexaoMySQL.fechaConexao();
    }
    
    public void setDadosCliente(){
        String itemSelecionado = (String) view.getComboBoxClientes().getSelectedItem();
        String rg = itemSelecionado.replaceAll("[^1234567890]", "");
        String query = "SELECT cpf, nome, endereco, data, telefone  FROM cliente WHERE rg = '" + rg + "'";
        
        Connection conexao = ConexaoMySQL.abreConexaoMySQL();
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conexao.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                view.getTextFieldCpf().setText(rs.getString(1));
                view.getTextFieldRg().setText(rg);
                view.getTextFieldNome().setText(rs.getString(2));
                view.getTextFieldEndereco().setText(rs.getString(3));
                view.getFormattedTextFieldData().setText(rs.getString(4));
                view.getTextFieldFone().setText(rs.getString(5));
            }
        }catch(SQLException ex){
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ConexaoMySQL.fechaConexao();
        
        view.revalidate();
    }
    
    public boolean salvaAlteracoesCliente(){
        String rg = (String) view.getTextFieldRg().getText();
        String endereco = (String) view.getTextFieldEndereco().getText();
        String fone = (String) view.getTextFieldFone().getText();
        
        Connection conexao = ConexaoMySQL.abreConexaoMySQL();
        Statement stmt = null;
        String query = "UPDATE cliente SET telefone = '" + fone + "', endereco = '" + endereco + "' WHERE rg = '" + rg + "'";
        try {
            stmt = conexao.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteUpdateController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public boolean dropClienteSelecionado(){
        String itemSelecionado = (String) view.getComboBoxClientes().getSelectedItem();
        String rg = itemSelecionado.replaceAll("[^1234567890]", "");
        String query = "DELETE FROM cliente WHERE rg = '" + rg + "'";
        
        System.out.println(query);
        Connection conexao = ConexaoMySQL.abreConexaoMySQL();
        Statement stmt = null;
        try {
            stmt = conexao.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteUpdateController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        ConexaoMySQL.fechaConexao();
        view.revalidate();
        return true;
    }
}
