import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Transportadoras implements Serializable {
    private double imposto, rev;
    private Tamanhos valorBase, precoExp, precoPremium;
    private Boolean premium, enviado;
    private LocalDate dataEnviado;
    private int diasAtraso;
    private String formula, fpremium;

    //calcula o preço de expedição premium para todos os tamanhos a partir de uma formula em string
    public void formulaPremium(String formula){
        this.setPrecoPremium(evaluate(formula));
    }

    //calcula o preço de expedição para todos os tamanhos a partir de uma formula em string
    public void formula(String formula){
        this.setPrecoExp(evaluate(formula));
    }

    // analisa os parenteses e faz conta por ordem
    public Tamanhos evaluate(String formula) {
        formula = formula.replaceAll("\\s+", "");

        while (formula.contains("(")) {
            int startIndex = formula.lastIndexOf("(");
            int endIndex = formula.indexOf(")", startIndex);
            String innerFormula = formula.substring(startIndex + 1, endIndex);
            Tamanhos result = evaluateInnermost(innerFormula);
            formula = formula.substring(0, startIndex) + result + formula.substring(endIndex + 1);
        }

        // Evaluate the remaining formula using myfunc
        return evaluateInnermost(formula);
    }

    // analisa a string para como ha de fazer conta (se o operador é +, -, ..., o tipo dos operandos etc.)
    public Tamanhos evaluateInnermost(String formula) {
        if(formula.contains("Tamanhos{pequeno=") && formula.contains("medio=") && formula.contains("grande=") && formula.contains("}")
        && !(formula.contains("*") || formula.contains("+") || formula.contains("-") || formula.contains("/") )){
            double p=-1.0, m=-1.0, g=-1.0;
            int tmp=0;
            for (int i = 0; i < formula.length(); i++) {
                char c = formula.charAt(i);
                if (c == ',' || c == '}'){
                    if(p == -1.0){
                        p = Double.parseDouble(formula.substring(17,i));
                        tmp = i;
                    } else if(m == -1.0){
                        m = Double.parseDouble(formula.substring(tmp+8,i));
                        tmp = i;
                    } else {
                        g = Double.parseDouble(formula.substring(tmp+9,i));
                        break;
                    }
                }
            }
            return new Tamanhos(p,m,g);
        }if (formula.equals("valor")){
            return this.getValorBase();
        }if (formula.equals("imposto")){
            return new Tamanhos(this.getImposto());
        } try {
            double p = Double.parseDouble(formula);
            return new Tamanhos(p);
        } catch (NumberFormatException e) {
            for (int i = formula.length() - 1; i >= 0; i--) {
                char c = formula.charAt(i);
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    String operator = String.valueOf(c);
                    Tamanhos leftOperand = evaluateInnermost(formula.substring(0, i));
                    Tamanhos rightOperand = evaluateInnermost(formula.substring(i + 1));
                    return ari(leftOperand, operator, rightOperand);
                }

            }
            throw new IllegalArgumentException("Invalid formula: " + formula);
        }
    }

    // faz conta com um operador
    public Tamanhos ari(Tamanhos st, String operador, Tamanhos nd){
        if(st.getGrande() == st.getMedio() && st.getMedio() == st.getPequeno()){
            return ari(st.getGrande(), operador, nd);
        } else{
            return ari(st, operador, nd.getGrande());
        }
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
    public void enviar(LocalDate now){
        if(this.getEnviado()){
            System.out.println("A transportadora já enviou este artigo!");
        } else{
            int quit = 0;
            for (int i = 0; i<3; i++){
                if(this.getPrecoExp().get(i) == -1.0){
                    System.out.println("Preço de expedição ainda não definido!");
                    quit++;
                    break;
                }
                if(this.getPremium()){
                    if(this.getPrecoPremium().get(i) == -1.0){
                        System.out.println("Preço do premium ainda não definido!");
                        quit++;
                        break;
                    }
                }
            }
            if (quit == 0){
                this.setEnviado(true);
                this.setDataEnviado(now);
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

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public Transportadoras(Transportadoras other) {
        this.precoPremium = new Tamanhos(other.getPrecoPremium());
        this.precoExp = new Tamanhos(other.getPrecoExp());
        this.valorBase = new Tamanhos(other.getValorBase());
        this.imposto = other.getImposto();
        this.premium = other.getPremium();
        this.enviado = other.getEnviado();
        this.dataEnviado = other.getDataEnviado();
        this.diasAtraso = other.getDiasAtraso();
        this.rev = other.getRev();
    }

    public Transportadoras(double valorBaseP, double valorBaseM, double valorBaseG, double imposto) {
        this.setValorBase(valorBaseP,valorBaseM,valorBaseG);
        this.imposto = imposto;
        this.diasAtraso = 1;
        this.setPremium(false);
        this.setEnviado(false);
        this.setDataEnviado(null);
        this.precoExp = new Tamanhos();
        this.precoPremium = new Tamanhos();
        this.rev = 0;
    }

    public double getRev() {
        return rev;
    }

    public void setRev(double rev) {
        this.rev = rev;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = diasAtraso;
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

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFpremium() {
        return fpremium;
    }

    public void setFpremium(String fpremium) {
        this.fpremium = fpremium;
    }

    public Tamanhos getPrecoExp() {
        return precoExp;
    }

    public void setPrecoExp(Tamanhos precoExp) {
        this.precoExp = new Tamanhos(precoExp);
    }

    public Tamanhos getPrecoPremium() {
        if (this.getPremium()){
            return precoPremium;
        }else{
            System.out.println("Premium não ativado!");
            return null;
        }
    }

    public void setPrecoPremium(Tamanhos precoPremium) {
        if (this.getPremium()){
            this.precoPremium = new Tamanhos(precoPremium);
        }else{
            System.out.println("Premium não ativado!");
        }
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
                && Integer.compare(that.getDiasAtraso(), getDiasAtraso()) == 0
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
        return "\n" + "Transportadoras{" +
                "imposto=" + imposto +
                ", valorBase=" + valorBase.toString() +
                ", precoExp=" + precoExp.toString() +
                ", precoPremium=" + precoPremium.toString() +
                ", premium=" + premium +
                ", enviado=" + enviado +
                ", dataEnviado=" + dataEnviado +
                ", diasAtraso=" + diasAtraso +
                '}';
    }

    public Transportadoras clone(){
        return new Transportadoras(this);
    }

}
