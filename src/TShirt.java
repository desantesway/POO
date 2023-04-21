public class TShirt extends Artigo {
    private String tamanho;
    private String padrao;

    // construtores, getters e setters


    public TShirt(String id, String descricao, String marca, double precoBase, double desconto, boolean novo, String tamanho, String padrao) {
        super(id, descricao, marca, precoBase, desconto, novo);
        this.tamanho = tamanho;
        this.padrao = padrao;
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


    @Override
    public double calculaPreco() {
        if (this.padrao.equals("liso")) {
            return getPrecoBase();
        } else {
            return getPrecoBase() * 0.5;
        }
    }


}
