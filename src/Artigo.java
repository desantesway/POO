public abstract class Artigo {
    private String id;
    private String descricao;
    private String marca;
    private double precoBase;
    private double desconto;
    private boolean novo;

    // construtor, getters e setters
    public Artigo(String id, String descricao, String marca, double precoBase, double desconto, boolean novo) {
        this.id = id;
        this.descricao = descricao;
        this.marca = marca;
        this.precoBase = precoBase;
        this.desconto = desconto;
        this.novo = novo;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
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
