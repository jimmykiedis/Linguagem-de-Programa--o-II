package controles;

import entidades.Sinistro;
import interfaces.JanelaCadastroSinistros;

public class ControladorCadastroSinistros {

    public ControladorCadastroSinistros() {
        new JanelaCadastroSinistros(this).setVisible(true);
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
