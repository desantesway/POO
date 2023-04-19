public abstract class Artigo {
    private String id;
    private String descricao;
    private String marca;
    private double precoBase;
    private double desconto;

    // construtor, getters e setters

    public Artigo(String id, String descricao, String marca, double precoBase, double desconto) {
        this.id = id;
        this.descricao = descricao;
        this.marca = marca;
        this.precoBase = precoBase;
        this.desconto = desconto;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMarca() {
        return marca;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public double getDesconto() {
        return desconto;
    }

    public abstract double calculaPreco();
}
