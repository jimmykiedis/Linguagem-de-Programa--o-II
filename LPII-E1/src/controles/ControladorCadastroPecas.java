package controles;

import entidades.Pecas;
import interfaces.JanelaCadastroPecas;
import java.awt.Frame;

public class ControladorCadastroPecas {
    
    public ControladorCadastroPecas() {
        this(null);
    }

    public ControladorCadastroPecas(Frame owner) {
        JanelaCadastroPecas janela = new JanelaCadastroPecas(this, owner);
        janela.setVisible(true);
        janela.toFront();
        janela.requestFocus();
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
