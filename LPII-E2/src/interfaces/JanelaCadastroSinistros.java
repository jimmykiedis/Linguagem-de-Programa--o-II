package interfaces;

import controles.ControladorCadastroPecasSinistros;
import controles.ControladorCadastroSinistros;
import entidades.Sinistro;
import java.awt.Frame;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class JanelaCadastroSinistros extends javax.swing.JFrame {

    ControladorCadastroSinistros controlador;
    Sinistro[] sinistros_cadastrados;
    private final DefaultListModel pecasSinistroModel = new DefaultListModel();

    public JanelaCadastroSinistros(ControladorCadastroSinistros controlador) {
        this(controlador, null);
    }

    public JanelaCadastroSinistros(ControladorCadastroSinistros controlador, Frame owner) {
        this.controlador = controlador;
        sinistros_cadastrados = Sinistro.getVisoes();
        initComponents();
        atualizarSinistrosCadastrados();
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

        int x = owner.getX() + (owner.getWidth() - getWidth()) / 2;
        int y = owner.getY() + (owner.getHeight() - getHeight()) / 2;
        setLocation(x, y);
    }

    private Sinistro localizarSinistro(String segurado) {
        if (sinistros_cadastrados == null) return null;
        for (Sinistro visao : sinistros_cadastrados) {
            if (Objects.equals(visao.getSegurado(), segurado)) return visao;
        }
        return null;
    }

    private void atualizarSinistrosCadastrados() {
        atualizarSinistrosCadastrados(null);
    }

    private void atualizarSinistrosCadastrados(String segurado_selecionado) {
        sinistros_cadastrados = Sinistro.getVisoes();
        sinistros_cadastradosComboBox.setModel(new DefaultComboBoxModel(sinistros_cadastrados));

        Sinistro visao_selecionada = localizarSinistro(segurado_selecionado);
        if (visao_selecionada != null) {
            sinistros_cadastradosComboBox.setSelectedItem(visao_selecionada);
        }
    }

    private void informarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void atualizarListaPecasSinistro(String segurado) {
        pecasSinistroModel.clear();
        if (segurado == null || segurado.trim().isEmpty()) {
            pecasSinistroList.setModel(pecasSinistroModel);
            return;
        }

        for (entidades.Pecas peca : entidades.Pecas.buscarPecasPorSinistro(segurado)) {
            pecasSinistroModel.addElement(peca);
        }
        pecasSinistroList.setModel(pecasSinistroModel);
    }

    private Boolean obterPerdaTotalInformada() {
        return perdaTotalCheckBox.isSelected();
    }

    private void definirPerdaTotal(Boolean valor) {
        perdaTotalCheckBox.setSelected(Boolean.TRUE.equals(valor));
    }

    private Sinistro obterSinistroInformado() {
        String segurado = numeroTextField.getText().trim();
        if (segurado.isEmpty()) return null;

        String telefone = telefoneTextField.getText().trim();
        if (telefone.isEmpty()) return null;

        String cidade = clienteTextField.getText().trim();
        if (cidade.isEmpty()) cidade = null;

        Sinistro.GrauMonta grau_monta =
                (Sinistro.GrauMonta) grauMontaComboBox.getSelectedItem();
        if (grau_monta == null) return null;

        Boolean perda_total = obterPerdaTotalInformada();
        if (perda_total == null) return null;

        return new Sinistro(segurado, telefone, cidade, grau_monta, perda_total);
    }

    private Sinistro getVisaoAlterada(String segurado) {
        for (Sinistro visao : sinistros_cadastrados) {
            if (Objects.equals(visao.getSegurado(), segurado)) return visao;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        comandosPanel = new javax.swing.JPanel();
        inserirSinistro = new javax.swing.JButton();
        alterarSinistro = new javax.swing.JButton();
        consultarSinistro = new javax.swing.JButton();
        removerSinistro = new javax.swing.JButton();
        pecasSinistro = new javax.swing.JButton();
        limparCampos = new javax.swing.JButton();
        sinistrosCadastradosLabel = new javax.swing.JLabel();
        sinistros_cadastradosComboBox = new javax.swing.JComboBox();
        numeroLabel = new javax.swing.JLabel();
        numeroTextField = new javax.swing.JTextField();
        clienteLabel = new javax.swing.JLabel();
        clienteTextField = new javax.swing.JTextField();
        telefoneLabel = new javax.swing.JLabel();
        telefoneTextField = new javax.swing.JTextField();
        grauMontaLabel = new javax.swing.JLabel();
        grauMontaComboBox = new javax.swing.JComboBox();
        perdaTotalLabel = new javax.swing.JLabel();
        perdaTotalPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        perdaTotalCheckBox = new javax.swing.JCheckBox();
        pecasSinistroLabel = new javax.swing.JLabel();
        pecasSinistroScrollPane = new javax.swing.JScrollPane();
        pecasSinistroList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Sinistros");
        setMinimumSize(new java.awt.Dimension(680, 420));
        setPreferredSize(new java.awt.Dimension(720, 560));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        inserirSinistro.setText("Inserir");
        inserirSinistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirSinistro(evt);
            }
        });
        comandosPanel.add(inserirSinistro);

        alterarSinistro.setText("Alterar");
        alterarSinistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarSinistro(evt);
            }
        });
        comandosPanel.add(alterarSinistro);

        consultarSinistro.setText("Consultar");
        consultarSinistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarSinistro(evt);
            }
        });
        comandosPanel.add(consultarSinistro);

        removerSinistro.setText("Remover");
        removerSinistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerSinistro(evt);
            }
        });
        comandosPanel.add(removerSinistro);

        pecasSinistro.setText("Peças");
        pecasSinistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pecasSinistro(evt);
            }
        });
        comandosPanel.add(pecasSinistro);

        limparCampos.setText("Limpar");
        limparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparCampos(evt);
            }
        });
        comandosPanel.add(limparCampos);

        sinistrosCadastradosLabel.setText("Sinistros Cadastrados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(sinistrosCadastradosLabel, gridBagConstraints);

        sinistros_cadastradosComboBox.setModel(new DefaultComboBoxModel(sinistros_cadastrados));
        sinistros_cadastradosComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sinistro visao = (Sinistro) sinistros_cadastradosComboBox.getSelectedItem();
                atualizarListaPecasSinistro(visao != null ? visao.getSegurado() : null);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(sinistros_cadastradosComboBox, gridBagConstraints);

        numeroLabel.setText("Segurado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(numeroLabel, gridBagConstraints);

        numeroTextField.setColumns(10);
        numeroTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(numeroTextField, gridBagConstraints);

        clienteLabel.setText("Cidade");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(clienteLabel, gridBagConstraints);

        clienteTextField.setColumns(24);
        clienteTextField.setPreferredSize(new java.awt.Dimension(240, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(clienteTextField, gridBagConstraints);

        telefoneLabel.setText("Telefone");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(telefoneLabel, gridBagConstraints);

        telefoneTextField.setColumns(16);
        telefoneTextField.setPreferredSize(new java.awt.Dimension(160, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(telefoneTextField, gridBagConstraints);

        grauMontaLabel.setText("Grau de Monta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(grauMontaLabel, gridBagConstraints);

        grauMontaComboBox.setModel(new DefaultComboBoxModel(Sinistro.GrauMonta.values()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(grauMontaComboBox, gridBagConstraints);

        perdaTotalLabel.setText("Perda Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(perdaTotalLabel, gridBagConstraints);

        perdaTotalCheckBox.setText("Sim");
        perdaTotalPanel.add(perdaTotalCheckBox);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(perdaTotalPanel, gridBagConstraints);

        pecasSinistroLabel.setText("Peças do Sinistro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(pecasSinistroLabel, gridBagConstraints);

        pecasSinistroList.setModel(pecasSinistroModel);
        pecasSinistroList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pecasSinistroScrollPane.setPreferredSize(new java.awt.Dimension(300, 90));
        pecasSinistroScrollPane.setViewportView(pecasSinistroList);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(pecasSinistroScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(comandosPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void limparCampos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparCampos
        numeroTextField.setText("");
        numeroTextField.setEditable(true);
        clienteTextField.setText("");
        telefoneTextField.setText("");
        grauMontaComboBox.setSelectedIndex(0);
        perdaTotalCheckBox.setSelected(false);
        sinistros_cadastradosComboBox.setSelectedIndex(-1);
        atualizarListaPecasSinistro(null);
    }//GEN-LAST:event_limparCampos

    private void consultarSinistro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarSinistro
        Sinistro visao = (Sinistro) sinistros_cadastradosComboBox.getSelectedItem();
        String mensagem_erro = null;
        if (visao == null) {
            mensagem_erro = "Nenhum Sinistro selecionado";
        } else {
            Sinistro sinistro = Sinistro.buscarSinistro(visao.getSegurado());
            if (sinistro == null) {
                mensagem_erro = "Sinistro não cadastrado";
            } else {
                numeroTextField.setText(sinistro.getSegurado());
                numeroTextField.setEditable(false);
                clienteTextField.setText(sinistro.getCidade());
                telefoneTextField.setText(sinistro.getTelefone());
                grauMontaComboBox.setSelectedItem(sinistro.getGrauMonta());
                definirPerdaTotal(sinistro.getPerdaTotal());
                atualizarListaPecasSinistro(sinistro.getSegurado());
            }
        }

        if (mensagem_erro != null) informarErro(mensagem_erro);
    }//GEN-LAST:event_consultarSinistro

    private void inserirSinistro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirSinistro
        Sinistro sinistro = obterSinistroInformado();
        String mensagem_erro = null;

        if (sinistro != null) {
            mensagem_erro = controlador.inserirSinistro(sinistro);
        } else {
            mensagem_erro = "Algum atributo do Sinistro não foi informado";
        }

        if (mensagem_erro == null) {
            atualizarSinistrosCadastrados(sinistro.getSegurado());
            atualizarListaPecasSinistro(sinistro.getSegurado());
            numeroTextField.setText(sinistro.getSegurado());
            numeroTextField.setEditable(false);
            sinistros_cadastradosComboBox.setSelectedItem(getVisaoAlterada(sinistro.getSegurado()));
        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_inserirSinistro

    private void alterarSinistro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterarSinistro
        Sinistro sinistro = obterSinistroInformado();
        String mensagem_erro = null;

        if (sinistro != null) {
            mensagem_erro = controlador.alterarSinistro(sinistro);
        } else {
            mensagem_erro = "Algum atributo do Sinistro não foi informado";
        }

        if (mensagem_erro == null) {
            atualizarSinistrosCadastrados(sinistro.getSegurado());
            atualizarListaPecasSinistro(sinistro.getSegurado());
            sinistros_cadastradosComboBox.setSelectedItem(getVisaoAlterada(sinistro.getSegurado()));
        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_alterarSinistro

    private void removerSinistro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerSinistro
        Sinistro visao = (Sinistro) sinistros_cadastradosComboBox.getSelectedItem();
        String mensagem_erro = null;

        if (visao != null) {
            mensagem_erro = controlador.removerSinistro(visao.getSegurado());
        } else {
            mensagem_erro = "Nenhum Sinistro selecionado";
        }

        if (mensagem_erro == null) {
            atualizarSinistrosCadastrados();
            limparCampos(evt);
        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_removerSinistro

    private void pecasSinistro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pecasSinistro
        Sinistro visao = (Sinistro) sinistros_cadastradosComboBox.getSelectedItem();
        String mensagem_erro = null;

        if (visao == null) {
            mensagem_erro = "Nenhum Sinistro selecionado";
        } else {
            Sinistro sinistro = Sinistro.buscarSinistro(visao.getSegurado());
            if (sinistro == null) {
                mensagem_erro = "Sinistro não cadastrado";
            } else {
                new ControladorCadastroPecasSinistros(this, sinistro);
                atualizarListaPecasSinistro(sinistro.getSegurado());
            }
        }

        if (mensagem_erro != null) {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_pecasSinistro

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterarSinistro;
    private javax.swing.JButton consultarSinistro;
    private javax.swing.JTextField clienteTextField;
    private javax.swing.JLabel clienteLabel;
    private javax.swing.JPanel comandosPanel;
    private javax.swing.JButton inserirSinistro;
    private javax.swing.JButton limparCampos;
    private javax.swing.JButton pecasSinistro;
    private javax.swing.JComboBox grauMontaComboBox;
    private javax.swing.JLabel grauMontaLabel;
    private javax.swing.JLabel numeroLabel;
    private javax.swing.JTextField numeroTextField;
    private javax.swing.JLabel perdaTotalLabel;
    private javax.swing.JPanel perdaTotalPanel;
    private javax.swing.JCheckBox perdaTotalCheckBox;
    private javax.swing.JLabel pecasSinistroLabel;
    private javax.swing.JList pecasSinistroList;
    private javax.swing.JScrollPane pecasSinistroScrollPane;
    private javax.swing.JButton removerSinistro;
    private javax.swing.JLabel sinistrosCadastradosLabel;
    private javax.swing.JComboBox sinistros_cadastradosComboBox;
    private javax.swing.JLabel telefoneLabel;
    private javax.swing.JTextField telefoneTextField;
    // End of variables declaration//GEN-END:variables
}
