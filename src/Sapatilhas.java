import java.util.Objects;
import java.time.LocalDate;

public class Sapatilhas extends Artigo {
    private int Atacadores;
    private String Cor;
    private LocalDate data;
    private LocalDate dataPremium;
    public Sapatilhas(){
        this.Atacadores=0;
        this.Cor="N/A";
        this.data=LocalDate.now();
        this.dataPremium=LocalDate.now();
    }

    public Sapatilhas(int Atacadores, String cor, LocalDate data, LocalDate dataPremium) {
        this.Atacadores = Atacadores;
        this.Cor = cor;
        this.data = data;
        this.dataPremium = dataPremium;
    }
    public Sapatilhas(Sapatilhas s) {
        this.Atacadores = s.getAtacadores();
        this.Cor = s.getCor();
        this.data = s.getData();
        this.dataPremium = s.getDataPremium();
    }

    public int getAtacadores() {
        return Atacadores;
    }

    public void setAtacadores(int atacadores) {
        Atacadores = atacadores;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String cor) {
        Cor = cor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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
        return "Sapatilhas{" +
                "Atacadores=" + Atacadores +
                ", Cor='" + Cor + '\'' +
                ", data=" + data +
                ", dataPremium=" + dataPremium +
                '}';
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sapatilhas that = (Sapatilhas) o;
        return Atacadores == that.Atacadores && Objects.equals(Cor, that.Cor) && Objects.equals(data, that.data) && Objects.equals(dataPremium, that.dataPremium);
    }

}
