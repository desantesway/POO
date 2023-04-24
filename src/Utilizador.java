
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utilizador {
    private int id;
    private String email;
    private String nome;
    private String morada;
    private String nif;
    private List<Artigo> produtosAVenda;
    private List<Artigo> produtosComprados;
    private List<Artigo> vendasEfetuadas;

    public Utilizador(){
        this.id=0;
        this.email="";
        this.nome="";
        this.morada="";
        this.nif="";
        this.produtosAVenda=new ArrayList<Artigo>();
        this.produtosComprados=new ArrayList<Artigo>();
        this.vendasEfetuadas=new ArrayList<Artigo>();
    }

    public Utilizador(int id,String email,String nome,String morada,String nif){
        this.id=id;
        this.email=email;
        this.nome=nome;
        this.morada=morada;
        this.nif=nif;
        this.produtosAVenda=new ArrayList<Artigo>();
        this.produtosComprados=new ArrayList<Artigo>();
        this.vendasEfetuadas=new ArrayList<Artigo>();
    }
     public int getId(){
        return this.id;
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
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Artigo> getprodutosAVenda(){
        ArrayList<Artigo> resultado=new ArrayList<>();
        for(Artigo le:this.produtosAVenda){
            resultado.add(le.clone());
        }
        return  resultado;
    }
    public ArrayList<Artigo> getprodutosComprados(){
        ArrayList<Artigo> resultado=new ArrayList<>();
        for(Artigo le:this.produtosComprados){
            resultado.add(le.clone());
        }
        return  resultado;
    }
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
    public void adicionarProdutoAVenda(Artigo produto) {
        this.produtosAVenda.add(produto);
    }

    public void removerProdutoAVenda(Artigo produto) {
        this.produtosAVenda.remove(produto);
    }

    // Métodos para adicionar e remover produtos à lista de produtos comprados
    public void adicionarProdutoComprado(Artigo produto) {
        this.produtosComprados.add(produto);
    }

    public void removerProdutoComprado(Artigo produto) {
        this.produtosComprados.remove(produto);
    }

    // Método para adicionar uma venda efetuada à lista de vendas efetuadas
    public void adicionarVendaEfetuada(Venda venda) {
        this.vendasEfetuadas.add(venda);
    }


    public Utilizador clone(){
        return new Utilizador();
    }


}

