import java.util.ArrayList;
import java.util.List;

public class sys {
    private List<Utilizador> user;
    private List<Encomendas> encomenda;
    private List<Transportadoras> transportadora;
    private List<Artigo> artigo; // mudar para map, mais facil para adicionar e remover;

    public void addArtigo(Artigo artigo){
        if(this.artigo.contains(artigo)){
            System.out.println("Esse artigo já existe na Vintage!");
        } else{
            this.artigo.add(artigo);
            System.out.println("Adicionado com sucesso!");
        }
    }

    public void addTransportadora(Transportadoras tr){
        if(this.transportadora.contains(tr)){
            System.out.println("Essa transportadora já está registada.");
        } else{
            this.transportadora.add(tr);
            System.out.println("Adicionado com sucesso!");
        }
    }

    public void removeArtigo(Artigo artigo){
        this.artigo.remove(artigo);
    }

    public Boolean UserExists(String email){
        for(Utilizador le:this.user){
            if(le.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }


    public void RegistarUser(Utilizador user){
        boolean add = true;
        for(Utilizador le:this.user){
            if(le.getEmail().equals(user.getEmail())){
                System.out.println("E-mail already registered!");
                add = false;
            }
        }
        if(add) {
            this.user.add(user);
        }
    }

    public void DelUser(Utilizador user){
        this.user.remove(user);
    }

    public void DelUser(String email){
        for(Utilizador le:this.user){
            if(le.getEmail().equals(email)){
                DelUser(le);
                System.out.println("Eliminado com exito!");
                return;
            }
        }
        System.out.println("Não existe user com e-mail:" + email);
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public sys(){
        this.user = new ArrayList<>();
        this.encomenda = new ArrayList<>();
        this.artigo = new ArrayList<>();
        this.transportadora = new ArrayList<>();
    }

    public List<Transportadoras> getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(List<Transportadoras> transportadora) {
        this.transportadora = transportadora;
    }

    public List<Artigo> getArtigo() {
        return artigo;
    }

    public void setArtigo(List<Artigo> artigo) {
        this.artigo = artigo;
    }

    public List<Utilizador> getUser() {
        return user;
    }

    public Utilizador getUser(String email) {
        for(Utilizador le:this.user){
            if(le.getEmail().equals(email)){
                return le;
            }
        }
        System.out.println("Este e-mail não está registado:" + email);
        return new Utilizador();
    }

    public void setUser(List<Utilizador> user) {
        this.user = user;
    }

    public List<Encomendas> getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(List<Encomendas> encomenda) {
        this.encomenda = encomenda;
    }
}
