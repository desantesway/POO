import java.util.UUID;

public class Artigo {
        private boolean publicado;
        private boolean premium;
        private String estado;
        private int NumeroDonos;
        private String descricao;
        private String Brand;
        private String ID;
        private double preco;
        private double precobase;
        private double desconto;
        private Colecao colecao;
        private String dimensoes;
        private Transportadoras transportadoras;

    public Artigo(boolean publicado, boolean premium, String estado, int numeroDonos, String descricao, String brand, double preco, double precobase, double desconto, Colecao colecao, String dimensoes, Transportadoras transportadoras) {
        this.publicado = publicado;
        this.premium = premium;
        this.estado = estado;
        this.NumeroDonos = numeroDonos;
        this.descricao = descricao;
        this.Brand = brand;
        this.ID =generateID();
        this.preco = preco;
        this.precobase = precobase;
        this.desconto = desconto;
        this.colecao = colecao;
        this.dimensoes = dimensoes;
        this.transportadoras = transportadoras;
    }

    public Artigo() {
        this.publicado=false;
        this.premium=false;
        this.estado="";
        this.NumeroDonos=0;
        this.descricao="";
        this.Brand="";
        this.ID="";
        this.preco=0;
        this.precobase=0;
        this.desconto=0;
        this.colecao=new Colecao();
        this.dimensoes="";
    }
    public Artigo(Artigo l){
        this.publicado=l.isPublicado();
        this.premium=l.isPremium();
        this.estado=l.getEstado();
        this.NumeroDonos=l.getNumeroDonos();
        this.descricao=l.getDescricao();
        this.Brand=l.getBrand();
        this.ID=getID();
        this.preco=getPreco();
        this.precobase=getPrecobase();
        this.desconto=getDesconto();
        this.colecao=getColecao();
        this.dimensoes=getDimensoes();
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
        return NumeroDonos;
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

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }

    public String getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(String dimensoes) {
        this.dimensoes = dimensoes;
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
                ", desconto=" + desconto +
                ", colecao=" + colecao +
                ", dimensoes='" + dimensoes + '\'' +
                ", transportadoras=" + transportadoras +
                '}';
    }

    public Artigo clone(){
        return new Artigo(this);
    }






}
