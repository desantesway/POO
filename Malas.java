import java.time.LocalDate;
import java.util.Objects;

public class Malas extends Artigo{
    private boolean Premium;
    private LocalDate dataPremium;
    private double valorizacao;

    public Malas(boolean premium, LocalDate dataPremium, double valorizacao) {
        this.Premium = premium;
        this.dataPremium = dataPremium;
        this.valorizacao = valorizacao;
    }
    public Malas(Malas m){
        this.Premium=m.isPremium();
        this.dataPremium=m.getDataPremium();
        this.valorizacao=m.getValorizacao();
    }
    public Malas(){
        this.Premium=false;
        this.dataPremium=LocalDate.now();
        this.valorizacao=0;
    }


    public boolean isPremium() {
        return Premium;
    }


    public void setPremium(boolean premium) {
        Premium = premium;
    }

    public LocalDate getDataPremium() {
        return dataPremium;
    }

    public void setDataPremium(LocalDate dataPremium) {
        this.dataPremium = dataPremium;
    }

    public double getValorizacao() {
        return valorizacao;
    }

    public void setValorizacao(double valorizacao) {
        this.valorizacao = valorizacao;
    }

    public Malas clone(){
        return new Malas(this);
    }


    public String toString() {
        return "Malas{" +
                "Premium=" + Premium +
                ", dataPremium=" + dataPremium +
                ", valorizacao=" + valorizacao +
                '}';
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Malas malas = (Malas) o;
        return Premium == malas.Premium && Double.compare(malas.valorizacao, valorizacao) == 0 && Objects.equals(dataPremium, malas.dataPremium);
    }
}
