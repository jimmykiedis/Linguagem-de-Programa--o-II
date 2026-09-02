package interfaces;

import controles.ControladorCadastroSeguradoras;
import controles.ControladorCadastroPecas;
import controles.ControladorCadastroSinistros;
import javax.swing.JOptionPane;
import persistência.BD;

public class JanelaSistema extends javax.swing.JFrame {

    public JanelaSistema() {
        BD.criaConexao();
        initComponents();
    }

    @SuppressWarnings("unchecked")

    private void informarServiçoIndisponível() {
        JOptionPane.showMessageDialog(
            this,
            "Serviço Indisponível",
            "Informação",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        seguradoras_orçamentoMenuBar = new javax.swing.JMenuBar();
        sinistroMenu = new javax.swing.JMenu();
        cadastrar_sinistroMenuItem = new javax.swing.JMenuItem();
        seguradoraMenu = new javax.swing.JMenu();
        cadastrar_seguradoraItemMenu = new javax.swing.JMenuItem();
        peçaMenu = new javax.swing.JMenu();
        cadastrar_peçaMenuItem = new javax.swing.JMenuItem();
        orçamentoMenu = new javax.swing.JMenu();
        cadastrar_orçamentoMenuItem = new javax.swing.JMenuItem();
        pesquisar_orçamentoMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Sinistros, Seguradoras e Pecas");
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                terminarSistema(evt);
            }
        });

        sinistroMenu.setText("Sinistro");

        cadastrar_sinistroMenuItem.setText("Cadastrar");
        cadastrar_sinistroMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarSinistro(evt);
            }
        });
        sinistroMenu.add(cadastrar_sinistroMenuItem);

        seguradoras_orçamentoMenuBar.add(sinistroMenu);

        seguradoraMenu.setText("Seguradora");

        cadastrar_seguradoraItemMenu.setText("Cadastrar");
        cadastrar_seguradoraItemMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarSeguradora(evt);
            }
        });
        seguradoraMenu.add(cadastrar_seguradoraItemMenu);

        seguradoras_orçamentoMenuBar.add(seguradoraMenu);

        peçaMenu.setText("Peça");

        cadastrar_peçaMenuItem.setText("Cadastrar");
        cadastrar_peçaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarPeça(evt);
            }
        });
        peçaMenu.add(cadastrar_peçaMenuItem);

        seguradoras_orçamentoMenuBar.add(peçaMenu);

        orçamentoMenu.setText("Orçamento");

        cadastrar_orçamentoMenuItem.setText("Cadastrar");
        cadastrar_orçamentoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarOrçamentos(evt);
            }
        });
        orçamentoMenu.add(cadastrar_orçamentoMenuItem);

        pesquisar_orçamentoMenuItem.setText("Pesquisar");
        pesquisar_orçamentoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisarOrçamentos(evt);
            }
        });
        orçamentoMenu.add(pesquisar_orçamentoMenuItem);

        seguradoras_orçamentoMenuBar.add(orçamentoMenu);

        setJMenuBar(seguradoras_orçamentoMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cadastrarSeguradora(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarSeguradora
        new ControladorCadastroSeguradoras(this);
    }//GEN-LAST:event_cadastrarSeguradora

    private void terminarSistema(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_terminarSistema
        BD.fechaConexao();
        System.exit(0);
    }//GEN-LAST:event_terminarSistema
  
    private void pesquisarOrçamentos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisarOrçamentos
        informarServiçoIndisponível();
    }//GEN-LAST:event_pesquisarOrçamentos

    private void cadastrarPeça(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarPeça
        new ControladorCadastroPecas(this);
    }//GEN-LAST:event_cadastrarPeça

    private void cadastrarOrçamentos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarOrçamentos
        informarServiçoIndisponível();
    }//GEN-LAST:event_cadastrarOrçamentos

    private void cadastrarSinistro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarSinistro
        new ControladorCadastroSinistros();
    }//GEN-LAST:event_cadastrarSinistro

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaSistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem cadastrar_orçamentoMenuItem;
    private javax.swing.JMenuItem cadastrar_peçaMenuItem;
    private javax.swing.JMenuItem cadastrar_seguradoraItemMenu;
    private javax.swing.JMenuItem cadastrar_sinistroMenuItem;
    private javax.swing.JMenu orçamentoMenu;
    private javax.swing.JMenuItem pesquisar_orçamentoMenuItem;
    private javax.swing.JMenu peçaMenu;
    private javax.swing.JMenu seguradoraMenu;
    private javax.swing.JMenuBar seguradoras_orçamentoMenuBar;
    private javax.swing.JMenu sinistroMenu;
    // End of variables declaration//GEN-END:variables
 
}
