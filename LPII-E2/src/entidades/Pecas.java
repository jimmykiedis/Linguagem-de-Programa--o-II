package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import persistência.BD;

public class Pecas {

    public enum MarcaPeca {
        OEM("oem"), ORIGINAL("original"), GENUINA("genuina");

        private final String texto;

        MarcaPeca(String texto) { this.texto = texto; }

        public static MarcaPeca fromTexto(String texto) {
            if (texto == null) throw new IllegalArgumentException("Marca da peca nao informada");
            String valor = texto.trim().toLowerCase(Locale.ROOT);
            for (MarcaPeca marca : values()) {
                if (marca.texto.equals(valor)) return marca;
            }
            throw new IllegalArgumentException("Marca da peca invalida: " + texto);
        }

        @Override
        public String toString() { return texto; }
    }

    public enum TipoPeca {
        MECANICA("mecanica"), LATARIA("lataria");

        private final String texto;

        TipoPeca(String texto) { this.texto = texto; }

        public static TipoPeca fromTexto(String texto) {
            if (texto == null) throw new IllegalArgumentException("Tipo da peca nao informado");
            String valor = texto.trim().toLowerCase(Locale.ROOT);
            for (TipoPeca tipo : values()) {
                if (tipo.texto.equals(valor)) return tipo;
            }
            throw new IllegalArgumentException("Tipo da peca invalido: " + texto);
        }

        @Override
        public String toString() { return texto; }
    }

    protected int codigo;
    protected String nome;
    protected MarcaPeca marca;
    protected double preco;
    protected TipoPeca tipo;
    protected boolean mao_obra_propria;
    protected Integer dias_garantia;
    protected String cor;

    protected Pecas(int codigo, String nome, MarcaPeca marca, double preco,
            TipoPeca tipo, boolean mao_obra_propria, Integer dias_garantia,
            String cor) {
        this.codigo = codigo;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.tipo = tipo;
        this.mao_obra_propria = mao_obra_propria;
        this.dias_garantia = dias_garantia;
        this.cor = cor;
    }

    public Pecas() { this(0, null, null, 0.0, null, false, null, null); }

