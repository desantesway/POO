import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello world!\n");
        Transportadoras t1 = new Transportadoras(3,4,10,2);
        Colecao inverno=new Colecao("Inverno", LocalDate.now());
        Artigo teste=new Artigo(false,true,"Mt usado",3,"Artigo monstro","Adidas",35,40,15,inverno,"15x53x12",t1);
        teste.publicar();
        System.out.printf("Publicado = "+ teste.isPublicado());
        System.out.printf("\nPremium = "+ teste.isPremium());
        System.out.printf("\nEstado = "+ teste.getEstado());
        System.out.printf("\nID = "+ teste.getID());
        Utilizador Joao=new Utilizador("joaorpg2015@gmail.com","Joao","Ruas das andorinhas","123456789");
        System.out.printf("\nMorada = "+ Joao.getMorada());
        System.out.printf("\nEmail = "+ Joao.getEmail());
        Joao.adicionarVendaEfetuada(teste);
        System.out.printf("\nVendasEfetuadas = "+ Joao.getvendasEfetuadas());
        System.out.printf("\nID = "+ Joao.getID());
    }
}