public class TShirt extends Artigo {
    private String tamanho;
    private String padrao;
    private boolean novo;
    private double estadoUtilizacao;

    // construtores, getters e setters

    public TShirt(String id, String descricao, String marca, double precoBase, double desconto) {
        super(id, descricao, marca, precoBase, desconto);
        this.tamanho = tamanho;
        this.padrao = padrao;
        this.novo = novo;
        this.estadoUtilizacao = estadoUtilizacao;

    }

    public String getTamanho(){
        return tamanho;
    }

    public String getPadrao(){
        return padrao;
    }

    public String setTamanho(){
        return tamanho;
    }

    public String setPadrao(){
        return padrao;
    }

    public boolean isNovo(){
        return novo;
    }

    public double getEstadoUtilizacao(){
        return estadoUtilizacao;
    }

    @Override
    public double calculaPreco() {
        if (this.padrao.equals("liso")) {
            return getPrecoBase();
        } else {
            return getPrecoBase() * 0.5;
        }
    }


}
