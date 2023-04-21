public class Transportadoras {
    private int nenc;
    private double imposto;
    private double valorBase;
    private double profit;
    private double precoExport;

    // construtor, getters e setters

    public Transportadoras(int nenc, double imposto) {
        this.nenc = nenc;
        this.imposto = imposto;
    }

    public Transportadoras(int nenc, double imposto, double precoExport) {
        this.nenc = nenc;
        this.imposto = imposto;
        this.precoExport = precoExport;
    }

    public double getPrecoExport() {
        return precoExport;
    }

    public void setPrecoExport(double precoExport) {
        this.precoExport = precoExport;
    }

    public int getNenc() {
        return nenc;
    }

    public void setNenc(int nenc) {
        this.nenc = nenc;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
