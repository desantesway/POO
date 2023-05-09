public class Tamanhos {

    private double pequeno, medio, grande;

    public Tamanhos(Tamanhos o){
        this.pequeno = o.getPequeno();
        this.medio = o.getMedio();
        this.grande = o.getGrande();
    }

    public Tamanhos(double left, double middle, double right) {
        this.pequeno = left;
        this.medio = middle;
        this.grande = right;
    }

    public Tamanhos(double d) {
        this.pequeno = d;
        this.medio = d;
        this.grande = d;
    }

    public Tamanhos() {
        this.pequeno = -1.0;
        this.medio = -1.0;
        this.grande = -1.0;
    }

    public double get(int i){
        if (i <= 0){
            return this.getPequeno();
        } else if (i == 1){
            return this.getMedio();
        } else{
            return this.getGrande();
        }
    }

    public void set(int i, double d){
        if (i <= 0){
            this.setPequeno(d);
        } else if (i == 1){
            this.setMedio(d);
        } else{
            this.setGrande(d);
        }
    }

    public void setPequeno(double pequeno) {
        this.pequeno = pequeno;
    }

    public void setMedio(double medio) {
        this.medio = medio;
    }

    public void setGrande(double grande) {
        this.grande = grande;
    }

    public double getPequeno() {
        return pequeno;
    }

    public double getMedio() {
        return medio;
    }

    public double getGrande() {
        return grande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tamanhos that = (Tamanhos) o;
        return Double.compare(that.getPequeno(), getPequeno()) == 0
                && Double.compare(that.getMedio(), getMedio()) == 0
                && Double.compare(that.getGrande(), getGrande()) == 0;

    }

    public Tamanhos clone(){
        return new Tamanhos(this);
    }

    @Override
    public String toString() {
        return "Tamanhos{" +
                "pequeno=" + pequeno +
                ", medio=" + medio +
                ", grande=" + grande +
                '}';
    }

    public static Tamanhos fromString(String tamanhosString) {
        String[] values = tamanhosString.split(",");
        if (values.length != 3) {
            throw new IllegalArgumentException("Invalid Tamanhos string format: " + tamanhosString);
        }
        double pequeno = Double.parseDouble(values[0]);
        double medio = Double.parseDouble(values[1]);
        double grande = Double.parseDouble(values[2]);
        return new Tamanhos(pequeno, medio, grande);
    }
}
