package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import persistência.BD;

public class Seguradora {

    public enum FormaPagamentoPreferencial {
        BOLETO("boleto"),
        CARTAO("cartao"),
        PIX("pix"),
        DEBITO_AUTOMATICO("debito_automatico");

        private final String texto;

        FormaPagamentoPreferencial(String texto) {
            this.texto = texto;
        }

        public static FormaPagamentoPreferencial fromTexto(String texto) {
            if (texto == null) {
                throw new IllegalArgumentException("Forma de pagamento não informada");
            }

            String normalizado = texto.trim().toLowerCase(Locale.ROOT);

            if (normalizado.equals("boleto")) return BOLETO;
            if (normalizado.equals("cartao") || normalizado.equals("cartão")) return CARTAO;
            if (normalizado.equals("pix")) return PIX;
            if (normalizado.equals("debito_automatico")
                    || normalizado.equals("debito automatico")
                    || normalizado.equals("débito_automático")
                    || normalizado.equals("débito automático")) {
                return DEBITO_AUTOMATICO;
            }

            throw new IllegalArgumentException("Forma de pagamento inválida: " + texto);
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    private String nome;
    private String cidade;
    private double cobertura_percentual;
    private boolean possui_atendimento_24h;
    private FormaPagamentoPreferencial forma_pagamento_preferencial;

    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public double getCoberturaPercentual() { return cobertura_percentual; }
    public boolean getPossuiAtendimento24h() { return possui_atendimento_24h; }
    public boolean isPossuiAtendimento24h() { return possui_atendimento_24h; }
    public FormaPagamentoPreferencial getFormaPagamentoPreferencial() { return forma_pagamento_preferencial; }

    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setCoberturaPercentual(double cobertura_percentual) { this.cobertura_percentual = cobertura_percentual; }
    public void setPossuiAtendimento24h(boolean possui_atendimento_24h) { this.possui_atendimento_24h = possui_atendimento_24h; }
    public void setFormaPagamentoPreferencial(FormaPagamentoPreferencial forma_pagamento_preferencial) {
        this.forma_pagamento_preferencial = forma_pagamento_preferencial;
    }

    public Seguradora(
            String nome,
            String cidade,
            double cobertura_percentual,
            boolean possui_atendimento_24h,
            FormaPagamentoPreferencial forma_pagamento_preferencial
    ) {
        this.nome = nome;
        this.cidade = cidade;
        this.cobertura_percentual = cobertura_percentual;
        this.possui_atendimento_24h = possui_atendimento_24h;
        this.forma_pagamento_preferencial = forma_pagamento_preferencial;
    }

    public Seguradora(String nome, String cidade, double cobertura_percentual) {
        this(nome, cidade, cobertura_percentual, false, FormaPagamentoPreferencial.BOLETO);
    }

    public Seguradora(String nome, String cidade) {
        this(nome, cidade, 0, false, FormaPagamentoPreferencial.BOLETO);
    }

    public String toString() {
        return nome + " [" + cidade + "]";
    }

    public Seguradora getVisao() {
        return new Seguradora(
                nome,
                cidade,
                cobertura_percentual,
                possui_atendimento_24h,
                forma_pagamento_preferencial
        );
    }

    public static Seguradora[] getVisoes() {
        String sql = "SELECT nome, cidade, cobertura_percentual, possui_atendimento_24h, forma_pagamento_preferencial FROM seguradoras";
        ResultSet lista_resultados = null;
        ArrayList<Seguradora> visoes = new ArrayList<>();

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                String nome = lista_resultados.getString("nome");
                String cidade = lista_resultados.getString("cidade");
                double cobertura_percentual = lista_resultados.getDouble("cobertura_percentual");
                boolean possui_atendimento_24h = lista_resultados.getBoolean("possui_atendimento_24h");
                FormaPagamentoPreferencial forma_pagamento_preferencial =
                        FormaPagamentoPreferencial.fromTexto(
                                lista_resultados.getString("forma_pagamento_preferencial")
                        );
                visoes.add(new Seguradora(
                        nome,
                        cidade,
                        cobertura_percentual,
                        possui_atendimento_24h,
                        forma_pagamento_preferencial
                ));
            }

            lista_resultados.close();
            comando.close();

        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }

        return visoes.toArray(new Seguradora[visoes.size()]);
    }

    public static Seguradora buscarSeguradora(String nome) {
        String sql = "SELECT * FROM seguradoras WHERE nome = ?";
        ResultSet lista_resultados = null;
        Seguradora seguradora = null;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, nome);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                seguradora = new Seguradora(
                        nome,
                        lista_resultados.getString("cidade"),
                        lista_resultados.getDouble("cobertura_percentual"),
                        lista_resultados.getBoolean("possui_atendimento_24h"),
                        FormaPagamentoPreferencial.fromTexto(
                                lista_resultados.getString("forma_pagamento_preferencial")
                        )
                );
            }

            lista_resultados.close();
            comando.close();

        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            seguradora = null;
        }

        return seguradora;
    }

    public static String inserirSeguradora(Seguradora seguradora) {
        String sql = "INSERT INTO seguradoras (nome, cidade, cobertura_percentual, possui_atendimento_24h, forma_pagamento_preferencial) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);

            comando.setString(1, seguradora.getNome());
            comando.setString(2, seguradora.getCidade());
            comando.setDouble(3, seguradora.getCoberturaPercentual());
            comando.setBoolean(4, seguradora.getPossuiAtendimento24h());
            comando.setString(5, seguradora.getFormaPagamentoPreferencial().toString());

            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Inserção da Seguradora no BD";
        }
    }

    public static String alterarSeguradora(Seguradora seguradora) {
        String sql = "UPDATE seguradoras SET cidade = ?, cobertura_percentual = ?, possui_atendimento_24h = ?, forma_pagamento_preferencial = ?"
                + " WHERE nome = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);

            comando.setString(1, seguradora.getCidade());
            comando.setDouble(2, seguradora.getCoberturaPercentual());
            comando.setBoolean(3, seguradora.getPossuiAtendimento24h());
            comando.setString(4, seguradora.getFormaPagamentoPreferencial().toString());
            comando.setString(5, seguradora.getNome());

            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Alteração da Seguradora no BD";
        }
    }

    public static String removerSeguradora(String nome) {
        String sql = "DELETE FROM seguradoras WHERE nome = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);

            comando.setString(1, nome);
            comando.executeUpdate();
            comando.close();

            return null;

        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Remoção da Seguradora no BD";
        }
    }
}
