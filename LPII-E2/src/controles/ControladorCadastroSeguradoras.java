package controles;

import entidades.Seguradora;
import interfaces.JanelaCadastroSeguradoras;
import java.awt.Frame;

public class ControladorCadastroSeguradoras {

    public ControladorCadastroSeguradoras() {
        this(null);
    }

    public ControladorCadastroSeguradoras(Frame owner) {
        JanelaCadastroSeguradoras janela = new JanelaCadastroSeguradoras(this, owner);
        janela.setVisible(true);
        janela.toFront();
        janela.requestFocus();
    }
       
    public String inserirSeguradora(Seguradora seguradora) {
        Seguradora seguradora_buscada = Seguradora.buscarSeguradora(seguradora.getNome());
        if (seguradora_buscada == null) return Seguradora.inserirSeguradora(seguradora);
        else return "Nome de Seguradora já cadastrado";
    }
    
    public String alterarSeguradora(Seguradora seguradora) {
        Seguradora seguradora_buscada = Seguradora.buscarSeguradora(seguradora.getNome());
        if (seguradora_buscada != null) return Seguradora.alterarSeguradora(seguradora);
        else return "Nome de Seguradora não cadastrado";
    }
    
    public String removerSeguradora(String nome) {
        Seguradora seguradora_buscada = Seguradora.buscarSeguradora(nome);
        if (seguradora_buscada != null) return Seguradora.removerSeguradora(nome);
        else return "Nome de Seguradora não cadastrado";
    }
}
