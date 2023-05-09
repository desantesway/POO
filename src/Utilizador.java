import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Utilizador {
    private int ID;
    private String email, nome, morada, nif;
    private List<Artigo> produtosAVenda, produtosComprados, vendasEfetuadas;
    private static Random random = new Random();

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public Utilizador(){
        this.ID=0;
        this.email="";
        this.nome="";
        this.morada="";
        this.nif="";
        this.produtosAVenda=new ArrayList<Artigo>();
        this.produtosComprados=new ArrayList<Artigo>();
        this.vendasEfetuadas=new ArrayList<Artigo>();
    }
    public Utilizador(Utilizador l){
        this.ID=l.getID();
        this.email=l.getEmail();
        this.nome=l.getNome();
        this.morada=l.getMorada();
        this.nif=l.getNif();
        this.produtosAVenda=new ArrayList<Artigo>();
        this.produtosComprados=new ArrayList<Artigo>();
        this.vendasEfetuadas=new ArrayList<Artigo>();
    }

    public Utilizador(String email,String nome,String morada,String nif){
        this.ID=gerarIDAleatorio();
        this.email=email;
        this.nome=nome;
        this.morada=morada;
        this.setNif(nif);
        this.produtosAVenda=new ArrayList<Artigo>();
        this.produtosComprados=new ArrayList<Artigo>();
        this.vendasEfetuadas=new ArrayList<Artigo>();
    }


    public int getID(){
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

    public void setId(int id) {
    this.ID = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
    this.nome = nome;
    }

    // Obtem lista dos produtos à venda
    public ArrayList<Artigo> getprodutosAVenda(){
        ArrayList<Artigo> resultado=new ArrayList<>();
        for(Artigo le:this.produtosAVenda){
            resultado.add(le.clone());
        }
        return  resultado;
    }

    // Obtem lista dos produtos comprados
    public ArrayList<Artigo> getprodutosComprados(){
        ArrayList<Artigo> resultado=new ArrayList<>();
        for(Artigo le:this.produtosComprados){
            resultado.add(le.clone());
        }
        return  resultado;
    }

    // Obtem lista dos produtos vendidos
    public ArrayList<Artigo> getvendasEfetuadas(){
        ArrayList<Artigo> resultado=new ArrayList<>();
        for(Artigo le:this.vendasEfetuadas){
            resultado.add(le.clone());
        }
    return  resultado;
    }

    public void setprodutosAVenda(ArrayList<Artigo> produtosAVenda) {
        this.produtosAVenda = new ArrayList<>();
        for (Artigo le : produtosAVenda) {
        this.produtosAVenda.add(le.clone());
        }
    }

    public void setProdutosComprados(ArrayList<Artigo> produtosComprados) {
        this.produtosComprados = new ArrayList<>();
        for (Artigo le : produtosComprados) {
        this.produtosComprados.add(le.clone());
        }
    }
    public void setVendasEfetuadas(ArrayList<Artigo> vendasEfetuadas) {
        this.vendasEfetuadas = new ArrayList<>();
        for (Artigo le : vendasEfetuadas) {
        this.vendasEfetuadas.add(le.clone());
        }
    }

    // adiciona um artigo a venda
    public void adicionarProdutoAVenda(Artigo produto) {
        this.produtosAVenda.add(produto);
    }

    // remove um artigo a venda
    public void removerProdutoAVenda(Artigo produto) {
        this.produtosAVenda.remove(produto);
    }

    // adiciona um artigo comprado
    public void adicionarProdutoComprado(Artigo produto) {
    this.produtosComprados.add(produto);
    }

    // remove um artigo comprado
    public void removerProdutoComprado(Artigo produto) {
    this.produtosComprados.remove(produto);
    }

    // adiciona um artigo vendido
    public void adicionarVendaEfetuada(Artigo venda) {
    this.vendasEfetuadas.add(venda);
    }

    // remove um artigo vendido
    public void removerVendaEfetuada(Artigo produto) {
        this.vendasEfetuadas.remove(produto);
    }

    // obtem o o valor total das vendas
    public double getRevenue(ArrayList<Artigo> vendasEfetuadas) {
        double revenue = 0.0;
        for (Artigo artigo : vendasEfetuadas) {
            revenue += artigo.getPreco();
        }
        return revenue;
    }

    // gera um id aleatorio para o utilizador
    private static int gerarIDAleatorio() {
        int ID = 0;
        do {
            ID = random.nextInt(1000000);
        } while (ID < 100000);
        return ID;
    }

    public boolean equals(Object o){
        if (this==o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Utilizador l = (Utilizador) o;
        return l.getID() == this.ID &&
            l.getEmail() == this.email &&
            l.getNome() == this.nome &&
            l.getNif() == this.nif &&
            l.getMorada() == this.morada;
    }

    @Override
    public String toString() {
        return "Utilizador{" +
            "ID=" + ID +
            ", email='" + email + '\'' +
            ", nome='" + nome + '\'' +
            ", morada='" + morada + '\'' +
            ", nif='" + nif + '\'' +
            ", produtosAVenda=" + produtosAVenda +
            ", produtosComprados=" + produtosComprados +
            ", vendasEfetuadas=" + vendasEfetuadas +
            '}';
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }
}