package interfaces;

import javax.swing.JOptionPane;
import controles.ControladorCadastroSeguradoras;
import entidades.Seguradora;
import javax.swing.DefaultComboBoxModel;

public class JanelaCadastroSeguradoras extends javax.swing.JFrame {

    ControladorCadastroSeguradoras controlador;
    Seguradora[] seguradoras_cadastradas;

    public JanelaCadastroSeguradoras(ControladorCadastroSeguradoras controlador) {
        this.controlador = controlador;
        seguradoras_cadastradas = Seguradora.getVisões();
        initComponents();
        limparCampos(null);
    }

    private void informarErro(String mensagem) {
        JOptionPane.showMessageDialog(
            this,
            mensagem,
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private Seguradora obterSeguradoraInformada() {

        String nome = nomeTextField.getText();
        if (nome.isEmpty()) return null;

        String cidade = cidadeTextField.getText();
        if (cidade.isEmpty()) cidade = null;

        String coberturaStr = coberturaPercentualTextField.getText();
        if (coberturaStr.isEmpty()) return null;

        double coberturaPercentual;

        try {
            coberturaPercentual =
                    Double.parseDouble(coberturaStr.replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }

        return new Seguradora(
            nome,
            cidade,
            coberturaPercentual
        );
    }

    private Seguradora getVisãoAlterada(String nome) {
        for (Seguradora visão : seguradoras_cadastradas) {
            if (visão.getNome().equals(nome)) return visão;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        comandosPanel = new javax.swing.JPanel();
        inserirSeguradora = new javax.swing.JButton();
        alterarSeguradora = new javax.swing.JButton();
        consultarSeguradora = new javax.swing.JButton();
        removerSeguradora = new javax.swing.JButton();
        limparCampos = new javax.swing.JButton();
        nomeLabel = new javax.swing.JLabel();
        nomeTextField = new javax.swing.JTextField();
        cidadeLabel = new javax.swing.JLabel();
        cidadeTextField = new javax.swing.JTextField();
        coberturaPercentualLabel = new javax.swing.JLabel();
        coberturaPercentualTextField = new javax.swing.JTextField();
        seguradorasCadastradasLabel = new javax.swing.JLabel();
        seguradoras_cadastradasComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Editoras");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        inserirSeguradora.setText("Inserir");
        inserirSeguradora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirSeguradora(evt);
            }
        });
        comandosPanel.add(inserirSeguradora);

        alterarSeguradora.setText("Alterar");
        alterarSeguradora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarSeguradora(evt);
            }
        });
        comandosPanel.add(alterarSeguradora);

        consultarSeguradora.setText("Consultar");
        consultarSeguradora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarSeguradora(evt);
            }
        });
        comandosPanel.add(consultarSeguradora);

        removerSeguradora.setText("Remover");
        removerSeguradora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerSeguradora(evt);
            }
        });
        comandosPanel.add(removerSeguradora);

        limparCampos.setText("Limpar");
        limparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparCampos(evt);
            }
        });
        comandosPanel.add(limparCampos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(comandosPanel, gridBagConstraints);

        nomeLabel.setText("Nome");
        nomeLabel.setMaximumSize(null);
        nomeLabel.setMinimumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 16, 10, 10);
        getContentPane().add(nomeLabel, gridBagConstraints);

        nomeTextField.setColumns(50);
        nomeTextField.setPreferredSize(new java.awt.Dimension(456, 20));
        nomeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(nomeTextField, gridBagConstraints);

        cidadeLabel.setText("Cidade");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 16, 10, 10);
        getContentPane().add(cidadeLabel, gridBagConstraints);

        cidadeTextField.setColumns(50);
        cidadeTextField.setPreferredSize(new java.awt.Dimension(456, 20));
        cidadeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidadeTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(cidadeTextField, gridBagConstraints);

        coberturaPercentualLabel.setText("Cobertura Percentual");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 16, 10, 10);
        getContentPane().add(coberturaPercentualLabel, gridBagConstraints);

        coberturaPercentualTextField.setColumns(50);
        coberturaPercentualTextField.setPreferredSize(new java.awt.Dimension(456, 20));
        coberturaPercentualTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coberturaPercentualTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(coberturaPercentualTextField, gridBagConstraints);

        seguradorasCadastradasLabel.setText("Seguradoras Cadastradas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 5);
        getContentPane().add(seguradorasCadastradasLabel, gridBagConstraints);

        seguradoras_cadastradasComboBox.setModel(new DefaultComboBoxModel(seguradoras_cadastradas));
        seguradoras_cadastradasComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seguradoras_cadastradasComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 5, 10, 5);
        getContentPane().add(seguradoras_cadastradasComboBox, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nomeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeTextFieldActionPerformed

    private void cidadeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cidadeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cidadeTextFieldActionPerformed

    private void coberturaPercentualTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coberturaPercentualTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_coberturaPercentualTextFieldActionPerformed

    private void alterarSeguradora(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterarSeguradora
        Seguradora seguradora =
                obterSeguradoraInformada();

        String mensagem_erro = null;

        if (seguradora != null)
            mensagem_erro =
                    controlador.alterarSeguradora(seguradora);
        else
            mensagem_erro =
                    "Algum atributo da Seguradora não foi informado.";

        if (mensagem_erro == null) {

            Seguradora visão =
                    getVisãoAlterada(seguradora.getNome());

            if (visão != null) {

                visão.setCidade(
                        seguradora.getCidade()
                );

                visão.setCoberturaPercentual(
                        seguradora.getCoberturaPercentual()
                );

                seguradoras_cadastradasComboBox.updateUI();

                seguradoras_cadastradasComboBox
                        .setSelectedItem(visão);
            }

        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_alterarSeguradora

    private void inserirSeguradora(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirSeguradora
        Seguradora seguradora =
                obterSeguradoraInformada();

        String mensagem_erro = null;

        if (seguradora != null)
            mensagem_erro =
                    controlador.inserirSeguradora(seguradora);
        else
            mensagem_erro =
                    "Algum atributo da Seguradora não foi informado";

        if (mensagem_erro == null) {

            Seguradora visão =
                    seguradora.getVisão();

            seguradoras_cadastradasComboBox
                    .addItem(visão);

            seguradoras_cadastradasComboBox
                    .setSelectedItem(visão);

        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_inserirSeguradora

    private void consultarSeguradora(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarSeguradora
        Seguradora visão =
                (Seguradora)
                seguradoras_cadastradasComboBox
                        .getSelectedItem();

        Seguradora seguradora = null;

        String mensagem_erro = null;

        if (visão != null) {

            seguradora =
                    Seguradora.buscarSeguradora(
                            visão.getNome()
                    );

            if (seguradora == null)
                mensagem_erro =
                        "Seguradora não cadastrada";

        } else {
            mensagem_erro =
                    "Nenhuma Seguradora selecionada";
        }

        if (mensagem_erro == null) {

            nomeTextField.setText(
                    seguradora.getNome()
            );

            String cidade =
                    seguradora.getCidade();

            if (cidade == null)
                cidade = "";

            cidadeTextField.setText(cidade);

            coberturaPercentualTextField.setText(
                    String.valueOf(
                            seguradora
                                    .getCoberturaPercentual()
                    )
            );

        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_consultarSeguradora

    private void removerSeguradora(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerSeguradora
        Seguradora visão =
                (Seguradora)
                seguradoras_cadastradasComboBox
                        .getSelectedItem();

        String mensagem_erro = null;

        if (visão != null)
            mensagem_erro =
                    controlador.removerSeguradora(
                            visão.getNome()
                    );
        else
            mensagem_erro =
                    "Nenhuma Seguradora selecionada";

        if (mensagem_erro == null) {

            seguradoras_cadastradasComboBox
                    .removeItem(visão);

            limparCampos(evt);

        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_removerSeguradora

    private void limparCampos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparCampos
        nomeTextField.setText("");
        cidadeTextField.setText("");
        coberturaPercentualTextField.setText("");
    }//GEN-LAST:event_limparCampos

    private void seguradoras_cadastradasComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seguradoras_cadastradasComboBoxActionPerformed
       
    }//GEN-LAST:event_seguradoras_cadastradasComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterarSeguradora;
    private javax.swing.JLabel cidadeLabel;
    private javax.swing.JTextField cidadeTextField;
    private javax.swing.JLabel coberturaPercentualLabel;
    private javax.swing.JTextField coberturaPercentualTextField;
    private javax.swing.JPanel comandosPanel;
    private javax.swing.JButton consultarSeguradora;
    private javax.swing.JButton inserirSeguradora;
    private javax.swing.JButton limparCampos;
    private javax.swing.JLabel nomeLabel;
    private javax.swing.JTextField nomeTextField;
    private javax.swing.JButton removerSeguradora;
    private javax.swing.JLabel seguradorasCadastradasLabel;
    private javax.swing.JComboBox seguradoras_cadastradasComboBox;
    // End of variables declaration//GEN-END:variables
}
