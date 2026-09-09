package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import persistência.BD;

public class Sinistro {

    public enum GrauMonta {
        PEQUENA("pequena"), MEDIA("media"), GRANDE("grande");
        private final String texto;
        GrauMonta(String texto) { this.texto = texto; }
        public static GrauMonta fromTexto(String texto) {
            if (texto == null) throw new IllegalArgumentException("Grau de monta não informado");
            String normalizado = texto.trim().toLowerCase(Locale.ROOT)
                    .replace("á", "a").replace("à", "a").replace("â", "a")
                    .replace("ã", "a").replace("é", "e").replace("ê", "e")
                    .replace("í", "i").replace("ó", "o").replace("ô", "o")
                    .replace("õ", "o").replace("ú", "u").replace("ç", "c");
            if (normalizado.contains("pequena")) return PEQUENA;
            if (normalizado.contains("media")) return MEDIA;
            if (normalizado.contains("grande")) return GRANDE;
            throw new IllegalArgumentException("Grau de monta inválido: " + texto);
        }
        @Override public String toString() { return texto; }
    }

    private int id;
    private String segurado;
    private String telefone;
    private String cidade;
    private GrauMonta grau_monta;
    private boolean perda_total;
    private ArrayList<Pecas> pecas = new ArrayList<Pecas>();

    public Sinistro(int id, String segurado, String telefone, String cidade,
            GrauMonta grau_monta, boolean perda_total) {
        this.id = id;
        this.segurado = segurado;
        this.telefone = telefone;
        this.cidade = cidade;
        this.grau_monta = grau_monta;
        this.perda_total = perda_total;
    }

    public Sinistro(String segurado, String telefone, String cidade,
            GrauMonta grau_monta, boolean perda_total) {
        this(0, segurado, telefone, cidade, grau_monta, perda_total);
    }

    public int getId() { return id; }
    public String getSegurado() { return segurado; }
    public String getTelefone() { return telefone; }
    public String getCidade() { return cidade; }
    public GrauMonta getGrauMonta() { return grau_monta; }
    public boolean getPerdaTotal() { return perda_total; }
    public boolean isPerdaTotal() { return perda_total; }
    public Pecas[] getPecas() { return pecas.toArray(new Pecas[0]); }
    public void setSegurado(String segurado) { this.segurado = segurado; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setGrauMonta(GrauMonta grau_monta) { this.grau_monta = grau_monta; }
    public void setPerdaTotal(boolean perda_total) { this.perda_total = perda_total; }
    public void setPecas(Pecas[] pecas) {
        this.pecas = new ArrayList<Pecas>();
        if (pecas != null) for (Pecas peca : pecas) if (peca != null) this.pecas.add(peca);
    }

    @Override
    public String toString() {
        return "#" + id + " - " + segurado + " - " + (cidade != null ? cidade : "")
                + " (" + grau_monta + ")";
    }

    private static Sinistro criarVisao(ResultSet resultado) throws SQLException {
        return new Sinistro(resultado.getInt("id"), resultado.getString("segurado"),
                resultado.getString("telefone"), resultado.getString("cidade"),
                GrauMonta.fromTexto(resultado.getString("grau_monta")),
                resultado.getBoolean("perda_total"));
    }

    public static Sinistro[] getVisoes() {
        ArrayList<Sinistro> visoes = new ArrayList<Sinistro>();
        String sql = "SELECT id, segurado, telefone, cidade, grau_monta, perda_total FROM sinistros ORDER BY id";
        try (PreparedStatement comando = BD.conexao.prepareStatement(sql);
                ResultSet resultados = comando.executeQuery()) {
            while (resultados.next()) visoes.add(criarVisao(resultados));
        } catch (SQLException | IllegalArgumentException excecao) { excecao.printStackTrace(); }
        return visoes.toArray(new Sinistro[0]);
    }

    public static Sinistro buscarSinistro(int id) {
        String sql = "SELECT id, segurado, telefone, cidade, grau_monta, perda_total FROM sinistros WHERE id = ?";
        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            try (ResultSet resultados = comando.executeQuery()) {
                if (!resultados.next()) return null;
                Sinistro sinistro = criarVisao(resultados);
                sinistro.setPecas(PecasSinistros.buscarPecasPorSinistro(id));
                return sinistro;
            }
        } catch (SQLException | IllegalArgumentException excecao) {
            excecao.printStackTrace();
            return null;
        }
    }

    public static String inserirSinistro(Sinistro sinistro) {
        String sql = "INSERT INTO sinistros (segurado, telefone, cidade, grau_monta, perda_total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement comando = BD.conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, sinistro.getSegurado());
            comando.setString(2, sinistro.getTelefone());
            comando.setString(3, sinistro.getCidade());
            comando.setString(4, sinistro.getGrauMonta().toString());
            comando.setBoolean(5, sinistro.getPerdaTotal());
            comando.executeUpdate();
            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (chaves.next()) sinistro.id = chaves.getInt(1);
            }
            return sinistro.id > 0 ? null : "Erro ao obter o ID do Sinistro criado";
        } catch (SQLException excecao) {
            excecao.printStackTrace();
            return "Erro na Insercao do Sinistro no BD";
        }
    }

    public static String alterarSinistro(Sinistro sinistro) {
        String sql = "UPDATE sinistros SET segurado = ?, telefone = ?, cidade = ?, grau_monta = ?, perda_total = ? WHERE id = ?";
        try (PreparedStatement comando = BD.conexao.prepareStatement(sql)) {
            comando.setString(1, sinistro.getSegurado());
            comando.setString(2, sinistro.getTelefone());
            comando.setString(3, sinistro.getCidade());
            comando.setString(4, sinistro.getGrauMonta().toString());
            comando.setBoolean(5, sinistro.getPerdaTotal());
            comando.setInt(6, sinistro.getId());
            return comando.executeUpdate() == 1 ? null : "Sinistro não cadastrado";
        } catch (SQLException excecao) {
            excecao.printStackTrace();
            return "Erro na Alteracao do Sinistro no BD";
        }
    }

    public static String removerSinistro(int id) {
        try (PreparedStatement comando = BD.conexao.prepareStatement("DELETE FROM sinistros WHERE id = ?")) {
            comando.setInt(1, id);
            return comando.executeUpdate() == 1 ? null : "Sinistro não cadastrado";
        } catch (SQLException excecao) {
            excecao.printStackTrace();
            return "Erro na Remocao do Sinistro no BD";
        }
    }
}
