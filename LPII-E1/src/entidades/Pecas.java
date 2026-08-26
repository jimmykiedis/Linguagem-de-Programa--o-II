package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import persistência.BD;

public class Pecas {

    private static HashMap<String, Pecas> pecas = new HashMap();
    public static HashMap<String, Pecas> getPecas() {
        return pecas;
    }

    private static ArrayList<Pecas> pecas1 = new ArrayList();
    public static ArrayList<Pecas> getPecas1() {
        return pecas1;
    }

    private int codigo;
    private String nome;
    private String categoria;
    private double preco;
    private String tipo;
    private String cor;
    private boolean maoDeObra;

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCor() {
        return cor;
    }

    public boolean getMaoDeObra() {
        return maoDeObra;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setMaoDeObra(boolean maoDeObra) {
        this.maoDeObra = maoDeObra;
    }

    public Pecas(int codigo, String nome, String categoria,
                 double preco, String tipo, String cor,
                 boolean maoDeObra) {

        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.tipo = tipo;
        this.cor = cor;
        this.maoDeObra = maoDeObra;
    }

    public String toString() {
        return codigo + " - " + nome + " [" + categoria + "]";
    }

    public static Pecas[] getVisoes() {

        String sql = "SELECT Codigo, Nome FROM Pecas";

        ResultSet lista_resultados = null;
        ArrayList<Pecas> visoes = new ArrayList();

        try {

            PreparedStatement comando =
                    BD.conexão.prepareStatement(sql);

            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {

                int codigo =
                        lista_resultados.getInt("Codigo");

                String nome =
                        lista_resultados.getString("Nome");

                visoes.add(
                        new Pecas(codigo, nome, null,
                                  0, null, null, false)
                );
            }

            lista_resultados.close();
            comando.close();

        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
        }

        return visoes.toArray(new Pecas[visoes.size()]);
    }

    public static Pecas[] getVisões() {
        return getVisoes();
    }

    public Pecas getVisão() {
        return new Pecas(
                codigo,
                nome,
                categoria,
                preco,
                tipo,
                cor,
                maoDeObra
        );
    }

    public static Pecas buscarPecas(String nome) {

        String sql =
                "SELECT * FROM Pecas WHERE Nome = ?";

        ResultSet lista_resultados = null;
        Pecas pecas = null;

        try {

            PreparedStatement comando =
                    BD.conexão.prepareStatement(sql);

            comando.setString(1, nome);

            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {

                pecas = new Pecas(
                        lista_resultados.getInt("Codigo"),
                        lista_resultados.getString("Nome"),
                        lista_resultados.getString("Categoria"),
                        lista_resultados.getDouble("Preco"),
                        lista_resultados.getString("Tipo"),
                        lista_resultados.getString("Cor"),
                        lista_resultados.getBoolean("MaoDeObra")
                );
            }

            lista_resultados.close();
            comando.close();

        } catch (SQLException exceção_sql) {

            exceção_sql.printStackTrace();
            pecas = null;
        }

        return pecas;
    }

    public static String inserirPecas(Pecas pecas) {

        String sql =
                "INSERT INTO Pecas " +
                "(Codigo, Nome, Categoria, Preco, Tipo, Cor, MaoDeObra) " +
                "VALUES (?,?,?,?,?,?,?)";

        try {

            PreparedStatement comando =
                    BD.conexão.prepareStatement(sql);

            comando.setInt(1, pecas.getCodigo());
            comando.setString(2, pecas.getNome());
            comando.setString(3, pecas.getCategoria());
            comando.setDouble(4, pecas.getPreco());
            comando.setString(5, pecas.getTipo());
            comando.setString(6, pecas.getCor());
            comando.setBoolean(7, pecas.getMaoDeObra());

            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException exceção_sql) {

            exceção_sql.printStackTrace();

            return "Erro na Inserção da Peça no BD";
        }
    }

    public static String alterarPecas(Pecas pecas) {

        String sql =
                "UPDATE Pecas SET " +
                "Codigo = ?, " +
                "Categoria = ?, " +
                "Preco = ?, " +
                "Tipo = ?, " +
                "Cor = ?, " +
                "MaoDeObra = ? " +
                "WHERE Nome = ?";

        try {

            PreparedStatement comando =
                    BD.conexão.prepareStatement(sql);

            comando.setInt(1, pecas.getCodigo());
            comando.setString(2, pecas.getCategoria());
            comando.setDouble(3, pecas.getPreco());
            comando.setString(4, pecas.getTipo());
            comando.setString(5, pecas.getCor());
            comando.setBoolean(6, pecas.getMaoDeObra());
            comando.setString(7, pecas.getNome());

            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException exceção_sql) {

            exceção_sql.printStackTrace();

            return "Erro na Alteração da Peça no BD";
        }
    }

    public static String removerPecas(String nome) {

        String sql =
                "DELETE FROM Pecas WHERE Nome = ?";

        try {

            PreparedStatement comando =
                    BD.conexão.prepareStatement(sql);

            comando.setString(1, nome);

            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException exceção_sql) {

            exceção_sql.printStackTrace();

            return "Erro na Remoção da Peça no BD";
        }
    }
}
