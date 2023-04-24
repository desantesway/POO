import java.util.Objects;

public class TShirt extends Artigo {
    private String tamanho;
    private String padrao;


    // construtores, getters e setters

    public TShirt(String id, String descricao, String marca, double precoBase, double desconto, int numeroDonos) {
        super(id, descricao, marca, precoBase, desconto, numeroDonos);
        this.tamanho = tamanho;
        this.padrao = padrao;


    }

    public String getTamanho(){
        return tamanho;
    }

    public String getPadrao(){
        return padrao;
    }

    public void setTamanho(){
        this.tamanho = tamanho;
    }

    public void setPadrao(){
        this.padrao = padrao;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TShirt)) return false;
        if (!super.equals(o)) return false;

        TShirt tShirt = (TShirt) o;

        if (!getTamanho().equals(tShirt.getTamanho())) return false;
        return getPadrao().equals(tShirt.getPadrao());
    }


    @Override
    public double calculaPreco() {
        double preco = getPrecoBase();
        if (padrao.equals("liso")) {
            return preco;
        } else {
            if (getNumeroDonos() > 0) {
                preco -= (preco * 0.50); // desconto de 50% no preço
            } else {
                return preco;

            }
        }
        return preco;
    }


}
