import java.time.LocalDate;
import java.util.Objects;

public class Transportadoras {
    private double valorBaseP, valorBaseM, valorBaseG, imposto, precoExp, precoPremium, profit;
    //private Triple<Double, Double, Double> triple;
    private Boolean premium, enviado;
    private LocalDate dataEnviado;
    private String formulaPreco, formulaPremium;

    // calcula o preco do premium com formula (ainda sem a formula)
    public void premium(String dimensao){
        if (this.getPremium()){
            double valorbase = 0;
            if (Objects.equals(dimensao, "pequeno")){
                valorbase = this.getValorBaseP();
            } else if (Objects.equals(dimensao, "medio")){
                valorbase = this.getValorBaseM();
            } else if (Objects.equals(dimensao, "grande")){
                valorbase = this.getValorBaseG();
            }
            this.setPrecoPremium((valorbase * this.getProfit() * (1 + this.getImposto())) * 0.9);
        } else{
            System.out.println("Esta transportadora não tem o premium ativado!\n");
        }
    }

    // calcula o preco com formula (ainda sem a formula)
    public void preco(String dimensao){
        double valorbase = 0;
        if (Objects.equals(dimensao, "pequeno")){
            valorbase = this.getValorBaseP();
        } else if (Objects.equals(dimensao, "medio")){
            valorbase = this.getValorBaseM();
        } else if (Objects.equals(dimensao, "grande")){
            valorbase = this.getValorBaseG();
        }
        this.setPrecoExp((valorbase * this.getProfit() * (1 + this.getImposto())) * 0.9);
    }

    //a transportadora envia o artigo
    public void enviar(){
        if(this.getEnviado()){
            System.out.println("A transportadora já enviou este artigo!");
        } else{
            this.setEnviado(true);
            this.setDataEnviado(LocalDate.now());
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
        this.valorBaseP = other.getValorBaseP();
        this.valorBaseM = other.getValorBaseM();
        this.valorBaseG = other.getValorBaseG();
        this.imposto = other.getImposto();
        this.precoExp = other.getPrecoExp();
        this.precoPremium = other.getPrecoPremium();
        this.profit = other.getProfit();
        this.premium = other.getPremium();
        this.enviado = other.getEnviado();
        this.dataEnviado = other.getDataEnviado();
        this.formulaPreco = other.getFormulaPreco();
        this.formulaPremium = other.getFormulaPremium();
    }

    public Transportadoras(double valorBaseP, double valorBaseM, double valorBaseG, double imposto) {
        this.valorBaseP = valorBaseP;
        this.valorBaseM = valorBaseM;
        this.valorBaseG = valorBaseG;
        this.imposto = imposto;
        this.setProfit(0);
        this.setPrecoExp(0);
        this.setPrecoPremium(0);
        this.setPremium(false);
        this.setEnviado(false);
        this.setDataEnviado(null);
    }

    public String getFormulaPreco() {
        return formulaPreco;
    }

    public void setFormulaPreco(String formulaPreco) {
        this.formulaPreco = formulaPreco;
    }

    public String getFormulaPremium() {
        return formulaPremium;
    }

    public void setFormulaPremium(String formulaPremium) {
        this.formulaPremium = formulaPremium;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getValorBaseP() {
        return valorBaseP;
    }

    public void setValorBaseP(double valorBaseP) {
        this.valorBaseP = valorBaseP;
    }

    public double getValorBaseM() {
        return valorBaseM;
    }

    public void setValorBaseM(double valorBaseM) {
        this.valorBaseM = valorBaseM;
    }

    public double getValorBaseG() {
        return valorBaseG;
    }

    public void setValorBaseG(double valorBaseG) {
        this.valorBaseG = valorBaseG;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public double getPrecoExp() {
        return precoExp;
    }

    public void setPrecoExp(double precoExp) {
        this.precoExp = precoExp;
    }

    public double getPrecoPremium() {
        return precoPremium;
    }

    public void setPrecoPremium(double precoPremium) {
        this.precoPremium = precoPremium;
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
        return Double.compare(that.getValorBaseP(), getValorBaseP()) == 0
                && Double.compare(that.getValorBaseM(), getValorBaseM()) == 0
                && Double.compare(that.getValorBaseG(), getValorBaseG()) == 0
                && Double.compare(that.getImposto(), getImposto()) == 0
                && Double.compare(that.getPrecoExp(), getPrecoExp()) == 0
                && Double.compare(that.getPrecoPremium(), getPrecoPremium()) == 0
                && Double.compare(that.getProfit(), getProfit()) == 0
                && Objects.equals(getPremium(), that.getPremium())
                && Objects.equals(getEnviado(), that.getEnviado())
                && Objects.equals(getDataEnviado(), that.getDataEnviado())
                && Objects.equals(getFormulaPreco(), that.getFormulaPreco())
                && Objects.equals(getFormulaPremium(), that.getFormulaPremium());

    }

    @Override
    public String toString() {
        return "Transportadoras{" +
                "valorBaseP=" + valorBaseP +
                ", valorBaseM=" + valorBaseM +
                ", valorBaseG=" + valorBaseG +
                ", imposto=" + imposto +
                ", precoExp=" + precoExp +
                ", precoPremium=" + precoPremium +
                ", profit=" + profit +
                ", premium=" + premium +
                ", enviado=" + enviado +
                ", dataEnviado=" + dataEnviado +
                ", formulaPreco='" + formulaPreco + '\'' +
                ", formulaPremium='" + formulaPremium + '\'' +
                '}';
    }

    public Transportadoras clone(){
        return new Transportadoras(this);
    }

}
