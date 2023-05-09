import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Malas extends Artigo{
    private LocalDate dataPremium;
    private String tamanho, material;
    private double valorizacao;

    //calcula o preço da mala
    public void calcPreco(){
        double desconto = 0, preco = super.getPrecobase();
        switch (this.getTamanho()) {
            case "S" -> desconto = 0.3;
            case "M" -> desconto = 0.25;
            case "L" -> desconto = 0.2;
            case "XL" -> desconto = 0.15;
            case "XXL" -> desconto = 0.1;
        }
        preco -= preco * desconto;
        if(super.isPremium()){
            preco += preco * this.getValorizacao() * (ChronoUnit.YEARS.between(LocalDate.now(), this.dataPremium));
        }
        super.setPreco(preco);
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */

    public Malas(boolean publicado, boolean premium, String estado, int numeroDonos, String descricao, String brand,
                 double precobase, double desconto, Colecao colecao,
                 Transportadoras transportadoras) {
        super(publicado, premium, estado, numeroDonos, descricao, brand, precobase, desconto, colecao, transportadoras);
        calcPreco();
        this.tamanho = "";
        this.material = "";
        this.dataPremium = LocalDate.now();
        this.valorizacao = 0.1;
    }

    public Malas(boolean publicado, boolean premium, String estado, int numeroDonos, String descricao, String brand,
                 double precobase, double desconto, Colecao colecao,
                 Transportadoras transportadoras, String tamanho, String material, double valorizacao) {
        super(publicado, premium, estado, numeroDonos, descricao, brand, precobase, desconto, colecao, transportadoras);
        calcPreco();
        this.tamanho = tamanho;
        this.material = material;
        this.dataPremium = LocalDate.now();
        this.valorizacao = valorizacao;
    }

    public Malas(LocalDate dataPremium, String tamanho, String material, double valorizacao) {
        super();
        this.tamanho = tamanho;
        this.material = material;
        this.dataPremium = dataPremium;
        this.valorizacao = valorizacao;
    }

    public Malas(Malas m){
        super(m.isPublicado(), m.isPremium(), m.getEstado(), m.getNumeroDonos(), m.getDescricao(), m.getBrand(),
                m.getPreco(), m.getPrecobase(), m.getDesconto(), m.getColecao(), m.getTransportadoras());
        this.tamanho = m.getTamanho();
        this.dataPremium=m.getDataPremium();
        this.material=m.getMaterial();
        this.valorizacao=m.getValorizacao();
    }

    public Malas(){
        super();
        this.tamanho = "";
        this.material = "";
        this.valorizacao = 0.1;
        this.dataPremium=null;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getValorizacao() {
        return valorizacao;
    }

    public void setValorizacao(double valorizacao) {
        this.valorizacao = valorizacao;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public LocalDate getDataPremium() {
        return dataPremium;
    }

    public void setDataPremium(LocalDate dataPremium) {
        this.dataPremium = dataPremium;
    }

    public Malas clone(){
        return new Malas(this);
    }


    public String toString() {
        return "Malas {" +
                ", dataPremium=" + dataPremium +
                ", material=" + material +
                ", tamanho=" + tamanho +
                ", valorização=" + valorizacao +
                ", " +
                super.toString(this) +
                '}';
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Malas malas = (Malas) o;
        return Objects.equals(dataPremium, malas.dataPremium)
                && Objects.equals(tamanho, malas.getTamanho())
                && Objects.equals(material, malas.getMaterial())
                && Double.compare(valorizacao, malas.valorizacao) == 0
                && super.equals(malas);
    }
}
