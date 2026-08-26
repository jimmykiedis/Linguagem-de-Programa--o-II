package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import persistência.BD;

public class Pecas {

    public enum CategoriaPeca {
        OEM("OEM"),
        ORIGINAL("Original"),
        GENUINA("Genuína");

        private final String texto;

        CategoriaPeca(String texto) {
            this.texto = texto;
        }

        public static CategoriaPeca fromTexto(String texto) {
            if (texto == null) {
                throw new IllegalArgumentException("Categoria da peça não informada");
            }

            String normalizado = texto.trim().toLowerCase(Locale.ROOT);

            if (normalizado.equals("oem")) return OEM;
            if (normalizado.equals("original")) return ORIGINAL;
            if (normalizado.equals("genuina") || normalizado.equals("genuína")) return GENUINA;

            throw new IllegalArgumentException("Categoria da peça inválida: " + texto);
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    public enum TipoPeca {
        MOTOR("motor"),
        INTERNO("interno"),
        EXTERNO("externo"),
        SUSPENSAO("suspensão");

        private final String texto;

        TipoPeca(String texto) {
            this.texto = texto;
        }

        public static TipoPeca fromTexto(String texto) {
            if (texto == null) {
                throw new IllegalArgumentException("Tipo da peça não informado");
            }

            String normalizado = texto.trim().toLowerCase(Locale.ROOT);

            if (normalizado.equals("motor")) return MOTOR;
            if (normalizado.equals("interno")) return INTERNO;
            if (normalizado.equals("externo")) return EXTERNO;
            if (normalizado.equals("suspensao") || normalizado.equals("suspensão")) return SUSPENSAO;

            throw new IllegalArgumentException("Tipo da peça inválido: " + texto);
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    private int codigo;
    private String nome;
    private CategoriaPeca categoria;
    private double preco;
    private TipoPeca tipo;
    private String cor;
    private boolean mao_obra_propria;

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public CategoriaPeca getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public TipoPeca getTipo() {
        return tipo;
    }

    public String getCor() {
        return cor;
    }

    public boolean getMaoObraPropria() {
        return mao_obra_propria;
    }

    public boolean isMaoObraPropria() {
        return mao_obra_propria;
    }

    public boolean getMaoDeObra() {
        return mao_obra_propria;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(CategoriaPeca categoria) {
        this.categoria = categoria;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTipo(TipoPeca tipo) {
        this.tipo = tipo;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setMaoObraPropria(boolean mao_obra_propria) {
        this.mao_obra_propria = mao_obra_propria;
    }

    public void setMaoDeObra(boolean mao_de_obra) {
        this.mao_obra_propria = mao_de_obra;
    }

    public Pecas(int codigo, String nome, CategoriaPeca categoria,
                 double preco, TipoPeca tipo, String cor,
                 boolean mao_obra_propria) {

        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.tipo = tipo;
        this.cor = cor;
        this.mao_obra_propria = mao_obra_propria;
    }

    public String toString() {
        return codigo + " - " + nome + " [" + categoria + "]";
    }

    public static Pecas[] getVisoes() {

        String sql = "SELECT Codigo, Nome, Categoria FROM Pecas";

        ResultSet lista_resultados = null;
        ArrayList<Pecas> visoes = new ArrayList();

        try {

            PreparedStatement comando =
                    BD.conexao.prepareStatement(sql);

            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {

                try {
                    int codigo =
                            lista_resultados.getInt("Codigo");

                    String nome =
                            lista_resultados.getString("Nome");

                    String categoria =
                            lista_resultados.getString("Categoria");

                    visoes.add(new Pecas(
                            codigo,
                            nome,
                            CategoriaPeca.fromTexto(categoria),
                            0,
                            null,
                            null,
                            false
                    ));
                } catch (IllegalArgumentException excecao_enum) {
                    // Ignora registros com valor fora do domínio esperado.
                }
            }

            lista_resultados.close();
            comando.close();

        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }

        return visoes.toArray(new Pecas[visoes.size()]);
    }

    public Pecas getVisao() {
        return new Pecas(
                codigo,
                nome,
                categoria,
                preco,
                tipo,
                cor,
                mao_obra_propria
        );
    }

    public static Pecas buscarPecas(String nome) {

        String sql =
                "SELECT * FROM Pecas WHERE Nome = ?";

        ResultSet lista_resultados = null;
        Pecas pecas = null;

        try {

            PreparedStatement comando =
                    BD.conexao.prepareStatement(sql);

            comando.setString(1, nome);

            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {

                try {
                    pecas = new Pecas(
                            lista_resultados.getInt("Codigo"),
                            lista_resultados.getString("Nome"),
                            CategoriaPeca.fromTexto(
                                    lista_resultados.getString("Categoria")
                            ),
                            lista_resultados.getDouble("Preco"),
                            TipoPeca.fromTexto(lista_resultados.getString("Tipo")),
                            lista_resultados.getString("Cor"),
                            lista_resultados.getBoolean("MaoObraPropria")
                    );
                } catch (IllegalArgumentException excecao_enum) {
                    pecas = null;
                }
            }

            lista_resultados.close();
            comando.close();

        } catch (SQLException excecao_sql) {

            excecao_sql.printStackTrace();
            pecas = null;
        }

        return pecas;
    }

    public static String inserirPecas(Pecas pecas) {

        String sql =
                "INSERT INTO Pecas " +
                "(Codigo, Nome, Categoria, Preco, Tipo, Cor, MaoObraPropria) " +
                "VALUES (?,?,?,?,?,?,?)";

        try {

            PreparedStatement comando =
                    BD.conexao.prepareStatement(sql);

            comando.setInt(1, pecas.getCodigo());
            comando.setString(2, pecas.getNome());
            comando.setString(3, pecas.getCategoria().toString());
            comando.setDouble(4, pecas.getPreco());
            comando.setString(5, pecas.getTipo().toString());
            comando.setString(6, pecas.getCor());
            comando.setBoolean(7, pecas.getMaoObraPropria());

            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException excecao_sql) {

            excecao_sql.printStackTrace();

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
                "MaoObraPropria = ? " +
                "WHERE Nome = ?";

        try {

            PreparedStatement comando =
                    BD.conexao.prepareStatement(sql);

            comando.setInt(1, pecas.getCodigo());
            comando.setString(2, pecas.getCategoria().toString());
            comando.setDouble(3, pecas.getPreco());
            comando.setString(4, pecas.getTipo().toString());
            comando.setString(5, pecas.getCor());
            comando.setBoolean(6, pecas.getMaoObraPropria());
            comando.setString(7, pecas.getNome());

            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException excecao_sql) {

            excecao_sql.printStackTrace();

            return "Erro na Alteração da Peça no BD";
        }
    }

    public static String removerPecas(String nome) {

        String sql =
                "DELETE FROM Pecas WHERE Nome = ?";

        try {

            PreparedStatement comando =
                    BD.conexao.prepareStatement(sql);

            comando.setString(1, nome);

            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException excecao_sql) {

            excecao_sql.printStackTrace();

            return "Erro na Remoção da Peça no BD";
        }
    }
}
