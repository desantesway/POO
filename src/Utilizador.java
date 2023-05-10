import java.io.Serializable;
import java.util.*;

public class Utilizador implements Serializable {
    private String email, nome, morada, nif, ID;
    private Map<String, Artigo> produtosAVenda, vendasEfetuadas, artigos;
    private Map<String, Encomendas> encomendas;

    // adiciona um artigo a venda
    public void adicionarProdutoAVenda(Artigo venda) {
        this.produtosAVenda.put(venda.getID(), venda);
    }

    // remove um artigo a venda
    public void removerProdutoAVenda(String key) {
        this.produtosAVenda.remove(key);
    }

    public Map<String, Artigo> getComprados(){
        Map<String, Artigo> ret = new HashMap<>();
        for (Map.Entry<String, Encomendas> entry : this.getEncomendas().entrySet()) {
            ret.putAll(entry.getValue().getArtigos());
        }
        return ret;
    }

    // adiciona uma encomenda
    public void adicionarEncomenda(Encomendas encomenda) {
        this.encomendas.put(Integer.toString(this.encomendas.size()) ,encomenda);
    }

    // elimina uma encomenda (apenas se nao foi efetuado a compra)
    public void eliminarEncomenda(String key) {
        if(this.encomendas.get(key).getEstado() == 0){
            this.encomendas.remove(key);
        } else{
            System.out.println("Eliminação não possivel, compra já foi realizada nesta encomenda.");
        }

    }

    // adiciona um artigo vendido
    public void adicionarVendaEfetuada(Artigo venda) {
        this.vendasEfetuadas.put(venda.getID(), venda);
    }

    // remove um artigo vendido
    public void removerVendaEfetuada(String key) {
        this.vendasEfetuadas.remove(key);
    }

    // obtem o o valor total das vendas
    public double getRevenue() {
        double revenue = 0.0;
        for (Map.Entry<String, Artigo> entry : this.getVendasEfetuadas().entrySet()) {
            revenue += entry.getValue().getPreco();
        }
        return revenue;
    }

    // gera um id aleatorio para o utilizador
    private static int gerarIDAleatorio() {
        Random random = new Random();
        int ID = 0;
        do {
            ID = random.nextInt(1000000);
        } while (ID < 100000);
        return ID;
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public Utilizador(){
        this.ID="";
        this.email="";
        this.nome="";
        this.morada="";
        this.nif="";
        this.encomendas = new HashMap<>();
        this.produtosAVenda = new HashMap<>();
        this.vendasEfetuadas = new HashMap<>();
        this.artigos = new HashMap<>();
    }
    public Utilizador(Utilizador l){
        this.ID=l.getID();
        this.email=l.getEmail();
        this.nome=l.getNome();
        this.morada=l.getMorada();
        this.nif=l.getNif();
        this.encomendas = l.getEncomendas();
        this.produtosAVenda = l.getProdutosAVenda();
        this.vendasEfetuadas = l.getVendasEfetuadas();
        this.artigos = l.getArtigos();
    }

    public Utilizador(String email,String nome,String morada,String nif){
        this.ID=generateID();
        this.email=email;
        this.nome=nome;
        this.morada=morada;
        this.setNif(nif);
        this.encomendas = new HashMap<>();
        this.produtosAVenda = new HashMap<>();
        this.vendasEfetuadas = new HashMap<>();
        this.artigos = new HashMap<>();
    }

    public String generateID(){
        ID=UUID.randomUUID().toString().toUpperCase().substring(0,6);
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Map<String, Artigo> getProdutosAVenda() {
        return produtosAVenda;
    }

    public void setProdutosAVenda(Map<String, Artigo> produtosAVenda) {
        this.produtosAVenda = produtosAVenda;
    }

    public Map<String, Artigo> getVendasEfetuadas() {
        return vendasEfetuadas;
    }

    public void setVendasEfetuadas(Map<String, Artigo> vendasEfetuadas) {
        this.vendasEfetuadas = vendasEfetuadas;
    }

    public Map<String, Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(Map<String, Artigo> artigos) {
        this.artigos = artigos;
    }

    public Map<String, Encomendas> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(Map<String, Encomendas> encomendas) {
        this.encomendas = encomendas;
    }

    public String getID(){
        return this.ID;
    }
    public String getEmail(){
        return this.email;
    }
    public String getNome(){
        return this.nome;
    }

    public String getMorada(){
        return this.morada;
    }

    public String getNif(){
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;

    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
    this.nome = nome;
    }

    public boolean equals(Object o){
        if (this==o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Utilizador l = (Utilizador) o;
        return l.getID().equals(this.ID) &&
            l.getEmail().equals(this.email) &&
            l.getNome().equals(this.nome) &&
            l.getNif().equals(this.nif) &&
            l.getMorada().equals(this.morada);
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "ID=" + ID + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", morada='" + morada + '\'' +
                ", nif='" + nif + '\'' +
                ", produtosAVenda=" + produtosAVenda +
                ", vendasEfetuadas=" + vendasEfetuadas +
                ", artigos=" + artigos +
                ", encomendas=" + encomendas +
                '}';
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }
}