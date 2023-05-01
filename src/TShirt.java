import java.util.Objects;

public class TShirt extends Artigo {
    private String Padrao;

    public TShirt(){
        this.Padrao="";
    }
    public TShirt(TShirt t){
        this.Padrao=getPadrao();
    }
    public TShirt(String Padrao){
        this.Padrao=Padrao;
    }

    public String getPadrao() {
        return Padrao;
    }

    public void setPadrao(String padrao) {
        Padrao = padrao;
    }

    public String toString() {
        return "TShirt{" +
                "Padrao='" + Padrao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TShirt tShirt = (TShirt) o;
        return Objects.equals(Padrao, tShirt.Padrao);
    }

    public TShirt clone(){
        return new TShirt(this);
    }
}
