import java.time.LocalDate;

public class Colecao {
    private String nome;
    private LocalDate data;

    public Colecao(){
        this.nome="";
        this.data=LocalDate.now();
    }
    public Colecao(String nome,LocalDate data){
        this.nome=nome;
        this.data=data;
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
        return (this.nome ==col.getnome() &&  this.data==getdata());
    }
}
