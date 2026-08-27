package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import persistência.BD;

public class Orcamento {

    private LocalDate data;
    private Sinistro sinistro;
    private Seguradora seguradora;

    public Orcamento(LocalDate data, Sinistro sinistro, Seguradora seguradora) {
        this.data = data;
        this.sinistro = sinistro;
        this.seguradora = seguradora;
    }

    public LocalDate getData() {
        return data;
    }

    public Sinistro getSinistro() {
        return sinistro;
    }

    public Seguradora getSeguradora() {
        return seguradora;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setSinistro(Sinistro sinistro) {
        this.sinistro = sinistro;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    @Override
    public String toString() {
        return data + " - " + (sinistro != null ? sinistro.getSegurado() : "") + " / "
                + (seguradora != null ? seguradora.getNome() : "");
    }

    public Orcamento getVisao() {
        return new Orcamento(data, sinistro, seguradora);
    }

    public static Orcamento[] getVisoes() {
        String sql = "SELECT data, sinistro_segurado, seguradora_nome FROM orcamentos";
        ResultSet lista_resultados = null;
        ArrayList<Orcamento> visoes = new ArrayList<>();

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                java.sql.Date data_sql = lista_resultados.getDate("data");
                LocalDate data = data_sql != null ? data_sql.toLocalDate() : null;
                Sinistro sinistro = Sinistro.buscarSinistro(
                        lista_resultados.getString("sinistro_segurado")
                );
                Seguradora seguradora = Seguradora.buscarSeguradora(
                        lista_resultados.getString("seguradora_nome")
                );
                visoes.add(new Orcamento(data, sinistro, seguradora));
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }

        return visoes.toArray(new Orcamento[visoes.size()]);
    }

    public static Orcamento buscarOrcamento(String sinistro_segurado, String seguradora_nome) {
        String sql = "SELECT data, sinistro_segurado, seguradora_nome FROM orcamentos WHERE sinistro_segurado = ? AND seguradora_nome = ?";
        ResultSet lista_resultados = null;
        Orcamento orcamento = null;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro_segurado);
            comando.setString(2, seguradora_nome);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                Date data_sql = lista_resultados.getDate("data");
                LocalDate data = data_sql != null ? data_sql.toLocalDate() : null;
                Sinistro sinistro = Sinistro.buscarSinistro(
                        lista_resultados.getString("sinistro_segurado")
                );
                Seguradora seguradora = Seguradora.buscarSeguradora(
                        lista_resultados.getString("seguradora_nome")
                );
                orcamento = new Orcamento(data, sinistro, seguradora);
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            orcamento = null;
        }

        return orcamento;
    }

    public static String inserirOrcamento(Orcamento orcamento) {
        String sql = "INSERT INTO orcamentos (data, sinistro_segurado, seguradora_nome) VALUES (?,?,?)";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            if (orcamento.getData() != null) {
                comando.setDate(1, Date.valueOf(orcamento.getData()));
            } else {
                comando.setNull(1, java.sql.Types.DATE);
            }
            comando.setString(2, orcamento.getSinistro() != null ? orcamento.getSinistro().getSegurado() : null);
            comando.setString(3, orcamento.getSeguradora() != null ? orcamento.getSeguradora().getNome() : null);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Inserção do Orçamento no BD";
        }
    }

    public static String alterarOrcamento(Orcamento orcamento) {
        String sql = "UPDATE orcamentos SET data = ? WHERE sinistro_segurado = ? AND seguradora_nome = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            if (orcamento.getData() != null) {
                comando.setDate(1, Date.valueOf(orcamento.getData()));
            } else {
                comando.setNull(1, java.sql.Types.DATE);
            }
            comando.setString(2, orcamento.getSinistro() != null ? orcamento.getSinistro().getSegurado() : null);
            comando.setString(3, orcamento.getSeguradora() != null ? orcamento.getSeguradora().getNome() : null);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Alteração do Orçamento no BD";
        }
    }

    public static String removerOrcamento(String sinistro_segurado, String seguradora_nome) {
        String sql = "DELETE FROM orcamentos WHERE sinistro_segurado = ? AND seguradora_nome = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro_segurado);
            comando.setString(2, seguradora_nome);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Remoção do Orçamento no BD";
        }
    }
}
