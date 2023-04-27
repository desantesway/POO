import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Encomendas {
    private List<Artigo> artigos;
    private String dimensao;
    private double precoFinal;
    private int estado; // 0, 1, 2
    private LocalDate data;

    // funçao para calcular o preço da encomenda
    public Boolean devolucao(){
        //setEstado();
        return estado == 2 && (this.data.plusDays(14).isAfter(LocalDate.now()));
    }

    //envia a encomenda
    public void enviar(){
        precoEncomenda();
        this.data = LocalDate.now();
        setEstado(1);
    }

    //calcula o preço da encomenda
    public void precoEncomenda(){
        double preco = 0;
        for (Artigo art : artigos) {
            preco += art.getPrecoBase() - art.getDesconto(); // mudar isto para quando o artigo tiver feito
            if (/*art.isNovo()*/0 == 1) { //isto tmb
                preco += 0.5;
            } else {
                preco += 0.25;
            }
        }
        this.setPrecoFinal(preco);
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
    public Encomendas(List<Artigo> artigos) {
        this.artigos = artigos;
        this.setDimensao();
        this.setPrecoFinal(0);
        //this.setEstado();
        this.data = null;
    }

    public Encomendas(Encomendas o) {
        this.artigos = o.getArtigos();
        this.data = o.getData();
        this.dimensao = o.getDimensao();
        this.precoFinal = o.getPrecoFinal();
        this.estado = o.getEstado();
    }

    public Encomendas() {
        this.artigos = new ArrayList<Artigo>();
        this.setDimensao("pequeno");
        this.setPrecoFinal(0);
        this.setEstado(0);
        this.data = null;
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
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

    /*public void setEstado() {
        if(artigos.size() > 0){
            if(artigos.get(0).getTransportadora().getEnviado()){
                this.setEstado(2);
            }
        } else{
            this.setEstado(0);
        }
    }*/

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
                && getEstado() == that.getEstado()
                && Objects.equals(getArtigos(), that.getArtigos())
                && Objects.equals(getDimensao(), that.getDimensao())
                && Objects.equals(getData(), that.getData());
    }

    @Override
    public String toString() {
        return "Encomendas{" +
                "artigos=" + artigos +
                ", dimensao='" + dimensao + '\'' +
                ", precoFinal=" + precoFinal +
                ", estado=" + estado +
                ", data=" + data +
                '}';
    }

    public Encomendas clone(){
        return new Encomendas(this);
    }
}
