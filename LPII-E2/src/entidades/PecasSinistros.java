package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import persistência.BD;

public class PecasSinistros {

    private final int peca_codigo;
    private final int sinistro_id;

    public PecasSinistros(int peca_codigo, int sinistro_id) {
        this.peca_codigo = peca_codigo;
        this.sinistro_id = sinistro_id;
    }

    public PecasSinistros(Pecas peca, Sinistro sinistro) {
        this(peca != null ? peca.getCodigo() : 0,
                sinistro != null ? sinistro.getId() : 0);
    }

    public int getPecaCodigo() {
        return peca_codigo;
    }

    public int getSinistroId() {
        return sinistro_id;
    }

    public String toString() {
        return peca_codigo + " / " + sinistro_id;
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

    public static Pecas[] buscarPecasPorSinistro(int sinistroId) {
        ArrayList<Pecas> visoes = new ArrayList<>();
        String sql = "SELECT p.codigo, p.nome, p.marca, p.preco, p.mao_obra_propria, "
                + "p.tipo_peca_mecanica, p.tipo_peca_lataria, p.dias_garantia, p.cor "
                + "FROM pecas_sinistros ps "
                + "JOIN pecas p ON p.codigo = ps.peca_codigo "
                + "WHERE ps.sinistro_id = ?";

        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setInt(1, sinistroId);
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

    public static boolean existePecasSinistros(int peca_codigo, int sinistro_id) {
        String sql = "SELECT COUNT(*) FROM pecas_sinistros WHERE peca_codigo = ? AND sinistro_id = ?";

        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setInt(1, peca_codigo);
            comando.setInt(2, sinistro_id);
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

        String sql = "INSERT INTO pecas_sinistros (peca_codigo, sinistro_id) VALUES (?, ?)";

        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setInt(1, peca.getCodigo());
            comando.setInt(2, sinistro.getId());
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

        String sql = "DELETE FROM pecas_sinistros WHERE peca_codigo = ? AND sinistro_id = ?";

        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setInt(1, peca.getCodigo());
            comando.setInt(2, sinistro.getId());
            comando.executeUpdate();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na remocao da associacao peca/sinistro no BD";
        }
    }
}
