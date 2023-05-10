import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class sys implements Serializable{
    private Map<String, Utilizador> user;
    private Map<String, Transportadoras> transportadora;

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

    /*public void gravaCsv(String nomef) throws IOException {
        FileWriter pw = new FileWriter(nomef);
        pw.write(this.toString());
        pw.close();
    }*/

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
        if(this.transportadora.containsValue(tr)){
            System.out.println("Essa transportadora já está registada.");
        } else{
            this.transportadora.put(Integer.toString(this.getTransportadora().size()),tr);
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

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public sys(){
        this.user = new HashMap<>();
        this.transportadora = new HashMap<>();
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
                "user=" + user +
                ", transportadora=" + transportadora +
                '}';
    }


}
