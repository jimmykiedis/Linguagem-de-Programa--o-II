package entidades;

public class PecaMecanica extends Pecas {

    private final TipoPeca tipo_peca_mecanica = TipoPeca.MECANICA;

    public PecaMecanica(int codigo, String nome, MarcaPeca marca, double preco,
            boolean mao_obra_propria, Integer dias_garantia) {
        super(codigo, nome, marca, preco, mao_obra_propria, dias_garantia, null);
    }

    @Override
    public TipoPeca getTipo() { return tipo_peca_mecanica; }

    public TipoPeca getTipoPecaMecanica() { return tipo_peca_mecanica; }

    @Override
    public void setTipo(TipoPeca tipo) { }
}
