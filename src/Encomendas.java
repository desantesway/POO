import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Encomendas implements Serializable {
    private Map<String, Artigo> artigos;
    private String dimensao;
    private double precoFinal;
    private int estado, devolucao; // 0, 1, 2
    private LocalDate data;

    //limpa a encomenda
    public void clean(){
        this.artigos = new HashMap<>();
        this.setDimensao("pequeno");
        this.setPrecoFinal(0);
        this.setEstado(0);
        this.data = null;
        this.setDevolucao(14);
    }

    // funçao para ver se pode pedir devolução ou não
    public Boolean devolucao(LocalDate now){
        setEstado(now);
        return this.estado == 2 && (this.data.plusDays(this.getDevolucao()).isAfter(now));
    }

    //envia a encomenda
    public void enviar(LocalDate now){
        precoEncomenda();
        this.data = now;
        setEstado(1);
    }

    //calcula o preço da encomenda
    public void precoEncomenda(){
        this.setDimensao();
        double preco = 0;
        Tamanhos tam;
        for (Map.Entry<String, Artigo> entry : this.getArtigos().entrySet()) {
            Artigo art = entry.getValue();
            tam = art.getTransportadoras().getPrecoExp();
            if (art.isPremium()){
                tam = art.getTransportadoras().getPrecoPremium();
            }
            switch (this.dimensao) {
                case "pequeno" -> preco += tam.getPequeno();
                case "medio" -> preco += tam.getMedio();
                case "grande" -> preco += tam.getGrande();
                default -> System.out.println("Erro na dimensão: não existe");
            }
            if (art.getNumeroDonos() == 0) {
                preco += 0.5;
            } else {
                preco += 0.25;
            }
            preco += art.getPreco();
        }
        this.setPrecoFinal(preco);
    }

    // funçao para remover um artigo a encomenda
    public void rmArtigo(String key){
        this.artigos.remove(key);
        this.setDimensao();
    }

    // funçao para adicionar um artigo a encomenda
    public void addArtigo(Artigo artigo){
        this.artigos.put(artigo.getID(), artigo);
        this.setDimensao();
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public Encomendas(Map<String, Artigo> artigos) {
        this.artigos = artigos;
        this.setDimensao();
        this.setPrecoFinal(0);
        this.setEstado(0);
        this.data = null;
        this.setDevolucao(14);
    }

    public Encomendas(Encomendas o) {
        this.artigos = o.getArtigos();
        this.data = o.getData();
        this.dimensao = o.getDimensao();
        this.precoFinal = o.getPrecoFinal();
        this.estado = o.getEstado();
        this.devolucao = o.getDevolucao();
    }

    public Encomendas() {
        this.artigos = new HashMap<>();
        this.setDimensao("pequeno");
        this.setPrecoFinal(0);
        this.setEstado(0);
        this.data = null;
        this.setDevolucao(14);
    }

    public int getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(int devolucao) {
        this.devolucao = devolucao;
    }

    public Map<String, Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(Map<String, Artigo> artigos) {
        this.artigos = artigos;
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
        }else if(this.artigos.size() <= 5) this.dimensao = "medio";
        else{
            this.dimensao = "grande";
        }
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(double precoFinal) {
        this.precoFinal = precoFinal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(LocalDate now) {
        LocalDate max = now;
        if(this.getEstado() == 1){
            for (Map.Entry<String, Artigo> entry : this.getArtigos().entrySet()) {
                Artigo art = entry.getValue();
                if(!(max.plusDays(-1).isAfter(now))){
                    max = this.getData().plusDays(art.getTransportadoras().getDiasAtraso());
                }
            }
        }
        if(!(max.isBefore(now))){
            this.setEstado(2);
        }
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encomendas that = (Encomendas) o;
        return Double.compare(that.getPrecoFinal(), getPrecoFinal()) == 0
                && that.getDevolucao() == getDevolucao()
                && getEstado() == that.getEstado()
                && Objects.equals(getArtigos(), that.getArtigos())
                && Objects.equals(getDimensao(), that.getDimensao())
                && Objects.equals(getData(), that.getData());
    }

    @Override
    public String toString() {
        return "\n" + "Encomendas{" +
                "artigos=" + artigos +
                ", dimensao='" + dimensao + '\'' +
                ", precoFinal=" + precoFinal +
                ", estado=" + estado +
                ", devolucao=" + devolucao +
                ", data=" + data +
                '}';
    }

    public Encomendas clone(){
        return new Encomendas(this);
    }
}
