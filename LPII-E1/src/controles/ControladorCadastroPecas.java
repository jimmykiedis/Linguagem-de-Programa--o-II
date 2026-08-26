package controles;

import entidades.Pecas;
import interfaces.JanelaCadastroPecas;

public class ControladorCadastroPecas {
    
    public ControladorCadastroPecas() {
        new JanelaCadastroPecas(this).setVisible(true);
    }
       
    public String inserirPecas(Pecas pecas) {
        Pecas pecas1 = Pecas.buscarPecas(pecas.getNome());
        if (pecas1 == null) return Pecas.inserirPecas(pecas);
        else return "Nome de Peça já cadastrado";
    }
    
    public String alterarPecas(Pecas pecas) {
        Pecas pecas1 = Pecas.buscarPecas(pecas.getNome());
        if (pecas1 != null) return Pecas.alterarPecas(pecas);
        else return "Nome de Peça não cadastrado";
    }
    
    public String removerPecas(String nome) {
        Pecas pecas1 = Pecas.buscarPecas(nome);
        if (pecas1 != null) return Pecas.removerPecas(nome);
        else return "Nome de Peça não cadastrado";
    }
}
