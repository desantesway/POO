import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.time.LocalDate;

public class Sapatilhas extends Artigo implements Serializable {
    private int tamanho;
    private boolean atacadores;
    private String Cor;
    private LocalDate dataPremium;

    //calcula o preço da sapatilha
    public void calcPreco(LocalDate now){
        double desconto = 0, preco = super.getPrecobase();
        if(super.getNumeroDonos() != 0){
            desconto += super.getPrecobase() / super.getNumeroDonos();
        }
        switch (super.getEstado()) {
            case "Pouco usado" -> desconto *= 2;
            case "Usado" -> desconto *= 3;
            case "Muito usado" -> desconto *= 4;
        } if (!(super.getColecao().getdata().plusDays(365).isAfter(now))
                || this.getTamanho() >= 45){
            preco -= desconto;
        } if(super.isPremium()){
            preco += 100 * (ChronoUnit.YEARS.between(now, this.dataPremium) *
                    (ChronoUnit.YEARS.between(now, this.dataPremium)));
        }
        super.setPreco(preco);
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */

    public Sapatilhas(Artigo art){
        super(art.isPublicado(), art.isPremium(), art.getEstado(), art.getNumeroDonos(), art.getDescricao(),
                art.getBrand(), art.getPrecobase(), art.getColecao(), art.getTransportadoras());
        super.setID(art.getID());
        this.atacadores=true;
        this.Cor="N/A";
        this.dataPremium= null;
    }
    public Sapatilhas(){
        super();
        this.atacadores=true;
        this.Cor="N/A";
        this.dataPremium= null;
    }

    public Sapatilhas(boolean publicado, boolean premium, String estado, int numeroDonos, String descricao, String brand,
                      double precobase, Colecao colecao,
                      Transportadoras transportadoras) {
        super(publicado, premium, estado, numeroDonos,
                descricao, brand, precobase, colecao, transportadoras);
        this.atacadores=true;
        this.Cor="N/A";
        this.dataPremium= null;
    }

    public Sapatilhas(boolean publicado, boolean premium, String estado, int numeroDonos, String descricao, String brand,
                 double precobase, double desconto, Colecao colecao,
                 Transportadoras transportadoras, boolean Atacadores, String cor, LocalDate dataPremium, int tamanho) {
        super(publicado, premium, estado, numeroDonos,
                descricao, brand, precobase, desconto, colecao, transportadoras);
        this.atacadores = Atacadores;
        this.Cor = cor;
        this.dataPremium = dataPremium;
        this.tamanho = tamanho;
    }

    public Sapatilhas(boolean Atacadores, String cor, LocalDate dataPremium, int tamanho) {
        super();
        this.atacadores = Atacadores;
        this.Cor = cor;
        this.dataPremium = dataPremium;
        this.tamanho = tamanho;
    }

    public Sapatilhas(Sapatilhas s) {
        super(s.isPublicado(), s.isPremium(), s.getEstado(), s.getNumeroDonos(), s.getDescricao(), s.getBrand(),
                s.getPreco(), s.getPrecobase(), s.getColecao(),
                s.getTransportadoras());
        this.atacadores = s.getAtacadores();
        this.Cor = s.getCor();
        this.dataPremium = s.getDataPremium();
        this.tamanho = s.getTamanho();
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public boolean getAtacadores() {
        return atacadores;
    }

    public void setAtacadores(boolean atacadores) {
        this.atacadores = atacadores;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String cor) {
        this.Cor = cor;
    }

    public LocalDate getDataPremium() {
        return dataPremium;
    }

    public void setDataPremium(LocalDate dataPremium) {
        this.dataPremium = dataPremium;
    }
    public Sapatilhas clone(){
        return new Sapatilhas(this);
    }

    public String toString() {
        return  "\n" + "Sapatilhas{" +
                " Tamanho=" + tamanho +
                ", Atacadores=" + atacadores +
                ", Cor='" + Cor + '\'' +
                ", dataPremium=" + dataPremium +
                ", " +
                super.toString(this) +
                '}';
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sapatilhas that = (Sapatilhas) o;
        return atacadores && that.atacadores
                && Objects.equals(Cor, that.Cor)
                && Objects.equals(dataPremium, that.dataPremium)
                && super.equals(that);
    }

}
