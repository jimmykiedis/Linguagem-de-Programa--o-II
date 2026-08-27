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
        Pecas peca_buscada = Pecas.buscarPecas(pecas.getCodigo());
        if (peca_buscada == null) return Pecas.inserirPecas(pecas);
        else return "Código de Peça já cadastrado";
    }
    
    public String alterarPecas(Pecas pecas) {
        Pecas peca_buscada = Pecas.buscarPecas(pecas.getCodigo());
        if (peca_buscada != null) return Pecas.alterarPecas(pecas);
        else return "Código de Peça não cadastrado";
    }
    
    public String removerPecas(int codigo) {
        Pecas peca_buscada = Pecas.buscarPecas(codigo);
        if (peca_buscada != null) return Pecas.removerPecas(codigo);
        else return "Código de Peça não cadastrado";
    }
}
