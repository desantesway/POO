import java.time.LocalDate;
import java.util.Objects;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Transportadoras {
    private double imposto;
    private Tamanhos valorBase, precoExp, precoPremium;
    private Boolean premium, enviado;
    private LocalDate dataEnviado;

    // calcula o preco do premium com formula (ainda sem a formula)
    public void premium(String formula){
        double[] list = formula(formula);
        this.setPrecoPremium(new Tamanhos(list[0],list[1],list[2]));
    }

    // calcula o preco com formula (ainda sem a formula)
    public void preco(String formula) {
        double[] list = formula(formula);
        this.setPrecoExp(new Tamanhos(list[0],list[1],list[2]));
    }

    private double[] formula(String formula) {
        String[] tokens = formula.split(" "); // dar split de ( ) e fazer isto com cada elemento
        double[] list = new double[3];
        for (int i = 0; i < 3; i++) {
            double result = parseToken(tokens[0], i);
            for (int j = 1; j < tokens.length; j += 2) {
                String operator = tokens[j];
                double operand = parseToken(tokens[j+1], i);
                switch (operator) {
                    case "" -> result = operand;
                    case "/" -> result /= operand;
                    case "+" -> result += operand;
                    case "-" -> result -= operand;
                    case "*" -> result *= operand;
                    default -> throw new IllegalArgumentException("Invalid operator: " + operator);
                }
            }
            list[i] = result;
        }
        return list;
    }

    private double parseToken(String token, int index) {
        switch (token) {
            case "valorBase", "valor", "valorbase", "getValorBase()":
                return this.getValorBase().get(index);
            case "imposto":
                return getImposto();
            default:
                try {
                    return Double.parseDouble(token);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid token: " + token);
                }
        }
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
        this.precoPremium = other.getPrecoPremium();
        this.precoExp = other.getPrecoExp();
        this.valorBase = other.getValorBase();
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
        this.valorBase = valorBase;
    }

    public void setValorBase(double left, double middle, double right) {
        this.valorBase = new Tamanhos(left,middle,right);
    }

    public Tamanhos getPrecoExp() {
        return precoExp;
    }

    public void setPrecoExp(Tamanhos precoExp) {
        this.precoExp = precoExp;
    }

    public Tamanhos getPrecoPremium() {
        return precoPremium;
    }

    public void setPrecoPremium(Tamanhos precoPremium) {
        this.precoPremium = precoPremium;
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
                        && getValorBase().equals(that.getValorBase())
                        && getPrecoExp().equals(that.getPrecoExp())
                        && getPrecoPremium().equals(that.getPrecoPremium())
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
