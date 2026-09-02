package interfaces;

import controles.ControladorCadastroPecasSinistros;
import entidades.Pecas;
import entidades.Sinistro;
import java.awt.Frame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class JanelaCadastroPecasSinistros extends javax.swing.JFrame {

    private final ControladorCadastroPecasSinistros controlador;
    private Sinistro sinistro;
    private Pecas[] pecas_cadastradas;
    private final DefaultListModel pecasSinistroModel = new DefaultListModel();

    public JanelaCadastroPecasSinistros(
            ControladorCadastroPecasSinistros controlador,
            Sinistro sinistro,
            Frame owner
    ) {
        this.controlador = controlador;
        this.sinistro = sinistro;
        pecas_cadastradas = Pecas.getVisoes();
        initComponents();
        configurarJanelaDependente(owner);
        if (owner instanceof JanelaCadastroSinistros) {
            final JanelaCadastroSinistros janelaMae = (JanelaCadastroSinistros) owner;
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent evt) {
                    if (sinistro != null) {
                        janelaMae.atualizarListaPecasSinistro(sinistro.getSegurado());
                    }
                }
            });
        }
        carregarSinistro();
        atualizarPecasCadastradas();
        atualizarPecasAssociadas();
        setSize(new java.awt.Dimension(760, 520));
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

    private void carregarSinistro() {
        if (sinistro == null) {
            sinistroTextField.setText("Sinistro nao informado");
            return;
        }

        sinistroTextField.setText(sinistro.toString());
    }

    private void informarErro(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void atualizarPecasCadastradas() {
        pecas_cadastradas = Pecas.getVisoes();
        pecas_cadastradasComboBox.setModel(
                new DefaultComboBoxModel(pecas_cadastradas)
        );
    }

    private void atualizarPecasAssociadas() {
        pecasSinistroModel.clear();

        if (sinistro == null) {
            pecasSinistroList.setModel(pecasSinistroModel);
            return;
        }

        Pecas[] pecas = Pecas.buscarPecasPorSinistro(sinistro.getSegurado());
        sinistro.setPecas(pecas);

        for (Pecas peca : pecas) {
            pecasSinistroModel.addElement(peca);
        }

        pecasSinistroList.setModel(pecasSinistroModel);
    }

    private Pecas obterPecaSelecionada() {
        return (Pecas) pecas_cadastradasComboBox.getSelectedItem();
    }

    private Pecas obterPecaAssociadaSelecionada() {
        return (Pecas) pecasSinistroList.getSelectedValue();
    }

    private void selecionarPecaNaLista(Pecas peca) {
        if (peca == null) return;
        pecasSinistroList.setSelectedValue(peca, true);
    }

    private Pecas localizarPeca(int codigo) {
        if (pecas_cadastradas == null) return null;
        for (Pecas visao : pecas_cadastradas) {
            if (visao.getCodigo() == codigo) return visao;
        }
        return null;
    }

    private void limparCampos(java.awt.event.ActionEvent evt) {
        if (pecas_cadastradasComboBox.getItemCount() > 0) {
            pecas_cadastradasComboBox.setSelectedIndex(0);
        } else {
            pecas_cadastradasComboBox.setSelectedItem(null);
        }
        pecasSinistroList.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        comandosPanel = new javax.swing.JPanel();
        inserirPeca = new javax.swing.JButton();
        removerPeca = new javax.swing.JButton();
        limparCampos = new javax.swing.JButton();
        sinistroLabel = new javax.swing.JLabel();
        sinistroTextField = new javax.swing.JTextField();
        pecasCadastradasLabel = new javax.swing.JLabel();
        pecas_cadastradasComboBox = new javax.swing.JComboBox();
        pecasSinistroLabel = new javax.swing.JLabel();
        pecasSinistroScrollPane = new javax.swing.JScrollPane();
        pecasSinistroList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Pecas por Sinistro");
        setMinimumSize(new java.awt.Dimension(680, 430));
        setPreferredSize(new java.awt.Dimension(760, 520));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        inserirPeca.setText("Inserir");
        inserirPeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirPeca(evt);
            }
        });
        comandosPanel.add(inserirPeca);

        removerPeca.setText("Remover");
        removerPeca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerPeca(evt);
            }
        });
        comandosPanel.add(removerPeca);

        limparCampos.setText("Limpar");
        limparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparCampos(evt);
            }
        });
        comandosPanel.add(limparCampos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(comandosPanel, gridBagConstraints);

        sinistroLabel.setText("Sinistro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(sinistroLabel, gridBagConstraints);

        sinistroTextField.setEditable(false);
        sinistroTextField.setColumns(28);
        sinistroTextField.setPreferredSize(new java.awt.Dimension(280, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(sinistroTextField, gridBagConstraints);

        pecasCadastradasLabel.setText("Peças Cadastradas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(pecasCadastradasLabel, gridBagConstraints);

        pecas_cadastradasComboBox.setModel(new DefaultComboBoxModel(pecas_cadastradas));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(pecas_cadastradasComboBox, gridBagConstraints);

        pecasSinistroLabel.setText("Peças do Sinistro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(pecasSinistroLabel, gridBagConstraints);

        pecasSinistroList.setModel(pecasSinistroModel);
        pecasSinistroList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pecasSinistroScrollPane.setViewportView(pecasSinistroList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(pecasSinistroScrollPane, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inserirPeca(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirPeca
        Pecas peca = obterPecaSelecionada();
        String mensagem_erro = null;

        if (sinistro == null) {
            mensagem_erro = "Sinistro nao informado";
        } else if (peca == null) {
            mensagem_erro = "Nenhuma Peça selecionada";
        } else {
            mensagem_erro = controlador.inserirPecasSinistros(peca, sinistro);
        }

        if (mensagem_erro == null) {
            atualizarPecasAssociadas();
            selecionarPecaNaLista(localizarPeca(peca.getCodigo()));
        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_inserirPeca

    private void removerPeca(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerPeca
        Pecas peca = obterPecaAssociadaSelecionada();
        String mensagem_erro = null;

        if (sinistro == null) {
            mensagem_erro = "Sinistro nao informado";
        } else if (peca == null) {
            mensagem_erro = "Nenhuma Peça selecionada";
        } else {
            mensagem_erro = controlador.removerPecasSinistros(peca, sinistro);
        }

        if (mensagem_erro == null) {
            atualizarPecasAssociadas();
            pecasSinistroList.clearSelection();
        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_removerPeca

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel comandosPanel;
    private javax.swing.JButton inserirPeca;
    private javax.swing.JButton limparCampos;
    private javax.swing.JLabel pecasCadastradasLabel;
    private javax.swing.JComboBox pecas_cadastradasComboBox;
    private javax.swing.JList pecasSinistroList;
    private javax.swing.JLabel pecasSinistroLabel;
    private javax.swing.JScrollPane pecasSinistroScrollPane;
    private javax.swing.JButton removerPeca;
    private javax.swing.JLabel sinistroLabel;
    private javax.swing.JTextField sinistroTextField;
    // End of variables declaration//GEN-END:variables
}
