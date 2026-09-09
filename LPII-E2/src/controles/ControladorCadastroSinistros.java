package controles;

import entidades.Sinistro;
import interfaces.JanelaCadastroSinistros;
import java.awt.Frame;

public class ControladorCadastroSinistros {

    public ControladorCadastroSinistros() {
        this(null);
    }

    public ControladorCadastroSinistros(Frame owner) {
        JanelaCadastroSinistros janela = new JanelaCadastroSinistros(this, owner);
        janela.setVisible(true);
        janela.toFront();
        janela.requestFocus();
    }

    public String inserirSinistro(Sinistro sinistro) {
        return Sinistro.inserirSinistro(sinistro);
    }

    public String alterarSinistro(Sinistro sinistro) {
        Sinistro sinistro1 = Sinistro.buscarSinistro(sinistro.getId());
        if (sinistro1 != null) return Sinistro.alterarSinistro(sinistro);
        else return "Segurado de Sinistro não cadastrado";
    }

    public String removerSinistro(int id) {
        Sinistro sinistro1 = Sinistro.buscarSinistro(id);
        if (sinistro1 != null) return Sinistro.removerSinistro(id);
        else return "Sinistro não cadastrado";
    }
}
