package interfaces;

import javax.swing.JOptionPane;
import controles.ControladorCadastroSeguradoras;
import entidades.Seguradora;
import javax.swing.DefaultComboBoxModel;
import java.awt.Frame;

public class JanelaCadastroSeguradoras extends javax.swing.JFrame {

    ControladorCadastroSeguradoras controlador;
    Seguradora[] seguradoras_cadastradas;

    public JanelaCadastroSeguradoras(ControladorCadastroSeguradoras controlador) {
        this(controlador, null);
    }

    public JanelaCadastroSeguradoras(
            ControladorCadastroSeguradoras controlador,
            Frame owner
    ) {
        this.controlador = controlador;
        seguradoras_cadastradas = Seguradora.getVisoes();
        initComponents();
        atualizarSeguradorasCadastradas();
        setSize(new java.awt.Dimension(700, 460));
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

    private Seguradora localizarSeguradora(String nome) {
        if (nome == null) return null;
        for (Seguradora visao : seguradoras_cadastradas) {
            if (nome.equals(visao.getNome())) return visao;
        }
        return null;
    }

    private void atualizarSeguradorasCadastradas() {
        atualizarSeguradorasCadastradas(null);
    }

    private void atualizarSeguradorasCadastradas(String nome_selecionado) {
        seguradoras_cadastradas = Seguradora.getVisoes();
        seguradoras_cadastradasComboBox.setModel(
                new DefaultComboBoxModel(seguradoras_cadastradas)
        );

        Seguradora visao_selecionada = localizarSeguradora(nome_selecionado);
        if (visao_selecionada != null) {
            seguradoras_cadastradasComboBox.setSelectedItem(visao_selecionada);
        }
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

        String cobertura_str = coberturaPercentualTextField.getText();
        if (cobertura_str.isEmpty()) return null;

        double cobertura_percentual;

        try {
            cobertura_percentual =
                    Double.parseDouble(cobertura_str.replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }

        return new Seguradora(
            nome,
            cidade,
            cobertura_percentual
        );
    }

    private Seguradora getVisaoAlterada(String nome) {
        for (Seguradora visao : seguradoras_cadastradas) {
            if (visao.getNome().equals(nome)) return visao;
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
        setTitle("Cadastrar Seguradoras");
        setMinimumSize(new java.awt.Dimension(620, 360));
        setPreferredSize(new java.awt.Dimension(700, 460));
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

        nomeTextField.setColumns(24);
        nomeTextField.setPreferredSize(new java.awt.Dimension(240, 20));
        nomeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 80;
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

        cidadeTextField.setColumns(18);
        cidadeTextField.setPreferredSize(new java.awt.Dimension(180, 20));
        cidadeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidadeTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 20;
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

        coberturaPercentualTextField.setColumns(10);
        coberturaPercentualTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        coberturaPercentualTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coberturaPercentualTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 0;
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

            Seguradora visao =
                    getVisaoAlterada(seguradora.getNome());

            if (visao != null) {

                visao.setCidade(
                        seguradora.getCidade()
                );

                visao.setCoberturaPercentual(
                        seguradora.getCoberturaPercentual()
                );

                seguradoras_cadastradasComboBox.updateUI();

                seguradoras_cadastradasComboBox
                        .setSelectedItem(visao);
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

            Seguradora visao = seguradora.getVisao();
            atualizarSeguradorasCadastradas(visao.getNome());

        } else {
            informarErro(mensagem_erro);
        }
    }//GEN-LAST:event_inserirSeguradora

    private void consultarSeguradora(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarSeguradora
        Seguradora visao =
                (Seguradora)
                seguradoras_cadastradasComboBox
                        .getSelectedItem();

        Seguradora seguradora = null;

        String mensagem_erro = null;

        if (visao != null) {

            seguradora =
                    Seguradora.buscarSeguradora(
                            visao.getNome()
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
        Seguradora visao =
                (Seguradora)
                seguradoras_cadastradasComboBox
                        .getSelectedItem();

        String mensagem_erro = null;

        if (visao != null)
            mensagem_erro =
                    controlador.removerSeguradora(
                            visao.getNome()
                    );
        else
            mensagem_erro =
                    "Nenhuma Seguradora selecionada";

        if (mensagem_erro == null) {

            atualizarSeguradorasCadastradas();

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
