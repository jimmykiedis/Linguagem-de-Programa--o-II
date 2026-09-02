package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import persistência.BD;

public class PecasSinistros {

    private final int peca_codigo;
    private final String sinistro_segurado;

    public PecasSinistros(int peca_codigo, String sinistro_segurado) {
        this.peca_codigo = peca_codigo;
        this.sinistro_segurado = sinistro_segurado;
    }

    public PecasSinistros(Pecas peca, Sinistro sinistro) {
        this(peca != null ? peca.getCodigo() : 0,
                sinistro != null ? sinistro.getSegurado() : null);
    }

    public int getPecaCodigo() {
        return peca_codigo;
    }

    public String getSinistroSegurado() {
        return sinistro_segurado;
    }

    public String toString() {
        return peca_codigo + " / " + sinistro_segurado;
    }

    private static Pecas criarPeca(ResultSet resultado) throws SQLException {
        int codigo = resultado.getInt("codigo");
        String nome = resultado.getString("nome");
        Pecas.MarcaPeca marca = Pecas.MarcaPeca.fromTexto(resultado.getString("marca"));
        double preco = resultado.getDouble("preco");
        boolean mao_obra_propria = resultado.getBoolean("mao_obra_propria");
        String tipo_peca_mecanica = resultado.getString("tipo_peca_mecanica");
        String tipo_peca_lataria = resultado.getString("tipo_peca_lataria");
        Integer dias_garantia = resultado.getObject("dias_garantia") == null
                ? null : resultado.getInt("dias_garantia");
        String cor = resultado.getString("cor");

        Pecas.TipoPeca tipo = null;
        if (tipo_peca_mecanica != null && !tipo_peca_mecanica.trim().isEmpty()) {
            tipo = Pecas.TipoPeca.MECANICA;
        } else if (tipo_peca_lataria != null && !tipo_peca_lataria.trim().isEmpty()) {
            tipo = Pecas.TipoPeca.LATARIA;
        }

        return new Pecas(codigo, nome, marca, preco, tipo, cor, dias_garantia,
                mao_obra_propria);
    }

    public static Pecas[] buscarPecasPorSinistro(String segurado) {
        ArrayList<Pecas> visoes = new ArrayList<>();
        String sql = "SELECT p.codigo, p.nome, p.marca, p.preco, p.mao_obra_propria, "
                + "p.tipo_peca_mecanica, p.tipo_peca_lataria, p.dias_garantia, p.cor "
                + "FROM pecas_sinistros ps "
                + "JOIN pecas p ON p.codigo = ps.peca_codigo "
                + "WHERE ps.sinistro_segurado = ?";

        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setString(1, segurado);
            try (ResultSet resultados = comando.executeQuery()) {
                while (resultados.next()) {
                    try {
                        visoes.add(criarPeca(resultados));
                    } catch (IllegalArgumentException excecao_enum) {
                        // Ignora peças com dados fora do domínio esperado.
                    }
                }
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }

        return visoes.toArray(new Pecas[0]);
    }

    public static boolean existePecasSinistros(int peca_codigo, String sinistro_segurado) {
        String sql = "SELECT COUNT(*) FROM pecas_sinistros WHERE peca_codigo = ? AND sinistro_segurado = ?";

        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setInt(1, peca_codigo);
            comando.setString(2, sinistro_segurado);
            try (ResultSet resultados = comando.executeQuery()) {
                return resultados.next() && resultados.getInt(1) > 0;
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return false;
        }
    }

    public static String inserirPecasSinistros(Pecas peca, Sinistro sinistro) {
        if (peca == null || sinistro == null) {
            return "Peca ou sinistro nao informado";
        }

        String sql = "INSERT INTO pecas_sinistros (peca_codigo, sinistro_segurado) VALUES (?, ?)";

        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setInt(1, peca.getCodigo());
            comando.setString(2, sinistro.getSegurado());
            comando.executeUpdate();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na insercao da associacao peca/sinistro no BD";
        }
    }

    public static String removerPecasSinistros(Pecas peca, Sinistro sinistro) {
        if (peca == null || sinistro == null) {
            return "Peca ou sinistro nao informado";
        }

        String sql = "DELETE FROM pecas_sinistros WHERE peca_codigo = ? AND sinistro_segurado = ?";

        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setInt(1, peca.getCodigo());
            comando.setString(2, sinistro.getSegurado());
            comando.executeUpdate();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na remocao da associacao peca/sinistro no BD";
        }
    }
}
