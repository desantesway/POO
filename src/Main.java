public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello world!\n");

        Transportadoras t1 = new Transportadoras(2,4,10,2);
        t1.ativaPremium();
        t1.setProfit(3);
        t1.premium("medio");
        System.out.printf("Preço = "+ t1.getPrecoPremium());
    }
}