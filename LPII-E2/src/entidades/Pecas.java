package entidades;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import persistência.BD;

public class Pecas {

    public enum MarcaPeca {
        OEM("oem"),
        ORIGINAL("original"),
        GENUINA("genuina");

        private final String texto;

        MarcaPeca(String texto) {
            this.texto = texto;
        }

        public static MarcaPeca fromTexto(String texto) {
            if (texto == null) {
                throw new IllegalArgumentException("Marca da peça não informada");
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

            if (normalizado.equals("oem")) return OEM;
            if (normalizado.equals("original")) return ORIGINAL;
            if (normalizado.equals("genuina")) return GENUINA;

            throw new IllegalArgumentException("Marca da peça inválida: " + texto);
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    public enum TipoPeca {
        FAROL("farol"),
        PARACHOQUES("parachoques"),
        CAPO("capo"),
        LANTERNAS("lanternas"),
        PORTAS("portas"),
        ESCAPAMENTO("escapamento"),
        PISTAO("pistao"),
        BIELA("biela"),
        CABECOTE("cabecote");

        private final String texto;

        TipoPeca(String texto) {
            this.texto = texto;
        }

        public static TipoPeca fromTexto(String texto) {
            if (texto == null) {
                throw new IllegalArgumentException("Tipo da peça não informado");
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

            if (normalizado.equals("farol")) return FAROL;
            if (normalizado.equals("parachoques")) return PARACHOQUES;
            if (normalizado.equals("capo")) return CAPO;
            if (normalizado.equals("lanternas")) return LANTERNAS;
            if (normalizado.equals("portas")) return PORTAS;
            if (normalizado.equals("escapamento")) return ESCAPAMENTO;
            if (normalizado.equals("pistao")) return PISTAO;
            if (normalizado.equals("biela")) return BIELA;
            if (normalizado.equals("cabecote")) return CABECOTE;

            throw new IllegalArgumentException("Tipo da peça inválido: " + texto);
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    public enum TipoRegistro {
        GERAL("geral"),
        MECANICA("mecanica"),
        LATARIA("lataria");

        private final String texto;

        TipoRegistro(String texto) {
            this.texto = texto;
        }

        public static TipoRegistro fromTexto(String texto) {
            if (texto == null || texto.trim().isEmpty()) {
                return GERAL;
            }

            String normalizado = texto.trim().toLowerCase(Locale.ROOT);
            if (normalizado.equals("mecanica") || normalizado.equals("mecânica")) return MECANICA;
            if (normalizado.equals("lataria")) return LATARIA;
            return GERAL;
        }

        @Override
        public String toString() {
            return texto;
        }
    }

    private int codigo;
    private String sinistro_segurado;
    private String nome;
    private MarcaPeca marca;
    private double preco;
    private boolean mao_obra_propria;
    private TipoPeca tipo;
    private Integer dias_garantia;
    private String cor;
    private TipoRegistro tipo_registro;

    public Pecas() {
        this(0, null, null, null, 0.0, false, null, null, null, TipoRegistro.GERAL);
    }

    public Pecas(
            int codigo,
            String nome,
            MarcaPeca marca,
            double preco,
            boolean mao_obra_propria
    ) {
        this(codigo, null, nome, marca, preco, mao_obra_propria, null, null, null, TipoRegistro.GERAL);
    }

    public Pecas(
            int codigo,
            String nome,
            MarcaPeca marca,
            double preco,
            TipoPeca tipo,
            String cor,
            boolean mao_obra_propria
    ) {
        this(codigo, null, nome, marca, preco, mao_obra_propria, tipo, null, cor, inferirTipoRegistro(tipo));
    }

    public Pecas(
            int codigo,
            String nome,
            MarcaPeca marca,
            double preco,
            TipoPeca tipo,
            String cor,
            Integer dias_garantia,
            boolean mao_obra_propria
    ) {
        this(codigo, null, nome, marca, preco, mao_obra_propria, tipo, dias_garantia, cor, inferirTipoRegistro(tipo));
    }

    public Pecas(
            int codigo,
            String sinistro_segurado,
            String nome,
            MarcaPeca marca,
            double preco,
            boolean mao_obra_propria,
            TipoPeca tipo,
            Integer dias_garantia,
            String cor,
            TipoRegistro tipo_registro
    ) {
        this.codigo = codigo;
        this.sinistro_segurado = sinistro_segurado;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.mao_obra_propria = mao_obra_propria;
        this.tipo = tipo;
        this.dias_garantia = dias_garantia;
        this.cor = cor;
        this.tipo_registro = tipo_registro != null ? tipo_registro : inferirTipoRegistro(tipo);
    }

    private static TipoRegistro inferirTipoRegistro(TipoPeca tipo) {
        if (tipo == null) {
            return TipoRegistro.GERAL;
        }

        switch (tipo) {
            case FAROL:
            case PARACHOQUES:
            case CAPO:
            case LANTERNAS:
            case PORTAS:
                return TipoRegistro.LATARIA;
            default:
                return TipoRegistro.MECANICA;
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public String getSinistroSegurado() {
        return sinistro_segurado;
    }

    public String getNome() {
        return nome;
    }

    public MarcaPeca getMarca() {
        return marca;
    }

    public MarcaPeca getCategoria() {
        return marca;
    }

    public double getPreco() {
        return preco;
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

    public TipoPeca getTipo() {
        return tipo;
    }

    public Integer getDiasGarantia() {
        return dias_garantia;
    }

    public String getCor() {
        return cor;
    }

    public TipoRegistro getTipoRegistro() {
        return tipo_registro;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setSinistroSegurado(String sinistro_segurado) {
        this.sinistro_segurado = sinistro_segurado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMarca(MarcaPeca marca) {
        this.marca = marca;
    }

    public void setCategoria(MarcaPeca categoria) {
        this.marca = categoria;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setMaoObraPropria(boolean mao_obra_propria) {
        this.mao_obra_propria = mao_obra_propria;
    }

    public void setMaoDeObra(boolean mao_obra_propria) {
        this.mao_obra_propria = mao_obra_propria;
    }

    public void setTipo(TipoPeca tipo) {
        this.tipo = tipo;
        if (this.tipo_registro == TipoRegistro.GERAL) {
            this.tipo_registro = inferirTipoRegistro(tipo);
        }
    }

    public void setDiasGarantia(Integer dias_garantia) {
        this.dias_garantia = dias_garantia;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setTipoRegistro(TipoRegistro tipo_registro) {
        this.tipo_registro = tipo_registro != null ? tipo_registro : TipoRegistro.GERAL;
    }

    public Pecas getVisao() {
        return new Pecas(
                codigo,
                sinistro_segurado,
                nome,
                marca,
                preco,
                mao_obra_propria,
                tipo,
                dias_garantia,
                cor,
                tipo_registro
        );
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " [" + marca + "]";
    }

    private static Pecas criarVisao(ResultSet resultado) throws SQLException {
        int codigo = resultado.getInt("codigo");
        String sinistro_segurado = resultado.getString("sinistro_segurado");
        String nome = resultado.getString("nome");
        MarcaPeca marca = MarcaPeca.fromTexto(resultado.getString("marca"));
        double preco = resultado.getDouble("preco");
        boolean mao_obra_propria = resultado.getBoolean("mao_obra_propria");
        TipoRegistro tipo_registro = TipoRegistro.fromTexto(resultado.getString("tipo_registro"));
        TipoPeca tipo = null;
        String tipoTexto = null;
        String cor = resultado.getString("cor");
        Integer dias_garantia = null;

        if (tipo_registro == TipoRegistro.MECANICA) {
            tipoTexto = resultado.getString("tipo_peca_moto");
            dias_garantia = resultado.getObject("dias_garantia") != null
                    ? resultado.getInt("dias_garantia")
                    : null;
        } else if (tipo_registro == TipoRegistro.LATARIA) {
            tipoTexto = resultado.getString("tipo_peca_carro");
        }

        if (tipoTexto != null && !tipoTexto.trim().isEmpty()) {
            tipo = TipoPeca.fromTexto(tipoTexto);
        }

        if (tipo_registro == TipoRegistro.GERAL && resultado.getObject("tipo_peca_carro") != null) {
            tipoTexto = resultado.getString("tipo_peca_carro");
            if (tipoTexto != null && !tipoTexto.trim().isEmpty()) {
                tipo = TipoPeca.fromTexto(tipoTexto);
            }
        }

        if (tipo_registro == TipoRegistro.GERAL && resultado.getObject("tipo_peca_moto") != null) {
            tipoTexto = resultado.getString("tipo_peca_moto");
            if (tipoTexto != null && !tipoTexto.trim().isEmpty()) {
                tipo = TipoPeca.fromTexto(tipoTexto);
            }
            if (resultado.getObject("dias_garantia") != null) {
                dias_garantia = resultado.getInt("dias_garantia");
            }
        }

        return new Pecas(
                codigo,
                sinistro_segurado,
                nome,
                marca,
                preco,
                mao_obra_propria,
                tipo,
                dias_garantia,
                cor,
                tipo_registro
        );
    }

    private static Pecas criarVisaoLegado(ResultSet resultado) throws SQLException {
        int codigo = resultado.getInt("codigo");
        String sinistro_segurado = resultado.getString("sinistro_numero");
        String nome = resultado.getString("nome");
        MarcaPeca marca = MarcaPeca.fromTexto(resultado.getString("categoria"));
        double preco = resultado.getDouble("preco");
        boolean mao_obra_propria = resultado.getBoolean("mao_obra_propria");
        String tipoTexto = resultado.getString("tipo");
        String cor = resultado.getString("cor");
        TipoPeca tipo = null;

        if (tipoTexto != null && !tipoTexto.trim().isEmpty()) {
            try {
                tipo = TipoPeca.fromTexto(tipoTexto);
            } catch (IllegalArgumentException excecao_enum) {
                tipo = null;
            }
        }

        return new Pecas(
                codigo,
                sinistro_segurado,
                nome,
                marca,
                preco,
                mao_obra_propria,
                tipo,
                null,
                cor,
                inferirTipoRegistro(tipo)
        );
    }

    public static Pecas[] getVisoes() {
        String sql = "SELECT codigo, sinistro_segurado, nome, marca, preco, mao_obra_propria, tipo_registro, tipo_peca_carro, tipo_peca_moto, dias_garantia, cor FROM pecas";
        ResultSet lista_resultados = null;
        ArrayList<Pecas> visoes = new ArrayList<>();

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
                String sql_legado = "SELECT codigo, sinistro_numero, nome, categoria, preco, tipo, cor, mao_obra_propria FROM pecas";
                PreparedStatement comando_legado = BD.conexao.prepareStatement(sql_legado);
                lista_resultados = comando_legado.executeQuery();

                while (lista_resultados.next()) {
                    try {
                        visoes.add(criarVisaoLegado(lista_resultados));
                    } catch (IllegalArgumentException excecao_enum) {
                        // Ignora registros com valor fora do domínio esperado.
                    }
                }

                lista_resultados.close();
                comando_legado.close();
            } catch (SQLException excecao_legado) {
                excecao_legado.printStackTrace();
            }
        }

        return visoes.toArray(new Pecas[visoes.size()]);
    }

    public static Pecas buscarPecas(String nome) {
        String sql = "SELECT codigo, sinistro_segurado, nome, marca, preco, mao_obra_propria, tipo_registro, tipo_peca_carro, tipo_peca_moto, dias_garantia, cor FROM pecas WHERE nome = ?";
        ResultSet lista_resultados = null;
        Pecas pecas = null;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, nome);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                try {
                    pecas = criarVisao(lista_resultados);
                } catch (IllegalArgumentException excecao_enum) {
                    pecas = null;
                }
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            try {
                String sql_legado = "SELECT codigo, sinistro_numero, nome, categoria, preco, tipo, cor, mao_obra_propria FROM pecas WHERE nome = ?";
                PreparedStatement comando_legado = BD.conexao.prepareStatement(sql_legado);
                comando_legado.setString(1, nome);
                lista_resultados = comando_legado.executeQuery();

                while (lista_resultados.next()) {
                    try {
                        pecas = criarVisaoLegado(lista_resultados);
                    } catch (IllegalArgumentException excecao_enum) {
                        pecas = null;
                    }
                }

                lista_resultados.close();
                comando_legado.close();
            } catch (SQLException excecao_legado) {
                excecao_legado.printStackTrace();
                pecas = null;
            }
        }

        return pecas;
    }

    public static Pecas buscarPecas(int codigo) {
        String sql = "SELECT codigo, sinistro_segurado, nome, marca, preco, mao_obra_propria, tipo_registro, tipo_peca_carro, tipo_peca_moto, dias_garantia, cor FROM pecas WHERE codigo = ?";
        ResultSet lista_resultados = null;
        Pecas pecas = null;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, codigo);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                try {
                    pecas = criarVisao(lista_resultados);
                } catch (IllegalArgumentException excecao_enum) {
                    pecas = null;
                }
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            try {
                String sql_legado = "SELECT codigo, sinistro_numero, nome, categoria, preco, tipo, cor, mao_obra_propria FROM pecas WHERE codigo = ?";
                PreparedStatement comando_legado = BD.conexao.prepareStatement(sql_legado);
                comando_legado.setInt(1, codigo);
                lista_resultados = comando_legado.executeQuery();

                while (lista_resultados.next()) {
                    try {
                        pecas = criarVisaoLegado(lista_resultados);
                    } catch (IllegalArgumentException excecao_enum) {
                        pecas = null;
                    }
                }

                lista_resultados.close();
                comando_legado.close();
            } catch (SQLException excecao_legado) {
                excecao_legado.printStackTrace();
                pecas = null;
            }
        }

        return pecas;
    }

    public static Pecas[] buscarPecasPorSinistro(String sinistro_segurado) {
        String sql = "SELECT codigo, sinistro_segurado, nome, marca, preco, mao_obra_propria, tipo_registro, tipo_peca_carro, tipo_peca_moto, dias_garantia, cor FROM pecas WHERE sinistro_segurado = ?";
        ResultSet lista_resultados = null;
        ArrayList<Pecas> visoes = new ArrayList<>();

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, sinistro_segurado);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                try {
                    visoes.add(criarVisao(lista_resultados));
                } catch (IllegalArgumentException excecao_enum) {
                    // Ignora registros inválidos.
                }
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            try {
                String sql_legado = "SELECT codigo, sinistro_numero, nome, categoria, preco, tipo, cor, mao_obra_propria FROM pecas WHERE sinistro_numero = ?";
                PreparedStatement comando_legado = BD.conexao.prepareStatement(sql_legado);
                comando_legado.setString(1, sinistro_segurado);
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

        return visoes.toArray(new Pecas[visoes.size()]);
    }

    public static String inserirPecas(Pecas pecas) {
        String sql = "INSERT INTO pecas (codigo, sinistro_segurado, nome, marca, preco, mao_obra_propria, tipo_registro, tipo_peca_carro, tipo_peca_moto, dias_garantia, cor) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, pecas.getCodigo());
            comando.setString(2, pecas.getSinistroSegurado());
            comando.setString(3, pecas.getNome());
            comando.setString(4, pecas.getMarca().toString());
            comando.setDouble(5, pecas.getPreco());
            comando.setBoolean(6, pecas.getMaoObraPropria());
            comando.setString(7, pecas.getTipoRegistro().toString());
            comando.setString(8, pecas.getTipo() != null && pecas.getTipoRegistro() == TipoRegistro.LATARIA
                    ? pecas.getTipo().toString()
                    : null);
            comando.setString(9, pecas.getTipo() != null && pecas.getTipoRegistro() == TipoRegistro.MECANICA
                    ? pecas.getTipo().toString()
                    : null);
            if (pecas.getDiasGarantia() != null) {
                comando.setInt(10, pecas.getDiasGarantia());
            } else {
                comando.setNull(10, java.sql.Types.INTEGER);
            }
            comando.setString(11, pecas.getCor());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            return inserirPecasLegado(pecas);
        }
    }

    public static String alterarPecas(Pecas pecas) {
        String sql = "UPDATE pecas SET sinistro_segurado = ?, nome = ?, marca = ?, preco = ?, mao_obra_propria = ?, tipo_registro = ?, tipo_peca_carro = ?, tipo_peca_moto = ?, dias_garantia = ?, cor = ? WHERE codigo = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, pecas.getSinistroSegurado());
            comando.setString(2, pecas.getNome());
            comando.setString(3, pecas.getMarca().toString());
            comando.setDouble(4, pecas.getPreco());
            comando.setBoolean(5, pecas.getMaoObraPropria());
            comando.setString(6, pecas.getTipoRegistro().toString());
            comando.setString(7, pecas.getTipo() != null && pecas.getTipoRegistro() == TipoRegistro.LATARIA
                    ? pecas.getTipo().toString()
                    : null);
            comando.setString(8, pecas.getTipo() != null && pecas.getTipoRegistro() == TipoRegistro.MECANICA
                    ? pecas.getTipo().toString()
                    : null);
            if (pecas.getDiasGarantia() != null) {
                comando.setInt(9, pecas.getDiasGarantia());
            } else {
                comando.setNull(9, java.sql.Types.INTEGER);
            }
            comando.setString(10, pecas.getCor());
            comando.setInt(11, pecas.getCodigo());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            return alterarPecasLegado(pecas);
        }
    }

    public static String removerPecas(String nome) {
        String sql = "DELETE FROM pecas WHERE nome = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, nome);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            return removerPecasLegado(nome);
        }
    }

    public static String removerPecas(int codigo) {
        String sql = "DELETE FROM pecas WHERE codigo = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, codigo);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            return removerPecasLegado(codigo);
        }
    }

    private static int ultimoCodigoLegado() {
        String sql = "SELECT MAX(codigo) FROM pecas";
        ResultSet lista_resultados = null;
        int codigo = 0;

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            lista_resultados = comando.executeQuery();

            while (lista_resultados.next()) {
                codigo = lista_resultados.getInt(1);
            }

            lista_resultados.close();
            comando.close();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
        }

        return codigo;
    }

    private static String inserirPecasLegado(Pecas pecas) {
        String sql = "INSERT INTO pecas (codigo, sinistro_numero, nome, categoria, preco, tipo, cor, mao_obra_propria) VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, pecas.getCodigo());
            if (pecas.getSinistroSegurado() != null) {
                try {
                    comando.setInt(2, Integer.parseInt(pecas.getSinistroSegurado()));
                } catch (NumberFormatException excecao_numero) {
                    comando.setNull(2, java.sql.Types.INTEGER);
                }
            } else {
                comando.setNull(2, java.sql.Types.INTEGER);
            }
            comando.setString(3, pecas.getNome());
            comando.setString(4, pecas.getMarca().toString());
            comando.setDouble(5, pecas.getPreco());
            comando.setString(6, pecas.getTipo() != null ? pecas.getTipo().toString() : null);
            comando.setString(7, pecas.getCor());
            comando.setBoolean(8, pecas.getMaoObraPropria());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Inserção da Peça no BD";
        }
    }

