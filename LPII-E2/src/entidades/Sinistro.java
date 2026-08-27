package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import persistência.BD;

public class Sinistro {

    private int numero;
    private String cliente;
    private String telefone;

    public int getNumero() { return numero; }
    public String getCliente() { return cliente; }
    public String getTelefone() { return telefone; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Sinistro(int numero, String cliente, String telefone) {
        this.numero = numero;
        this.cliente = cliente;
        this.telefone = telefone;
    }

    public Sinistro(String cliente, String telefone) {
        this(0, cliente, telefone);
    }

    public int getSequencial() { return numero; }
    public void setSequencial(int sequencial) { this.numero = sequencial; }

    @Override
    public String toString() {
        return "[" + numero + "] " + cliente;
    }

    public Sinistro getVisao() {
        return new Sinistro(numero, cliente, telefone);
    }

    public static Sinistro[] getVisoes() {
        String sql = "SELECT numero, nome, telefone FROM sinistros";
        ResultSet lista_resultados = null;
        ArrayList<Sinistro> visoes = new ArrayList<>();

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                int numero = lista_resultados.getInt("numero");
                String cliente = lista_resultados.getString("nome");
                String telefone = lista_resultados.getString("telefone");
                visoes.add(new Sinistro(numero, cliente, telefone));
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }

        return visoes.toArray(new Sinistro[visoes.size()]);
    }

    public static Sinistro buscarSinistro(int numero) {
        String sql = "SELECT * FROM sinistros WHERE numero = ?";
        ResultSet lista_resultados = null;
        Sinistro sinistro = null;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, numero);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                sinistro = new Sinistro(
                        numero,
                        lista_resultados.getString("nome"),
                        lista_resultados.getString("telefone")
                );
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            sinistro = null;
        }

        return sinistro;
    }

    public static String inserirSinistro(Sinistro sinistro) {
        String sql = "INSERT INTO sinistros (numero, nome, telefone) VALUES (?,?,?)";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, sinistro.getNumero());
            comando.setString(2, sinistro.getCliente());
            comando.setString(3, sinistro.getTelefone());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Inserção do Sinistro no BD";
        }
    }

    public static String alterarSinistro(Sinistro sinistro) {
        String sql = "UPDATE sinistros SET nome = ?, telefone = ? WHERE numero = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro.getCliente());
            comando.setString(2, sinistro.getTelefone());
            comando.setInt(3, sinistro.getNumero());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Alteração do Sinistro no BD";
        }
    }

    public static String removerSinistro(int numero) {
        String sql = "DELETE FROM sinistros WHERE numero = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, numero);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Remoção do Sinistro no BD";
        }
    }

    public static int ultimoNumero() {
        String sql = "SELECT MAX(numero) FROM sinistros";
        ResultSet lista_resultados = null;
        int numero = 0;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                numero = lista_resultados.getInt(1);
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }

        return numero;
    }

    public static boolean existeSinistroMesmosAtributos(Sinistro sinistro) {
        String sql = "SELECT COUNT(numero) FROM sinistros WHERE nome = ? AND telefone = ?";
        ResultSet lista_resultados = null;
        int n_sinistros_mesmos_atributos = 0;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro.getCliente());
            comando.setString(2, sinistro.getTelefone());
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                n_sinistros_mesmos_atributos = lista_resultados.getInt(1);
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }

        return n_sinistros_mesmos_atributos > 0;
    }
}
