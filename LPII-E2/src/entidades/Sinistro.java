package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import persistência.BD;

public class Sinistro {

    public enum GrauMonta {
        PEQUENA("pequena"),
        MEDIA("media"),
        GRANDE("grande");

        private final String texto;

        GrauMonta(String texto) {
            this.texto = texto;
        }

        public static GrauMonta fromTexto(String texto) {
            if (texto == null) {
                throw new IllegalArgumentException("Grau de monta não informado");
            }

            String normalizado = texto.trim().toLowerCase(Locale.ROOT)
                    .replace("á", "a")
                    .replace("à", "a")
                    .replace("â", "a")
                    .replace("ã", "a")
                    .replace("é", "e")
                    .replace("ê", "e")
                    .replace("í", "i")
                    .replace("ó", "o")
                    .replace("ô", "o")
                    .replace("õ", "o")
                    .replace("ú", "u")
                    .replace("ç", "c");

            if (normalizado.contains("pequena")) return PEQUENA;
            if (normalizado.contains("media")) return MEDIA;
            if (normalizado.contains("grande")) return GRANDE;

            throw new IllegalArgumentException("Grau de monta inválido: " + texto);
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    private String segurado;
    private String telefone;
    private String cidade;
    private GrauMonta grau_monta;
    private boolean perda_total;
    private ArrayList<Pecas> pecas;

    public Sinistro(
            String segurado,
            String telefone,
            String cidade,
            GrauMonta grau_monta,
            boolean perda_total,
            ArrayList<Pecas> pecas
    ) {
        this.segurado = segurado;
        this.telefone = telefone;
        this.cidade = cidade;
        this.grau_monta = grau_monta != null ? grau_monta : GrauMonta.PEQUENA;
        this.perda_total = perda_total;
        this.pecas = pecas != null ? pecas : new ArrayList<Pecas>();
    }

    public Sinistro(
            String segurado,
            String telefone,
            String cidade,
            GrauMonta grau_monta,
            boolean perda_total
    ) {
        this(segurado, telefone, cidade, grau_monta, perda_total, new ArrayList<Pecas>());
    }

    public Sinistro(String segurado, String telefone, String cidade) {
        this(segurado, telefone, cidade, GrauMonta.PEQUENA, false);
    }

    public Sinistro(String segurado, String telefone) {
        this(segurado, telefone, null, GrauMonta.PEQUENA, false);
    }

    public String getSegurado() {
        return segurado;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public GrauMonta getGrauMonta() {
        return grau_monta;
    }

    public boolean getPerdaTotal() {
        return perda_total;
    }

    public boolean isPerdaTotal() {
        return perda_total;
    }

    public Pecas[] getPecas() {
        return pecas.toArray(new Pecas[pecas.size()]);
    }

    public void setSegurado(String segurado) {
        this.segurado = segurado;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setGrauMonta(GrauMonta grau_monta) {
        this.grau_monta = grau_monta != null ? grau_monta : GrauMonta.PEQUENA;
    }

    public void setPerdaTotal(boolean perda_total) {
        this.perda_total = perda_total;
    }

    public void setPecas(Pecas[] pecas) {
        this.pecas = new ArrayList<Pecas>();
        if (pecas != null) {
            for (Pecas peca : pecas) {
                if (peca != null) {
                    this.pecas.add(peca);
                }
            }
        }
    }

    public void adicionarPeca(Pecas peca) {
        if (peca != null) {
            if (pecas == null) {
                pecas = new ArrayList<Pecas>();
            }
            pecas.add(peca);
        }
    }

    @Override
    public String toString() {
        String cidade_texto = cidade != null ? cidade : "";
        return segurado + " - " + cidade_texto + " (" + grau_monta + ")";
    }

    public Sinistro getVisao() {
        return new Sinistro(
                segurado,
                telefone,
                cidade,
                grau_monta,
                perda_total,
                pecas
        );
    }

    private static Sinistro criarVisao(ResultSet resultado) throws SQLException {
        String segurado = resultado.getString("segurado");
        String telefone = resultado.getString("telefone");
        String cidade = resultado.getString("cidade");
        GrauMonta grau_monta = GrauMonta.fromTexto(resultado.getString("grau_monta"));
        boolean perda_total = resultado.getBoolean("perda_total");
        return new Sinistro(segurado, telefone, cidade, grau_monta, perda_total);
    }

    private static Sinistro criarVisaoLegado(ResultSet resultado) throws SQLException {
        String segurado = resultado.getString("nome");
        String telefone = resultado.getString("telefone");
        String cidade = null;
        GrauMonta grau_monta = GrauMonta.fromTexto(resultado.getString("grau_monta"));
        boolean perda_total = resultado.getBoolean("perda_total");
        return new Sinistro(segurado, telefone, cidade, grau_monta, perda_total);
    }

    public static Sinistro[] getVisoes() {
        String sql = "SELECT segurado, telefone, cidade, grau_monta, perda_total FROM sinistros";
        ResultSet lista_resultados = null;
        ArrayList<Sinistro> visoes = new ArrayList<>();

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                try {
                    visoes.add(criarVisao(lista_resultados));
                } catch (IllegalArgumentException excecao_enum) {
                    // Ignora registros com valor fora do domínio esperado.
                }
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            try {
                String sql_legado = "SELECT numero, nome, telefone, grau_monta, perda_total FROM sinistros";
                PreparedStatement comando_legado = BD.conexao.prepareStatement(sql_legado);
                lista_resultados = comando_legado.executeQuery();

                while (lista_resultados.next()) {
                    try {
                        visoes.add(criarVisaoLegado(lista_resultados));
                    } catch (IllegalArgumentException excecao_enum) {
                        // Ignora registros inválidos.
                    }
                }

                lista_resultados.close();
                comando_legado.close();
            } catch (SQLException excecao_legado) {
                excecao_legado.printStackTrace();
            }
        }

        return visoes.toArray(new Sinistro[visoes.size()]);
    }

    public static Sinistro buscarSinistro(String segurado) {
        String sql = "SELECT segurado, telefone, cidade, grau_monta, perda_total FROM sinistros WHERE segurado = ?";
        ResultSet lista_resultados = null;
        Sinistro sinistro = null;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, segurado);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                try {
                    sinistro = criarVisao(lista_resultados);
                    sinistro.setPecas(Pecas.buscarPecasPorSinistro(segurado));
                } catch (IllegalArgumentException excecao_enum) {
                    sinistro = null;
                }
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            try {
                String sql_legado = "SELECT numero, nome, telefone, grau_monta, perda_total FROM sinistros WHERE nome = ?";
                PreparedStatement comando_legado = BD.conexao.prepareStatement(sql_legado);
                comando_legado.setString(1, segurado);
                lista_resultados = comando_legado.executeQuery();

                while (lista_resultados.next()) {
                    try {
                        sinistro = criarVisaoLegado(lista_resultados);
                        sinistro.setPecas(new Pecas[0]);
                    } catch (IllegalArgumentException excecao_enum) {
                        sinistro = null;
                    }
                }

                lista_resultados.close();
                comando_legado.close();
            } catch (SQLException excecao_legado) {
                excecao_legado.printStackTrace();
                sinistro = null;
            }
        }

        return sinistro;
    }

