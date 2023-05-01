import java.time.LocalDate;
import java.util.Objects;

public class Transportadoras {
    private double imposto;
    private Tamanhos valorBase, precoExp, precoPremium;
    private Boolean premium, enviado;
    private LocalDate dataEnviado;

    // faz conta do o 1º elemento com o 2ª elemento e da return do resultado
    public double ari(double st,String operador, double nd){
        double result = 0.0;
        switch (operador){
            case "*" -> result = st * nd;
            case "+" -> result = st + nd;
            case "-" -> result = st - nd;
            case "/" -> result = st / nd;
        }
        return result;
    }

    // faz conta com todos os elementos do 1º com o 2ª elemento e da return do resultado
    public Tamanhos ari(Tamanhos st,String operador, double nd){
        Tamanhos result = new Tamanhos();
        for (int i = 0; i<3; i++){
            switch (operador){
                case "*" -> result.set(i, st.get(i) * nd);
                case "+" -> result.set(i, st.get(i) + nd);
                case "-" -> result.set(i, st.get(i) - nd);
                case "/" -> result.set(i, st.get(i) / nd);
            }
        }
        return result;
    }

    // faz conta do 1º com todos os elementos do 2ª elemento e da return do resultado
    public Tamanhos ari(double st,String operador, Tamanhos nd){
        Tamanhos result = new Tamanhos();
        for (int i = 0; i<3; i++){
            switch (operador){
                case "*" -> result.set(i, st * nd.get(i));
                case "+" -> result.set(i, st + nd.get(i));
                case "-" -> result.set(i, st - nd.get(i));
                case "/" -> result.set(i, st / nd.get(i));
            }
        }
        return result;
    }

    //a transportadora envia o artigo
    public void enviar(){
        if(this.getEnviado()){
            System.out.println("A transportadora já enviou este artigo!");
        } else{
            int quit = 0;
            for (int i = 0; i<3; i++){
                if(this.getPrecoExp().get(i) == -1.0){
                    System.out.println("Preço de expedição ainda não definido!");
                    quit++;
                }
                if(this.getPrecoPremium().get(i) == -1.0 && this.getPremium()){
                    System.out.println("Preço do premium ainda não definido!");
                    quit++;
                }
            }
            if (quit == 0){
                this.setEnviado(true);
                this.setDataEnviado(LocalDate.now());
            } else{
                System.out.println("Erro ao enviar artigo");
            }
        }
    }

    //ativa o premium na transportadora
    public void ativaPremium(){
        this.setPremium(true);
    }

    //desativa o premium na transportadora
    public void desativaPremium(){
        this.setPremium(false);
    }

    // constructor, clone, tostring, getters e setters
    public Transportadoras(Transportadoras other) {
        this.precoPremium = new Tamanhos(other.getPrecoPremium());
        this.precoExp = new Tamanhos(other.getPrecoExp());
        this.valorBase = new Tamanhos(other.getValorBase());
        this.imposto = other.getImposto();
        this.premium = other.getPremium();
        this.enviado = other.getEnviado();
        this.dataEnviado = other.getDataEnviado();
    }

    public Transportadoras(double valorBaseP, double valorBaseM, double valorBaseG, double imposto) {
        this.setValorBase(valorBaseP,valorBaseM,valorBaseG);
        this.imposto = imposto;
        this.setPremium(false);
        this.setEnviado(false);
        this.setDataEnviado(null);
        this.precoExp = new Tamanhos();
        this.precoPremium = new Tamanhos();
    }

    public Tamanhos getValorBase() {
        return valorBase;
    }

    public void setValorBase(Tamanhos valorBase) {
        this.valorBase = new Tamanhos(valorBase);
    }

    public void setValorBase(double left, double middle, double right) {
        this.valorBase = new Tamanhos(left,middle,right);
    }

    public Tamanhos getPrecoExp() {
        return precoExp;
    }

    public void setPrecoExp(Tamanhos precoExp) {
        this.precoExp = new Tamanhos(precoExp);
    }

    public Tamanhos getPrecoPremium() {
        return precoPremium;
    }

    public void setPrecoPremium(Tamanhos precoPremium) {
        this.precoPremium = new Tamanhos(precoPremium);
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public Boolean getPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public LocalDate getDataEnviado() {
        return dataEnviado;
    }

    public void setDataEnviado(LocalDate dataEnviado) {
        this.dataEnviado = dataEnviado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportadoras that = (Transportadoras) o;
        return Double.compare(that.getImposto(), getImposto()) == 0
                && this.getValorBase().equals(that.getValorBase())
                && this.getPrecoExp().equals(that.getPrecoExp())
                && this.getPrecoPremium().equals(that.getPrecoPremium())
                && Objects.equals(getPremium(), that.getPremium())
                && Objects.equals(getEnviado(), that.getEnviado())
                && Objects.equals(getDataEnviado(), that.getDataEnviado());
    }

    //to string on triple
    @Override
    public String toString() {
        return "Transportadoras{" +
                "imposto=" + imposto +
                ", valorBase=" + valorBase.toString() +
                ", precoExp=" + precoExp.toString() +
                ", precoPremium=" + precoPremium.toString() +
                ", premium=" + premium +
                ", enviado=" + enviado +
                ", dataEnviado=" + dataEnviado +
                '}';
    }

    public Transportadoras clone(){
        return new Transportadoras(this);
    }

}
