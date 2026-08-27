package controles;

import entidades.Sinistro;
import interfaces.JanelaCadastroSinistros;

public class ControladorCadastroSinistros {

    public ControladorCadastroSinistros() {
        new JanelaCadastroSinistros(this).setVisible(true);
    }

    public String inserirSinistro(Sinistro sinistro) {
        Sinistro sinistro_buscado = Sinistro.buscarSinistro(sinistro.getSegurado());
        if (sinistro_buscado == null) return Sinistro.inserirSinistro(sinistro);
        else return "Segurado de Sinistro já cadastrado";
    }

    public String alterarSinistro(Sinistro sinistro) {
        Sinistro sinistro1 = Sinistro.buscarSinistro(sinistro.getSegurado());
        if (sinistro1 != null) return Sinistro.alterarSinistro(sinistro);
        else return "Segurado de Sinistro não cadastrado";
    }

    public String removerSinistro(String segurado) {
        Sinistro sinistro1 = Sinistro.buscarSinistro(segurado);
        if (sinistro1 != null) return Sinistro.removerSinistro(segurado);
        else return "Segurado de Sinistro não cadastrado";
    }
}
