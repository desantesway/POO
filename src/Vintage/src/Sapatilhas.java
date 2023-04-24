import java.util.Objects;

public class Sapatilhas extends Artigo {
    private int tamanho;
    private boolean atacadores;
    private String cor;
    private int anoColecao;
    private double estadoUtilizacao;
    private boolean premium;

    // construtor, getters e setters

    public Sapatilhas(String id, String descricao, String marca, double precoBase, double desconto,
                      int tamanho, boolean atacadores, String cor, int anoColecao, int numeroDonos,
                      boolean novo, double estadoUtilizacao, boolean Premium) {
        super(id, descricao, marca, precoBase, desconto, numeroDonos);
        this.tamanho = tamanho;
        this.atacadores = atacadores;
        this.cor = cor;
        this.anoColecao = anoColecao;
        this.estadoUtilizacao = estadoUtilizacao;
        this.premium = premium;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isAtacadores() {
        return atacadores;
    }

    public void setAtacadores(boolean atacadores) {
        this.atacadores = atacadores;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAnoColecao() {
        return anoColecao;
    }

    public void setAnoColecao(int anoColecao) {
        this.anoColecao = anoColecao;
    }

    public double getEstadoUtilizacao() {
        return estadoUtilizacao;
    }

    public void setEstadoUtilizacao(double estadoUtilizacao) {
        this.estadoUtilizacao = estadoUtilizacao;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sapatilhas)) return false;
        if (!super.equals(o)) return false;
        Sapatilhas that = (Sapatilhas) o;
        return getTamanho() == that.getTamanho() && isAtacadores() == that.isAtacadores() && getAnoColecao() == that.getAnoColecao() && Double.compare(that.getEstadoUtilizacao(), getEstadoUtilizacao()) == 0 && isPremium() == that.isPremium() && Objects.equals(getCor(), that.getCor());
    }


    @Override
    public double calculaPreco() {
        double preco = getPrecoBase();
        if (getNumeroDonos() > 0) {
            double desconto = preco / getNumeroDonos();
            if (!premium && (getNumeroDonos() > 1 || anoColecao < 2020 || tamanho > 45)) {
                preco -= desconto;
            } else if (premium) {
                preco += (preco * 0.1); // aumento de 10% no preço
            }
        }
        return preco;
    }
}
