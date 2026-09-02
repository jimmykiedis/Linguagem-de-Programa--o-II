package controles;

import entidades.Pecas;
import entidades.PecasSinistros;
import entidades.Sinistro;
import interfaces.JanelaCadastroPecasSinistros;
import java.awt.Frame;

public class ControladorCadastroPecasSinistros {

    public ControladorCadastroPecasSinistros() {
        this(null, null);
    }

    public ControladorCadastroPecasSinistros(Frame owner, Sinistro sinistro) {
        JanelaCadastroPecasSinistros janela =
                new JanelaCadastroPecasSinistros(this, sinistro, owner);
        janela.setVisible(true);
        janela.toFront();
        janela.requestFocus();
    }

    public String inserirPecasSinistros(Pecas peca, Sinistro sinistro) {
        Sinistro sinistro_buscado =
                sinistro != null ? Sinistro.buscarSinistro(sinistro.getSegurado()) : null;
        if (sinistro_buscado == null) return "Sinistro nao cadastrado";

        Pecas peca_buscada =
                peca != null ? Pecas.buscarPecas(peca.getCodigo()) : null;
        if (peca_buscada == null) return "Peca nao cadastrada";

        if (PecasSinistros.existePecasSinistros(peca.getCodigo(), sinistro.getSegurado())) {
            return "Peca ja associada ao sinistro";
        }

        return PecasSinistros.inserirPecasSinistros(peca_buscada, sinistro_buscado);
    }

    public String removerPecasSinistros(Pecas peca, Sinistro sinistro) {
        Sinistro sinistro_buscado =
                sinistro != null ? Sinistro.buscarSinistro(sinistro.getSegurado()) : null;
        if (sinistro_buscado == null) return "Sinistro nao cadastrado";

        Pecas peca_buscada =
                peca != null ? Pecas.buscarPecas(peca.getCodigo()) : null;
        if (peca_buscada == null) return "Peca nao cadastrada";

        if (!PecasSinistros.existePecasSinistros(peca.getCodigo(), sinistro.getSegurado())) {
            return "Peca nao associada ao sinistro";
        }

        return PecasSinistros.removerPecasSinistros(peca_buscada, sinistro_buscado);
    }
}
