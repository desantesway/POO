import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Encomendas {
    private List<Artigo> artigos;
    private Transportadoras transportadora;
    private String dimensao;
    private double precofinal;
    private int estado;
    private LocalDate date;

    // funçao para calcular o preço da encomenda
    public String Devolucao(){
        if (estado == 2 && (this.date.plusDays(14).isAfter(date.now()))){
            System.out.println("Devoluçao");

        }
        return "sem devoluçao";
    }
    public void Enviar(){
        Calculapreco();
        this.date = date.now();
        setEstado(1);
    }
    public void Calculapreco(){
        double preco = 0;
        for (int i = 0;i< artigos.size() ;i++){
            Artigo art = artigos.get(i);
            preco += art.getPrecoBase() - art.getDesconto();
            if (art.isNovo()){
                preco += 0.5;
            } else{
                preco += 0.25;
            }
        }
        preco += transportadora.getPrecoExport();
        this.setPrecofinal(preco);
    }
    // funçao para remover um artigo a encomenda
    public void rmArtigo(Artigo artigo){
        this.artigos.remove(artigo);
        this.setDimensao();
    }
    // funçao para adicionar um artigo a encomenda
    public void addArtigo(Artigo artigo){
        this.artigos.add(artigo);
        this.setDimensao();
    }

    // construtor, getters e setters
    public Encomendas() {
        artigos = new ArrayList<>();
        transportadora = new Transportadoras(0,0);
        dimensao = "pequeno";
        precofinal = 0;
        estado = 0;
    }

    public Encomendas(List<Artigo> artigos, Transportadoras transportadora, String dimensao, int preçofinal, int estado, LocalDate date) {
        this.artigos = artigos;
        this.transportadora = transportadora;
        this.dimensao = dimensao;
        this.precofinal = preçofinal;
        this.estado = estado;
        this.date = date;
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
        this.artigos = artigos;
    }

    public Transportadoras getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadoras transportadora) {
        this.transportadora = transportadora;
    }

    public String getDimensao() {
        return dimensao;
    }

    public void setDimensao(String dimensao) {
        this.dimensao = dimensao;
    }
    public void setDimensao() {
        if(this.artigos.size() <= 1){
            this.dimensao = "pequeno";
        }else if(this.artigos.size() >= 2 && this.artigos.size() <= 5) this.dimensao = "médio";
        else{
            this.dimensao = "grande";
        }
    }

    public double getPrecofinal() {
        return precofinal;
    }

    public void setPrecofinal(double preçofinal) {
        this.precofinal = preçofinal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