    public static String inserirSinistro(Sinistro sinistro) {
        String sql = "INSERT INTO sinistros (segurado, telefone, cidade, grau_monta, perda_total) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro.getSegurado());
            comando.setString(2, sinistro.getTelefone());
            comando.setString(3, sinistro.getCidade());
            comando.setString(4, sinistro.getGrauMonta().toString());
            comando.setBoolean(5, sinistro.getPerdaTotal());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Insercao do Sinistro no BD";
        }
    }

    public static String alterarSinistro(Sinistro sinistro) {
        String sql = "UPDATE sinistros SET telefone = ?, cidade = ?, grau_monta = ?, perda_total = ? WHERE segurado = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro.getTelefone());
            comando.setString(2, sinistro.getCidade());
            comando.setString(3, sinistro.getGrauMonta().toString());
            comando.setBoolean(4, sinistro.getPerdaTotal());
            comando.setString(5, sinistro.getSegurado());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Alteracao do Sinistro no BD";
        }
    }

    public static String removerSinistro(String segurado) {
        String sql = "DELETE FROM sinistros WHERE segurado = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, segurado);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Remocao do Sinistro no BD";
        }
    }

    public static boolean existeSinistroMesmosAtributos(Sinistro sinistro) {
        String sql = "SELECT COUNT(segurado) FROM sinistros WHERE segurado = ? AND telefone = ? AND cidade = ? AND grau_monta = ? AND perda_total = ?";
        ResultSet lista_resultados = null;
        int n_sinistros_mesmos_atributos = 0;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro.getSegurado());
            comando.setString(2, sinistro.getTelefone());
            comando.setString(3, sinistro.getCidade());
            comando.setString(4, sinistro.getGrauMonta().toString());
            comando.setBoolean(5, sinistro.getPerdaTotal());
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                n_sinistros_mesmos_atributos = lista_resultados.getInt(1);
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            try {
                String sql_legado = "SELECT COUNT(numero) FROM sinistros WHERE nome = ? AND telefone = ? AND grau_monta = ? AND perda_total = ?";
                PreparedStatement comando_legado = BD.conexao.prepareStatement(sql_legado);
                comando_legado.setString(1, sinistro.getSegurado());
                comando_legado.setString(2, sinistro.getTelefone());
                comando_legado.setString(3, sinistro.getGrauMonta().toString());
                comando_legado.setBoolean(4, sinistro.getPerdaTotal());
                lista_resultados = comando_legado.executeQuery();

                while (lista_resultados.next()) {
                    n_sinistros_mesmos_atributos = lista_resultados.getInt(1);
                }

                lista_resultados.close();
                comando_legado.close();
            } catch (SQLException excecao_legado) {
                excecao_legado.printStackTrace();
            }
        }

        return n_sinistros_mesmos_atributos > 0;
    }

    private static int ultimoNumeroLegado() {
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

    private static String inserirSinistroLegado(Sinistro sinistro) {
        String sql = "INSERT INTO sinistros (numero, nome, telefone, grau_monta, perda_total) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, ultimoNumeroLegado() + 1);
            comando.setString(2, sinistro.getSegurado());
            comando.setString(3, sinistro.getTelefone());
            comando.setString(4, sinistro.getGrauMonta().toString());
            comando.setBoolean(5, sinistro.getPerdaTotal());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Inserção do Sinistro no BD";
        }
    }

    private static String alterarSinistroLegado(Sinistro sinistro) {
        String sql = "UPDATE sinistros SET telefone = ?, grau_monta = ?, perda_total = ? WHERE nome = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro.getTelefone());
            comando.setString(2, sinistro.getGrauMonta().toString());
            comando.setBoolean(3, sinistro.getPerdaTotal());
            comando.setString(4, sinistro.getSegurado());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Alteração do Sinistro no BD";
        }
    }

    private static String removerSinistroLegado(String segurado) {
        String sql = "DELETE FROM sinistros WHERE nome = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, segurado);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Remoção do Sinistro no BD";
        }
    }
}
