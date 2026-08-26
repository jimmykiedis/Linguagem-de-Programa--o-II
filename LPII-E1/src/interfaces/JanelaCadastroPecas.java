package interfaces;

import javax.swing.JOptionPane;
import controles.ControladorCadastroPecas;
import entidades.Pecas;
import javax.swing.DefaultComboBoxModel;
import java.awt.Frame;

public class JanelaCadastroPecas extends javax.swing.JFrame {
    
    ControladorCadastroPecas controlador;
    Pecas[] pecas_cadastradas;
    
    public JanelaCadastroPecas(ControladorCadastroPecas controlador) {
        this(controlador, null);
    }

    public JanelaCadastroPecas(
            ControladorCadastroPecas controlador,
            Frame owner
    ) {
        this.controlador = controlador;
        pecas_cadastradas = Pecas.getVisoes();
        initComponents();
        setSize(new java.awt.Dimension(720, 560));
        configurarJanelaDependente(owner);
        limparCampos(null);
    }

    private void configurarJanelaDependente(Frame owner) {
        setAutoRequestFocus(true);
        setAlwaysOnTop(true);

        if (owner == null) {
            setLocationByPlatform(true);
            return;
        }

        posicionarRelativaAoOwner(owner);
    }

    private void posicionarRelativaAoOwner(Frame owner) {
        int x = owner.getX() + (owner.getWidth() - getWidth()) / 2;
        int y = owner.getY() + (owner.getHeight() - getHeight()) / 2;
        setLocation(x, y);
    }
    
    private void informarErro(String mensagem) {
        JOptionPane.showMessageDialog(
            this,
            mensagem,
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private Pecas obterPecasInformada() {
        String codigoStr = codigoTextField.getText();
        if (codigoStr.isEmpty()) return null;
        
        int codigo;
        try {
            codigo = Integer.parseInt(codigoStr);
        } catch (NumberFormatException e) {
            return null;
        }
        
        String nome = nomeTextField.getText();
        if (nome.isEmpty()) return null;
        
        String categoria = categoriaTextField.getText();
        if (categoria.isEmpty()) return null;
        
        String precoStr = precoTextField.getText();
        if (precoStr.isEmpty()) return null;
        
        double preco;
        try {
            preco = Double.parseDouble(precoStr.replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
        
        String tipo = tipoTextField.getText();
        if (tipo.isEmpty()) return null;
        
        String cor = corTextField.getText();
        if (cor.isEmpty()) return null;
        
        String mao_de_obra_str = maoDeObraTextField.getText();
        if (mao_de_obra_str.isEmpty()) return null;
        
        boolean mao_de_obra;
        
        if (mao_de_obra_str.equalsIgnoreCase("S")
                || mao_de_obra_str.equalsIgnoreCase("SIM")
                || mao_de_obra_str.equalsIgnoreCase("TRUE")) {
            mao_de_obra = true;
        } else if (mao_de_obra_str.equalsIgnoreCase("N")
                || mao_de_obra_str.equalsIgnoreCase("NAO")
                || mao_de_obra_str.equalsIgnoreCase("NÃO")
                || mao_de_obra_str.equalsIgnoreCase("FALSE")) {
            mao_de_obra = false;
        } else {
            return null;
        }
        
        return new Pecas(
            codigo,
            nome,
            categoria,
            preco,
            tipo,
            cor,
            mao_de_obra
        );
    }
    
    private Pecas getVisaoAlterada(String nome) {
        for (Pecas visao : pecas_cadastradas) {
            if (visao.getNome().equals(nome)) return visao;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        comandosPanel = new javax.swing.JPanel();
        inserirPecas = new javax.swing.JButton();
        alterarPecas = new javax.swing.JButton();
        consultarPecas = new javax.swing.JButton();
        removerPecas = new javax.swing.JButton();
        limparCampos = new javax.swing.JButton();

        codigoLabel = new javax.swing.JLabel();
        codigoTextField = new javax.swing.JTextField();

        nomeLabel = new javax.swing.JLabel();
        nomeTextField = new javax.swing.JTextField();

        categoriaLabel = new javax.swing.JLabel();
        categoriaTextField = new javax.swing.JTextField();

        precoLabel = new javax.swing.JLabel();
        precoTextField = new javax.swing.JTextField();

        tipoLabel = new javax.swing.JLabel();
        tipoTextField = new javax.swing.JTextField();

        corLabel = new javax.swing.JLabel();
        corTextField = new javax.swing.JTextField();

        maoDeObraLabel = new javax.swing.JLabel();
        maoDeObraTextField = new javax.swing.JTextField();

        pecas_cadastradasComboBox = new javax.swing.JComboBox();
        pecasCadastradasLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Peças");
        setMinimumSize(new java.awt.Dimension(640, 420));
        setPreferredSize(new java.awt.Dimension(720, 560));

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.rowHeights = new int[] {8};
        getContentPane().setLayout(layout);

        inserirPecas.setText("Inserir");
        inserirPecas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirPecas(evt);
            }
        });
        comandosPanel.add(inserirPecas);

        alterarPecas.setText("Alterar");
        alterarPecas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarPecas(evt);
            }
        });
        comandosPanel.add(alterarPecas);

