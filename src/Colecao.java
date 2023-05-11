import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Colecao implements Serializable {
    private String nome;
    private LocalDate data;

    /*
        construtores, getters, setters, clone, tostring e equals
     */
    public Colecao(){
        this.nome="";
        this.data=null;
    }

    public Colecao(String nome,LocalDate data){
        this.nome=nome;
        this.data=data;
    }

    public Colecao(String nome){
        this.nome=nome;
        this.data=LocalDate.now();
    }

    public Colecao(Colecao col){
        this.nome=col.getnome();
        this.data=col.getdata();
    }

    public LocalDate getdata(){
        return this.data;
    }
    public String getnome(){
        return this.nome;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Colecao clone(){
        return new Colecao(this);
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Colecao col = (Colecao) o;
        return (Objects.equals(getnome(), col.getnome())
                &&  this.data==getdata());
    }
    @Override
    public String toString() {
        return "Colecao{" +
                "nome='" + nome + '\'' +
                ", data=" + data +
                '}';
    }
}
