package entidades;

public class PecaLataria extends Pecas {

    private final TipoPeca tipo_peca_lataria = TipoPeca.LATARIA;

    public PecaLataria(int codigo, String nome, MarcaPeca marca, double preco,
            boolean mao_obra_propria, String cor) {
        super(codigo, nome, marca, preco, mao_obra_propria, null, cor);
    }

    @Override
    public TipoPeca getTipo() { return tipo_peca_lataria; }

    public TipoPeca getTipoPecaLataria() { return tipo_peca_lataria; }

    @Override
    public void setTipo(TipoPeca tipo) { }
}
