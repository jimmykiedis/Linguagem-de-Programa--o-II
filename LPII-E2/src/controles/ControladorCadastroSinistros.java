package controles;

import entidades.Sinistro;
import interfaces.JanelaCadastroSinistros;

public class ControladorCadastroSinistros {

    public ControladorCadastroSinistros() {
        new JanelaCadastroSinistros(this).setVisible(true);
    }

    public String inserirSinistro(Sinistro sinistro) {
        if (!Sinistro.existeSinistroMesmosAtributos(sinistro)) return Sinistro.inserirSinistro(sinistro);
        else return "Já existe um Sinistro com os mesmos atributos";
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