    private static String alterarPecasLegado(Pecas pecas) {
        String sql = "UPDATE pecas SET sinistro_numero = ?, nome = ?, categoria = ?, preco = ?, tipo = ?, cor = ?, mao_obra_propria = ? WHERE codigo = ?";

        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            if (pecas.getSinistroSegurado() != null) {
                try {
                    comando.setInt(1, Integer.parseInt(pecas.getSinistroSegurado()));
                } catch (NumberFormatException excecao_numero) {
                    comando.setNull(1, java.sql.Types.INTEGER);
                }
            } else {
                comando.setNull(1, java.sql.Types.INTEGER);
            }
            comando.setString(2, pecas.getNome());
            comando.setString(3, pecas.getMarca().toString());
            comando.setDouble(4, pecas.getPreco());
            comando.setString(5, pecas.getTipo() != null ? pecas.getTipo().toString() : null);
            comando.setString(6, pecas.getCor());
            comando.setBoolean(7, pecas.getMaoObraPropria());
            comando.setInt(8, pecas.getCodigo());
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Alteração da Peça no BD";
        }
    }

    private static String removerPecasLegado(String nome) {
        String sql = "DELETE FROM pecas WHERE nome = ?";
        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setString(1, nome);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Remoção da Peça no BD";
        }
    }

    private static String removerPecasLegado(int codigo) {
        String sql = "DELETE FROM pecas WHERE codigo = ?";
        try {
            PreparedStatement comando = BD.conexao.prepareStatement(sql);
            comando.setInt(1, codigo);
            comando.executeUpdate();
            comando.close();
            return null;
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace();
            return "Erro na Remoção da Peça no BD";
        }
    }
}
