import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Malas extends Artigo implements Serializable {
    private LocalDate dataPremium;
    private String tamanho, material;
    private double valorizacao;

    //string para mala
    public Malas fromString (String input){
        int num = 0, bf = 0;
        Malas mala = new Malas();
        for(int i = 0; i< input.length(); i++){

            if(input.charAt(i) == '='){
                bf=i+1;
            }
            if(input.charAt(i) == ','){
                if(num == 0){
                    if(input.substring(bf, i).contains("null")){
                        mala.setDataPremium(null);
                    }else{
                        mala.setDataPremium(LocalDate.parse(input.substring(bf, i)));
                    }
                } else if(num == 1){
                    if(bf+1<= i-1) mala.setMaterial(input.substring(bf+1, i-1));
                } else if(num == 2){
                    if(bf+1<= i-1) mala.setTamanho(input.substring(bf+1, i-1));
                } else if(num == 3){
                    mala.setValorizacao(Double.parseDouble(input.substring(bf, i)));
                    break;
                }
                num += 1;
            }
        }
        return mala;
    }

    //calcula o preço da mala
    public void calcPreco(LocalDate now){
        double desconto = 0, preco = super.getPrecobase();
        switch (this.getTamanho()) {
            case "Pequeno" -> desconto = 0.3;
            case "Medio" -> desconto = 0.25;
            case "Grande" -> desconto = 0.2;
        }
        preco -= preco * desconto;
        if(super.isPremium()){
            if(!now.isBefore(this.dataPremium)) preco += preco * this.getValorizacao() *
                    (ChronoUnit.YEARS.between(this.dataPremium, now) + 1);
        }
        super.setPreco(preco);
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */

    public Malas(Artigo art){
        super(art.isPublicado(), art.isPremium(), art.getEstado(), art.getNumeroDonos(), art.getDescricao(),
                art.getBrand(), art.getPrecobase(), art.getColecao(), art.getTransportadoras());
        super.setID(art.getID());
        super.setBorn(art.getBorn());
        super.setSold(art.getSold());
        super.setDevolucao(art.getDevolucao());
        super.setPreco(art.getPreco());
        this.tamanho = "";
        this.material = "";
        this.valorizacao = 0.1;
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
                m.getPreco(), m.getPrecobase(), m.getColecao(), m.getTransportadoras());
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
        return "\n" + "Malas {" +
                " dataPremium=" + dataPremium +
                ", material='" + material + '\'' +
                ", tamanho='" + tamanho + '\'' +
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
