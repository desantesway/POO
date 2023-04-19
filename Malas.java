public class Malas extends Artigo{
    private String tamanho;
    private String material;
    private int anoColecao;
    private boolean novo;
    private double estadoUtilizacao;
    private boolean premium;

    // construtores, getters e setters

    public Malas(String id, String descricao, String marca, double precoBase, double desconto) {
        super(id, descricao, marca, precoBase, desconto);
        this.tamanho = tamanho;
        this.material = material;
        this.anoColecao = anoColecao;
        this.novo = novo;
        this.estadoUtilizacao = estadoUtilizacao;
        this.premium = premium;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getMaterial() {
        return material;
    }

    public int getAnoColecao() {
        return anoColecao;
    }

    public boolean isNovo() {
        return novo;
    }

    public double getEstadoUtilizacao() {
        return estadoUtilizacao;
    }

    public boolean isPremium(){
        return premium;
    }

    @Override
    public double calculaPreco() {
        if (premium){
        return getPrecoBase() * ;
    }
}
