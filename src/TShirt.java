import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TShirt extends Artigo implements Serializable {
    private String Padrao, tamanho;

    //calcula o preço da T-shirt
    public void calcPreco(){
        double desconto = 0, preco = super.getPrecobase();
        switch (super.getEstado()) {
            case "Pouco usado", "Usado", "Muito usado" -> desconto = 0.5;
        } if (!(this.getPadrao().equals("liso")) && this.getNumeroDonos() > 0){
            preco *= desconto;
        }
        super.setPreco(preco);
    }

    // string para tshirt
    public TShirt fromString (String input){
        int num = 0, bf = 0;
        TShirt t = new TShirt();
        for(int i = 0; i< input.length(); i++){

            if(input.charAt(i) == '='){
                bf=i+1;
            }
            if(input.charAt(i) == ','){
                if(num == 0){
                    if(bf+1<= i-1) t.setPadrao(input.substring(bf+1, i-1));
                } else if(num == 1){
                    if(bf+1<= i-1) t.setTamanho(input.substring(bf+1, i-1));
                    break;
                }
                num += 1;
            }
        }
        return t;
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public TShirt(boolean publicado, boolean premium, String estado, int numeroDonos, String descricao, String brand,
                      double precobase, double desconto, Colecao colecao,
                      Transportadoras transportadoras) {
        super(publicado, premium, estado, numeroDonos,
                descricao, brand, precobase, desconto, colecao, transportadoras);
        this.Padrao="";
    }

    public TShirt(boolean publicado, boolean premium, String estado, int numeroDonos, String descricao, String brand,
                  double precobase, Colecao colecao,
                  Transportadoras transportadoras, String Padrao, String tamanho) {
        super(publicado, premium, estado, numeroDonos,
                descricao, brand, precobase, colecao, transportadoras);
        this.Padrao=Padrao;
        this.tamanho = tamanho;
    }

    public TShirt(Artigo art){
        super(art.isPublicado(), art.isPremium(), art.getEstado(), art.getNumeroDonos(), art.getDescricao(),
                art.getBrand(), art.getPrecobase(), art.getColecao(), art.getTransportadoras());
        super.setID(art.getID());
        this.Padrao="";
        this.tamanho = "S";
    }

    public TShirt(){
        super();
        this.Padrao="";
        this.tamanho = "S";
    }
    public TShirt(TShirt s){
        super(s.isPublicado(), s.isPremium(), s.getEstado(), s.getNumeroDonos(), s.getDescricao(), s.getBrand(),
                s.getPreco(), s.getPrecobase(), s.getColecao(),
                s.getTransportadoras());
        this.Padrao=s.getPadrao();
        this.tamanho = s.getTamanho();
    }
    public TShirt(String Padrao, String tamanho){
        super();
        this.Padrao=Padrao;
        this.tamanho = tamanho;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getPadrao() {
        return Padrao;
    }

    public void setPadrao(String padrao) {
        Padrao = padrao;
    }

    public String toString() {
        return "\n" + "TShirt{" +
                " Padrao='" + Padrao + '\'' +
                " Tamanho='" + tamanho + '\'' +
                ", " +
                super.toString(this) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TShirt tShirt = (TShirt) o;
        return Objects.equals(Padrao, tShirt.Padrao)
                && Objects.equals(tamanho, tShirt.tamanho)
                && super.equals(tShirt);
    }

    public TShirt clone(){
        return new TShirt(this);
    }
}