    public Pecas(int codigo, String nome, MarcaPeca marca, double preco,
            TipoPeca tipo, String cor, Integer dias_garantia,
            boolean mao_obra_propria) {
        this(codigo, nome, marca, preco, tipo, mao_obra_propria, dias_garantia, cor);
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public MarcaPeca getMarca() { return marca; }
    public MarcaPeca getCategoria() { return marca; }
    public double getPreco() { return preco; }
    public TipoPeca getTipo() { return tipo; }
    public boolean getMaoObraPropria() { return mao_obra_propria; }
    public boolean isMaoObraPropria() { return mao_obra_propria; }
    public boolean getMaoDeObra() { return mao_obra_propria; }
    public Integer getDiasGarantia() { return dias_garantia; }
    public String getCor() { return cor; }

    public void setCodigo(int codigo) { this.codigo = codigo; }
    public void setNome(String nome) { this.nome = nome; }
    public void setMarca(MarcaPeca marca) { this.marca = marca; }
    public void setCategoria(MarcaPeca marca) { this.marca = marca; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setTipo(TipoPeca tipo) { this.tipo = tipo; }
    public void setMaoObraPropria(boolean valor) { this.mao_obra_propria = valor; }
    public void setMaoDeObra(boolean valor) { this.mao_obra_propria = valor; }
    public void setDiasGarantia(Integer valor) { this.dias_garantia = valor; }
    public void setCor(String cor) { this.cor = cor; }

    public Pecas getVisao() { return this; }

    @Override
    public String toString() { return codigo + " - " + nome + " [" + marca + "]"; }

    private static Pecas criarVisao(ResultSet resultado) throws SQLException {
        int codigo = resultado.getInt("codigo");
        String nome = resultado.getString("nome");
        MarcaPeca marca = MarcaPeca.fromTexto(resultado.getString("marca"));
        double preco = resultado.getDouble("preco");
        boolean mao_obra_propria = resultado.getBoolean("mao_obra_propria");
        String tipo_peca_mecanica = resultado.getString("tipo_peca_mecanica");
        String tipo_peca_lataria = resultado.getString("tipo_peca_lataria");
        Integer dias_garantia = resultado.getObject("dias_garantia") == null
                ? null : resultado.getInt("dias_garantia");
        String cor = resultado.getString("cor");

        TipoPeca tipo = null;
        if (tipo_peca_mecanica != null && !tipo_peca_mecanica.trim().isEmpty()) {
            tipo = TipoPeca.MECANICA;
        } else if (tipo_peca_lataria != null && !tipo_peca_lataria.trim().isEmpty()) {
            tipo = TipoPeca.LATARIA;
        }

        return new Pecas(codigo, nome, marca, preco, tipo, cor, dias_garantia,
                mao_obra_propria);
    }

    private static final String COLUNAS =
            "codigo, nome, marca, preco, mao_obra_propria, "
            + "tipo_peca_mecanica, tipo_peca_lataria, dias_garantia, cor";

    public static Pecas[] getVisoes() {
        ArrayList<Pecas> visoes = new ArrayList<>();
        try (PreparedStatement comando = BD.conexao.prepareStatement(
                "SELECT " + COLUNAS + " FROM pecas");
             ResultSet resultados = comando.executeQuery()) {
            while (resultados.next()) visoes.add(criarVisao(resultados));
        } catch (SQLException | IllegalArgumentException excecao) {
            excecao.printStackTrace();
        }
        return visoes.toArray(new Pecas[0]);
    }

    public static Pecas buscarPecas(int codigo) {
        try (PreparedStatement comando = BD.conexao.prepareStatement(
                "SELECT " + COLUNAS + " FROM pecas WHERE codigo = ?")) {
            comando.setInt(1, codigo);
            try (ResultSet resultados = comando.executeQuery()) {
                return resultados.next() ? criarVisao(resultados) : null;
            }
        } catch (SQLException | IllegalArgumentException excecao) {
            excecao.printStackTrace();
            return null;
        }
    }

    public static Pecas buscarPecas(String nome) {
        try (PreparedStatement comando = BD.conexao.prepareStatement(
                "SELECT " + COLUNAS + " FROM pecas WHERE nome = ?")) {
            comando.setString(1, nome);
            try (ResultSet resultados = comando.executeQuery()) {
                return resultados.next() ? criarVisao(resultados) : null;
            }
        } catch (SQLException | IllegalArgumentException excecao) {
            excecao.printStackTrace();
            return null;
        }
    }

    public static Pecas[] buscarPecasPorSinistro(String segurado) {
        ArrayList<Pecas> visoes = new ArrayList<>();
        String sql = "SELECT p." + COLUNAS.replace(", ", ", p.")
                + " FROM pecas p JOIN pecas_sinistros ps ON ps.peca_codigo = p.codigo"
                + " WHERE ps.sinistro_segurado = ?";
        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setString(1, segurado);
            try (ResultSet resultados = comando.executeQuery()) {
                while (resultados.next()) visoes.add(criarVisao(resultados));
            }
        } catch (SQLException | IllegalArgumentException excecao) {
            excecao.printStackTrace();
        }
        return visoes.toArray(new Pecas[0]);
    }

    private static void preencher(PreparedStatement comando, Pecas peca) throws SQLException {
        comando.setInt(1, peca.codigo);
        comando.setString(2, peca.nome);
        comando.setString(3, peca.marca.toString());
        comando.setDouble(4, peca.preco);
        comando.setBoolean(5, peca.mao_obra_propria);
        comando.setString(6, peca.tipo == TipoPeca.MECANICA ? TipoPeca.MECANICA.toString() : null);
        comando.setString(7, peca.tipo == TipoPeca.LATARIA ? TipoPeca.LATARIA.toString() : null);
        if (peca.dias_garantia == null) comando.setNull(8, java.sql.Types.INTEGER);
        else comando.setInt(8, peca.dias_garantia);
        comando.setString(9, peca.cor);
    }

    public static String inserirPecas(Pecas peca) {
        String sql = "INSERT INTO pecas (" + COLUNAS + ") VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            preencher(comando, peca);
            comando.executeUpdate();
            return null;
        } catch (SQLException | NullPointerException excecao) {
            excecao.printStackTrace();
            return "Erro na insercao da peca no BD";
        }
    }

    public static String alterarPecas(Pecas peca) {
        String sql = "UPDATE pecas SET nome=?, marca=?, preco=?, mao_obra_propria=?,"
                + " tipo_peca_mecanica=?, tipo_peca_lataria=?, dias_garantia=?, cor=?"
                + " WHERE codigo=?";
        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setString(1, peca.nome);
            comando.setString(2, peca.marca.toString());
            comando.setDouble(3, peca.preco);
            comando.setBoolean(4, peca.mao_obra_propria);
            comando.setString(5, peca.tipo == TipoPeca.MECANICA ? TipoPeca.MECANICA.toString() : null);
            comando.setString(6, peca.tipo == TipoPeca.LATARIA ? TipoPeca.LATARIA.toString() : null);
            if (peca.dias_garantia == null) comando.setNull(7, java.sql.Types.INTEGER);
            else comando.setInt(7, peca.dias_garantia);
            comando.setString(8, peca.cor);
            comando.setInt(9, peca.codigo);
            comando.executeUpdate();
            return null;
        } catch (SQLException | NullPointerException excecao) {
            excecao.printStackTrace();
            return "Erro na alteracao da peca no BD";
        }
    }

    public static String removerPecas(int codigo) {
        try (PreparedStatement comando = BD.conexao.prepareStatement(
                "DELETE FROM pecas WHERE codigo = ?")) {
            comando.setInt(1, codigo);
            comando.executeUpdate();
            return null;
        } catch (SQLException excecao) {
            excecao.printStackTrace();
            return "Erro na remocao da peca no BD";
        }
    }

    public static String removerPecas(String nome) {
        try (PreparedStatement comando = BD.conexao.prepareStatement(
                "DELETE FROM pecas WHERE nome = ?")) {
            comando.setString(1, nome);
            comando.executeUpdate();
            return null;
        } catch (SQLException excecao) {
            excecao.printStackTrace();
            return "Erro na remocao da peca no BD";
        }
    }
}
