public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello world!\n");

        Transportadoras t1 = new Transportadoras(3,4,10,2);
        t1.setPrecoPremium(t1.ari(t1.ari(t1.ari(t1.getValorBase(), "+", 2),"*", 3),"+",t1.ari(3,"+",2)));
        System.out.printf("Preço = "+ t1.getPrecoPremium().getGrande());
    }
}