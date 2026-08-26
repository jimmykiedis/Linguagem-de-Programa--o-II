package controles;

import entidades.Seguradora;
import interfaces.JanelaCadastroSeguradoras;

public class ControladorCadastroSeguradoras {

    public ControladorCadastroSeguradoras() {
        new JanelaCadastroSeguradoras(this).setVisible(true);
    }
       
    public String inserirSeguradora(Seguradora seguradora) {
        Seguradora seguradora1 = Seguradora.buscarSeguradora(seguradora.getNome());
        if (seguradora1 == null) return Seguradora.inserirSeguradora(seguradora);
        else return "Nome de Seguradora já cadastrado";
    }
    
    public String alterarSeguradora(Seguradora seguradora) {
        Seguradora seguradora1 = Seguradora.buscarSeguradora(seguradora.getNome());
        if (seguradora1 != null) return Seguradora.alterarSeguradora(seguradora);
        else return "Nome de Seguradora não cadastrado";
    }
    
    public String removerSeguradora(String nome) {
        Seguradora seguradora1 = Seguradora.buscarSeguradora(nome);
        if (seguradora1 != null) return Seguradora.removerSeguradora(nome);
        else return "Nome de Seguradora não cadastrado";
    }
}