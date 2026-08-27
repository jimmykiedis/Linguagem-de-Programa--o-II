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

            String normalizado = texto.trim().toLowerCase(Locale.ROOT);
            normalizado = normalizado
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

    private int numero;
    private String cliente;
    private String telefone;
    private GrauMonta grau_monta;
    private boolean perda_total;

    public int getNumero() { return numero; }
    public String getCliente() { return cliente; }
    public String getTelefone() { return telefone; }
    public GrauMonta getGrauMonta() { return grau_monta; }
    public boolean getPerdaTotal() { return perda_total; }
    public boolean isPerdaTotal() { return perda_total; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setGrauMonta(GrauMonta grau_monta) {
        this.grau_monta = grau_monta != null ? grau_monta : GrauMonta.PEQUENA;
    }
    public void setPerdaTotal(boolean perda_total) { this.perda_total = perda_total; }

    public Sinistro(
            int numero,
            String cliente,
            String telefone,
            GrauMonta grau_monta,
            boolean perda_total
    ) {
        this.numero = numero;
        this.cliente = cliente;
        this.telefone = telefone;
        this.grau_monta = grau_monta != null ? grau_monta : GrauMonta.PEQUENA;
        this.perda_total = perda_total;
    }

    public Sinistro(int numero, String cliente, String telefone) {
        this(numero, cliente, telefone, GrauMonta.PEQUENA, false);
    }

    public Sinistro(String cliente, String telefone) {
        this(0, cliente, telefone, GrauMonta.PEQUENA, false);
    }

    public Sinistro(String cliente, String telefone, GrauMonta grau_monta, boolean perda_total) {
        this(0, cliente, telefone, grau_monta, perda_total);
    }

    public int getSequencial() { return numero; }
    public void setSequencial(int sequencial) { this.numero = sequencial; }

    @Override
    public String toString() {
        return "[" + numero + "] " + cliente + " (" + grau_monta + ")";
    }

    public Sinistro getVisao() {
        return new Sinistro(numero, cliente, telefone, grau_monta, perda_total);
    }

    public static Sinistro[] getVisoes() {
        String sql = "SELECT numero, nome, telefone, grau_monta, perda_total FROM sinistros";
        ResultSet lista_resultados = null;
        ArrayList<Sinistro> visoes = new ArrayList<>();

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                try {
                    int numero = lista_resultados.getInt("numero");
                    String cliente = lista_resultados.getString("nome");
                    String telefone = lista_resultados.getString("telefone");
                    GrauMonta grau_monta = GrauMonta.fromTexto(
                            lista_resultados.getString("grau_monta")
                    );
                    boolean perda_total = lista_resultados.getBoolean("perda_total");
                    visoes.add(new Sinistro(
                            numero,
                            cliente,
                            telefone,
                            grau_monta,
                            perda_total
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
                try {
                    sinistro = new Sinistro(
                            numero,
                            lista_resultados.getString("nome"),
                            lista_resultados.getString("telefone"),
                            GrauMonta.fromTexto(lista_resultados.getString("grau_monta")),
                            lista_resultados.getBoolean("perda_total")
                    );
                } catch (IllegalArgumentException excecao_enum) {
                    sinistro = null;
                }
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
        String sql = "INSERT INTO sinistros (numero, nome, telefone, grau_monta, perda_total) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, sinistro.getNumero());
            comando.setString(2, sinistro.getCliente());
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

    public static String alterarSinistro(Sinistro sinistro) {
        String sql = "UPDATE sinistros SET nome = ?, telefone = ?, grau_monta = ?, perda_total = ? WHERE numero = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro.getCliente());
            comando.setString(2, sinistro.getTelefone());
            comando.setString(3, sinistro.getGrauMonta().toString());
            comando.setBoolean(4, sinistro.getPerdaTotal());
            comando.setInt(5, sinistro.getNumero());
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
        String sql = "SELECT COUNT(numero) FROM sinistros WHERE nome = ? AND telefone = ? AND grau_monta = ? AND perda_total = ?";
        ResultSet lista_resultados = null;
        int n_sinistros_mesmos_atributos = 0;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro.getCliente());
            comando.setString(2, sinistro.getTelefone());
            comando.setString(3, sinistro.getGrauMonta().toString());
            comando.setBoolean(4, sinistro.getPerdaTotal());
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
