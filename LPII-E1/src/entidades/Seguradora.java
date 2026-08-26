package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistência.BD;

public class Seguradora {

    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public double getCoberturaPercentual() { return cobertura_percentual; }

    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setCoberturaPercentual(double cobertura_percentual) { this.cobertura_percentual = cobertura_percentual; }
    
    private String nome, cidade;
    private double cobertura_percentual;
    
    public Seguradora(String nome, String cidade, double cobertura_percentual) {
        this.nome = nome;
        this.cidade = cidade;
        this.cobertura_percentual = cobertura_percentual;
    }
    
    public String toString() {
        return nome + " [" + cidade + "]";
    }
    
    public Seguradora getVisao() {
        return new Seguradora(nome, cidade);
    }
    
    public Seguradora(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }
    
    public static Seguradora[] getVisoes() {
        String sql = "SELECT Nome, Cidade FROM Seguradoras";
        ResultSet lista_resultados = null;
        java.util.ArrayList<Seguradora> visoes = new java.util.ArrayList();
        
        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            lista_resultados = comando.executeQuery();
            
            while (lista_resultados.next()) {
                String nome = lista_resultados.getString("Nome");
                String cidade = lista_resultados.getString("Cidade");
                visoes.add(new Seguradora(nome, cidade));
            }
            
            lista_resultados.close();
            comando.close();
            
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }
        
        return visoes.toArray(new Seguradora[visoes.size()]);
    }

    public static Seguradora buscarSeguradora(String nome) {
        String sql = "SELECT * FROM Seguradoras WHERE Nome = ?";
        ResultSet lista_resultados = null;
        Seguradora seguradora = null;
        
        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, nome);
            lista_resultados = comando.executeQuery();
            
            while (lista_resultados.next()) {
                seguradora = new Seguradora(
                    nome,
                    lista_resultados.getString("Cidade"),
                    lista_resultados.getDouble("CoberturaPercentual")
                );
            }
            
            lista_resultados.close();
            comando.close();
            
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            seguradora = null;
        }
        
        return seguradora;
    }
    
    public static String inserirSeguradora(Seguradora seguradora) {
        String sql = "INSERT INTO Seguradoras (Nome, Cidade, CoberturaPercentual) VALUES (?,?,?)";
        
        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            
            comando.setString(1, seguradora.getNome());
            comando.setString(2, seguradora.getCidade());
            comando.setDouble(3, seguradora.getCoberturaPercentual());
            
            comando.executeUpdate();
            comando.close();
            
            return null;
            
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Inserção da Seguradora no BD";
        }
    }
    
    public static String alterarSeguradora(Seguradora seguradora) {
        String sql = "UPDATE Seguradoras SET Cidade = ?, CoberturaPercentual = ?"
                + " WHERE Nome = ?";
        
        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            
            comando.setString(1, seguradora.getCidade());
            comando.setDouble(2, seguradora.getCoberturaPercentual());
            comando.setString(3, seguradora.getNome());
            
            comando.executeUpdate();
            comando.close();
            
            return null;
            
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Alteração da Seguradora no BD";
        }
    }
    
    public static String removerSeguradora(String nome) {
        String sql = "DELETE FROM Seguradoras WHERE Nome = ?";
        
        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            
            comando.setString(1, nome);
            comando.executeUpdate();
            comando.close();
            
            return null;
            
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Remoção da Seguradora no BD";
        }
    }
}
