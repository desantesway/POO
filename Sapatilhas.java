public class Sapatilhas extends Artigo {
    private int tamanho;
    private boolean atacadores;
    private String cor;
    private int anoColecao;
    private int numeroDonos;
    private boolean novo;
    private double estadoUtilizacao;
    private boolean premium;

    // construtor, getters e setters

    public Sapatilhas(String id, String descricao, String marca, double precoBase, double desconto,
                   int tamanho, boolean atacadores, String cor, int anoColecao, int numeroDonos,
                   boolean novo, double estadoUtilizacao, boolean Premium) {
        super(id, descricao, marca, precoBase, desconto);
        this.tamanho = tamanho;
        this.atacadores = atacadores;
        this.cor = cor;
        this.anoColecao = anoColecao;
        this.numeroDonos = numeroDonos;
        this.novo = novo;
        this.estadoUtilizacao = estadoUtilizacao;
        this.premium = premium;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean isAtacadores() {
        return atacadores;
    }

    public String getCor() {
        return cor;
    }

    public int getAnoColecao() {
        return anoColecao;
    }

    public int getNumeroDonos() {
        return numeroDonos;
    }

    public boolean isNovo() {
        return novo;
    }

    public double getEstadoUtilizacao(){
        return estadoUtilizacao;
    }

    public boolean isPremium() {
        return premium;
    }

    @Override
    public double calculaPreco() {
        if (premium) {
            return getPrecoBase() * 1+ getDesconto();
        } else {
            if (this.novo && this.tamanho > 45) {
                return (getPrecoBase() / getNumeroDonos())* getEstadoUtilizacao() ;
            } else {
                return getPrecoBase();
            }
        }

        }
    }