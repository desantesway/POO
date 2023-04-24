import java.util.Objects;

public abstract class Artigo {
    private String id;
    private String descricao;
    private String marca;
    private double precoBase;
    private double desconto;
    private int numeroDonos;

    // construtor, getters e setters

    public Artigo(String id, String descricao, String marca, double precoBase, double desconto, int numeroDonos) {
        this.id = id;
        this.descricao = descricao;
        this.marca = marca;
        this.precoBase = precoBase;
        this.desconto = desconto;
        this.numeroDonos = numeroDonos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public int getNumeroDonos(){
        return numeroDonos;
    }

    public void setNumeroDonos(int numeroDonos) {
        this.numeroDonos = numeroDonos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artigo)) return false;

        Artigo artigo = (Artigo) o;

        if (Double.compare(artigo.getPrecoBase(), getPrecoBase()) != 0) return false;
        if (Double.compare(artigo.getDesconto(), getDesconto()) != 0) return false;
        if (getNumeroDonos() != artigo.getNumeroDonos()) return false;
        if (!getId().equals(artigo.getId())) return false;
        if (!getDescricao().equals(artigo.getDescricao())) return false;
        return getMarca().equals(artigo.getMarca());
    }


    public Artigo clone() {
        try {
            return (Artigo) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public abstract double calculaPreco();


}
