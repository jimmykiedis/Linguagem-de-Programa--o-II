package controles;

import entidades.Sinistro;
import interfaces.JanelaCadastroSinistros;

public class ControladorCadastroSinistros {

    public ControladorCadastroSinistros() {
        new JanelaCadastroSinistros(this).setVisible(true);
    }

    public String inserirSinistro(Sinistro sinistro) {
        Sinistro sinistro_buscado = Sinistro.buscarSinistro(sinistro.getSequencial());
        if (sinistro_buscado == null) return Sinistro.inserirSinistro(sinistro);
        else return "Numero de Sinistro já cadastrado";
    }

    public String alterarSinistro(Sinistro sinistro) {
        Sinistro sinistro1 = Sinistro.buscarSinistro(sinistro.getSequencial());
        if (sinistro1 != null) return Sinistro.alterarSinistro(sinistro);
        else return "Numero de Sinistro não cadastrado";
    }

    public String removerSinistro(int sequencial) {
        Sinistro sinistro1 = Sinistro.buscarSinistro(sequencial);
        if (sinistro1 != null) return Sinistro.removerSinistro(sequencial);
        else return "Numero de Sinistro não cadastrado";
    }
}
