package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistência.BD;
import java.util.ArrayList;
import java.util.HashMap;

public class Seguradora {
    
    private static HashMap<String, Seguradora> seguradoras = new HashMap();
    public static HashMap<String, Seguradora> getSeguradoras() { return seguradoras; }
    
    private static ArrayList<Seguradora> seguradoras1 = new ArrayList();
    public static ArrayList<Seguradora> getSeguradoras1() { return seguradoras1; }

    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public double getCoberturaPercentual() { return coberturaPercentual; }

    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setCoberturaPercentual(double coberturaPercentual) { this.coberturaPercentual = coberturaPercentual; }
    
    private String nome, cidade;
    private double coberturaPercentual;
    
    public Seguradora(String nome, String cidade, double coberturaPercentual) {
        this.nome = nome;
        this.cidade = cidade;
        this.coberturaPercentual = coberturaPercentual;
    }
    
    public String toString() {
        return nome + " [" + cidade + "]";
    }
    
    public Seguradora getVisão() {
        return new Seguradora(nome, cidade);
    }
    
    public Seguradora(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }
    
    public static Seguradora[] getVisões() {
        String sql = "SELECT Nome, Cidade FROM Seguradoras";
        ResultSet lista_resultados = null;
        ArrayList<Seguradora> visões = new ArrayList();
        
        try {
            PreparedStatement comando = BD.conexão.prepareStatement(sql);
            lista_resultados = comando.executeQuery();
            
            while (lista_resultados.next()) {
                String nome = lista_resultados.getString("Nome");
                String cidade = lista_resultados.getString("Cidade");
                visões.add(new Seguradora(nome, cidade));
            }
            
            lista_resultados.close();
            comando.close();
            
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
        }
        
        return visões.toArray(new Seguradora[visões.size()]);
    }

    public static Seguradora buscarSeguradora(String nome) {
        String sql = "SELECT * FROM Seguradoras WHERE Nome = ?";
        ResultSet lista_resultados = null;
        Seguradora seguradora = null;
        
        try {
            PreparedStatement comando = BD.conexão.prepareStatement(sql);
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
            
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
            seguradora = null;
        }
        
        return seguradora;
    }
    
    public static String inserirSeguradora(Seguradora seguradora) {
        String sql = "INSERT INTO Seguradoras (Nome, Cidade, CoberturaPercentual) VALUES (?,?,?)";
        
        try {
            PreparedStatement comando = BD.conexão.prepareStatement(sql);
            
            comando.setString(1, seguradora.getNome());
            comando.setString(2, seguradora.getCidade());
            comando.setDouble(3, seguradora.getCoberturaPercentual());
            
            comando.executeUpdate();
            comando.close();
            
            return null;
            
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
            return "Erro na Inserção da Seguradora no BD";
        }
    }
    
    public static String alterarSeguradora(Seguradora seguradora) {
        String sql = "UPDATE Seguradoras SET Cidade = ?, CoberturaPercentual = ?"
                + " WHERE Nome = ?";
        
        try {
            PreparedStatement comando = BD.conexão.prepareStatement(sql);
            
            comando.setString(1, seguradora.getCidade());
            comando.setDouble(2, seguradora.getCoberturaPercentual());
            comando.setString(3, seguradora.getNome());
            
            comando.executeUpdate();
            comando.close();
            
            return null;
            
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
            return "Erro na Alteração da Seguradora no BD";
        }
    }
    
    public static String removerSeguradora(String nome) {
        String sql = "DELETE FROM Seguradoras WHERE Nome = ?";
        
        try {
            PreparedStatement comando = BD.conexão.prepareStatement(sql);
            
            comando.setString(1, nome);
            comando.executeUpdate();
            comando.close();
            
            return null;
            
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
            return "Erro na Remoção da Seguradora no BD";
        }
    }
}