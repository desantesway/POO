public class Malas extends Artigo{
    private String tamanho;
    private String material;
    private int anoColecao;
    private boolean premium;

    // construtores, getters e setters

    public Malas(String id, String descricao, String marca, double precoBase, double desconto, int numeroDonos) {
        super(id, descricao, marca, precoBase, desconto, numeroDonos);
        this.tamanho = tamanho;
        this.material = material;
        this.anoColecao = anoColecao;
        this.premium = premium;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getAnoColecao() {
        return anoColecao;
    }

    public void setAnoColecao(int anoColecao) {
        this.anoColecao = anoColecao;
    }

    public boolean isPremium(){
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Malas)) return false;
        if (!super.equals(o)) return false;

        Malas malas = (Malas) o;

        if (getAnoColecao() != malas.getAnoColecao()) return false;
        if (isPremium() != malas.isPremium()) return false;
        if (!getTamanho().equals(malas.getTamanho())) return false;
        return getMaterial().equals(malas.getMaterial());
    }

    

    @Override
    public double calculaPreco() {
        double preco = getPrecoBase();

        // se a mala é usada, aplica desconto em função do número de donos e estado de utilização
        if (getNumeroDonos() > 0) {
            preco -= preco / (getNumeroDonos() * 0.5 * getAnoColecao());
        }

        // adiciona 20% ao preço para malas com material de couro
        if (this.material.equals("couro")) {
            preco += preco * 0.2;
        }

        // adiciona 10% ao preço para malas com tamanho acima de 80cm
        if (this.tamanho.toLowerCase().equals("grande") || this.tamanho.toLowerCase().equals("gigante")) {
            preco += preco * 0.1;
        }

        return preco;
    }
}
