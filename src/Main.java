public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello world!\n");

        Transportadoras t1 = new Transportadoras(3,4,10,2);
        t1.ativaPremium();
        t1.desativaPremium();
        System.out.printf("Preço = "+ t1.getPremium());
    }
}