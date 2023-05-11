import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
public class Artigo implements Serializable {
    private boolean publicado, premium;
    private String estado, descricao, Brand, ID;
    private int NumeroDonos, devolucao, sold;
    private double preco, precobase;
    private Colecao colecao;
    private Transportadoras transportadoras;

    //fez uma venda
    public void sold(){
        this.setSold(this.getSold()+1);
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public Artigo(boolean publicado, boolean premium, String estado, int numeroDonos,
                  String descricao, String brand, double precobase, Colecao colecao, Transportadoras transportadoras) {
        this.publicado = publicado;
        this.premium = premium;
        this.estado = estado;
        this.NumeroDonos = numeroDonos;
        this.descricao = descricao;
        this.Brand = brand;
        this.ID =generateID();
        this.preco = -1.0;
        this.precobase = precobase;
        this.colecao = colecao;
        this.transportadoras = transportadoras;
        this.devolucao = 14;
        this.sold = 0;
    }

    public Artigo(boolean publicado, boolean premium, String estado, int numeroDonos,
                  String descricao, String brand, double precobase, double preco,
                  Colecao colecao, Transportadoras transportadoras) {
        this.publicado = publicado;
        this.premium = premium;
        this.estado = estado;
        this.NumeroDonos = numeroDonos;
        this.descricao = descricao;
        this.Brand = brand;
        this.ID =generateID();
        this.preco = preco;
        this.precobase = precobase;
        this.colecao = colecao;
        this.transportadoras = transportadoras;
        this.devolucao = 14;
        this.sold = 0;
    }

    public Artigo() {
        this.publicado=false;
        this.premium=false;
        this.estado="";
        this.NumeroDonos=0;
        this.descricao="";
        this.Brand="";
        this.ID = generateID();
        this.preco=0;
        this.precobase=0;
        this.colecao=new Colecao();
        this.devolucao = 14;
        this.sold = 0;
    }

    public Artigo(Transportadoras transportadoras) {
        this.publicado=false;
        this.premium=false;
        this.estado="";
        this.NumeroDonos=0;
        this.descricao="";
        this.Brand="";
        this.ID = generateID();
        this.preco=0;
        this.precobase=0;
        this.colecao=new Colecao();
        this.transportadoras = transportadoras;
        this.devolucao = 14;
        this.sold = 0;
    }

    public Artigo(Artigo l){
        this.publicado=l.isPublicado();
        this.premium=l.isPremium();
        this.estado=l.getEstado();
        this.NumeroDonos=l.getNumeroDonos();
        this.descricao=l.getDescricao();
        this.Brand=l.getBrand();
        this.ID=l.getID();
        this.preco=l.getPreco();
        this.precobase=l.getPrecobase();
        this.colecao=l.getColecao();
        this.transportadoras=l.getTransportadoras();
        this.devolucao = l.getDevolucao();
        this.sold = l.getSold();
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(int devolucao) {
        this.devolucao = devolucao;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumeroDonos() {
        return this.NumeroDonos;
    }

    public void setNumeroDonos(int numeroDonos) {
        NumeroDonos = numeroDonos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getPrecobase() {
        return precobase;
    }

    public void setPrecobase(double precobase) {
        this.precobase = precobase;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }

    public Transportadoras getTransportadoras() {
        return transportadoras;
    }

    public void setTransportadoras(Transportadoras transportadoras) {
        this.transportadoras = transportadoras;
    }

    public String generateID(){
        ID=UUID.randomUUID().toString().toUpperCase().substring(0,6);
        return ID;
    }

    public void publicar(){
        this.publicado=true;
    }

    public void privar(){
        this.publicado=false;
    }

    public void ativaPremium(){
        this.premium=true;
    }

    public void desativaPremium(){
        this.premium=false;
    }

    public String toString() {
        return "Artigo{" +
                "publicado=" + publicado +
                ", premium=" + premium +
                ", estado='" + estado + '\'' +
                ", NumeroDonos=" + NumeroDonos +
                ", descricao='" + descricao + '\'' +
                ", Brand='" + Brand + '\'' +
                ", ID='" + ID + '\'' +
                ", preco=" + preco +
                ", precobase=" + precobase +
                ", colecao=" + colecao +
                ", transportadoras=" + transportadoras +
                ", devolução=" + devolucao +
                '}';
    }

    public String toString(Object o) {
        return "publicado=" + publicado +
                ", premium=" + premium +
                ", estado='" + estado + '\'' +
                ", NumeroDonos=" + NumeroDonos +
                ", descricao='" + descricao + '\'' +
                ", Brand='" + Brand + '\'' +
                ", ID='" + ID + '\'' +
                ", preco=" + preco +
                ", precobase=" + precobase +
                ", colecao=" + colecao +
                ", transportadoras=" + transportadoras +
                ", devolução=" + devolucao;
    }

    public Artigo clone(){
        return new Artigo(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artigo artigo = (Artigo) o;
        return publicado == artigo.publicado
                && premium == artigo.premium
                && NumeroDonos == artigo.NumeroDonos
                && devolucao == artigo.devolucao
                && Double.compare(artigo.preco, preco) == 0
                && Double.compare(artigo.precobase, precobase) == 0
                && Objects.equals(estado, artigo.estado)
                && Objects.equals(descricao, artigo.descricao)
                && Objects.equals(Brand, artigo.Brand)
                && Objects.equals(ID, artigo.ID)
                && Objects.equals(colecao, artigo.colecao)
                && Objects.equals(transportadoras, artigo.transportadoras);
    }

}
