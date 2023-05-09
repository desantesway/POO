public class Main {
    public static void main(String[] args) {

        Transportadoras t1 = new Transportadoras(3,4,10,2);
        t1.formula("1 + (valor * (2 + imposto))");
        t1.ativaPremium();
        t1.formulaPremium("10 + (valor * (2 + imposto))");
        System.out.println(t1.getPrecoExp()+ " " +t1.getPrecoPremium());
    }
}