        consultarPecas.setText("Consultar");
        consultarPecas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarPecas(evt);
            }
        });
        comandosPanel.add(consultarPecas);

        removerPecas.setText("Remover");
        removerPecas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerPecas(evt);
            }
        });
        comandosPanel.add(removerPecas);

        limparCampos.setText("Limpar");
        limparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparCampos(evt);
            }
        });
        comandosPanel.add(limparCampos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(comandosPanel, gridBagConstraints);

        pecasCadastradasLabel.setText("Peças Cadastradas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(pecasCadastradasLabel, gridBagConstraints);

        pecas_cadastradasComboBox.setModel(
            new DefaultComboBoxModel(pecas_cadastradas)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(pecas_cadastradasComboBox, gridBagConstraints);

        codigoLabel.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(codigoLabel, gridBagConstraints);

        codigoTextField.setColumns(8);
        codigoTextField.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(codigoTextField, gridBagConstraints);

        nomeLabel.setText("Nome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(nomeLabel, gridBagConstraints);

        nomeTextField.setColumns(24);
        nomeTextField.setPreferredSize(new java.awt.Dimension(240, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(nomeTextField, gridBagConstraints);

        categoriaLabel.setText("Categoria");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(categoriaLabel, gridBagConstraints);

        categoriaTextField.setColumns(18);
        categoriaTextField.setPreferredSize(new java.awt.Dimension(180, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(categoriaTextField, gridBagConstraints);

        precoLabel.setText("Preço");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(precoLabel, gridBagConstraints);

        precoTextField.setColumns(10);
        precoTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(precoTextField, gridBagConstraints);

        tipoLabel.setText("Tipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(tipoLabel, gridBagConstraints);

        tipoTextField.setColumns(14);
        tipoTextField.setPreferredSize(new java.awt.Dimension(140, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(tipoTextField, gridBagConstraints);

        corLabel.setText("Cor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(corLabel, gridBagConstraints);

        corTextField.setColumns(10);
        corTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(corTextField, gridBagConstraints);

        maoDeObraLabel.setText("Mão de Obra");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(maoDeObraLabel, gridBagConstraints);

        maoDeObraTextField.setColumns(8);
        maoDeObraTextField.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(maoDeObraTextField, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inserirPecas(java.awt.event.ActionEvent evt) {
        Pecas pecas = obterPecasInformada();
        String mensagem_erro = null;

        if (pecas != null)
            mensagem_erro = controlador.inserirPecas(pecas);
        else
            mensagem_erro = "Algum atributo da Peça não foi informado";

        if (mensagem_erro == null) {
            Pecas visao = pecas.getVisao();
            pecas_cadastradasComboBox.addItem(visao);
            pecas_cadastradasComboBox.setSelectedItem(visao);
        } else {
            informarErro(mensagem_erro);
        }
    }

    private void alterarPecas(java.awt.event.ActionEvent evt) {
        Pecas pecas = obterPecasInformada();
        String mensagem_erro = null;

        if (pecas != null)
            mensagem_erro = controlador.alterarPecas(pecas);
        else
            mensagem_erro = "Algum atributo da Peça não foi informado";

        if (mensagem_erro == null) {
            Pecas visao = getVisaoAlterada(pecas.getNome());

            if (visao != null) {
                visao.setCodigo(pecas.getCodigo());
                visao.setCategoria(pecas.getCategoria());
                visao.setPreco(pecas.getPreco());
                visao.setTipo(pecas.getTipo());
                visao.setCor(pecas.getCor());
                visao.setMaoDeObra(pecas.getMaoDeObra());

                pecas_cadastradasComboBox.updateUI();
                pecas_cadastradasComboBox.setSelectedItem(visao);
            }
        } else {
            informarErro(mensagem_erro);
        }
    }

    private void consultarPecas(java.awt.event.ActionEvent evt) {
        Pecas visao =
                (Pecas) pecas_cadastradasComboBox.getSelectedItem();

        Pecas pecas = null;
        String mensagem_erro = null;

        if (visao != null) {
            pecas = Pecas.buscarPecas(visao.getNome());

            if (pecas == null)
                mensagem_erro = "Peça não cadastrada";
        } else {
            mensagem_erro = "Nenhuma Peça selecionada";
        }

        if (mensagem_erro == null) {

            codigoTextField.setText(
                    String.valueOf(pecas.getCodigo())
            );

            nomeTextField.setText(pecas.getNome());
            categoriaTextField.setText(pecas.getCategoria());

            precoTextField.setText(
                    String.valueOf(pecas.getPreco())
            );

            tipoTextField.setText(pecas.getTipo());
            corTextField.setText(pecas.getCor());

            maoDeObraTextField.setText(
                    pecas.getMaoDeObra() ? "S" : "N"
            );

        } else {
            informarErro(mensagem_erro);
        }
    }

    private void removerPecas(java.awt.event.ActionEvent evt) {
        Pecas visao =
                (Pecas) pecas_cadastradasComboBox.getSelectedItem();

        String mensagem_erro = null;

        if (visao != null)
            mensagem_erro =
                    controlador.removerPecas(visao.getNome());
        else
            mensagem_erro = "Nenhuma Peça selecionada";

        if (mensagem_erro == null) {
            pecas_cadastradasComboBox.removeItem(visao);
            limparCampos(evt);
        } else {
            informarErro(mensagem_erro);
        }
    }

    private void limparCampos(java.awt.event.ActionEvent evt) {
        codigoTextField.setText("");
        nomeTextField.setText("");
        categoriaTextField.setText("");
        precoTextField.setText("");
        tipoTextField.setText("");
        corTextField.setText("");
        maoDeObraTextField.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterarPecas;
    private javax.swing.JPanel comandosPanel;
    private javax.swing.JButton consultarPecas;
    private javax.swing.JLabel codigoLabel;
    private javax.swing.JTextField codigoTextField;
    private javax.swing.JLabel nomeLabel;
    private javax.swing.JTextField nomeTextField;
    private javax.swing.JLabel categoriaLabel;
    private javax.swing.JTextField categoriaTextField;
    private javax.swing.JLabel precoLabel;
    private javax.swing.JTextField precoTextField;
    private javax.swing.JLabel tipoLabel;
    private javax.swing.JTextField tipoTextField;
    private javax.swing.JLabel corLabel;
    private javax.swing.JTextField corTextField;
    private javax.swing.JLabel maoDeObraLabel;
    private javax.swing.JTextField maoDeObraTextField;
    private javax.swing.JButton inserirPecas;
    private javax.swing.JButton limparCampos;
    private javax.swing.JLabel pecasCadastradasLabel;
    private javax.swing.JComboBox pecas_cadastradasComboBox;
    private javax.swing.JButton removerPecas;
    // End of variables declaration//GEN-END:variables
}
