import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class sys implements Serializable{
    private double vintagecut, rev;
    private int now;
    private Map<String, Utilizador> user;
    private Map<String, Transportadoras> transportadora;
    private Map<String, Colecao> colecao;

    // mostra todos os artigos do sistema
    public Map<String, Artigo> getArtigos(){
        Map<String, Artigo> ret = new HashMap<>();
        for(Map.Entry<String, Utilizador> entry : this.getUser().entrySet()){
            ret.putAll(entry.getValue().getArtigos());
        }
        return ret;
    }

    // mostra todos os artigos do sistema publicados
    public Map<String, Artigo> getCardapio(){
        Map<String, Artigo> ret = new HashMap<>();
        for(Map.Entry<String, Utilizador> entry : this.getUser().entrySet()){
            for(Map.Entry<String, Artigo> entry2 : entry.getValue().getArtigos().entrySet()){
                if(entry2.getValue().isPublicado()){
                    ret.put(entry2.getKey(), entry2.getValue());
                }
            }
        }
        return ret;
    }

    // guarda o sistema num ficheiro
    public void save(String nomef) throws IOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomef));
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving object: " + e.getMessage());
            System.out.println("File name: " + nomef);
            throw e;
        }
    }

    // carrega o ficheiro salvo e retorna o sistema
    public sys load(String nomef) throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomef));
            return (sys) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading object: " + e.getMessage());
            System.out.println("File name: " + nomef);
            throw e;
        }
    }

    public void gravaCsv(String nomef) throws IOException {
        FileWriter pw = new FileWriter(nomef);
        pw.write(this.toString());
        pw.close();
    }

    //adiciona coleção ao sistema
    public void addColecao(Colecao col){
        this.getColecao().put(col.getnome(), col);
    }

    // ve se tem conta com esse email
    public boolean existsEmail(String email){
        for (Map.Entry<String, Utilizador> entry : this.getUser().entrySet()) {
            Utilizador art = entry.getValue();
            if(art.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    //adiciona transportadora ao sistema

    public void addTransportadora(Transportadoras tr){
        if(this.getTransportadora().containsValue(tr)){
            System.out.println("Essa transportadora já está registada.");
        } else{
            this.getTransportadora().put(Integer.toString(this.getTransportadora().size()),tr);
            System.out.println("Adicionado com sucesso!");
        }
    }

    //adiciona user ao sistema
    public void RegistarUser(Utilizador user){
        if(this.user.containsValue(user)){
            System.out.println("E-mail already registered!");
        } else{
            this.user.put(user.getID(), user);
        }
    }

    //remove user do sistema
    public void DelUser(Utilizador user){
        this.user.remove(user.getID());
    }

    //remove user do sistema com email/id
    public void DelUser(String id){
        if(id.contains("@")){
            for (Map.Entry<String, Utilizador> entry : this.getUser().entrySet()) {
                Utilizador art = entry.getValue();
                if(art.getEmail().equals(id)){
                    this.user.remove(entry.getKey());
                }
            }
        }else {
            this.user.remove(id);
        }
    }

    public double getRev() {
        return rev;
    }

    public void setRev(double rev) {
        this.rev = rev;
    }

    public void past(int days){
        this.setNow(this.getNow() - days);
    }

    public void future(int days){
        this.setNow(this.getNow() + days);
    }

    // obtem o dia de hoje + os dias adiantados
    public LocalDate now(){
        return LocalDate.now().plusDays(this.getNow());
    }

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public sys(){
        this.user = new HashMap<>();
        this.transportadora = new HashMap<>();
        this.colecao = new HashMap<>();
        this.vintagecut = 0.025;
        this.now = 0;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }

    public Map<String, Colecao> getColecao() {
        return colecao;
    }

    public void setColecao(Map<String, Colecao> colecao) {
        this.colecao = colecao;
    }

    public double getVintagecut() {
        return vintagecut;
    }

    public void setVintagecut(double vintagecut) {
        this.vintagecut = vintagecut;
    }

    public Map<String, Utilizador> getUser() {
        return user;
    }

    public void setUser(Map<String, Utilizador> user) {
        this.user = user;
    }

    public Map<String, Transportadoras> getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Map<String, Transportadoras> transportadora) {
        this.transportadora = transportadora;
    }

    public Utilizador getUser(String email) {
        if(email.contains("@")){
            for (Map.Entry<String, Utilizador> entry : this.getUser().entrySet()) {
                Utilizador art = entry.getValue();
                if(art.getEmail().equals(email)){
                    return art;
                }
            }
        }
        return this.user.get(email);
    }

    @Override
    public String toString() {
        return "sys{" +
                "vintagecut=" + vintagecut +
                ", user=" + user +
                ", transportadora=" + transportadora +
                ", colecao=" + colecao +
                '}' + "\n";
    }
}
